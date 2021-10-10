package io.github.riesenpilz.nmsUtilities.packet.playOut;

import org.apache.commons.lang.Validate;
import org.bukkit.entity.Player;

import io.github.riesenpilz.nmsUtilities.reflections.Field;
import net.minecraft.server.v1_16_R3.Packet;
import net.minecraft.server.v1_16_R3.PacketListenerPlayOut;
import net.minecraft.server.v1_16_R3.PacketPlayOutWorldBorder;
import net.minecraft.server.v1_16_R3.PacketPlayOutWorldBorder.EnumWorldBorderAction;

/**
 * https://wiki.vg/Protocol#Initialize_World_Border
 * <p>
 * Packet ID: 0x20<br>
 * State: Play<br>
 * Bound To: Client
 * 
 * @author Martin
 *
 */
public class PacketPlayOutWorldBorderInitializeEvent extends PacketPlayOutEvent {

	/**
	 * Resulting coordinates from a portal teleport are limited to ±value. Usually
	 * 29999984.
	 */
	private int portalTeleportBoundary;
	private double centerX;
	private double centerZ;

	/**
	 * Current length of a single side of the world border, in meters.
	 */
	private double oldDiameter;

	/**
	 * Target length of a single side of the world border, in meters.
	 */
	private double newDiameter;

	/**
	 * Number of real-time milliseconds until New Diameter is reached. It appears
	 * that Notchian server does not sync world border speed to game ticks, so it
	 * gets out of sync with server lag. If the world border is not moving, this is
	 * set to 0.
	 */
	private long speed;
	private int warningBlocks;

	/**
	 * In seconds as set by /worldborder warning time.
	 */
	private int warningTime;

	public PacketPlayOutWorldBorderInitializeEvent(Player injectedPlayer, PacketPlayOutWorldBorder packet) {
		super(injectedPlayer);

		Validate.notNull(packet);

		portalTeleportBoundary = Field.get(packet, "b", int.class);
		centerX = Field.get(packet, "c", double.class);
		centerZ = Field.get(packet, "d", double.class);
		oldDiameter = Field.get(packet, "e", double.class);
		newDiameter = Field.get(packet, "f", double.class);
		speed = Field.get(packet, "g", long.class);
		warningBlocks = Field.get(packet, "h", int.class);
		warningTime = Field.get(packet, "i", int.class);
	}

	public PacketPlayOutWorldBorderInitializeEvent(Player injectedPlayer, int portalTeleportBoundary, double centerX,
			double centerZ, double oldDiameter, double newDiameter, long speed, int warningBlocks, int warningTime) {
		super(injectedPlayer);

		this.portalTeleportBoundary = portalTeleportBoundary;
		this.centerX = centerX;
		this.centerZ = centerZ;
		this.oldDiameter = oldDiameter;
		this.newDiameter = newDiameter;
		this.speed = speed;
		this.warningBlocks = warningBlocks;
		this.warningTime = warningTime;
	}

	public int getPortalTeleportBoundary() {
		return portalTeleportBoundary;
	}

	public double getCenterX() {
		return centerX;
	}

	public double getCenterZ() {
		return centerZ;
	}

	public double getOldDiameter() {
		return oldDiameter;
	}

	public double getNewDiameter() {
		return newDiameter;
	}

	public long getSpeed() {
		return speed;
	}

	public int getWarningBlocks() {
		return warningBlocks;
	}

	public int getWarningTime() {
		return warningTime;
	}

	@Override
	public Packet<PacketListenerPlayOut> getNMS() {
		final PacketPlayOutWorldBorder packet = new PacketPlayOutWorldBorder();
		Field.set(packet, "a", EnumWorldBorderAction.INITIALIZE);
		Field.set(packet, "b", portalTeleportBoundary);
		Field.set(packet, "c", centerX);
		Field.set(packet, "d", centerZ);
		Field.set(packet, "e", oldDiameter);
		Field.set(packet, "f", newDiameter);
		Field.set(packet, "g", speed);
		Field.set(packet, "h", warningBlocks);
		Field.set(packet, "i", warningTime);
		return packet;
	}

	@Override
	public int getPacketID() {
		return 0x20;
	}

	@Override
	public String getProtocolURLString() {
		return "https://wiki.vg/Protocol#Initialize_World_Border";
	}
}
