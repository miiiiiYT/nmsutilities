package io.github.riesenpilz.nmsUtilities.packet.playOut;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;

import com.mojang.datafixers.util.Pair;

import io.github.riesenpilz.nmsUtilities.inventory.RecipeBookType;
import io.github.riesenpilz.nmsUtilities.nbt.NBTTag;
import io.github.riesenpilz.nmsUtilities.reflections.Field;
import net.minecraft.server.v1_16_R3.MinecraftKey;
import net.minecraft.server.v1_16_R3.NBTTagCompound;
import net.minecraft.server.v1_16_R3.Packet;
import net.minecraft.server.v1_16_R3.PacketListenerPlayOut;
import net.minecraft.server.v1_16_R3.PacketPlayOutRecipes;
import net.minecraft.server.v1_16_R3.RecipeBookSettings;

/**
 * https://wiki.vg/Protocol#Unlock_Recipes
 * <p>
 * Packet ID: 0x39<br>
 * State: Play<br>
 * Bound To: Client
 * 
 * @author Martin
 *
 */
public class PacketPlayOutRecipesUnlockEvent extends PacketPlayOutEvent {

	private Action action;
	private List<NamespacedKey> recipeIds;

	/**
	 * Only present if {@link PacketPlayOutRecipesUnlockEvent#a} is INIT
	 */
	private List<NamespacedKey> recipeIds1;
	private Set<RecipeBookSetting> settings;

	@SuppressWarnings({ "deprecation", "unchecked" })
	public PacketPlayOutRecipesUnlockEvent(Player injectedPlayer, PacketPlayOutRecipes packet) {
		super(injectedPlayer);
		action = Action.getAction(Field.get(packet, "a", PacketPlayOutRecipes.Action.class));
		List<MinecraftKey> nmsRecipeIds = Field.get(packet, "b", List.class);
		recipeIds = new ArrayList<>();
		for (MinecraftKey minecraftKey : nmsRecipeIds)
			recipeIds.add(new NamespacedKey(minecraftKey.getNamespace(), minecraftKey.getKey()));
		List<MinecraftKey> nmsRecipeIds1 = Field.get(packet, "c", List.class);
		recipeIds1 = new ArrayList<>();
		for (MinecraftKey minecraftKey : nmsRecipeIds1)
			recipeIds1.add(new NamespacedKey(minecraftKey.getNamespace(), minecraftKey.getKey()));
		settings = RecipeBookSetting.getRecipeBookSettingsOf(Field.get(packet, "d", RecipeBookSettings.class));
	}

	@Override
	public Packet<PacketListenerPlayOut> getNMS() {

		List<MinecraftKey> nmsRecipeIds = new ArrayList<>();
		for (NamespacedKey namespacedKey : recipeIds)
			nmsRecipeIds.add(new MinecraftKey(namespacedKey.getNamespace(), namespacedKey.getKey()));

		List<MinecraftKey> nmsRecipeIds1 = new ArrayList<>();
		for (NamespacedKey namespacedKey : recipeIds1)
			nmsRecipeIds1.add(new MinecraftKey(namespacedKey.getNamespace(), namespacedKey.getKey()));

		return new PacketPlayOutRecipes(action.getNMS(), nmsRecipeIds, nmsRecipeIds1,
				RecipeBookSetting.getNMS(settings));
	}

	public PacketPlayOutRecipesUnlockEvent(Player injectedPlayer, Action action, List<NamespacedKey> recipeIds,
			List<NamespacedKey> recipeIds1, Set<RecipeBookSetting> settings) {
		super(injectedPlayer);
		this.action = action;
		this.recipeIds = recipeIds;
		this.recipeIds1 = recipeIds1;
		this.settings = settings;
	}

	public Action getAction() {
		return action;
	}

	public List<NamespacedKey> getRecipeIds() {
		return recipeIds;
	}

	public List<NamespacedKey> getRecipeIds1() {
		return recipeIds1;
	}

	public Set<RecipeBookSetting> getSettings() {
		return settings;
	}

	@Override
	public int getPacketID() {
		return 0x39;
	}

	@Override
	public String getProtocolURLString() {
		return "https://wiki.vg/Protocol#Unlock_Recipes";
	}

	public static class RecipeBookSetting {

		private RecipeBookType type;
		private boolean open;
		private boolean filtering;

		public RecipeBookSetting(RecipeBookType type, boolean open, boolean filtering) {
			this.type = type;
			this.open = open;
			this.filtering = filtering;
		}

		public static RecipeBookSettings getNMS(Set<RecipeBookSetting> recipeBookSettings) {
			final RecipeBookSettings nms = new RecipeBookSettings();
			for (RecipeBookSetting setting : recipeBookSettings) {
				if (setting.open)
					nms.a(setting.type.getNMS(), true);
				if (setting.filtering)
					nms.a(setting.type.getNMS(), true);
			}
			return nms;
		}

		public static Set<RecipeBookSetting> getRecipeBookSettingsOf(RecipeBookSettings nms) {
			Set<RecipeBookSetting> recipeBookSettings = new HashSet<>();
			@SuppressWarnings("unchecked")
			final Map<net.minecraft.server.v1_16_R3.RecipeBookType, Pair<String, String>> keys = Field
					.getConstant(RecipeBookSettings.class, "a", Map.class);

			final NBTTagCompound nmsTag = new NBTTagCompound();
			nms.b(nmsTag);
			final NBTTag tag = NBTTag.getNBTTagOf(nmsTag);

			keys.forEach((type, pair) -> {
				recipeBookSettings.add(new RecipeBookSetting(RecipeBookType.getRecipeBookType(type),
						tag.getBoolean(pair.getFirst()), tag.getBoolean(pair.getSecond())));
			});
			return recipeBookSettings;
		}
	}

	public enum Action {
		/**
		 * All the recipes in list 1 will be tagged as displayed, and all the recipes in
		 * list 2 will be added to the recipe book. Recipes that aren't tagged will be
		 * shown in the notification.
		 */
		INIT(net.minecraft.server.v1_16_R3.PacketPlayOutRecipes.Action.INIT),

		/**
		 * All the recipes in the list are added to the recipe book and their icons will
		 * be shown in the notification.
		 */
		ADD(net.minecraft.server.v1_16_R3.PacketPlayOutRecipes.Action.ADD),

		/**
		 * Remove all the recipes in the list. This allows them to be re-displayed when
		 * they are re-added.
		 */
		REMOVE(net.minecraft.server.v1_16_R3.PacketPlayOutRecipes.Action.REMOVE);

		private net.minecraft.server.v1_16_R3.PacketPlayOutRecipes.Action nms;

		private Action(net.minecraft.server.v1_16_R3.PacketPlayOutRecipes.Action nms) {
			this.nms = nms;
		}

		public net.minecraft.server.v1_16_R3.PacketPlayOutRecipes.Action getNMS() {
			return nms;
		}

		public static Action getAction(net.minecraft.server.v1_16_R3.PacketPlayOutRecipes.Action nms) {
			for (Action action : values())
				if (action.getNMS().equals(nms))
					return action;
			return null;
		}
	}
}
