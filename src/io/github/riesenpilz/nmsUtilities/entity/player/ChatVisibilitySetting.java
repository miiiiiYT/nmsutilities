package io.github.riesenpilz.nmsUtilities.entity.player;

import org.apache.commons.lang.Validate;

import net.minecraft.server.v1_16_R3.EnumChatVisibility;

/**
 * Represents {@link EnumChatVisibility}. Only used by packets.
 * 
 * @see PacketPlayInSettingsEvent
 *
 */
public enum ChatVisibilitySetting {
	ENABLED(EnumChatVisibility.FULL), COMMANDS_ONLY(EnumChatVisibility.SYSTEM), HIDDEN(EnumChatVisibility.HIDDEN);

	private final EnumChatVisibility nms;

	private ChatVisibilitySetting(EnumChatVisibility nms) {
		this.nms = nms;
	}

	public EnumChatVisibility getNMS() {
		return nms;
	}

	public static ChatVisibilitySetting getChatVisibility(EnumChatVisibility nms) {
		Validate.notNull(nms);
		for (ChatVisibilitySetting chatVisibility : values())
			if (chatVisibility.getNMS().equals(nms))
				return chatVisibility;
		throw new IllegalArgumentException();
	}
}
