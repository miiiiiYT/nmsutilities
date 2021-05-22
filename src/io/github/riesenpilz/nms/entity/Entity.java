package io.github.riesenpilz.nms.entity;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftEntity;

import com.google.gson.JsonObject;

import io.github.riesenpilz.nms.nbt.NBTTag;
import io.github.riesenpilz.nms.world.World;
import net.minecraft.server.v1_16_R3.NBTTagCompound;
import net.minecraft.server.v1_16_R3.WorldServer;

public class Entity {
	private final org.bukkit.entity.Entity entity;

	public Entity(org.bukkit.entity.Entity entity) {
		this.entity = entity;
	}

	public Entity(net.minecraft.server.v1_16_R3.Entity nms) {
		this.entity = Bukkit.getEntity(nms.getUniqueID());
	}

	public Entity(int entityID, org.bukkit.World world) {
		final WorldServer nmsWorld = new World(world).getNMS();
		this.entity = Bukkit.getEntity(nmsWorld.getEntity(entityID).getUniqueID());
	}

	public Entity(UUID uuid) {
		this.entity = Bukkit.getEntity(uuid);
	}

	public net.minecraft.server.v1_16_R3.Entity getNMS() {
		return ((CraftEntity) entity).getHandle();
	}

	public void setNBTTag(NBTTag nbtTag) {
		getNMS().load(nbtTag.getNBTTagCompound());
	}

	public NBTTag getNBTTag() {
		return new NBTTag(getNMS().save(new NBTTagCompound()));
	}

	public void setPermaTag(JsonObject jsonObject) {
		JsonObject config = getWorld().getConfig("entities");
		config.add(getUUIDString(), jsonObject);
		getWorld().setConfig("entities", config);
	}

	public JsonObject getPermaTag() {
		final JsonObject config = getWorld().getConfig("entities");
		return config.has(getUUIDString()) ? config.getAsJsonObject(getUUIDString()) : new JsonObject();
	}

	public void removePermaTag() {
		setPermaTag(null);
	}

	public World getWorld() {
		return new World(getEntity().getWorld());
	}

	public org.bukkit.entity.Entity getEntity() {
		return entity;
	}

	public String getUUIDString() {
		return getEntity().getUniqueId().toString();
	}

	public int getID() {
		return entity.getEntityId();
	}
}
