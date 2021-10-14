package io.github.riesenpilz.nmsUtilities.entity.livingEntity.player;

import org.apache.commons.lang.Validate;

import io.github.riesenpilz.nmsUtilities.packet.playIn.PacketPlayInArmAnimationEvent;
import io.github.riesenpilz.nmsUtilities.packet.playIn.PacketPlayInBlockPlaceEvent;
import io.github.riesenpilz.nmsUtilities.packet.playIn.PacketPlayInEditBookEvent;
import io.github.riesenpilz.nmsUtilities.packet.playIn.PacketPlayInEntityInteractEvent;
import io.github.riesenpilz.nmsUtilities.packet.playIn.PacketPlayInUseItemEvent;
import io.github.riesenpilz.nmsUtilities.packet.playOut.PacketPlayOutOpenBookEvent;
import net.minecraft.server.v1_16_R3.EnumHand;

/**
 * Represents {@link EnumHand} Only used by packets.
 * 
 * @see PacketPlayInArmAnimationEvent
 * @see PacketPlayInBlockPlaceEvent
 * @see PacketPlayInEditBookEvent
 * @see PacketPlayInEntityInteractEvent
 * @see PacketPlayInUseItemEvent
 * @see PacketPlayOutOpenBookEvent
 */
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
		Validate.notNull(nms);
		for (Hand hand : values())
			if (hand.getNMS().equals(nms))
				return hand;
		throw new IllegalArgumentException();
	}

	public static Hand getByIndex(int i) {
		for (Hand hand : values())
			if (hand.getIndex() == i)
				return hand;
		throw new IllegalArgumentException();
	}
}
