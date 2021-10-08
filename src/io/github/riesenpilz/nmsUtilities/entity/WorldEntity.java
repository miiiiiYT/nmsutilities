package io.github.riesenpilz.nmsUtilities.entity;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Nullable;

import org.apache.commons.lang.Validate;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftEntity;

import io.github.riesenpilz.nmsUtilities.nbt.NBTTag;
import io.github.riesenpilz.nmsUtilities.world.ServerWorld;
import io.github.riesenpilz.nmsUtilities.world.chunk.Chunk;
import net.minecraft.server.v1_16_R3.NBTTagCompound;
import net.minecraft.server.v1_16_R3.WorldServer;

public class WorldEntity {
	public static final Map<org.bukkit.entity.Entity, NBTTag> tags = new HashMap<>();
	private final org.bukkit.entity.Entity bukkit;

	protected WorldEntity(org.bukkit.entity.Entity bukkit) {
		Validate.notNull(bukkit);
		this.bukkit = bukkit;
	}

	protected WorldEntity(UUID uuid) {
		bukkit = Bukkit.getEntity(uuid);
	}

	public static WorldEntity getWorldEntity(UUID uuid) {
		return new WorldEntity(uuid);
	}

	public static WorldEntity getWorldEntity(org.bukkit.entity.Entity bukkit) {
		return new WorldEntity(bukkit);
	}

	public static WorldEntity getWorldEntity(int entityId, org.bukkit.World world) {
		Validate.notNull(world);
		final WorldServer nmsWorld = ServerWorld.getWorldOf(world).getNMS();
		final net.minecraft.server.v1_16_R3.Entity nmsEntity = nmsWorld.getEntity(entityId);
		Validate.notNull(nmsEntity);
		return new WorldEntity(Bukkit.getEntity(nmsEntity.getUniqueID()));
	}

	public static WorldEntity getWorldEntity(net.minecraft.server.v1_16_R3.Entity nms) {
		Validate.notNull(nms);
		return new WorldEntity(nms.getUniqueID());
	}

	public net.minecraft.server.v1_16_R3.Entity getNMS() {
		return ((CraftEntity) bukkit).getHandle();
	}

	public void loadFromNBTTag(NBTTag nbtTag) {
		getNMS().load(nbtTag.getNMS());
	}

	public NBTTag saveToNBTTag() {
		return NBTTag.getNBTTagOf(getNMS().save(new NBTTagCompound()));
	}

	public ServerWorld getWorld() {
		return ServerWorld.getWorldOf(getBukkit().getWorld());
	}

	public org.bukkit.entity.Entity getBukkit() {
		return bukkit;
	}

	public Entity getEntity() {
		return Entity.fromNMS(getNMS());
	}

	public String getUUIDString() {
		return getBukkit().getUniqueId().toString();
	}

	public int getId() {
		return bukkit.getEntityId();
	}

	public Chunk getChunk() {
		return new Chunk(bukkit.getLocation().getChunk());
	}

	public Location getLocation() {
		return bukkit.getLocation();
	}

	@Nullable
	public NBTTag getNBTTag() {
		return tags.get(bukkit);
	}

	public void setNBTTag(@Nullable NBTTag tag) {
		tags.put(bukkit, tag);
	}

	public void removeNBTTag() {
		tags.remove(bukkit);
	}
}
