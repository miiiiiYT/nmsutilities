package io.github.riesenpilz.nmsUtilities.boss;

import org.apache.commons.lang.Validate;

import net.minecraft.server.v1_16_R3.PacketPlayOutBoss.Action;

/**
 * Represents {@link Action}. Only used by packets.
 *
 * @see PacketPlayOutBossBarEvent
 */
public enum BossBarAction {
	ADD(Action.ADD), REMOVE(Action.REMOVE), UPDATE_HEALTH(Action.UPDATE_PCT), UPDATE_TITLE(Action.UPDATE_NAME),
	UPDATE_STYLE(Action.UPDATE_STYLE), UPDATE_PROPERTIES(Action.UPDATE_PROPERTIES);

	private final Action nms;

	BossBarAction(Action action) {
		nms = action;
	}

	public Action getNMS() {
		return nms;
	}

	public static BossBarAction getBossBarAction(Action nms) {
		Validate.notNull(nms);
		for (BossBarAction action : values())
			if (action.getNMS().equals(nms))
				return action;
		throw new IllegalArgumentException();
	}
}
