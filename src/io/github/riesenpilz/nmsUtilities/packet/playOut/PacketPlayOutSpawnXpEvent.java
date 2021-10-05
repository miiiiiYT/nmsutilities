package io.github.riesenpilz.nmsUtilities.packet.playOut;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import io.github.riesenpilz.nmsUtilities.reflections.Field;
import net.minecraft.server.v1_16_R3.Packet;
import net.minecraft.server.v1_16_R3.PacketListenerPlayOut;
import net.minecraft.server.v1_16_R3.PacketPlayOutSpawnEntityExperienceOrb;

/**
 * https://wiki.vg/Protocol#Spawn_Experience_Orb<br>
 * <br>
 * Spawns one or more experience orbs.<br>
 * <br>
 * Packet ID: 0x01<br>
 * State: Play<br>
 * Bound To: Client
 * 
 * @author Martin
 *
 */
public class PacketPlayOutSpawnXpEvent extends PacketPlayOutEntityEvent {

	private Location location;

	/**
	 * The amount of experience this orb will reward once collected.
	 */
	private int count;

	public PacketPlayOutSpawnXpEvent(Player injectedPlayer, int entityId, Location location, int count) {
		super(injectedPlayer, entityId);
		this.location = location;
		this.count = count;
	}

	public PacketPlayOutSpawnXpEvent(Player injectedPlayer, PacketPlayOutSpawnEntityExperienceOrb packet) {
		super(injectedPlayer, packet);
		location = new Location(injectedPlayer.getWorld(), Field.get(packet, "b", double.class),
				Field.get(packet, "c", double.class), Field.get(packet, "d", double.class));
		count = Field.get(packet, "e", int.class);
	}

	public Location getLocation() {
		return location;
	}

	public int getCount() {
		return count;
	}

	@Override
	public Packet<PacketListenerPlayOut> getNMS() {
		final PacketPlayOutSpawnEntityExperienceOrb packet = new PacketPlayOutSpawnEntityExperienceOrb();
		Field.set(packet, "a", getEntityId());
		Field.set(packet, "b", location.getX());
		Field.set(packet, "c", location.getY());
		Field.set(packet, "d", location.getZ());
		Field.set(packet, "e", count);
		return packet;
	}

	@Override
	public int getPacketID() {
		return 0x01;
	}

	@Override
	public String getProtocolURLString() {
		return "https://wiki.vg/Protocol#Spawn_Experience_Orb";
	}
}
