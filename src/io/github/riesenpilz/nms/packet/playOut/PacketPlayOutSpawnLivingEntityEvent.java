package io.github.riesenpilz.nms.packet.playOut;

import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import io.github.riesenpilz.nms.reflections.Field;
import net.minecraft.server.v1_16_R3.Packet;
import net.minecraft.server.v1_16_R3.PacketListenerPlayOut;
import net.minecraft.server.v1_16_R3.PacketPlayOutSpawnEntityLiving;

/**
 * https://wiki.vg/Protocol#Spawn_Living_Entity<br>
 * <br>
 * Sent by the server when a living entity is spawned.<br>
 * <br>
 * Packet ID: 0x02<br>
 * State: Play<br>
 * Bound To: Client
 * 
 * @author Martin
 *
 */
public class PacketPlayOutSpawnLivingEntityEvent extends PacketPlayOutEvent {

	private int entityID;
	private UUID uuid;
	private Location location;
	private EntityType type;
	private Vector velocity;
	private float headPitch;

	public PacketPlayOutSpawnLivingEntityEvent(Player injectedPlayer, int entityID, UUID uuid, Location location,
			EntityType type, Vector velocity, float headPitch) {
		super(injectedPlayer);
		this.entityID = entityID;
		this.uuid = uuid;
		this.location = location;
		this.type = type;
		this.velocity = velocity;
		this.headPitch = headPitch;
	}

	@SuppressWarnings("deprecation")
	public PacketPlayOutSpawnLivingEntityEvent(Player injectedPlayer, PacketPlayOutSpawnEntityLiving packet) {
		super(injectedPlayer);
		entityID = (int) new Field(PacketPlayOutSpawnEntityLiving.class, "a").get(packet);
		uuid = (UUID) new Field(PacketPlayOutSpawnEntityLiving.class, "b").get(packet);
		type = EntityType.fromId((int) new Field(PacketPlayOutSpawnEntityLiving.class, "c").get(packet));
		location = new Location(injectedPlayer.getWorld(),
				(double) new Field(PacketPlayOutSpawnEntityLiving.class, "d").get(packet),
				(double) new Field(PacketPlayOutSpawnEntityLiving.class, "e").get(packet),
				(double) new Field(PacketPlayOutSpawnEntityLiving.class, "f").get(packet),
				((float) (((int) new Field(PacketPlayOutSpawnEntityLiving.class, "j").get(packet)) / 256)) * 360,
				((float) (((int) new Field(PacketPlayOutSpawnEntityLiving.class, "k").get(packet)) / 256)) * 360);
		headPitch = ((float) (((int) new Field(PacketPlayOutSpawnEntityLiving.class, "l").get(packet)) / 256)) * 360;
		velocity = new Vector((double) ((int) new Field(PacketPlayOutSpawnEntityLiving.class, "g").get(packet) / 8000),
				(double) ((int) new Field(PacketPlayOutSpawnEntityLiving.class, "h").get(packet) / 8000),
				(double) ((int) new Field(PacketPlayOutSpawnEntityLiving.class, "i").get(packet) / 8000));
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

	public float getHeadPitch() {
		return headPitch;
	}

	@SuppressWarnings("deprecation")
	@Override
	public Packet<PacketListenerPlayOut> getNMS() {
		final PacketPlayOutSpawnEntityLiving packet = new PacketPlayOutSpawnEntityLiving();
		new Field(PacketPlayOutSpawnEntityLiving.class, "a").set(packet, entityID);
		new Field(PacketPlayOutSpawnEntityLiving.class, "b").set(packet, uuid);
		new Field(PacketPlayOutSpawnEntityLiving.class, "c").set(packet, type.getTypeId());
		new Field(PacketPlayOutSpawnEntityLiving.class, "d").set(packet, location.getX());
		new Field(PacketPlayOutSpawnEntityLiving.class, "e").set(packet, location.getY());
		new Field(PacketPlayOutSpawnEntityLiving.class, "f").set(packet, location.getZ());
		new Field(PacketPlayOutSpawnEntityLiving.class, "g").set(packet, velocity.getX() * 8000);
		new Field(PacketPlayOutSpawnEntityLiving.class, "h").set(packet, velocity.getY() * 8000);
		new Field(PacketPlayOutSpawnEntityLiving.class, "i").set(packet, velocity.getZ() * 8000);
		new Field(PacketPlayOutSpawnEntityLiving.class, "j").set(packet, location.getYaw() * 256 / 360);
		new Field(PacketPlayOutSpawnEntityLiving.class, "k").set(packet, location.getPitch() * 256 / 360);
		new Field(PacketPlayOutSpawnEntityLiving.class, "l").set(packet, headPitch * 256 / 360);
		return packet;
	}

	@Override
	public int getPacketID() {
		return 2;
	}

	@Override
	public String getProtocolURLString() {
		return "https://wiki.vg/Protocol#Spawn_Living_Entity";
	}
}
