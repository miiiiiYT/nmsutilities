package io.github.riesenpilz.nms.entity.player;

import net.minecraft.server.v1_16_R3.EnumHand;

public enum Hand {
	MAIN_HAND(EnumHand.MAIN_HAND, 0), OFF_HAND(EnumHand.OFF_HAND, 1);

	private final EnumHand hand;
	private final int index;

	private Hand(EnumHand hand, int index) {
		this.hand = hand;
		this.index = index;
	}

	public int getIndex() {
		return index;
	}

	public EnumHand getNMS() {
		return hand;
	}

	public static Hand getHand(EnumHand nms) {
		for (Hand hand : values())
			if (hand.getNMS().equals(nms))
				return hand;
		return null;
	}
	public static Hand getByIndex(int i) {
		for (Hand hand : values())
			if (hand.getIndex() == i)
				return hand;
		return null;
	}
}
