package io.github.riesenpilz.nmsUtilities.world;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Nullable;

import org.apache.commons.lang.Validate;
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

import io.github.riesenpilz.nmsUtilities.Main;
import io.github.riesenpilz.nmsUtilities.inventory.PlayerWorldInventory;
import io.github.riesenpilz.nmsUtilities.world.chunk.Chunk;
import io.github.riesenpilz.nmsUtilities.world.chunk.storage.IOWorker;
import net.minecraft.server.v1_16_R3.WorldServer;

@SuppressWarnings("deprecation")
public class ServerWorld {
	static final Map<org.bukkit.World, IOWorker> IO_WORKERS = new HashMap<>();
	
	public static void init() {
		for (org.bukkit.World world : Bukkit.getWorlds())
		IO_WORKERS.put(world, new IOWorker(world.getWorldFolder(), true, world.getName()));
	}
	
	private final org.bukkit.World bukkit;

	protected ServerWorld(org.bukkit.World bukkit) {
		Validate.notNull(bukkit);
		this.bukkit = bukkit;
	}

	protected ServerWorld(net.minecraft.server.v1_16_R3.WorldServer nms) {
		Validate.notNull(nms);
		this.bukkit = Bukkit.getWorld(nms.uuid);
	}
	public static ServerWorld getWorldOf(WorldServer nms) {
		return new ServerWorld(nms);
	}
	public static ServerWorld getWorldOf(org.bukkit.World bukkit) {
		return new ServerWorld(bukkit);
	}
	private JsonObject getWorldConfig() {
		return getConfig("worldConfig");
	}

	private void setWorldConfig(JsonObject jsonObject) {
		setConfig("worldConfig", jsonObject);
	}

	public Chunk getChunk(Location location) {
		return new Chunk(bukkit.getChunkAt(location));
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
		return bukkit.getWorldFolder();
	}

	@Deprecated
	public void setInventory(PlayerWorldInventory inventory, String name) {
		JsonObject config = getWorldConfig();
		JsonObject invs = config.has("inventorys") ? config.getAsJsonObject("inventorys") : new JsonObject();
		invs.add(name, inventory.toJson());
		config.add("inventorys", invs);
		setWorldConfig(config);
	}

	@Deprecated
	public PlayerWorldInventory getInventory(String name) {
		JsonObject config = getWorldConfig();
		if (!config.has("inventorys"))
			return new PlayerWorldInventory();
		JsonObject invs = config.getAsJsonObject("inventorys");
		if (!invs.has(name))
			return new PlayerWorldInventory();
		return new PlayerWorldInventory(invs.getAsJsonObject(name));
	}

	@Deprecated
	public void removeInventory(String name) {
		JsonObject config = getWorldConfig();
		JsonObject invs = config.getAsJsonObject("inventorys");
		invs.remove(name);
		config.add("inventorys", invs);
		setWorldConfig(config);
	}

	/**
	 * Unloads a deletes this world. If tpPlayersTo is NULL or the Location is in
	 * the same world, all players in this world will be kicked.
	 * 
	 * @param tpPlayersTo the location where all players should be teleported to
	 * @param message     the kick message or the message after the teleport.
	 */
	public void delete(@Nullable Location tpPlayersTo, String message) {
		Validate.notNull(message);
		if (tpPlayersTo != null && !tpPlayersTo.getWorld().equals(bukkit)) {
			for (Player player : getPlayers()) {
				player.teleport(tpPlayersTo);
				player.sendMessage(message);
			}
			new BukkitRunnable() {

				@Override
				public void run() {
					Bukkit.unloadWorld(bukkit, false);
					try {
						FileUtils.forceDelete(getFolder());
					} catch (IOException e) {
						e.printStackTrace();
					}

				}
			}.runTask(Main.getPlugin());
			return;
		}
		for (Player player : getPlayers())
			player.kickPlayer(message);
		new BukkitRunnable() {

			@Override
			public void run() {
				Bukkit.unloadWorld(bukkit, false);
				try {
					FileUtils.forceDelete(getFolder());
				} catch (IOException e) {
					e.printStackTrace();
				}

			}
		}.runTask(Main.getPlugin());
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
		return bukkit.getPlayers();
	}

	public WorldServer getNMS() {
		return ((CraftWorld) bukkit).getHandle();
	}

	public org.bukkit.World getBukkit() {
		return bukkit;
	}

	public World getWorld() {
		return World.getWorldOf(getNMS());
	}

	public IOWorker getIOWorker() {
		return IO_WORKERS.get(bukkit);
	}

	
}