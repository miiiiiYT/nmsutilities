package io.github.riesenpilz.nmsUtilities.packet.playOut;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.annotation.Nullable;

import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;

import com.google.gson.JsonObject;

import io.github.riesenpilz.nmsUtilities.inventory.ItemStack;
import io.github.riesenpilz.nmsUtilities.packet.PacketUtils;
import io.github.riesenpilz.nmsUtilities.reflections.Field;
import net.minecraft.server.v1_16_R3.Advancement.SerializedAdvancement;
import net.minecraft.server.v1_16_R3.AdvancementDisplay;
import net.minecraft.server.v1_16_R3.AdvancementRewards;
import net.minecraft.server.v1_16_R3.CriterionInstance;
import net.minecraft.server.v1_16_R3.CriterionProgress;
import net.minecraft.server.v1_16_R3.CustomFunction;
import net.minecraft.server.v1_16_R3.CustomFunction.a;
import net.minecraft.server.v1_16_R3.IChatBaseComponent;
import net.minecraft.server.v1_16_R3.LootSerializationContext;
import net.minecraft.server.v1_16_R3.MinecraftKey;
import net.minecraft.server.v1_16_R3.Packet;
import net.minecraft.server.v1_16_R3.PacketListenerPlayOut;
import net.minecraft.server.v1_16_R3.PacketPlayOutAdvancements;

/**
 * https://wiki.vg/Protocol#Advancements
 * <p>
 * Packet ID: 0x62<br>
 * State: Play<br>
 * Bound To: Client
 * 
 * @author Martin
 *
 */
public class PacketPlayOutAdvancementsEvent extends PacketPlayOutEvent {

	/**
	 * Whether to reset/clear the current advancements.
	 */
	private boolean reset;
	private Map<NamespacedKey, Advancement> advancements;

	/**
	 * The identifiers of the advancements that should be removed.
	 */
	private Set<NamespacedKey> identifiers;
	private Map<NamespacedKey, AdvancementProgress> progresses;

	@SuppressWarnings("unchecked")
	public PacketPlayOutAdvancementsEvent(Player injectedPlayer, PacketPlayOutAdvancements packet) {
		super(injectedPlayer);

		reset = Field.get(packet, "a", boolean.class);

		advancements = new HashMap<>();
		identifiers = new HashSet<>();
		progresses = new HashMap<>();

		final Map<MinecraftKey, SerializedAdvancement> nmsAdvancements = Field.get(packet, "b", Map.class);
		for (Entry<MinecraftKey, SerializedAdvancement> entry : nmsAdvancements.entrySet())
			advancements.put(PacketUtils.toNamespacedKey(entry.getKey()),
					Advancement.getAdvancementOf(entry.getValue()));

		final Set<MinecraftKey> nmsIdentifiers = Field.get(packet, "c", Set.class);
		for (MinecraftKey minecraftKey : nmsIdentifiers)
			identifiers.add(PacketUtils.toNamespacedKey(minecraftKey));

		final Map<MinecraftKey, net.minecraft.server.v1_16_R3.AdvancementProgress> nmsProgresses = Field.get(packet,
				"d", Map.class);
		for (Entry<MinecraftKey, net.minecraft.server.v1_16_R3.AdvancementProgress> entry : nmsProgresses.entrySet())
			progresses.put(PacketUtils.toNamespacedKey(entry.getKey()),
					AdvancementProgress.getAdvancementProgressFrom(entry.getValue()));
	}

	public PacketPlayOutAdvancementsEvent(Player injectedPlayer, boolean reset,
			Map<NamespacedKey, Advancement> advancements, Set<NamespacedKey> identifiers,
			Map<NamespacedKey, AdvancementProgress> progresses) {
		super(injectedPlayer);
		this.reset = reset;
		this.advancements = advancements;
		this.identifiers = identifiers;
		this.progresses = progresses;
	}

	public boolean isReset() {
		return reset;
	}

	public Map<NamespacedKey, Advancement> getAdvancements() {
		return advancements;
	}

	public Set<NamespacedKey> getIdentifiers() {
		return identifiers;
	}

	public Map<NamespacedKey, AdvancementProgress> getProgresses() {
		return progresses;
	}

