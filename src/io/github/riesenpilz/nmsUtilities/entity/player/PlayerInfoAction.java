package io.github.riesenpilz.nmsUtilities.entity.player;

import org.apache.commons.lang.Validate;

import io.github.riesenpilz.nmsUtilities.packet.playOut.PacketPlayOutPlayerInfoEvent;
import net.minecraft.server.v1_16_R3.PacketPlayOutPlayerInfo;

/**
 * Represents {@link PacketPlayOutPlayerInfo.EnumPlayerInfoAction}. Only used by
 * packets.
 * 
 * @see PacketPlayOutPlayerInfoEvent
 *
 */
public enum PlayerInfoAction {
	ADD_PLAYER(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER),
	UPDATE_GAME_MODE(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.UPDATE_GAME_MODE),
	UPDATE_LATENCY(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.UPDATE_LATENCY),
	UPDATE_DISPLAY_NAME(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.UPDATE_DISPLAY_NAME),
	REMOVE_PLAYER(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.REMOVE_PLAYER);

	private final PacketPlayOutPlayerInfo.EnumPlayerInfoAction nms;

	private PlayerInfoAction(PacketPlayOutPlayerInfo.EnumPlayerInfoAction nms) {
		this.nms = nms;
	}

	public PacketPlayOutPlayerInfo.EnumPlayerInfoAction getNMS() {
		return nms;
	}

	public static PlayerInfoAction getAction(PacketPlayOutPlayerInfo.EnumPlayerInfoAction nms) {
		Validate.notNull(nms);
		for (PlayerInfoAction action : values())
			if (action.getNMS().equals(nms))
				return action;
		throw new IllegalArgumentException();
	}
}