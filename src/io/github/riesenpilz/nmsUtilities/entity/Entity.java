package io.github.riesenpilz.nmsUtilities.entity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

import org.bukkit.Location;
import org.bukkit.entity.EntityType;

import io.github.riesenpilz.nmsUtilities.nbt.NBTTag;
import io.github.riesenpilz.nmsUtilities.packet.PacketUtils;
import io.github.riesenpilz.nmsUtilities.reflections.Field;
import io.github.riesenpilz.nmsUtilities.world.ServerWorld;
import io.github.riesenpilz.nmsUtilities.world.World;
import net.minecraft.server.v1_16_R3.NBTTagCompound;
import net.minecraft.server.v1_16_R3.Packet;
import net.minecraft.server.v1_16_R3.PacketPlayOutSpawnEntity;

/**
 * Not finished yet!
 *
 */
public abstract class Entity {
	private List<DataWatcherItem<?>> dataWatcher;
	private final net.minecraft.server.v1_16_R3.Entity entity;

	public Entity(EntityType type, World world) {
		entity = new net.minecraft.server.v1_16_R3.Entity(PacketUtils.toEntityTypes(type), world.getNMS()) {

			@Override
			protected void saveData(NBTTagCompound arg0) {
				arg0.a(Entity.this.saveData().getNMS());
			}

			@Override
			protected void loadData(NBTTagCompound arg0) {
				Entity.this.loadData(NBTTag.getNBTTagOf(arg0));
			}

			@Override
			protected void initDatawatcher() {
				Entity.this.initDatawatcher();
			}

			@Override
			public Packet<?> P() {
				return new PacketPlayOutSpawnEntity(entity);
			}
		};
	}

	private Entity(net.minecraft.server.v1_16_R3.Entity nms) {
		this.entity = nms;
	}

	public static Entity fromNMS(net.minecraft.server.v1_16_R3.Entity nms) {
		return new Entity(nms) {

			@Override
			public NBTTag saveData() {
				final NBTTag nbtTag = NBTTag.getNBTTagOf(nms.save(new NBTTagCompound()));
				nbtTag.set("hi", true);
				return nbtTag;
			}

			@Override
			public void loadData(NBTTag tag) {
				nms.load(tag.getNMS());
			}
		};
	}

	protected void initDatawatcher() {
		dataWatcher = new ArrayList<>();
	}

	public List<DataWatcherItem<?>> getDataWatcher() {
		return dataWatcher;
	}

	public abstract void loadData(NBTTag tag);

	public abstract NBTTag saveData();

	public final net.minecraft.server.v1_16_R3.Entity getNMS() {
		return entity;
	}

	public final int getId() {
		return entity.getId();
	}

	@SuppressWarnings("deprecation")
	public final EntityType getType() {
		return EntityType.fromName(entity.getEntityType().f());
	}

	public final World getWorld() {
		return World.getWorldOf(entity.world);
	}

	public final void setWorld(World world) {
		entity.world = world.getNMS();
	}

	public final List<Entity> getPassengers() {
		List<Entity> passengers = new ArrayList<>();
		for (net.minecraft.server.v1_16_R3.Entity nms : entity.passengers)
			passengers.add(Entity.fromNMS(nms));
		return passengers;
	}

	public final void setPassengers(List<Entity> passengers) {
		List<net.minecraft.server.v1_16_R3.Entity> nms = new ArrayList<>();
		for (Entity entity : passengers)
			nms.add(entity.getNMS());
		for (int i = 0; i < entity.getPassengers().size(); i++)
			entity.passengers.remove(i);
		entity.passengers.addAll(nms);
	}

	public final void addPassanger(Entity entity) {
		this.entity.passengers.add(entity.getNMS());
	}

	public final void setVehicle(Entity entity) {
		Field.set(this.entity, "vehicle", entity);
	}

	public final Entity getVehicle() {
		return fromNMS(entity.getVehicle());
	}

	public final boolean hasVehicle() {
		return getVehicle() != null;
	}

	public Entity getRootVehicle() {
		return Entity.fromNMS(entity.getRootVehicle());
	}

	public final boolean isAttachedToPlayer() {
		return entity.attachedToPlayer;
	}

	public final void setAttachedToPlayer(boolean attachedToPlayer) {
		entity.attachedToPlayer = attachedToPlayer;
	}

	public final void setLastLocation(Location location) {
		entity.lastX = location.getX();
		entity.lastY = location.getX();
		entity.lastZ = location.getX();
		entity.lastYaw = location.getYaw();
		entity.lastPitch = location.getPitch();
	}

	public final Location getLastLocation() {
		return new Location(null, entity.lastX, entity.lastY, entity.lastZ, entity.lastYaw, entity.lastPitch);
	}

	public final Set<Entity> getAllPassangers() {
		Set<Entity> entities = new HashSet<>();
		for (net.minecraft.server.v1_16_R3.Entity nms : entity.getAllPassengers())
			entities.add(Entity.fromNMS(nms));
		return entities;
	}

	public final void setLocation(Location location) {
		entity.world = World.getWorldOf(location.getWorld()).getNMS();
		entity.setLocation(location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch());
	}

	public final Location getLocation() {
		return new Location(getWorld().getBukkit(), entity.locX(), entity.locY(), entity.locZ(), entity.yaw,
				entity.pitch);
	}

	public void spawn() {
		entity.getWorld().addEntity(entity);
	}

	/**
	 * public boolean i; <br>
	 * public boolean positionChanged; <br>
	 * public boolean v; <br>
	 * public boolean velocityChanged; <br>
	 * public boolean dead; <br>
	 * public float z; <br>
	 * public float A; <br>
	 * public float B; <br>
	 * public float fallDistance; <br>
	 * public double D; <br>
	 * public double E; <br>
	 * public double F; <br>
	 * public float G; <br>
	 * public boolean noclip; <br>
	 * public float I; <br>
	 * public int ticksLived; <br>
	 * public int fireTicks; <br>
	 * public boolean inWater; <br>
	 * public int noDamageTicks; <br>
	 * public boolean inChunk; <br>
	 * public int chunkX; <br>
	 * public int chunkY; <br>
	 * public int chunkZ; <br>
	 * public boolean Y; <br>
	 * public boolean impulse; <br>
	 * public int portalCooldown; <br>
	 * public boolean glowing; <br>
	 * 
	 */

	/**
	 * Gets a new entity id for fake entities in packets.
	 * 
	 * @return new entity id
	 */
	public static int getNewEntityId() {
		return Field.getConstant(net.minecraft.server.v1_16_R3.Entity.class, "entityCount", AtomicInteger.class)
				.incrementAndGet();
	}

	public void teleportTo(Location location) {
		entity.teleportTo(ServerWorld.getWorldOf(location.getWorld()).getNMS(), PacketUtils.toBlockPosition(location));
	}
}
