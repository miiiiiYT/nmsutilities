package io.github.riesenpilz.nms.entity.player;

import net.minecraft.server.v1_16_R3.EnumChatVisibility;

public enum ChatVisibilitySetting {
	ENABLED(EnumChatVisibility.FULL), COMMANDS_ONLY(EnumChatVisibility.SYSTEM), HIDDEN(EnumChatVisibility.HIDDEN);

	private EnumChatVisibility nms;

	private ChatVisibilitySetting(EnumChatVisibility nms) {
		this.nms = nms;
	}

	public EnumChatVisibility getNMS() {
		return nms;
	}

	public static ChatVisibilitySetting getChatVisibility(EnumChatVisibility nms) {
		for (ChatVisibilitySetting chatVisibility : values())
			if (chatVisibility.getNMS().equals(nms))
				return chatVisibility;
		return null;
	}
}
