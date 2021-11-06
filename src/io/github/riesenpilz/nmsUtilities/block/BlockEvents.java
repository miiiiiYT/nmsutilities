package io.github.riesenpilz.nmsUtilities.block;

import java.util.HashMap;
import java.util.Map.Entry;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockBurnEvent;
import org.bukkit.event.block.BlockPistonExtendEvent;
import org.bukkit.event.block.BlockPistonRetractEvent;

import io.github.riesenpilz.nmsUtilities.inventory.ItemStack;
import io.github.riesenpilz.nmsUtilities.nbt.NBTTag;

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
		for (org.bukkit.block.Block bukkit : e.getBlocks()) {
			switch (bukkit.getPistonMoveReaction()) {
			case BREAK:
				Block block1 = Block.getBlockOf(bukkit);
				block1.removeNBTTag();
				break;
			case MOVE:
			case PUSH_ONLY:
				block1 = Block.getBlockOf(bukkit);
				newLocations.put(Block.getBlockOf(bukkit.getRelative(e.getDirection())), block1.getNBTTag());
				block1.removeNBTTag();
				break;
			case BLOCK:
			case IGNORE:
			default:
				break;
			}
		}
		for (Entry<Block, NBTTag> entry : newLocations.entrySet())
			entry.getKey().setNBTTag(entry.getValue());
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void onPistonRetract(BlockPistonRetractEvent e) {
		if (e.isCancelled())
			return;

		HashMap<Block, NBTTag> newLocations = new HashMap<>();
		for (org.bukkit.block.Block bukkit : e.getBlocks())
			switch (bukkit.getPistonMoveReaction()) {
			case BREAK:
				Block block1 = Block.getBlockOf(bukkit);
				block1.removeNBTTag();
				break;
			case MOVE:
				block1 = Block.getBlockOf(bukkit);
				newLocations.put(Block.getBlockOf(bukkit.getRelative(e.getDirection())), block1.getNBTTag());
				block1.removeNBTTag();
				break;
			case BLOCK:
			case IGNORE:
			case PUSH_ONLY:
			default:
				break;
			}
		for (Entry<Block, NBTTag> entry : newLocations.entrySet())
			entry.getKey().setNBTTag(entry.getValue());
	}

}
