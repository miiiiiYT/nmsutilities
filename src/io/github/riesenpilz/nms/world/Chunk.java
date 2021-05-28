package io.github.riesenpilz.nms.world;

import org.bukkit.Location;

import com.google.gson.JsonObject;

public class Chunk {
	private final org.bukkit.Chunk chunk;

	public Chunk(org.bukkit.Chunk chunk) {
		this.chunk = chunk;
	}

	public void setTag(JsonObject jsonObject) {
		JsonObject jsonObject1 = getWorld().getConfig("chunks");
		jsonObject1.add(getKey(), jsonObject);
		getWorld().setConfig("chunks", jsonObject1);
	}

	public JsonObject getTag() {
		JsonObject tag = getWorld().getConfig("chunks");
		return tag.has(getKey()) ? (JsonObject) tag.get(getKey()):new JsonObject();
	}

	public void removeTag() {
		setTag(null);
	}

	public boolean hasTag() {
		return getTag() != null;
	}

	public ServerWorld getWorld() {
		return new ServerWorld(chunk.getWorld());
	}

	public String getKey() {
		return chunk.getX() + ":" + chunk.getZ();
	}

	public Location getLocation() {
		return new Location(getChunk().getWorld(), getChunk().getX() * 16, 0, getChunk().getZ() * 16);
	}

	public org.bukkit.Chunk getChunk() {
		return chunk;
	}
}
