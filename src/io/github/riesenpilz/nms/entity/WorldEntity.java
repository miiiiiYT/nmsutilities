package io.github.riesenpilz.nms.entity;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftEntity;

import com.google.gson.JsonObject;

import io.github.riesenpilz.nms.nbt.NBTTag;
import io.github.riesenpilz.nms.world.ServerWorld;
import io.github.riesenpilz.nms.world.chunk.Chunk;
import net.minecraft.server.v1_16_R3.NBTTagCompound;
import net.minecraft.server.v1_16_R3.WorldServer;

public class WorldEntity {
	private final org.bukkit.entity.Entity entity;

	public WorldEntity(org.bukkit.entity.Entity entity) {
		this.entity = entity;
	}

	public WorldEntity(int entityID, org.bukkit.World world) {
		final WorldServer nmsWorld = new ServerWorld(world).getNMS();
		this.entity = Bukkit.getEntity(nmsWorld.getEntity(entityID).getUniqueID());
	}

	public WorldEntity(UUID uuid) {
		this.entity = Bukkit.getEntity(uuid);
	}

	public net.minecraft.server.v1_16_R3.Entity getNMS() {
		return ((CraftEntity) entity).getHandle();
	}

	public void loadFromNBTTag(NBTTag nbtTag) {
		getNMS().load(nbtTag.getNMS());
	}

	public NBTTag saveToNBTTag() {
		return new NBTTag(getNMS().save(new NBTTagCompound()));
	}

	@Deprecated
	public void setTags(JsonObject jsonObject) {
		JsonObject config = getWorld().getConfig("entities");
		config.add(getUUIDString(), jsonObject);
		getWorld().setConfig("entities", config);
	}

	@Deprecated
	public JsonObject getTags() {
		final JsonObject config = getWorld().getConfig("entities");
		return config.has(getUUIDString()) ? config.getAsJsonObject(getUUIDString()) : new JsonObject();
	}

	@Deprecated
	public void removePermaTag() {
		setTags(null);
	}

	public ServerWorld getWorld() {
		return new ServerWorld(getBukkitEntity().getWorld());
	}

	public org.bukkit.entity.Entity getBukkitEntity() {
		return entity;
	}

	public Entity getEntity() {
		return Entity.fromNMS(getNMS());
	}

	public String getUUIDString() {
		return getBukkitEntity().getUniqueId().toString();
	}

	public int getID() {
		return entity.getEntityId();
	}

	public Chunk getChunk() {
		return new Chunk(entity.getLocation().getChunk());
	}

	public Location getLocation() {
		return entity.getLocation();
	}

	public NBTTag getNBTTag() {
		final NBTTag allNBTTags = getChunk().getAllNBTTags();
		final NBTTag blockTags = allNBTTags.getOrDefNBTTag("entity", new NBTTag());
		return blockTags.getOrDefNBTTag(getUUIDString(), new NBTTag());
	}

	public void setNBTTag(NBTTag tag) {
		final NBTTag allNBTTags = getChunk().getAllNBTTags();
		final NBTTag blockTags = allNBTTags.getOrDefNBTTag("entity", new NBTTag());
		blockTags.setNBTTag(getUUIDString(), tag);
	}

	public void removeNBTTag() {
		final NBTTag allNBTTags = getChunk().getAllNBTTags();
		final NBTTag blockTags = allNBTTags.getOrDefNBTTag("entity", new NBTTag());
		blockTags.remove(getUUIDString());
	}
}
