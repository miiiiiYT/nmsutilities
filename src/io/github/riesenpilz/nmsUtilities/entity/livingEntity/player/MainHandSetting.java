package io.github.riesenpilz.nmsUtilities.entity.livingEntity.player;

import org.apache.commons.lang.Validate;

import io.github.riesenpilz.nmsUtilities.packet.playIn.PacketPlayInSettingsEvent;
import net.minecraft.server.v1_16_R3.EnumMainHand;

/**
 * Represents {@link MainHandSetting}. Only used by packets.
 * 
 * @see PacketPlayInSettingsEvent
 */
public enum MainHandSetting {
	LEFT(EnumMainHand.LEFT), RIGHT(EnumMainHand.RIGHT);

	private final EnumMainHand nms;

	private MainHandSetting(EnumMainHand nms) {
		this.nms = nms;
	}

	public EnumMainHand getNMS() {
		return nms;
	}

	public static MainHandSetting getMainHand(EnumMainHand nms) {
		Validate.notNull(nms);
		for (MainHandSetting mainHand : MainHandSetting.values())
			if (mainHand.getNMS().equals(nms))
				return mainHand;
		throw new IllegalArgumentException();
	}
}
