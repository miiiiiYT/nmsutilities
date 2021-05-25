package io.github.riesenpilz.nms.entity.player;

import org.bukkit.block.BlockFace;

import net.minecraft.server.v1_16_R3.PacketPlayInBlockDig.EnumPlayerDigType;

public enum DigType {

	START_DESTROY_BLOCK(EnumPlayerDigType.START_DESTROY_BLOCK),
	/**
	 * Sent when the player lets go of the Mine Block key (default: left click).
	 */
	CANCEL_DESTROY_BLOCK(EnumPlayerDigType.ABORT_DESTROY_BLOCK),
	/**
	 * Sent when the client thinks it is finished.
	 */
	FINISH_DESTROY_BLOCK(EnumPlayerDigType.STOP_DESTROY_BLOCK),
	/**
	 * Triggered by using the Drop Item key (default: Q) with the modifier to drop
	 * the entire selected stack (default: depends on OS). Location is always set to
	 * 0/0/0, Face is always set to {@link BlockFace#DOWN}.
	 */
	DROP_ALL_ITEMS(EnumPlayerDigType.DROP_ALL_ITEMS),
	/**
	 * Triggered by using the Drop Item key (default: Q). Location is always set to
	 * 0/0/0, Face is always set to {@link BlockFace#DOWN}.
	 */
	DROP_ITEM(EnumPlayerDigType.DROP_ITEM),
	/**
	 * Indicates that the currently held item should have its state updated such as
	 * eating food, pulling back bows, using buckets, etc. Location is always set to
	 * 0/0/0, Face is always set to {@link BlockFace#DOWN}.
	 */
	RELEASE_USE_ITEM(EnumPlayerDigType.RELEASE_USE_ITEM),
	/**
	 * Used to swap or assign an item to the second hand. Location is always set to
	 * 0/0/0, Face is always set to {@link BlockFace#DOWN}.
	 * 
	 */
	SWAP_ITEM_WITH_OFFHAND(EnumPlayerDigType.SWAP_ITEM_WITH_OFFHAND);

	private EnumPlayerDigType nms;

	private DigType(EnumPlayerDigType nms) {
		this.nms = nms;
	}

	public EnumPlayerDigType getNMS() {
		return nms;
	}

	public static DigType getPlayerDigType(EnumPlayerDigType nms) {
		for (DigType clickType : values())
			if (clickType.getNMS().equals(nms))
				return clickType;
		return null;
	}
}
