package io.github.riesenpilz.nmsUtilities.advancemts;

import org.apache.commons.lang.Validate;

import io.github.riesenpilz.nmsUtilities.packet.playOut.PacketPlayOutSelectAdvancementTabEvent;

/**
 * Only used by packets.
 * 
 * @see PacketPlayOutSelectAdvancementTabEvent
 */
public enum AdvancementTab {
	STORY("story/root"), NETHER("nether/root"), END("end/root"), ADVENTURE("adventure/root"),
	HUSBANDRY("husbandry/root");

	private final String identifier;

	private AdvancementTab(String identifier) {
		this.identifier = identifier;
	}

	public String getIdentifier() {
		return identifier;
	}

	public static AdvancementTab getAdvancementTab(String identifier) {
		Validate.notNull(identifier);
		for (AdvancementTab tab : AdvancementTab.values())
			if (tab.getIdentifier().equals(identifier))
				return tab;
		throw new IllegalArgumentException();
	}
}
