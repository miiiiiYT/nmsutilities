package io.github.riesenpilz.nms.block;

import org.apache.commons.lang.Validate;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_16_R3.block.CraftBlock;

import io.github.riesenpilz.nms.packet.PacketUtils;
import io.github.riesenpilz.nms.world.ServerWorld;
import net.minecraft.server.v1_16_R3.Block;
import net.minecraft.server.v1_16_R3.IBlockData;

public class BlockData {
	private final Block nms;

	protected BlockData(Block nms) {
		Validate.notNull(nms);
		this.nms = nms;
	}

	protected BlockData(IBlockData nms) {
		Validate.notNull(nms);
		this.nms = nms.getBlock();
	}

	protected BlockData(org.bukkit.block.Block block) {
		Validate.notNull(block);
		this.nms = ((CraftBlock) block).getNMS().getBlock();
	}

	public static BlockData getBlockDataOf(Block nms) {
		return new BlockData(nms);
	}

	public static BlockData getBlockDataOf(IBlockData nms) {
		return new BlockData(nms);
	}
	
	public static BlockData getBlockDataOf(org.bukkit.block.Block block) {
		return new BlockData(block);
	}
	
	public io.github.riesenpilz.nms.block.Block setBlock(Location loc) {
		nms.c(new ServerWorld(loc.getWorld()).getNMS(), PacketUtils.toBlockPosition(loc));
		return io.github.riesenpilz.nms.block.Block.getBlockOf(loc);
	}

	public Block getNMS() {
		return nms;
	}
}
