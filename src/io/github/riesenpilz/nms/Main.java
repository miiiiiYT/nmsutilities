package io.github.riesenpilz.nms;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import io.github.riesenpilz.nms.block.BlockEvents;
import io.github.riesenpilz.nms.entity.EntityEvents;
import io.github.riesenpilz.nms.packet.Injections;
import io.github.riesenpilz.nms.world.chunk.ChunkEvents;


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
