package io.github.riesenpilz.nms.world.chunk;

import java.io.File;
import java.util.HashMap;

import org.bukkit.Location;

import io.github.riesenpilz.nms.nbt.NBTTag;
import io.github.riesenpilz.nms.world.ServerWorld;

public class Chunk {
	private static final HashMap<org.bukkit.Chunk, NBTTag> nbtTags = new HashMap<>();
	private final org.bukkit.Chunk chunk;

	public Chunk(org.bukkit.Chunk chunk) {
		this.chunk = chunk;
	}


	public ServerWorld getWorld() {
		return new ServerWorld(chunk.getWorld());
	}

	public Location getLocation() {
		return new Location(getChunk().getWorld(), getChunk().getX() * 16, 0, getChunk().getZ() * 16);
	}

	public org.bukkit.Chunk getChunk() {
		return chunk;
	}

	public File getFile() {
		return new File(getWorld().getFolder().getPath() + "\\tags\\r." + (int) getChunk().getX() / 8 + "."
				+ (int) getChunk().getZ() / 8 + ".txt");
	}

	public NBTTag getAllNBTTags() {
		if (!chunk.isLoaded())
			throw new IllegalStateException("Chunk have to be loaded to access the NBT tag.");
		if (!nbtTags.containsKey(chunk))
			setAllNBTTags(new NBTTag());
		return nbtTags.get(chunk);
	}

	public void setAllNBTTags(NBTTag tag) {
		if (!chunk.isLoaded())
			throw new IllegalStateException("Chunk have to be loaded to set the NBT tag.");
		nbtTags.put(chunk, tag);
	}

	public void removeAllNBTTags() {
		if (!chunk.isLoaded())
			throw new IllegalStateException("Chunk have to be loaded to remove the NBT tag.");
		nbtTags.remove(chunk);
	}

	public NBTTag getNBTTag() {
		return getAllNBTTags().getOrDefNBTTag("chunk", new NBTTag());
	}

	public void setNBTTag(NBTTag tag) {
		getNBTTag().setNBTTag("chunk", tag);
	}

	public void removeNBTTag() {
		getAllNBTTags().remove("chunk");
	}
}
