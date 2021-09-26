package io.github.riesenpilz.nms.block;

import java.util.HashMap;
import java.util.Map.Entry;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockBurnEvent;
import org.bukkit.event.block.BlockPistonExtendEvent;
import org.bukkit.event.block.BlockPistonRetractEvent;

import io.github.riesenpilz.nms.inventory.ItemStack;
import io.github.riesenpilz.nms.nbt.NBTTag;

/**
 * This class removes and moves block NBTTags when its necessary and drops the
 * custom drops
 *
 */
public class BlockEvents implements Listener {
	@EventHandler(priority = EventPriority.LOWEST)
	public void onBlockBreak(BlockBreakEvent e) {
		if (e.isCancelled())
			return;

		Block block = Block.getBlockOf(e.getBlock());

		e.setDropItems(!block.hasDrops());
		if (!block.hasDrops())
			e.setExpToDrop(0);

		for (ItemStack itemStack : block.getDrops())
			itemStack.dropNaturally(block.getLocation());

		block.removeNBTTag();
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void onBlockChange(BlockBurnEvent e) {
		if (e.isCancelled())
			return;

		Block block = Block.getBlockOf(e.getBlock());

		block.removeNBTTag();
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void onPistonExtend(BlockPistonExtendEvent e) {
		if (e.isCancelled())
			return;

		HashMap<Block, NBTTag> newLocations = new HashMap<>();
		for (org.bukkit.block.Block block : e.getBlocks()) {
			final Block block1 = Block.getBlockOf(block);
			newLocations.put(Block.getBlockOf(block.getRelative(e.getDirection())), block1.getNBTTag());
			block1.removeNBTTag();
		}
		for (Entry<Block, NBTTag> entry : newLocations.entrySet())
			entry.getKey().setNBTTag(entry.getValue());
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void onPistonRetract(BlockPistonRetractEvent e) {
		if (e.isCancelled())
			return;

		HashMap<Block, NBTTag> newLocations = new HashMap<>();
		for (org.bukkit.block.Block block : e.getBlocks()) {
			final Block block1 = Block.getBlockOf(block);
			newLocations.put(Block.getBlockOf(block.getRelative(e.getDirection())), block1.getNBTTag());
			block1.removeNBTTag();
		}
		for (Entry<Block, NBTTag> entry : newLocations.entrySet())
			entry.getKey().setNBTTag(entry.getValue());
	}

}
