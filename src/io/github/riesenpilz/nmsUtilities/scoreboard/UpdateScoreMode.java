package io.github.riesenpilz.nmsUtilities.scoreboard;

import net.minecraft.server.v1_16_R3.ScoreboardServer.Action;

/**
 * Only used by packets.
 * 
 * @see PacketPlayOutScoreboardUpdateScoreEvent
 */
public enum UpdateScoreMode {
	CREATE_OR_UPDATE(Action.CHANGE), REMOVE(Action.REMOVE);

	private Action nms;

	private UpdateScoreMode(Action nms) {
		this.nms = nms;
	}

	public Action getNMS() {
		return nms;
	}

	public static UpdateScoreMode getMode(Action nms) {
		for (UpdateScoreMode mode : values())
			if (mode.getNMS().equals(nms))
				return mode;
		return null;
	}
}