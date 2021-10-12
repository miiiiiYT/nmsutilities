package io.github.riesenpilz.nmsUtilities.advancemts;

import javax.annotation.Nullable;

import org.apache.commons.lang.Validate;
import org.bukkit.NamespacedKey;

import com.google.gson.JsonObject;

import io.github.riesenpilz.nmsUtilities.packet.PacketUtils;
import io.github.riesenpilz.nmsUtilities.packet.playOut.PacketPlayOutAdvancementsEvent;
import io.github.riesenpilz.nmsUtilities.reflections.Field;
import net.minecraft.server.v1_16_R3.CriterionInstance;
import net.minecraft.server.v1_16_R3.CriterionTriggers;
import net.minecraft.server.v1_16_R3.LootSerializationContext;
import net.minecraft.server.v1_16_R3.MinecraftKey;

/**
 * Represents a {@link net.minecraft.server.v1_16_R3.Criterion}. Only used by
 * packets.
 * 
 * @see PacketPlayOutAdvancementsEvent
 */
public class Criterion {

	/**
	 * @see CriterionTriggers
	 */
	@Nullable
	private NamespacedKey trigger;

	@Nullable
	private JsonObject conditions;

	public Criterion(NamespacedKey trigger, JsonObject conditions) {

		Validate.notNull(trigger);
		Validate.notNull(conditions);

		this.trigger = trigger;
		this.conditions = conditions;
	}

	public NamespacedKey getTrigger() {
		return trigger;
	}

	public void setTrigger(NamespacedKey trigger) {
		Validate.notNull(trigger);
		this.trigger = trigger;
	}

	public JsonObject getConditions() {
		return conditions;
	}

	public void setConditions(JsonObject conditions) {
		Validate.notNull(conditions);
		this.conditions = conditions;
	}

	public static Criterion getCriterionOf(net.minecraft.server.v1_16_R3.Criterion criterion) {
		Validate.notNull(criterion);
		Validate.notNull(criterion.a());
		return new Criterion(PacketUtils.toNamespacedKey(criterion.a().a()),
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
