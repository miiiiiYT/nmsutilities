package io.github.riesenpilz.nmsUtilities.packet.playOut;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import io.github.riesenpilz.nmsUtilities.reflections.Field;
import net.minecraft.server.v1_16_R3.Packet;
import net.minecraft.server.v1_16_R3.PacketListenerPlayOut;
import net.minecraft.server.v1_16_R3.PacketPlayOutEntityTeleport;

/**
 * https://wiki.vg/Protocol#Entity_Teleport
 * <p>
 * This packet is sent by the server when an entity moves more than 8 blocks.
 * <p>
 * Packet ID: 0x61<br>
 * State: Play<br>
 * Bound To: Client
 * 
 * @author Martin
 *
 */
public class PacketPlayOutEntityTeleportEvent extends PacketPlayOutEntityEvent {

	private Location location;

	/**
	 * New angle, not a delta.
	 */
	private byte yaw;

	/**
	 * New angle, not a delta.
	 */
	private byte pitch;
	private boolean onGround;

	public PacketPlayOutEntityTeleportEvent(Player injectedPlayer, PacketPlayOutEntityTeleport packet) {
		super(injectedPlayer, packet);

		location = new Location(injectedPlayer.getWorld(), Field.get(packet, "b", double.class),
				Field.get(packet, "c", double.class), Field.get(packet, "d", double.class));
		yaw = Field.get(packet, "e", byte.class);
		pitch = Field.get(packet, "f", byte.class);
		onGround = Field.get(packet, "g", boolean.class);
	}

	public PacketPlayOutEntityTeleportEvent(Player injectedPlayer, int entityId, Location location, byte yaw,
			byte pitch, boolean onGround) {
		super(injectedPlayer, entityId);
		this.location = location;
		this.yaw = yaw;
		this.pitch = pitch;
		this.onGround = onGround;
	}

	public Location getLocation() {
		return location;
	}

	public byte getYaw() {
		return yaw;
	}

	public byte getPitch() {
		return pitch;
	}

	public boolean isOnGround() {
		return onGround;
	}

	@Override
	public Packet<PacketListenerPlayOut> getNMS() {
		final PacketPlayOutEntityTeleport packet = new PacketPlayOutEntityTeleport();
		Field.set(packet, "a", getEntityId());
		Field.set(packet, "b", location.getX());
		Field.set(packet, "c", location.getY());
		Field.set(packet, "d", location.getZ());
		Field.set(packet, "e", yaw);
		Field.set(packet, "f", pitch);
		Field.set(packet, "g", onGround);
		return packet;
	}

	@Override
	public int getPacketID() {
		return 0x61;
	}

	@Override
	public String getProtocolURLString() {
		return "https://wiki.vg/Protocol#Entity_Teleport";
	}
}