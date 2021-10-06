package io.github.riesenpilz.nmsUtilities.packet;

import org.bukkit.Location;

import io.github.riesenpilz.nmsUtilities.block.Block;

public interface HasBlockPosition {

	public Location getBlockLocation();
	public default Block getBlock() {
		return Block.getBlockOf(getBlockLocation());
	}
}
