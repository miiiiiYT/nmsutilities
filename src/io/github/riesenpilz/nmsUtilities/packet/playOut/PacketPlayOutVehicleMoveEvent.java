package io.github.riesenpilz.nmsUtilities.packet.playOut;

import org.apache.commons.lang.Validate;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import io.github.riesenpilz.nmsUtilities.reflections.Field;
import net.minecraft.server.v1_16_R3.Packet;
import net.minecraft.server.v1_16_R3.PacketListenerPlayOut;
import net.minecraft.server.v1_16_R3.PacketPlayOutVehicleMove;

/**
 * https://wiki.vg/Protocol#Vehicle_Move_.28clientbound.29
 * <p>
 * Note that all fields use absolute positioning and do not allow for relative
 * positioning.
 * <p>
 * Packet ID: 0x2C<br>
 * State: Play<br>
 * Bound To: Client
 * 
 * @author Martin
 *
 */
public class PacketPlayOutVehicleMoveEvent extends PacketPlayOutEvent {

	private Location newLocation;

	public PacketPlayOutVehicleMoveEvent(Player injectedPlayer, PacketPlayOutVehicleMove packet) {
		super(injectedPlayer);

		Validate.notNull(packet);

		newLocation = new Location(injectedPlayer.getWorld(), Field.get(packet, "a", double.class),
				Field.get(packet, "b", double.class), Field.get(packet, "c", double.class),
				Field.get(packet, "d", float.class), Field.get(packet, "e", float.class));
	}

	public PacketPlayOutVehicleMoveEvent(Player injectedPlayer, Location newLocation) {
		super(injectedPlayer);

		Validate.notNull(newLocation);

		this.newLocation = newLocation;
	}

	public Location getNewLocation() {
		return newLocation;
	}

	@Override
	public Packet<PacketListenerPlayOut> getNMS() {
		final PacketPlayOutVehicleMove packet = new PacketPlayOutVehicleMove();
		Field.set(packet, "a", newLocation.getX());
		Field.set(packet, "b", newLocation.getY());
		Field.set(packet, "c", newLocation.getZ());
		Field.set(packet, "d", newLocation.getYaw());
		Field.set(packet, "e", newLocation.getPitch());
		return packet;
	}

	@Override
	public int getPacketID() {
		return 0x2C;
	}

	@Override
	public String getProtocolURLString() {
		return "https://wiki.vg/Protocol#Vehicle_Move_.28clientbound.29";
	}
}
