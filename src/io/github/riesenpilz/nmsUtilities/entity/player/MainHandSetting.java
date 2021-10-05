package io.github.riesenpilz.nmsUtilities.entity.player;

import net.minecraft.server.v1_16_R3.EnumMainHand;

public enum MainHandSetting {
	LEFT(EnumMainHand.LEFT), RIGHT(EnumMainHand.RIGHT);

	private EnumMainHand nms;

	private MainHandSetting(EnumMainHand nms) {
		this.nms = nms;
	}

	public EnumMainHand getNMS() {
		return nms;
	}

	public static MainHandSetting getMainHand(EnumMainHand nms) {
		for (MainHandSetting mainHand : MainHandSetting.values())
			if (mainHand.getNMS().equals(nms))
				return mainHand;
		return null;
	}
}
