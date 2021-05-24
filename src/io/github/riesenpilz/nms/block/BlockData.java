package io.github.riesenpilz.nms.block;

import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_16_R3.block.CraftBlock;

import io.github.riesenpilz.nms.packet.PacketUtils;
import io.github.riesenpilz.nms.world.World;
import net.minecraft.server.v1_16_R3.Block;
import net.minecraft.server.v1_16_R3.IBlockData;

public class BlockData {
	private final Block block;

	public BlockData(Block block) {
		this.block = block;
	}
	public BlockData(IBlockData blockData) {
		this.block = blockData.getBlock();
	}
	public BlockData(org.bukkit.block.Block block) {
		this.block = ((CraftBlock)block).getNMS().getBlock();
	}
	

	public io.github.riesenpilz.nms.block.Block setBlock(Location loc) {
		block.c(new World(loc.getWorld()).getNMS(), PacketUtils.toBlockPosition(loc));
		return new io.github.riesenpilz.nms.block.Block(loc);
	}

	public Block getNMS() {
		return block;
	}
}
