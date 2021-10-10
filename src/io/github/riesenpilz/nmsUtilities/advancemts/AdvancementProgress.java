package io.github.riesenpilz.nmsUtilities.advancemts;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.Validate;

import io.github.riesenpilz.nmsUtilities.reflections.Field;
import net.minecraft.server.v1_16_R3.CriterionProgress;

/**
 * Represents {@link net.minecraft.server.v1_16_R3.AdvancementProgress}. Only
 * used by packets.
 * 
 * @see net.minecraft.server.v1_16_R3.AdvancementProgress
 *
 */
public class AdvancementProgress {

	private Map<String, Date> progress;

	private String[][] requirements;

	public AdvancementProgress(Map<String, Date> progress, String[][] requirements) {
		Validate.notNull(progress);
		Validate.notNull(requirements);
		this.progress = progress;
		this.requirements = requirements;
	}

	protected AdvancementProgress(net.minecraft.server.v1_16_R3.AdvancementProgress nms) {
		Validate.notNull(nms);

		@SuppressWarnings("unchecked")
		Map<String, CriterionProgress> nmsCriterionProgresses = Field.get(nms, "a", Map.class);
		progress = new HashMap<>();
		for (Entry<String, CriterionProgress> entry : nmsCriterionProgresses.entrySet())
			progress.put(entry.getKey(), entry.getValue().getDate());

		requirements = Field.get(nms, "b", String[][].class);
	}

	public Map<String, Date> getProgress() {
		return progress;
	}

	public void setProgress(Map<String, Date> progress) {
		Validate.notNull(progress);
		this.progress = progress;
	}

	public String[][] getRequirements() {
		return requirements;
	}

	public void setRequirements(String[][] requirements) {
		Validate.notNull(requirements);
		this.requirements = requirements;
	}

	public static AdvancementProgress getAdvancementProgress(net.minecraft.server.v1_16_R3.AdvancementProgress nms) {
		return new AdvancementProgress(nms);
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
