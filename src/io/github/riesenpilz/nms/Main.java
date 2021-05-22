package io.github.riesenpilz.nms;

import io.github.riesenpilz.nms.block.BlockEvents;
import io.github.riesenpilz.nms.packet.Injections;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;


public class Main extends JavaPlugin {
    private static JavaPlugin plugin;

    public void onEnable() {
        plugin = this;
        org.bukkit.plugin.PluginManager pluginManager = Bukkit.getPluginManager();
        pluginManager.registerEvents(new BlockEvents(), this);
        pluginManager.registerEvents(new Injections(), this);
    }

    public static JavaPlugin getPlugin() {
        return plugin;
    }
}
