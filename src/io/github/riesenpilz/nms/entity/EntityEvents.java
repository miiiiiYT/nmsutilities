package io.github.riesenpilz.nms.entity;

import org.bukkit.Chunk;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.scheduler.BukkitRunnable;

import io.github.riesenpilz.nms.Main;
import io.github.riesenpilz.nms.nbt.NBTTag;
import io.github.riesenpilz.nms.packet.playOut.PacketPlayOutEntityMoveEvent;

public class EntityEvents implements Listener {
	
	@EventHandler(priority = EventPriority.LOWEST)
	public void onEntityDie(EntityDeathEvent e) {
		new WorldEntity(e.getEntity()).removeNBTTag();
	}
	
	@EventHandler(priority = EventPriority.LOWEST)
	public void onEntityMove(PacketPlayOutEntityMoveEvent e) {
		if (e.isCanceled())
			return;
		
		final Chunk chunkTo = e.getTo().getChunk();
		if (e.getFrom().getChunk().equals(chunkTo))
			return;
		NBTTag tag = e.getEntity().getNBTTag();
		e.getEntity().removeNBTTag();
		new BukkitRunnable() {
			
			@Override
			public void run() {
				e.getEntity().setNBTTag(tag);
				
			}
		}.runTask(Main.getPlugin());
	}
}
