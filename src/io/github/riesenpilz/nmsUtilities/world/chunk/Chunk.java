package io.github.riesenpilz.nmsUtilities.world.chunk;

import java.io.File;
import java.util.HashMap;

import org.apache.commons.lang.Validate;
import org.bukkit.Location;

import io.github.riesenpilz.nmsUtilities.nbt.NBTTag;
import io.github.riesenpilz.nmsUtilities.world.ServerWorld;

public class Chunk {
	private static final HashMap<org.bukkit.Chunk, NBTTag> nbtTags = new HashMap<>();
	private final org.bukkit.Chunk bukkitChunk;

	public Chunk(org.bukkit.Chunk bukkitChunk) {
		Validate.notNull(bukkitChunk);
		this.bukkitChunk = bukkitChunk;
	}

	public static Chunk getChunkOf(org.bukkit.Chunk bukkitChunk) {
		return new Chunk(bukkitChunk);
	}

	public ServerWorld getWorld() {
		return ServerWorld.getWorldOf(bukkitChunk.getWorld());
	}

	public Location getLocation() {
		return new Location(getBukkit().getWorld(), getBukkit().getX() * 16, 0, getBukkit().getZ() * 16);
	}

	public org.bukkit.Chunk getBukkit() {
		return bukkitChunk;
	}

	public File getFile() {
		return new File(getWorld().getFolder().getPath() + "\\tags\\r." + (int) getBukkit().getX() / 8 + "."
				+ (int) getBukkit().getZ() / 8 + ".txt");
	}

	public NBTTag getAllNBTTags() {
		Validate.isTrue(bukkitChunk.isLoaded(), "Chunk have to be loaded to access the NBT tag.");
		if (!nbtTags.containsKey(bukkitChunk))
			setAllNBTTags(new NBTTag());
		return nbtTags.get(bukkitChunk);
	}

	public void setAllNBTTags(NBTTag tag) {
		Validate.isTrue(bukkitChunk.isLoaded(), "Chunk have to be loaded to set the NBT tag.");
		nbtTags.put(bukkitChunk, tag);
	}

	public void removeAllNBTTags() {
		Validate.isTrue(bukkitChunk.isLoaded(), "Chunk have to be loaded to remove the NBT tag.");
		nbtTags.remove(bukkitChunk);
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
