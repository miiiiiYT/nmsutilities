package io.github.riesenpilz.nmsUtilities.world;

import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.WorldLoadEvent;
import org.bukkit.event.world.WorldUnloadEvent;

import io.github.riesenpilz.nmsUtilities.world.chunk.storage.IOWorker;

public class WorldEvents implements Listener {
	@EventHandler
	public void onWorldLoad(WorldLoadEvent e) {
		final World world = e.getWorld();
		ServerWorld.IO_WORKERS.put(world, new IOWorker(world.getWorldFolder(), true, world.getName()));
	}

	@EventHandler
	public void onWorldUnload(WorldUnloadEvent e) {
		ServerWorld.IO_WORKERS.remove(e.getWorld());
	}
}
