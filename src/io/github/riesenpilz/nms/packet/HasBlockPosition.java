package io.github.riesenpilz.nms.packet;

import org.bukkit.Location;

import io.github.riesenpilz.nms.block.Block;

public interface HasBlockPosition {

	public Location getBlockLocation();
	public default Block getBlock() {
		return Block.getBlockOf(getBlockLocation());
	}
}
