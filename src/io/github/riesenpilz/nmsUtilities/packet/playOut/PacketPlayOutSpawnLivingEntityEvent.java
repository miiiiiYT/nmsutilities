package io.github.riesenpilz.nmsUtilities.packet.playOut;

import java.util.UUID;

import org.apache.commons.lang.Validate;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import io.github.riesenpilz.nmsUtilities.reflections.Field;
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
public class PacketPlayOutSpawnLivingEntityEvent extends PacketPlayOutEntityEvent {

	private UUID uuid;
	private Location location;
	private EntityType type;
	private Vector velocity;
	private float headPitch;

	@SuppressWarnings("deprecation")
	public PacketPlayOutSpawnLivingEntityEvent(Player injectedPlayer, PacketPlayOutSpawnEntityLiving packet) {
		super(injectedPlayer, packet);

		uuid = Field.get(packet, "b", UUID.class);
		type = EntityType.fromId(Field.get(packet, "c", int.class));
		location = new Location(injectedPlayer.getWorld(), Field.get(packet, "d", double.class),
				Field.get(packet, "e", double.class), Field.get(packet, "f", double.class),
				((float) (Field.get(packet, "j", byte.class) / 256f)) * 360,
				((float) (Field.get(packet, "k", byte.class) / 256f)) * 360);
		headPitch = ((float) (Field.get(packet, "l", byte.class) / 256f)) * 360;
		velocity = new Vector((double) (Field.get(packet, "g", int.class) / 8000D),
				(double) (Field.get(packet, "h", int.class) / 8000D),
				(double) (Field.get(packet, "i", int.class) / 8000D));
	}

	public PacketPlayOutSpawnLivingEntityEvent(Player injectedPlayer, int entityId, UUID uuid, Location location,
			EntityType type, Vector velocity, float headPitch) {
		super(injectedPlayer, entityId);

		Validate.notNull(uuid);
		Validate.notNull(location);
		Validate.notNull(type);
		Validate.notNull(velocity);

		this.uuid = uuid;
		this.location = location;
		this.type = type;
		this.velocity = velocity;
		this.headPitch = headPitch;
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
		Field.set(packet, "a", getEntityId());
		Field.set(packet, "b", uuid);
		Field.set(packet, "c", type.getTypeId());
		Field.set(packet, "d", location.getX());
		Field.set(packet, "e", location.getY());
		Field.set(packet, "f", location.getZ());
		Field.set(packet, "g", velocity.getX() * 8000);
		Field.set(packet, "h", velocity.getY() * 8000);
		Field.set(packet, "i", velocity.getZ() * 8000);
		Field.set(packet, "j", (byte) location.getYaw() * 256 / 360);
		Field.set(packet, "k", (byte) location.getPitch() * 256 / 360);
		Field.set(packet, "l", (byte) headPitch * 256 / 360);
		return packet;
	}

	@Override
	public int getPacketID() {
		return 0x02;
	}

	@Override
	public String getProtocolURLString() {
		return "https://wiki.vg/Protocol#Spawn_Living_Entity";
	}
}
