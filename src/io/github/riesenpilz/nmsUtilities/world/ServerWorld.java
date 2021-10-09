package io.github.riesenpilz.nmsUtilities.world;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import javax.annotation.Nullable;

import org.apache.commons.lang.Validate;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.libs.org.apache.commons.io.FileUtils;
import org.bukkit.craftbukkit.v1_16_R3.CraftWorld;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonWriter;

import io.github.riesenpilz.nmsUtilities.Main;
import io.github.riesenpilz.nmsUtilities.entity.WorldEntity;
import io.github.riesenpilz.nmsUtilities.world.chunk.Chunk;
import io.github.riesenpilz.nmsUtilities.world.chunk.storage.IOWorker;
import net.minecraft.server.v1_16_R3.Entity;
import net.minecraft.server.v1_16_R3.WorldServer;

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

	public Chunk getChunk(Location location) {
		return Chunk.getChunkOf(bukkit.getChunkAt(location));
	}

	/**
	 * Reads a json file from disc.
	 * 
	 * @param name the name of the file.
	 * @return the contents of the file.
	 * @throws IOException
	 * 
	 * @see #setConfig(String, JsonElement)
	 */
	public JsonObject getConfig(String name) throws IOException {
		JsonObject jsonObject = new Gson().fromJson(new FileReader(createFile(name)), JsonObject.class);
		return jsonObject == null ? new JsonObject() : jsonObject;
	}

	/**
	 * Writes a json file to disc.
	 * 
	 * @param name     the name of the file.
	 * @param contents the contents of the file.
	 * @throws IOException
	 */
	public void setConfig(String name, JsonObject contents) throws IOException {
		Validate.notNull(contents);
		final JsonWriter jsonWriter = new JsonWriter(new FileWriter(createFile(name)));
		new Gson().toJson(contents, jsonWriter);
		jsonWriter.flush();
		jsonWriter.close();
	}

	private File createFile(String name) throws IOException {
		Validate.notNull(name);
		File file = new File(getWorldFolder().getPath() + "//" + name + ".json");
		if (!file.exists())
			file.createNewFile();
		return file;
	}

	public File getWorldFolder() {
		return bukkit.getWorldFolder();
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

		final Consumer<BukkitTask> delete = (task) -> {
			Bukkit.unloadWorld(bukkit, false);
			try {
				FileUtils.forceDelete(getWorldFolder());
			} catch (IOException e) {
				e.printStackTrace();
			}
		};

		if (tpPlayersTo == null || tpPlayersTo.getWorld().equals(bukkit)) {
			for (Player player : getPlayers())
				player.kickPlayer(message);
			Bukkit.getScheduler().runTask(Main.getPlugin(), delete);
			return;
		}

		for (Player player : getPlayers()) {
			player.teleport(tpPlayersTo);
			player.sendMessage(message);
		}
		Bukkit.getScheduler().runTask(Main.getPlugin(), delete);
	}

	public File copyWorld(String newWorldName) throws IOException {
		Validate.notNull(newWorldName);
		File newWorldFile = new File(getWorldFolder().getParentFile(), newWorldName);
		FileUtils.copyDirectory(getWorldFolder(), newWorldFile);
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

	@Nullable
	public WorldEntity getEntityById(int id) {
		final @Nullable Entity entity = getNMS().getEntity(id);
		return entity == null ? null : WorldEntity.getWorldEntity(entity);
	}

}