package io.github.riesenpilz.nmsUtilities.packet.playOut;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import io.github.riesenpilz.nmsUtilities.reflections.Field;
import net.minecraft.server.v1_16_R3.BlockPosition;
import net.minecraft.server.v1_16_R3.Packet;
import net.minecraft.server.v1_16_R3.PacketListenerPlayOut;
import net.minecraft.server.v1_16_R3.PacketPlayOutExplosion;
import net.minecraft.server.v1_16_R3.Vec3D;

/**
 * https://wiki.vg/Protocol#Explosion
 * <p>
 * Sent when an explosion occurs (creepers, TNT, and ghast fireballs).
 * <p>
 * Each block in Records is set to air.
 * <p>
 * Packet ID: 0x1B<br>
 * State: Play<br>
 * Bound To: Client
 *
 * @author Martin
 *
 */
public class PacketPlayOutExplosionEvent extends PacketPlayOutEvent {

	private Location explosionLocation;

	/**
	 * A strength greater than or equal to 2.0 spawns a minecraft:explosion_emitter
	 * particle, while a lesser strength spawns a minecraft:explosion particle.
	 */
	private float strength;
	private List<Location> records;
	private Vector velocity;

	@SuppressWarnings("unchecked")
	public PacketPlayOutExplosionEvent(Player injectedPlayer, PacketPlayOutExplosion packet) {
		super(injectedPlayer);
		explosionLocation = new Location(injectedPlayer.getWorld(), Field.get(packet, "a", double.class),
				Field.get(packet, "b", double.class), Field.get(packet, "c", double.class));
		strength = Field.get(packet, "d", float.class);
		records = new ArrayList<>();
		for (BlockPosition pos : (List<BlockPosition>) Field.get(packet, "e", List.class))
			records.add(new Location(injectedPlayer.getWorld(), pos.getX(), pos.getY(), pos.getZ()));
		velocity = new Vector(Field.get(packet, "f", float.class), Field.get(packet, "g", float.class),
				Field.get(packet, "h", float.class));
	}

	public PacketPlayOutExplosionEvent(Player injectedPlayer, Location explosionLocation, float strength,
			List<Location> records, Vector velocity) {
		super(injectedPlayer);
		this.explosionLocation = explosionLocation;
		this.strength = strength;
		this.records = records;
		this.velocity = velocity;
	}

	public Location getExplosionLocation() {
		return explosionLocation;
	}

	public float getStrength() {
		return strength;
	}

	public List<Location> getRecords() {
		return records;
	}

	public Vector getVelocity() {
		return velocity;
	}

	@Override
	public Packet<PacketListenerPlayOut> getNMS() {
		List<BlockPosition> nmsRecords = new ArrayList<>();
		for (Location loc : records)
			nmsRecords.add(new BlockPosition(loc.getX(), loc.getY(), loc.getZ()));
		return new PacketPlayOutExplosion(explosionLocation.getX(), explosionLocation.getY(), explosionLocation.getZ(),
				strength, nmsRecords, new Vec3D(velocity.getX(), velocity.getY(), velocity.getZ()));
	}

	@Override
	public int getPacketID() {
		return 0x1B;
	}

	@Override
	public String getProtocolURLString() {
		return "https://wiki.vg/Protocol#Explosion";
	}
}
