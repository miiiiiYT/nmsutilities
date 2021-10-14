package io.github.riesenpilz.nmsUtilities.entity.livingEntity.player;

import org.apache.commons.lang.Validate;

import io.github.riesenpilz.nmsUtilities.packet.playOut.PacketPlayOutPayerPositionEvent;
import net.minecraft.server.v1_16_R3.PacketPlayOutPosition.EnumPlayerTeleportFlags;

/**
 * Represents {@link EnumPlayerTeleportFlags}. Only used by packets.
 *
 *@see PacketPlayOutPayerPositionEvent
 */
public enum TeleportFlags {

	X(EnumPlayerTeleportFlags.X), Y(EnumPlayerTeleportFlags.Y), Z(EnumPlayerTeleportFlags.Z),
	Y_ROT(EnumPlayerTeleportFlags.Y_ROT), X_ROT(EnumPlayerTeleportFlags.X_ROT);

	private final EnumPlayerTeleportFlags nms;

	private TeleportFlags(EnumPlayerTeleportFlags nms) {
		this.nms = nms;
	}

	public EnumPlayerTeleportFlags getNMS() {
		return nms;
	}

	public static TeleportFlags getPlayerTeleportFlags(EnumPlayerTeleportFlags nms) {
		Validate.notNull(nms);
		for (TeleportFlags flag : values())
			if (flag.getNMS().equals(nms))
				return flag;
		throw new IllegalArgumentException();
	}
}