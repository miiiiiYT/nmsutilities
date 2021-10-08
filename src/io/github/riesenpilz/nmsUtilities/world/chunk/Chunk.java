package io.github.riesenpilz.nmsUtilities.world.chunk;

import java.io.IOException;
import java.util.HashMap;

import org.apache.commons.lang.Validate;
import org.bukkit.Location;
import org.bukkit.entity.Entity;

import io.github.riesenpilz.nmsUtilities.entity.WorldEntity;
import io.github.riesenpilz.nmsUtilities.nbt.NBTTag;
import io.github.riesenpilz.nmsUtilities.nbt.NBTType;
import io.github.riesenpilz.nmsUtilities.world.ServerWorld;
import io.github.riesenpilz.nmsUtilities.world.chunk.storage.IOWorker;
import net.minecraft.server.v1_16_R3.ChunkCoordIntPair;

public class Chunk {
	public static final HashMap<org.bukkit.Chunk, NBTTag> nbtTags = new HashMap<>();
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
		return new Location(getBukkit().getWorld(), getX() * 16, 0, getZ() * 16);
	}

	public org.bukkit.Chunk getBukkit() {
		return bukkitChunk;
	}

	public NBTTag getAllNBTTags() {
		Validate.isTrue(bukkitChunk.isLoaded(), "Chunk have to be loaded to access the NBT tag.");
		NBTTag entities = new NBTTag();
		for (Entity entity : getEntities())
			entities.setNBTTag(entity.getUniqueId().toString(), WorldEntity.getWorldEntity(entity).getNBTTag());
		NBTTag tag = nbtTags.containsKey(bukkitChunk) ? nbtTags.get(bukkitChunk) : new NBTTag();
		tag.set("entities", entities);
		return tag;
	}

	public void setAllNBTTags(NBTTag tag) {
		Validate.isTrue(bukkitChunk.isLoaded(), "Chunk have to be loaded to set the NBT tag.");
		nbtTags.put(bukkitChunk, tag);
		if (!tag.hasKeyWithValueType("entities", NBTType.NBT_TAG))
			return;
		NBTTag entities = tag.getNBTTag("entities");
		for (Entity entity : getEntities())
			WorldEntity.getWorldEntity(entity).setNBTTag(entities.getNBTTag(entity.getUniqueId().toString()));
	}

	public void removeAllNBTTags() {
		Validate.isTrue(bukkitChunk.isLoaded(), "Chunk have to be loaded to remove the NBT tag.");
		nbtTags.remove(bukkitChunk);
		for (Entity entity : getEntities())
			WorldEntity.getWorldEntity(entity).removeNBTTag();
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

	public Entity[] getEntities() {
		return bukkitChunk.getEntities();
	}

	public int getX() {
		return bukkitChunk.getX();
	}

	public int getZ() {
		return bukkitChunk.getZ();
	}

	public IOWorker getIOWorker() {
		return getWorld().getIOWorker();
	}

	public void load() {
		try {
			setAllNBTTags(NBTTag.getNBTTagOf(getIOWorker().read(new ChunkCoordIntPair(getX(), getZ()))));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void save() {
		getIOWorker().write(new ChunkCoordIntPair(getX(), getZ()), getAllNBTTags().getNMS());
		removeAllNBTTags();
	}
}
