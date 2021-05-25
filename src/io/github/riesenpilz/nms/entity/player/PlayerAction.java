package io.github.riesenpilz.nms.entity.player;

import net.minecraft.server.v1_16_R3.PacketPlayInEntityAction.EnumPlayerAction;

public enum PlayerAction {

	START_SNEAKING(EnumPlayerAction.PRESS_SHIFT_KEY), STOP_SNEAKING(EnumPlayerAction.RELEASE_SHIFT_KEY),
	/**
	 * This is only sent when the “Leave Bed” button is clicked on the sleep GUI,
	 * not when waking up due today time.
	 */
	STOP_SLEEPING(EnumPlayerAction.STOP_SLEEPING), START_SPRINTING(EnumPlayerAction.START_SPRINTING),
	STOP_SPRINTING(EnumPlayerAction.STOP_SPRINTING), START_JUMP_WITH_HORSE(EnumPlayerAction.START_RIDING_JUMP),
	STOP_JUMP_WITH_HORSE(EnumPlayerAction.STOP_RIDING_JUMP),
	/**
	 * This is only sent when pressing the inventory key (default: E) while riding —
	 * all other methods of opening a horse's inventory (involving right-clicking or
	 * shift-right-clicking it) do not use this packet.
	 */
	OPEN_INVENTORY(EnumPlayerAction.OPEN_INVENTORY), START_FLYING_WITH_ELYTRA(EnumPlayerAction.START_FALL_FLYING);

	private final EnumPlayerAction nms;

	private PlayerAction(EnumPlayerAction nms) {
		this.nms = nms;
	}

	public EnumPlayerAction getNMS() {
		return nms;
	}

	public static PlayerAction getPlayerAction(EnumPlayerAction nms) {
		for (PlayerAction action : values())
			if (action.getNMS().equals(nms))
				return action;
		return null;
	}
}
