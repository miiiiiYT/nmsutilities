package io.github.riesenpilz.nms.entity;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

public class EntityEvents implements Listener {
	
	@EventHandler(priority = EventPriority.LOWEST)
	public void onEntityDie(EntityDeathEvent e) {
		new WorldEntity(e.getEntity()).removeNBTTag();
	}
}
