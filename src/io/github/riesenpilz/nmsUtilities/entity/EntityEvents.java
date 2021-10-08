package io.github.riesenpilz.nmsUtilities.entity;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntitySpawnEvent;

import io.github.riesenpilz.nmsUtilities.nbt.NBTTag;

public class EntityEvents implements Listener {

	@EventHandler
	public void onEntityDeath(EntityDeathEvent e) {
		WorldEntity.tags.remove(e.getEntity());
	}
	@EventHandler
	public void onEntitySpawn(EntitySpawnEvent e) {
		NBTTag nbtTag = new NBTTag();
		WorldEntity.getWorldEntity(e.getEntity()).setNBTTag(nbtTag);
	}
}
