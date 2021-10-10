package io.github.riesenpilz.nmsUtilities.advancemts;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Nullable;

import org.apache.commons.lang.Validate;
import org.bukkit.NamespacedKey;

import io.github.riesenpilz.nmsUtilities.packet.PacketUtils;
import io.github.riesenpilz.nmsUtilities.reflections.Field;
import net.minecraft.server.v1_16_R3.Advancement.SerializedAdvancement;
import net.minecraft.server.v1_16_R3.AdvancementDisplay;
import net.minecraft.server.v1_16_R3.AdvancementRewards;
import net.minecraft.server.v1_16_R3.MinecraftKey;

/**
 * Represents a {@link SerializedAdvancement}. Only used by packets.
 * 
 * @see PacketPlayOutAdvancementsEvent
 *
 */
public class Advancement {
	@Nullable
	private NamespacedKey parent;

	@Nullable
	private Display display;
	private Rewards rewards;
	private Map<String, Criterion> criteria;
	private String[][] requirements;

	public Advancement(@Nullable NamespacedKey parent, @Nullable Display display, Rewards rewards,
			Map<String, Criterion> criteria, String[][] requirements) {

		Validate.notNull(rewards);
		Validate.notNull(criteria);
		Validate.notNull(requirements);

		this.parent = parent;
		this.display = display;
		this.rewards = rewards;
		this.criteria = criteria;
		this.requirements = requirements;
	}

	protected Advancement(SerializedAdvancement nms) {

		Validate.notNull(nms);

		@SuppressWarnings("unchecked")
		final Map<String, net.minecraft.server.v1_16_R3.Criterion> nmsCriteria = Field.get(nms, "e", Map.class);
		criteria = new HashMap<>();
		for (Entry<String, net.minecraft.server.v1_16_R3.Criterion> entry : nmsCriteria.entrySet())
			criteria.put(entry.getKey(), Criterion.getCriterionOf(entry.getValue()));

		final MinecraftKey nmsParent = Field.get(nms, "a", MinecraftKey.class);
		parent = nmsParent == null ? null : PacketUtils.toNamespacedKey(nmsParent);

		final AdvancementDisplay nmsDisplay = Field.get(nms, "c", AdvancementDisplay.class);
		display = nmsDisplay == null ? null : Display.getDisplayOf(nmsDisplay);

		rewards = Rewards.getRewardsOf(Field.get(nms, "d", AdvancementRewards.class));
		requirements = Field.get(nms, "f", String[][].class);
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
		Validate.notNull(rewards);
		this.rewards = rewards;
	}

	public Map<String, Criterion> getCriteria() {
		return criteria;
	}

	public void setCriteria(Map<String, Criterion> criteria) {
		Validate.notNull(criteria);
		this.criteria = criteria;
	}

	public String[][] getRequirements() {
		return requirements;
	}

	public void setRequirements(String[][] requirements) {
		Validate.notNull(requirements);
		this.requirements = requirements;
	}

	public static Advancement getAdvancementOf(SerializedAdvancement nms) {
		return new Advancement(nms);
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