package io.github.riesenpilz.nmsUtilities.scoreboard;

import io.github.riesenpilz.nmsUtilities.packet.playOut.PacketPlayOutScoreboardTeamEvent;

/**
 * Only used by packets.
 * 
 * @see PacketPlayOutScoreboardTeamEvent
 */
public enum FriendlyTag {
	FRIENDLY_FIRE(0x01), SEE_INVISIBLE_PLAYERS(0x02);

	private final int bit;

	private FriendlyTag(int bit) {
		this.bit = bit;
	}

	public int getBit() {
		return bit;
	}

	public static FriendlyTag getByBitposition(int position) {
		final double pow = Math.pow(2, position);
		for (FriendlyTag tag : values())
			if (tag.getBit() == pow)
				return tag;
		throw new IllegalArgumentException();
	}
}

