package io.github.riesenpilz.nmsUtilities.entity.player;

import io.github.riesenpilz.nmsUtilities.packet.playOut.PacketPlayOutAnimationEvent;

/**
 * Only used by packets.
 * 
 * @see PacketPlayOutAnimationEvent
 */
public enum PlayerAnimation {
	CRITICAL_EFFECT(4), LEAVE_BED(2), MAGIC_CRITICAL_EFFECT(5), SWING_MAIN_ARM(0), SWING_OFFHAND(3), TAKE_DAMAGE(1);

	private final int id;

	PlayerAnimation(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public static PlayerAnimation getByID(int id) {
		for (PlayerAnimation animation : values())
			if (animation.getId() == id)
				return animation;
		throw new IllegalArgumentException();
	}

}