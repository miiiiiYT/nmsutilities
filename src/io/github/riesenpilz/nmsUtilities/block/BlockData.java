package io.github.riesenpilz.nmsUtilities.block;

import org.apache.commons.lang.Validate;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_16_R3.block.CraftBlock;

import io.github.riesenpilz.nmsUtilities.packet.PacketUtils;
import io.github.riesenpilz.nmsUtilities.world.ServerWorld;
import net.minecraft.server.v1_16_R3.Block;
import net.minecraft.server.v1_16_R3.IBlockData;
import net.minecraft.server.v1_16_R3.Material;

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

	public io.github.riesenpilz.nmsUtilities.block.Block setBlock(Location loc) {
		nms.c(ServerWorld.getWorldOf(loc.getWorld()).getNMS(), PacketUtils.toBlockPosition(loc));
		return io.github.riesenpilz.nmsUtilities.block.Block.getBlockOf(loc);
	}

	public Material getNMSMaterial() {
		return nms.getBlockData().getMaterial();
	}

	public Block getNMS() {
		return nms;
	}
}
