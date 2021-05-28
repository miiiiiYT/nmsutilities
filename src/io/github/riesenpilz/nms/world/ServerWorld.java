package io.github.riesenpilz.nms.world;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

import javax.annotation.Nullable;

import io.github.riesenpilz.nms.inventory.PlayerWorldInventory;
import net.minecraft.server.v1_16_R3.WorldServer;
import io.github.riesenpilz.nms.Main;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.libs.org.apache.commons.io.FileUtils;
import org.bukkit.craftbukkit.v1_16_R3.CraftWorld;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import com.google.gson.stream.JsonWriter;

public class ServerWorld {
	private final org.bukkit.World world;

	public ServerWorld(org.bukkit.World world) {
		this.world = world;
	}

	public ServerWorld(net.minecraft.server.v1_16_R3.WorldServer nms) {
		this.world = Bukkit.getWorld(nms.uuid);
	}

	private JsonObject getWorldConfig() {
		return getConfig("worldConfig");
	}

	private void setWorldConfig(JsonObject jsonObject) {
		setConfig("worldConfig", jsonObject);
	}

	public Chunk getChunk(Location location) {
		return new Chunk(world.getChunkAt(location));
	}

	public JsonObject getConfig(String name) {
		File file = createFile(name);
		JsonObject jsonObject;
		try {
			jsonObject = new Gson().fromJson(new FileReader(file), JsonObject.class);
		} catch (JsonSyntaxException | JsonIOException | FileNotFoundException e) {
			e.printStackTrace();
			jsonObject = new JsonObject();
		}
		if (jsonObject == null || jsonObject.isJsonNull())
			jsonObject = new JsonObject();
		return jsonObject;
	}

	public void setConfig(String name, JsonElement jsonElement) {
		File file = createFile(name);
		try {
			final FileWriter fileWriter = new FileWriter(file);
			final JsonWriter jsonWriter = new JsonWriter(fileWriter);
			new Gson().toJson(jsonElement, jsonWriter);
			jsonWriter.flush();
			jsonWriter.close();
		} catch (JsonSyntaxException | JsonIOException | IOException e) {
			e.printStackTrace();
		}
	}

	private File createFile(String name) {
		File file = new File(getFolder().getPath() + "//" + name + ".json");
		if (!file.exists())
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		return file;
	}

	public File getFolder() {
		return world.getWorldFolder();
	}

	public void setInventory(PlayerWorldInventory inventory, String name) {
		JsonObject config = getWorldConfig();
		JsonObject invs = config.has("inventorys") ? config.getAsJsonObject("inventorys") : new JsonObject();
		invs.add(name, inventory.toJson());
		config.add("inventorys", invs);
		setWorldConfig(config);
	}

	public PlayerWorldInventory getInventory(String name) {
		JsonObject config = getWorldConfig();
		if (!config.has("inventorys"))
			return new PlayerWorldInventory();
		JsonObject invs = config.getAsJsonObject("inventorys");
		if (!invs.has(name))
			return new PlayerWorldInventory();
		return new PlayerWorldInventory(invs.getAsJsonObject(name));
	}

	public void removeInventory(String name) {
		JsonObject config = getWorldConfig();
		JsonObject invs = config.getAsJsonObject("inventorys");
		invs.remove(name);
		config.add("inventorys", invs);
		setWorldConfig(config);
	}

	public void delete(@Nullable Location tpPlayersTo, String message) {
		if (tpPlayersTo != null && !Objects.requireNonNull(tpPlayersTo.getWorld()).equals(world)) {
			for (Player player : getPlayers())
				player.teleport(tpPlayersTo);
			new BukkitRunnable() {

				@Override
				public void run() {
					Bukkit.unloadWorld(world, false);
					try {
						FileUtils.forceDelete(getFolder());
					} catch (IOException e) {
						e.printStackTrace();
					}

				}
			}.runTaskLater(Main.getPlugin(), 1);
			return;
		}
		for (Player player : getPlayers())
			player.kickPlayer(message);
		new BukkitRunnable() {

			@Override
			public void run() {
				Bukkit.unloadWorld(world, false);
				try {
					FileUtils.forceDelete(getFolder());
				} catch (IOException e) {
					e.printStackTrace();
				}

			}
		}.runTaskLater(Main.getPlugin(), 1);
	}

	public File copyWorldTo(String newWorldName) {
		File newWorldFile = new File(newWorldName);
		try {
			FileUtils.copyDirectory(getFolder(), newWorldFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return newWorldFile;
	}

	public List<Player> getPlayers() {
		return world.getPlayers();
	}

	public WorldServer getNMS() {
		return ((CraftWorld) world).getHandle();
	}

	public org.bukkit.World getWorld() {
		return world;
	}
}