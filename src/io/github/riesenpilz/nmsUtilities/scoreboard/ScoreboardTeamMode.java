package io.github.riesenpilz.nmsUtilities.scoreboard;

/**
 * Only used by packets.
 * 
 * @see PacketPlayOutScoreboardTeamEvent
 */
public enum ScoreboardTeamMode {
	CREATE((byte) 0), REMOVE((byte) 1), UPDATE_INFO((byte) 2), ADD_ENTITIES((byte) 3), REMOVE_ENTITIES((byte) 4);

	private final byte id;

	private ScoreboardTeamMode(byte id) {
		this.id = id;
	}

	public byte getId() {
		return id;
	}

	public static ScoreboardTeamMode getById(int id) {
		for (ScoreboardTeamMode mode : values())
			if (mode.getId() == id)
				return mode;
		throw new IllegalArgumentException();
	}
}
