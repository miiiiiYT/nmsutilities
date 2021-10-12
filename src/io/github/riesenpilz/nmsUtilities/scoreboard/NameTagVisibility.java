package io.github.riesenpilz.nmsUtilities.scoreboard;

import org.apache.commons.lang.Validate;

import io.github.riesenpilz.nmsUtilities.packet.playOut.PacketPlayOutScoreboardTeamEvent;

/**
 * Only used by packets.
 * 
 * @see PacketPlayOutScoreboardTeamEvent
 */
public enum NameTagVisibility {
	ALWAYS("always"), HIDE_FOR_OTHER_TEAMS("hideForOtherTeams"), HIDE_FOR_OWN_TEAMS("hideForOwnTeam"),
	NEVER("never");

	private final String name;

	private NameTagVisibility(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public static NameTagVisibility getByName(String name) {
		Validate.notNull(name);
		for (NameTagVisibility rule : values())
			if (rule.getName().equals(name))
				return rule;
		throw new IllegalArgumentException();
	}
}
