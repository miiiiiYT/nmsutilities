package io.github.riesenpilz.nmsUtilities.scoreboard;

import org.apache.commons.lang.Validate;

import io.github.riesenpilz.nmsUtilities.packet.playOut.PacketPlayOutScoreboardTeamEvent;

/**
 * Only used by packets.
 * 
 * @see PacketPlayOutScoreboardTeamEvent
 */
public enum CollisionRule {
	ALWAYS("always"), PUSH_OTHER_TEAMS("pushOtherTeams"), PUSH_OWN_TEAMS("pushOwnTeam"), NEVER("never");

	private final String name;

	private CollisionRule(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public static CollisionRule getByName(String name) {
		Validate.notNull(name);
		for (CollisionRule rule : values())
			if (rule.getName().equals(name))
				return rule;
		throw new IllegalArgumentException();
	}
}