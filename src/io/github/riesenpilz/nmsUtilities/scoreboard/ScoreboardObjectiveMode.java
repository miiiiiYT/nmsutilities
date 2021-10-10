package io.github.riesenpilz.nmsUtilities.scoreboard;


public enum ScoreboardObjectiveMode {
	CREATE((byte) 0), REMOVE((byte) 1), UPDATE_DISPLAY((byte) 2);

	private byte id;

	private ScoreboardObjectiveMode(byte id) {
		this.id = id;
	}

	public byte getId() {
		return id;
	}

	public static ScoreboardObjectiveMode getById(int id) {
		for (ScoreboardObjectiveMode mode : values())
			if (mode.getId() == id)
				return mode;
		return null;
	}
}
