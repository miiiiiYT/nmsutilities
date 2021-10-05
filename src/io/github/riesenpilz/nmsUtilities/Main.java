package io.github.riesenpilz.nmsUtilities;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import io.github.riesenpilz.nmsUtilities.block.BlockEvents;
import io.github.riesenpilz.nmsUtilities.entity.EntityEvents;
import io.github.riesenpilz.nmsUtilities.packet.Injections;
import io.github.riesenpilz.nmsUtilities.world.chunk.ChunkEvents;


public class Main extends JavaPlugin {
    private static JavaPlugin plugin;

    public void onEnable() {
        plugin = this;
        PluginManager pluginManager = Bukkit.getPluginManager();
        pluginManager.registerEvents(new BlockEvents(), this);
        pluginManager.registerEvents(new ChunkEvents(), this);
        pluginManager.registerEvents(new EntityEvents(), this);
        pluginManager.registerEvents(new Injections(), this);
    }

    public static JavaPlugin getPlugin() {
        return plugin;
    }
}