	@Override
	public Packet<PacketListenerPlayOut> getNMS() {

		final Map<MinecraftKey, SerializedAdvancement> nmsAdvancements = new HashMap<>();
		final Set<MinecraftKey> nmsIdentifiers = new HashSet<>();
		final Map<MinecraftKey, net.minecraft.server.v1_16_R3.AdvancementProgress> nmsProgresses = new HashMap<>();

		for (Entry<NamespacedKey, Advancement> entry : advancements.entrySet())
			nmsAdvancements.put(PacketUtils.toMinecraftKey(entry.getKey()), entry.getValue().getNMS());

		for (NamespacedKey namespacedKey : identifiers)
			nmsIdentifiers.add(PacketUtils.toMinecraftKey(namespacedKey));

		for (Entry<NamespacedKey, AdvancementProgress> entry : progresses.entrySet())
			nmsProgresses.put(PacketUtils.toMinecraftKey(entry.getKey()), entry.getValue().getNMS());

		final PacketPlayOutAdvancements packet = new PacketPlayOutAdvancements();
		return packet;
	}

	@Override
	public int getPacketID() {
		return 0x62;
	}

	@Override
	public String getProtocolURLString() {
		return "https://wiki.vg/Protocol#Advancements";
	}

	public static class AdvancementProgress {

		private Map<String, Date> progress = new HashMap<>();

		private String[][] requirements = new String[0][];

		public AdvancementProgress(Map<String, Date> progress, String[][] requirements) {
			this.progress = progress;
			this.requirements = requirements;
		}

		public static AdvancementProgress getAdvancementProgressFrom(
				net.minecraft.server.v1_16_R3.AdvancementProgress nms) {
			@SuppressWarnings("unchecked")
			Map<String, CriterionProgress> nmsCriterionProgresses = Field.get(nms, "a", Map.class);
			Map<String, Date> progress = new HashMap<>();
			for (Entry<String, CriterionProgress> entry : nmsCriterionProgresses.entrySet())
				progress.put(entry.getKey(), entry.getValue().getDate());
			return new AdvancementProgress(null, Field.get(nms, "b", String[][].class));
		}

		@SuppressWarnings("unchecked")
		public net.minecraft.server.v1_16_R3.AdvancementProgress getNMS() {
			final net.minecraft.server.v1_16_R3.AdvancementProgress nms = new net.minecraft.server.v1_16_R3.AdvancementProgress();
			Map<String, CriterionProgress> nmsCriterionProgresses = new HashMap<>();
			for (Entry<String, Date> entry : progress.entrySet()) {
				final CriterionProgress value = new CriterionProgress();
				Field.set(value, "b", entry.getValue());
				nmsCriterionProgresses.put(entry.getKey(), value);
			}
			Field.get(nms, "a", Map.class).putAll(nmsCriterionProgresses);
			Field.set(nms, "b", requirements);
			return nms;
		}
	}

	public static class Advancement {
		@Nullable
		private NamespacedKey parent;

		@Nullable
		private Display display;
		private Rewards rewards;
		private Map<String, Criterion> criteria;
		private String[][] requirements;

		public Advancement(@Nullable NamespacedKey parent, @Nullable Display display, Rewards rewards,
				Map<String, Criterion> criteria, String[][] requirements) {
			this.parent = parent;
			this.display = display;
			this.rewards = rewards;
			this.criteria = criteria;
			this.requirements = requirements;
		}

		@Nullable
		public NamespacedKey getParent() {
			return parent;
		}

		public void setParent(@Nullable NamespacedKey parent) {
			this.parent = parent;
		}

		@Nullable
		public Display getDisplay() {
			return display;
		}

		public void setDisplay(@Nullable Display display) {
			this.display = display;
		}

		public Rewards getRewards() {
			return rewards;
		}

		public void setRewards(Rewards rewards) {
			this.rewards = rewards;
		}

		public Map<String, Criterion> getCriteria() {
			return criteria;
		}

		public void setCriteria(Map<String, Criterion> criteria) {
			this.criteria = criteria;
		}

		public String[][] getRequirements() {
			return requirements;
		}

		public void setRequirements(String[][] requirements) {
			this.requirements = requirements;
		}

		@SuppressWarnings("unchecked")
		public static Advancement getAdvancementOf(SerializedAdvancement nms) {
			final Map<String, net.minecraft.server.v1_16_R3.Criterion> nmsCriteria = Field.get(nms, "e", Map.class);
			final Map<String, Criterion> criteria = new HashMap<>();
			for (Entry<String, net.minecraft.server.v1_16_R3.Criterion> entry : nmsCriteria.entrySet())
				criteria.put(entry.getKey(), Criterion.getCriterionOf(entry.getValue()));

			final AdvancementDisplay nmsDisplay = Field.get(nms, "c", AdvancementDisplay.class);
			final MinecraftKey nmsParent = Field.get(nms, "a", MinecraftKey.class);
			return new Advancement(nmsParent == null ? null : PacketUtils.toNamespacedKey(nmsParent),
					nmsDisplay == null ? null : Display.getDisplayOf(nmsDisplay),
					Rewards.getRewardsOf(Field.get(nms, "d", AdvancementRewards.class)), criteria,
					Field.get(nms, "f", String[][].class));
		}

