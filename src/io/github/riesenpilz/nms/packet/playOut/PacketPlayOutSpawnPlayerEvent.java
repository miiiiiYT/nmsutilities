package io.github.riesenpilz.nms.packet.playOut;

import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import io.github.riesenpilz.nms.reflections.Field;
import net.minecraft.server.v1_16_R3.Packet;
import net.minecraft.server.v1_16_R3.PacketListenerPlayOut;
import net.minecraft.server.v1_16_R3.PacketPlayOutNamedEntitySpawn;

/**
 * https://wiki.vg/Protocol#Spawn_Player<br>
 * <br>
 * This packet is sent by the server when a player comes into visible range, not
 * when a player joins.<br>
 * <br>
 * This packet must be sent after the Player Info packet that adds the player
 * data for the client to use when spawning a player. If the Player Info for the
 * player spawned by this packet is not present when this packet arrives,
 * Notchian clients will not spawn the player entity. The Player Info packet
 * includes skin/cape data. <br>
 * <br>
 * Servers can, however, safely spawn player entities for players not in visible
 * range. The client appears to handle it correctly.<br>
 * <br>
 * <br>
 * When in online mode, the UUIDs must be valid and have valid skin blobs.<br>
 * <br>
 * In offline mode, UUID v3 is used with the String OfflinePlayer:<player name>,
 * encoded in UTF-8 (and case-sensitive). The Notchain server uses
 * UUID.nameUUIDFromBytes, implemented by OpenJDK here.<br>
 * <br>
 * For NPCs UUID v2 should be used. Note:<br>
 * <br>
 * <code><+Grum> i will never confirm this as a feature you know that :)</code><br>
 * In an example UUID, xxxxxxxx-xxxx-Yxxx-xxxx-xxxxxxxxxxxx, the UUID version is
 * specified by Y. So, for UUID v3, Y will always be 3, and for UUID v2, Y will
 * always be 2.<br>
 * <br>
 * Packet ID: 0x04<br>
 * State: Play<br>
 * Bound To: Client
 * 
 * @author Martin
 *
 */
public class PacketPlayOutSpawnPlayerEvent extends PacketPlayOutEntityEvent {

	private UUID uuid;
	private Location location;

	PacketPlayOutSpawnPlayerEvent(Player injectedPlayer, int entityId, UUID uuid, Location location) {
		super(injectedPlayer, entityId);
		this.uuid = uuid;
		this.location = location;
	}

	public PacketPlayOutSpawnPlayerEvent(Player injectedPlayer, PacketPlayOutNamedEntitySpawn packet) {
		super(injectedPlayer, packet);
		uuid = Field.get(packet, "b", UUID.class);
		location = new Location(injectedPlayer.getWorld(), Field.get(packet, "c", double.class),
				Field.get(packet, "d", double.class), Field.get(packet, "e", double.class),
				((float) (Field.get(packet, "f", int.class) / 256f)) * 360,
				((float) (Field.get(packet, "g", int.class) / 256f)) * 360);
	}

	public UUID getUuid() {
		return uuid;
	}

	public Location getLocation() {
		return location;
	}

	@Override
	public Packet<PacketListenerPlayOut> getNMS() {
		final PacketPlayOutNamedEntitySpawn packet = new PacketPlayOutNamedEntitySpawn();
		Field.set(packet, "a", getEntityId());
		Field.set(packet, "b", uuid);
		Field.set(packet, "c", location.getX());
		Field.set(packet, "d", location.getY());
		Field.set(packet, "e", location.getZ());
		Field.set(packet, "f", (byte) ((int) location.getYaw() * 256 / 360));
		Field.set(packet, "g", (byte) ((int) location.getPitch() * 256 / 360));
		return packet;
	}

	@Override
	public int getPacketID() {
		return 0x04;
	}

	@Override
	public String getProtocolURLString() {
		return "https://wiki.vg/Protocol#Spawn_Player";
	}
}
