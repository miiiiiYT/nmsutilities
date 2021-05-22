package io.github.riesenpilz.nms.block;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

public class BlockEvents implements Listener {
    @EventHandler(priority = EventPriority.LOWEST)
    public void onBreakBlock(BlockBreakEvent e) {
        if (e.isCancelled())
            return;
        Block block = new Block(e.getBlock());
        e.setDropItems(block.getDrops().isEmpty());
        for (ItemStack itemStack : block.getDrops())
            e.getBlock().getWorld().dropItem(e.getBlock().getLocation(), itemStack);
        block.removeTags();
    }
}
