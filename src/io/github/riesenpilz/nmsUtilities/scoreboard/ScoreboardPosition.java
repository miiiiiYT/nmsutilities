package io.github.riesenpilz.nmsUtilities.scoreboard;

/**
 * Only used by packets.
 * 
 * @see PacketPlayOutScoreboardDisplayEvent
 *
 */
public enum ScoreboardPosition {

	LIST(0), SIDEBAR(1), BELOW_NAME(2), TEAM_WHITE(3), TEAM_ORANGE(4), TEAM_MAGENTA(5), TEAM_LIGHT_BLUE(6),
	TEAM_YELLOW(7), TEAM_LIME(8), TEAM_PINK(9), TEAM_GRAY(10), TEAM_LIGHT_GRAY(11), TEAM_CYAN(12), TEAM_PURPLE(13),
	TEAM_BLUE(14), TEAM_BROWN(15), TEAM_GREEN(16), TEAM_RED(17), TEAM_BLACK(18);

	private final int positionValue;

	private ScoreboardPosition(int position) {
		this.positionValue = position;
	}

	public int getPositionValue() {
		return positionValue;
	}

	public static ScoreboardPosition getScoreboardPosition(int positionValue) {
		for (ScoreboardPosition position : values())
			if (position.getPositionValue() == positionValue)
				return position;
		throw new IllegalArgumentException();
	}
}
