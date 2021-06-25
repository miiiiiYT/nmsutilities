package io.github.riesenpilz.nms.block;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import io.github.riesenpilz.nms.inventory.ItemStack;

public class BlockEvents implements Listener {
	@EventHandler(priority = EventPriority.LOWEST)
	public void onBreakBlock(BlockBreakEvent e) {
		if (e.isCancelled())
			return;
		Block block = new Block(e.getBlock());
		e.setDropItems(!block.hasDrops());
		if (!block.hasDrops())
			e.setExpToDrop(0);
		for (ItemStack itemStack : block.getDrops())
			itemStack.dropNaturally(block.getLocation());
		block.removeNBTTag();
	}
}
