package io.github.riesenpilz.nms.packet.playOut;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import io.github.riesenpilz.nms.reflections.Field;
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
public class PacketPlayOutSpawnXpEvent extends PacketPlayOutEvent {

	private int entityID;

	private Location location;

	/**
	 * The amount of experience this orb will reward once collected.
	 */
	private int count;

	public PacketPlayOutSpawnXpEvent(Player injectedPlayer, int entityID, Location location, int count) {
		super(injectedPlayer);
		this.entityID = entityID;
		this.location = location;
		this.count = count;
	}

	public PacketPlayOutSpawnXpEvent(Player injectedPlayer, PacketPlayOutSpawnEntityExperienceOrb packet) {
		super(injectedPlayer);
		entityID = (int) new Field(PacketPlayOutSpawnEntityExperienceOrb.class, "a").get(packet);
		location = new Location(injectedPlayer.getWorld(),
				(double) new Field(PacketPlayOutSpawnEntityExperienceOrb.class, "b").get(packet),
				(double) new Field(PacketPlayOutSpawnEntityExperienceOrb.class, "c").get(packet),
				(double) new Field(PacketPlayOutSpawnEntityExperienceOrb.class, "d").get(packet));
		count = (int) new Field(PacketPlayOutSpawnEntityExperienceOrb.class, "e").get(packet);
	}

	public int getEntityID() {
		return entityID;
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
		new Field(PacketPlayOutSpawnEntityExperienceOrb.class, "a").set(packet, entityID);
		new Field(PacketPlayOutSpawnEntityExperienceOrb.class, "b").set(packet, location.getX());
		new Field(PacketPlayOutSpawnEntityExperienceOrb.class, "c").set(packet, location.getY());
		new Field(PacketPlayOutSpawnEntityExperienceOrb.class, "d").set(packet, location.getZ());
		new Field(PacketPlayOutSpawnEntityExperienceOrb.class, "e").set(packet, count);
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