		public SerializedAdvancement getNMS() {
			Map<String, net.minecraft.server.v1_16_R3.Criterion> nmsCriteria = new HashMap<>();
			for (Entry<String, Criterion> entry : criteria.entrySet())
				nmsCriteria.put(entry.getKey(), entry.getValue().getNMS());
			final SerializedAdvancement nms = new net.minecraft.server.v1_16_R3.Advancement(null, null,
					display == null ? null : display.getNMS(), rewards.getNMS(), nmsCriteria, requirements).a();
			if (parent == null)
				Field.set(nms, "a", PacketUtils.toMinecraftKey(parent));
			return nms;
		}

	}

	public static class Display {
		private IChatBaseComponent title;
		private IChatBaseComponent description;
		private ItemStack icon;

		@Nullable
		private NamespacedKey background;
		private AdvancementFrameType frame;
		private boolean showToast;
		private boolean announceToChat;
		private boolean hidden;
		private float x;
		private float y;

		public Display(IChatBaseComponent title, IChatBaseComponent description, ItemStack icon,
				@Nullable NamespacedKey background, AdvancementFrameType frame, boolean showToast,
				boolean announceToChat, boolean hidden, float x, float y) {
			this.title = title;
			this.description = description;
			this.icon = icon;
			this.background = background;
			this.frame = frame;
			this.showToast = showToast;
			this.announceToChat = announceToChat;
			this.hidden = hidden;
			this.x = x;
			this.y = y;
		}

		public IChatBaseComponent getTitle() {
			return title;
		}

		public void setTitle(IChatBaseComponent title) {
			this.title = title;
		}

		public IChatBaseComponent getDescription() {
			return description;
		}

		public void setDescription(IChatBaseComponent description) {
			this.description = description;
		}

		public ItemStack getIcon() {
			return icon;
		}

		public void setIcon(ItemStack icon) {
			this.icon = icon;
		}

		@Nullable
		public NamespacedKey getBackground() {
			return background;
		}

		public void setBackground(@Nullable NamespacedKey background) {
			this.background = background;
		}

		public AdvancementFrameType getFrame() {
			return frame;
		}

		public void setFrame(AdvancementFrameType frame) {
			this.frame = frame;
		}

		public boolean isShowToast() {
			return showToast;
		}

		public void setShowToast(boolean showToast) {
			this.showToast = showToast;
		}

		public boolean isAnnounceToChat() {
			return announceToChat;
		}

		public void setAnnounceToChat(boolean announceToChat) {
			this.announceToChat = announceToChat;
		}

		public boolean isHidden() {
			return hidden;
		}

		public void setHidden(boolean hidden) {
			this.hidden = hidden;
		}

		public float getX() {
			return x;
		}

		public void setX(float x) {
			this.x = x;
		}

		public float getY() {
			return y;
		}

		public void setY(float y) {
			this.y = y;
		}

		public static Display getDisplayOf(AdvancementDisplay nms) {
			final MinecraftKey nmsBackGground = Field.get(nms, "d", MinecraftKey.class);
			return new Display(nms.a(), nms.b(),
					ItemStack.getItemStackOf(Field.get(nms, "c", net.minecraft.server.v1_16_R3.ItemStack.class)),
					nmsBackGground == null ? null : PacketUtils.toNamespacedKey(nmsBackGground),
					AdvancementFrameType.getAdvancementFrameTypeOf(nms.e()), Field.get(nms, "f", boolean.class),
					nms.i(), nms.j(), Field.get(nms, "i", float.class), Field.get(nms, "j", float.class));
		}

		public AdvancementDisplay getNMS() {
			final AdvancementDisplay nms = new AdvancementDisplay(icon.getNMS(), title, description,
					background == null ? null : PacketUtils.toMinecraftKey(background), frame.getNMS(), showToast,
					announceToChat, hidden);
			nms.a(x, y);
			return nms;
		}

	}

	public static class Rewards {
		private int xp;
		private NamespacedKey[] loot;
		private NamespacedKey[] recipes;
		private CustomFunction.a function;

		public Rewards(int xp, NamespacedKey[] loot, NamespacedKey[] recipes, a function) {
			super();
			this.xp = xp;
			this.loot = loot;
			this.recipes = recipes;
			this.function = function;
		}

