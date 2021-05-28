package io.github.riesenpilz.nms.entity;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftEntity;

import com.google.gson.JsonObject;

import io.github.riesenpilz.nms.nbt.NBTTag;
import io.github.riesenpilz.nms.world.ServerWorld;
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

	public NBTTag getAsNBTTag() {
		return new NBTTag(getNMS().save(new NBTTagCompound()));
	}

	public void setTags(JsonObject jsonObject) {
		JsonObject config = getWorld().getConfig("entities");
		config.add(getUUIDString(), jsonObject);
		getWorld().setConfig("entities", config);
	}

	public JsonObject getTags() {
		final JsonObject config = getWorld().getConfig("entities");
		return config.has(getUUIDString()) ? config.getAsJsonObject(getUUIDString()) : new JsonObject();
	}

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
}
