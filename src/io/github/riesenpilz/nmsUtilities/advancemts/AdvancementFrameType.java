package io.github.riesenpilz.nmsUtilities.advancemts;

import org.apache.commons.lang.Validate;

/**
 * Represents {@link net.minecraft.server.v1_16_R3.AdvancementFrameType}. Only
 * used by packets.
 *
 * @see PacketPlayOutAdvancementsEvent
 */
public enum AdvancementFrameType {
	TASK(net.minecraft.server.v1_16_R3.AdvancementFrameType.TASK),
	CHALLENGE(net.minecraft.server.v1_16_R3.AdvancementFrameType.CHALLENGE),
	GOAL(net.minecraft.server.v1_16_R3.AdvancementFrameType.GOAL);

	private final net.minecraft.server.v1_16_R3.AdvancementFrameType nms;

	private AdvancementFrameType(net.minecraft.server.v1_16_R3.AdvancementFrameType nms) {
		this.nms = nms;
	}

	public net.minecraft.server.v1_16_R3.AdvancementFrameType getNMS() {
		return nms;
	}

	public static AdvancementFrameType getAdvancementFrameTypeOf(
			net.minecraft.server.v1_16_R3.AdvancementFrameType nms) {
		Validate.notNull(nms);
		for (AdvancementFrameType type : values())
			if (type.getNMS().equals(nms))
				return type;
		throw new IllegalArgumentException();
	}
}
