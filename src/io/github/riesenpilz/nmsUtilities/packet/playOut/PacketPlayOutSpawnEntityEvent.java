package io.github.riesenpilz.nmsUtilities.packet.playOut;

import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import io.github.riesenpilz.nmsUtilities.packet.PacketUtils;
import io.github.riesenpilz.nmsUtilities.reflections.Field;
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
public class PacketPlayOutSpawnEntityEvent extends PacketPlayOutEntityEvent {

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

	public PacketPlayOutSpawnEntityEvent(Player injectedPlayer, PacketPlayOutSpawnEntity packet) {
		super(injectedPlayer, packet);
		uuid = Field.get(packet, "b", UUID.class);
		location = new Location(injectedPlayer.getWorld(), Field.get(packet, "c", double.class),
				Field.get(packet, "d", double.class), Field.get(packet, "e", double.class),
				((float) (Field.get(packet, "i", int.class) / 256f)) * 360,
				((float) (Field.get(packet, "j", int.class) / 256f)) * 360);
		type = PacketUtils.toEntityType((Field.get(packet, "k", EntityTypes.class)));
		velocity = new Vector((double) (Field.get(packet, "f", int.class) / 8000D),
				(double) (Field.get(packet, "g", int.class) / 8000D),
				(double) (Field.get(packet, "h", int.class) / 8000D));
		data = Field.get(packet, "l", int.class);
	}

	public PacketPlayOutSpawnEntityEvent(Player injectedPlayer, int entityId, UUID uuid, Location location,
			EntityType type, Vector velocity, int data) {
		super(injectedPlayer, entityId);
		this.uuid = uuid;
		this.location = location;
		this.type = type;
		this.velocity = velocity;
		this.data = data;
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

	@Override
	public Packet<PacketListenerPlayOut> getNMS() {
		return new PacketPlayOutSpawnEntity(getEntityId(), uuid, location.getX(), location.getY(), location.getZ(),
				location.getYaw(), location.getPitch(), PacketUtils.toEntityTypes(type), data,
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