		public int getXp() {
			return xp;
		}

		public void setXp(int xp) {
			this.xp = xp;
		}

		public NamespacedKey[] getLoot() {
			return loot;
		}

		public void setLoot(NamespacedKey[] loot) {
			this.loot = loot;
		}

		public NamespacedKey[] getRecipes() {
			return recipes;
		}

		public void setRecipes(NamespacedKey[] recipes) {
			this.recipes = recipes;
		}

		public CustomFunction.a getFunction() {
			return function;
		}

		public void setFunction(CustomFunction.a function) {
			this.function = function;
		}

		public static Rewards getRewardsOf(AdvancementRewards nms) {
			MinecraftKey[] nmsLoot = Field.get(nms, "c", MinecraftKey[].class);
			MinecraftKey[] nmsRecipes = Field.get(nms, "d", MinecraftKey[].class);
			NamespacedKey[] loot = new NamespacedKey[nmsLoot.length];
			NamespacedKey[] recipes = new NamespacedKey[nmsRecipes.length];

			for (int i = 0; i < nmsLoot.length; i++)
				loot[i] = PacketUtils.toNamespacedKey(nmsLoot[i]);
			for (int i = 0; i < nmsRecipes.length; i++)
				recipes[i] = PacketUtils.toNamespacedKey(nmsRecipes[i]);

			return new Rewards(Field.get(nms, "b", int.class), loot, recipes,
					Field.get(nms, "e", CustomFunction.a.class));
		}

		public AdvancementRewards getNMS() {
			MinecraftKey[] nmsLoot = new MinecraftKey[loot.length];
			MinecraftKey[] nmsRecipes = new MinecraftKey[recipes.length];

			for (int i = 0; i < loot.length; i++)
				nmsLoot[i] = PacketUtils.toMinecraftKey(loot[i]);
			for (int i = 0; i < recipes.length; i++)
				nmsRecipes[i] = PacketUtils.toMinecraftKey(recipes[i]);

			return new AdvancementRewards(xp, nmsLoot, nmsRecipes, function);
		}
	}

	public static class Criterion {
		/**
		 * @see CriterionTriggers
		 */
		@Nullable
		private NamespacedKey trigger;

		@Nullable
		private JsonObject conditions;

		public Criterion(NamespacedKey trigger, JsonObject conditions) {
			this.trigger = trigger;
			this.conditions = conditions;
		}

		public NamespacedKey getTrigger() {
			return trigger;
		}

		public void setTrigger(NamespacedKey trigger) {
			this.trigger = trigger;
		}

		public JsonObject getConditions() {
			return conditions;
		}

		public void setConditions(JsonObject conditions) {
			this.conditions = conditions;
		}

		public static Criterion getCriterionOf(net.minecraft.server.v1_16_R3.Criterion criterion) {

			return criterion.a() == null ? new Criterion(null, null)
					: new Criterion(PacketUtils.toNamespacedKey(criterion.a().a()),
							criterion.a().a(LootSerializationContext.a));
		}

		public net.minecraft.server.v1_16_R3.Criterion getNMS() {
			final net.minecraft.server.v1_16_R3.Criterion nms = new net.minecraft.server.v1_16_R3.Criterion();
			Field.set(nms, "a", new CriterionInstance() {

				@Override
				public JsonObject a(LootSerializationContext ctx) {
					return conditions;
				}

				@Override
				public MinecraftKey a() {
					return PacketUtils.toMinecraftKey(trigger);
				}
			});
			return nms;
		}

	}

	public enum AdvancementFrameType {
		TASK(net.minecraft.server.v1_16_R3.AdvancementFrameType.TASK),
		CHALLENGE(net.minecraft.server.v1_16_R3.AdvancementFrameType.CHALLENGE),
		GOAL(net.minecraft.server.v1_16_R3.AdvancementFrameType.GOAL);

		private net.minecraft.server.v1_16_R3.AdvancementFrameType nms;

		private AdvancementFrameType(net.minecraft.server.v1_16_R3.AdvancementFrameType nms) {
			this.nms = nms;
		}

		public net.minecraft.server.v1_16_R3.AdvancementFrameType getNMS() {
			return nms;
		}

		public static AdvancementFrameType getAdvancementFrameTypeOf(
				net.minecraft.server.v1_16_R3.AdvancementFrameType nms) {
			for (AdvancementFrameType type : values())
				if (type.getNMS().equals(nms))
					return type;
			return null;
		}
	}
}
