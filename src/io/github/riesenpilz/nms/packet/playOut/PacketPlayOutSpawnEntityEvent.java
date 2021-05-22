package io.github.riesenpilz.nms.packet.playOut;

import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import io.github.riesenpilz.nms.reflections.Field;
import net.minecraft.server.v1_16_R3.EntityTypes;
import net.minecraft.server.v1_16_R3.Packet;
import net.minecraft.server.v1_16_R3.PacketListenerPlayOut;
import net.minecraft.server.v1_16_R3.PacketPlayOutSpawnEntity;
import net.minecraft.server.v1_16_R3.Vec3D;

/**
 * https://wiki.vg/Protocol#Spawn_Entity<br>
 * <br>
 * Sent by the server when a vehicle or other non-living entity is created.<br>
 * <br>
 * Packet ID: 0x00<br>
 * State: Play<br>
 * Bound To: Client
 * 
 * @author Martin
 *
 */
public class PacketPlayOutSpawnEntityEvent extends PacketPlayOutEvent {

	private int entityID;
	private UUID uuid;
	private Location location;

	/**
	 * Always sent, but only used when Data is greater than 0 (except for some
	 * entities which always ignore it; see Object Data for details).
	 */
	private EntityType type;
	private Vector velocity;

	/**
	 * Meaning dependent on the value of the Type field, see Object Data for
	 * details.
	 */
	private int data;

	public PacketPlayOutSpawnEntityEvent(Player injectedPlayer, int entityID, UUID uuid, Location location,
			EntityType type, Vector velocity, int data) {
		super(injectedPlayer);
		this.entityID = entityID;
		this.uuid = uuid;
		this.location = location;
		this.type = type;
		this.velocity = velocity;
		this.data = data;
	}

	@SuppressWarnings("deprecation")
	public PacketPlayOutSpawnEntityEvent(Player injectedPlayer, PacketPlayOutSpawnEntity packet) {
		super(injectedPlayer);
		entityID = (int) new Field(PacketPlayOutSpawnEntity.class, "a").get(packet);
		uuid = (UUID) new Field(PacketPlayOutSpawnEntity.class, "b").get(packet);
		location = new Location(injectedPlayer.getWorld(),
				(double) new Field(PacketPlayOutSpawnEntity.class, "c").get(packet),
				(double) new Field(PacketPlayOutSpawnEntity.class, "d").get(packet),
				(double) new Field(PacketPlayOutSpawnEntity.class, "e").get(packet),
				((float) (((int) new Field(PacketPlayOutSpawnEntity.class, "i").get(packet)) / 256)) * 360,
				((float) (((int) new Field(PacketPlayOutSpawnEntity.class, "j").get(packet)) / 256)) * 360);
		type = EntityType.fromName(((EntityTypes<?>) new Field(PacketPlayOutSpawnEntity.class, "k").get(packet)).f());
		velocity = new Vector((double) ((int) new Field(PacketPlayOutSpawnEntity.class, "f").get(packet) / 8000),
				(double) ((int) new Field(PacketPlayOutSpawnEntity.class, "g").get(packet) / 8000),
				(double) ((int) new Field(PacketPlayOutSpawnEntity.class, "h").get(packet) / 8000));
		data = (int) new Field(PacketPlayOutSpawnEntity.class, "l").get(packet);
	}

	public int getEntityID() {
		return entityID;
	}

	public UUID getUuid() {
		return uuid;
	}

	public Location getLocation() {
		return location;
	}

	public EntityType getType() {
		return type;
	}

	public Vector getVelocity() {
		return velocity;
	}

	public int getData() {
		return data;
	}

	@SuppressWarnings("deprecation")
	@Override
	public Packet<PacketListenerPlayOut> getNMS() {
		return new PacketPlayOutSpawnEntity(entityID, uuid, location.getX(), location.getY(), location.getZ(),
				location.getYaw(), location.getPitch(), EntityTypes.a(type.getName()).get(), data,
				new Vec3D(velocity.getX(), velocity.getY(), velocity.getZ()));
	}

	@Override
	public int getPacketID() {
		return 0x00;
	}

	@Override
	public String getProtocolURLString() {
		return "https://wiki.vg/Protocol#Spawn_Entity";
	}
}
