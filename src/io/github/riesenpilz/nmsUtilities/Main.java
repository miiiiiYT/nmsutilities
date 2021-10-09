package io.github.riesenpilz.nmsUtilities;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import io.github.riesenpilz.nmsUtilities.block.BlockEvents;
import io.github.riesenpilz.nmsUtilities.entity.EntityEvents;
import io.github.riesenpilz.nmsUtilities.entity.WorldEntity;
import io.github.riesenpilz.nmsUtilities.packet.Injections;
import io.github.riesenpilz.nmsUtilities.world.ServerWorld;
import io.github.riesenpilz.nmsUtilities.world.WorldEvents;
import io.github.riesenpilz.nmsUtilities.world.chunk.Chunk;
import io.github.riesenpilz.nmsUtilities.world.chunk.storage.ChunkEvents;

public class Main extends JavaPlugin {
	private static JavaPlugin plugin;

	public void onEnable() {
		plugin = this;
		PluginManager pluginManager = Bukkit.getPluginManager();
		pluginManager.registerEvents(new BlockEvents(), this);
		pluginManager.registerEvents(new ChunkEvents(), this);
		pluginManager.registerEvents(new WorldEvents(), this);
		pluginManager.registerEvents(new EntityEvents(), this);
		pluginManager.registerEvents(new Injections(), this);
		ServerWorld.init();
	}

	public void onDisable() {
		for (org.bukkit.Chunk chunk : Chunk.nbtTags.keySet())
			Chunk.getChunkOf(chunk).save();
		for (Entity entity : WorldEntity.ENTITY_TAGS.keySet())
			WorldEntity.getWorldEntity(entity).getChunk().save();
	}

	public static JavaPlugin getPlugin() {
		return plugin;
	}
}
