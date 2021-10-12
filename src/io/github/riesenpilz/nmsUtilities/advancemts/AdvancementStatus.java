package io.github.riesenpilz.nmsUtilities.advancemts;

import org.apache.commons.lang.Validate;

import io.github.riesenpilz.nmsUtilities.packet.playIn.PacketPlayInAdvancementsEvent;
import net.minecraft.server.v1_16_R3.PacketPlayInAdvancements;

/**
 * Represents {@link PacketPlayInAdvancements.Status}. Only used by packets.
 * 
 * @see PacketPlayInAdvancementsEvent
 *
 */
public enum AdvancementStatus {
	OPENED_TAB(PacketPlayInAdvancements.Status.OPENED_TAB),
	CLOSED_SCREEN(PacketPlayInAdvancements.Status.CLOSED_SCREEN);

	private final PacketPlayInAdvancements.Status nms;

	private AdvancementStatus(PacketPlayInAdvancements.Status nms) {
		this.nms = nms;
	}

	public PacketPlayInAdvancements.Status getNMS() {
		return nms;
	}

	public static AdvancementStatus getAdvancementStatus(PacketPlayInAdvancements.Status nms) {
		Validate.notNull(nms);
		for (AdvancementStatus status : values())
			if (status.getNMS().equals(nms))
				return status;
		throw new IllegalArgumentException();
	}

}
