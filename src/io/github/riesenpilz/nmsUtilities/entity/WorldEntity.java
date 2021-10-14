package io.github.riesenpilz.nmsUtilities.entity;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import javax.annotation.Nullable;

import org.apache.commons.lang.Validate;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftEntity;
import org.bukkit.entity.Pose;

import io.github.riesenpilz.nmsUtilities.nbt.NBTTag;
import io.github.riesenpilz.nmsUtilities.packet.ByteBuilder;
import io.github.riesenpilz.nmsUtilities.packet.PacketUtils;
import io.github.riesenpilz.nmsUtilities.world.ServerWorld;
import io.github.riesenpilz.nmsUtilities.world.chunk.Chunk;
import net.minecraft.server.v1_16_R3.EntityPose;
import net.minecraft.server.v1_16_R3.IChatBaseComponent;
import net.minecraft.server.v1_16_R3.NBTTagCompound;

/**
 * Represents a {@link org.bukkit.entity.Entity}.
 *
 */
public class WorldEntity {

	/**
	 * Stores all custom entity tags. Internal use only!
	 * 
	 * @see WorldEntity#getNBTTag()
	 */
	public static final Map<org.bukkit.entity.Entity, NBTTag> ENTITY_TAGS = new HashMap<>();

	private final org.bukkit.entity.Entity bukkit;

	protected WorldEntity(org.bukkit.entity.Entity bukkit) {
		Validate.notNull(bukkit);
		this.bukkit = bukkit;
	}

	protected WorldEntity(UUID uuid) {
		final org.bukkit.entity.Entity entity = Bukkit.getEntity(uuid);
		Validate.notNull(entity);
		bukkit = entity;
	}

	public static WorldEntity getWorldEntity(UUID uuid) {
		return new WorldEntity(uuid);
	}

	public static WorldEntity getWorldEntity(org.bukkit.entity.Entity bukkit) {
		return new WorldEntity(bukkit);
	}

	public static WorldEntity getWorldEntity(int entityId, org.bukkit.World world) {
		final @Nullable WorldEntity entity = ServerWorld.getWorldOf(world).getEntityById(entityId);
		Validate.notNull(entity);
		return entity;
	}

	public static WorldEntity getWorldEntity(net.minecraft.server.v1_16_R3.Entity nms) {
		Validate.notNull(nms);
		return new WorldEntity(nms.getUniqueID());
	}

	public net.minecraft.server.v1_16_R3.Entity getNMS() {
		return ((CraftEntity) bukkit).getHandle();
	}

	public void loadFromNBTTag(NBTTag nbtTag) {
		Validate.notNull(nbtTag);
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
		return Chunk.getChunkOf(bukkit.getLocation().getChunk());
	}

	public Location getLocation() {
		return bukkit.getLocation();
	}

	/**
	 * Gets the Custom tag of the entity.
	 * 
	 * @return the tag
	 */
	@Nullable
	public NBTTag getNBTTag() {
		return ENTITY_TAGS.get(bukkit);
	}

	/**
	 * Sets the Custom tag of the entity.
	 * 
	 * @param tag the tag to set
	 */
	public void setNBTTag(@Nullable NBTTag tag) {
		ENTITY_TAGS.put(bukkit, tag);
	}

	/**
	 * Removes the Custom tag
	 */
	public void removeNBTTag() {
		ENTITY_TAGS.remove(bukkit);
	}

	// DataWatcher (DW)
	public static DataWatcherItem<Byte> getDWStates(byte states) {
		return new DataWatcherItem<>(0, states);
	}

	public static class DWStatesBuilder extends ByteBuilder {
		private boolean onFire;
		private boolean crouching;
		private boolean unused;
		private boolean sprinting;
		private boolean swimming;
		private boolean invisable;
		private boolean glowing;
		private boolean elytraFlying;

		public DWStatesBuilder setOnFire(boolean onFire) {
			this.onFire = onFire;
			return this;
		}

		public DWStatesBuilder setCrouching(boolean crouching) {
			this.crouching = crouching;
			return this;
		}

		public DWStatesBuilder setUnused(boolean unused) {
			this.unused = unused;
			return this;
		}

		public DWStatesBuilder setSprinting(boolean sprinting) {
			this.sprinting = sprinting;
			return this;
		}

		public DWStatesBuilder setSwimming(boolean swimming) {
			this.swimming = swimming;
			return this;
		}

		public DWStatesBuilder setInvisable(boolean invisable) {
			this.invisable = invisable;
			return this;
		}

		public DWStatesBuilder setGlowing(boolean glowing) {
			this.glowing = glowing;
			return this;
		}

		public DWStatesBuilder setElytraFlying(boolean elytraFlying) {
			this.elytraFlying = elytraFlying;
			return this;
		}

		public byte build() {
			return super.build(onFire, crouching, unused, sprinting, swimming, invisable, glowing, elytraFlying);
		}
	}

	public static DataWatcherItem<Integer> getDWAirTicks(int airTicks) {
		return new DataWatcherItem<>(1, airTicks);
	}

	public static DataWatcherItem<Optional<IChatBaseComponent>> getDWCustomName(
			Optional<IChatBaseComponent> customName) {
		return new DataWatcherItem<>(2, customName);
	}

	public static DataWatcherItem<Boolean> getDWIsCustomNameVisible(boolean customNaeVisible) {
		return new DataWatcherItem<>(3, customNaeVisible);
	}

	public static DataWatcherItem<Boolean> getDWIsSilent(boolean silent) {
		return new DataWatcherItem<>(4, silent);
	}

	public static DataWatcherItem<Boolean> getDWHasNoGravity(boolean noGravity) {
		return new DataWatcherItem<>(5, noGravity);
	}

	public static DataWatcherItem<EntityPose> getDWPose(Pose pose) {
		return new DataWatcherItem<>(6, PacketUtils.toEntityPose(pose));
	}
}
