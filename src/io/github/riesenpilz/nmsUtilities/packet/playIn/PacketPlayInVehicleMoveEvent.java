package io.github.riesenpilz.nmsUtilities.packet.playIn;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import io.github.riesenpilz.nmsUtilities.reflections.Field;
import net.minecraft.server.v1_16_R3.Packet;
import net.minecraft.server.v1_16_R3.PacketListenerPlayIn;
import net.minecraft.server.v1_16_R3.PacketPlayInVehicleMove;

/**
 * https://wiki.vg/Protocol#Vehicle_Move_.28serverbound.29
 * <p>
 * Sent when a player moves in a vehicle. Note that all fields use absolute
 * positioning and do not allow for relative positioning.
 * <p>
 * Packet ID: 0x16<br>
 * State: Play<br>
 * Bound To: Server
 *
 * @author Martin
 *
 */
public class PacketPlayInVehicleMoveEvent extends PacketPlayInEvent {

	/**
	 * Absolute {@link Location}.
	 */
	Location locationTo;

	public PacketPlayInVehicleMoveEvent(Player injectedPlayer, PacketPlayInVehicleMove packet) {
		super(injectedPlayer);
		locationTo = new Location(injectedPlayer.getWorld(), packet.getX(), packet.getY(), packet.getZ(),
				packet.getYaw(), packet.getPitch());
	}

	public PacketPlayInVehicleMoveEvent(Player injectedPlayer, Location locationTo) {
		super(injectedPlayer);
		this.locationTo = locationTo;
	}

	public Location getLocationTo() {
		return locationTo;
	}

	@Override
	public Packet<PacketListenerPlayIn> getNMS() {
		final PacketPlayInVehicleMove packet = new PacketPlayInVehicleMove();
		Field.set(packet, "a", locationTo.getX());
		Field.set(packet, "b", locationTo.getY());
		Field.set(packet, "c", locationTo.getZ());
		Field.set(packet, "d", locationTo.getYaw());
		Field.set(packet, "e", locationTo.getPitch());
		return packet;
	}

	@Override
	public int getPacketID() {
		return 0x16;
	}

	@Override
	public String getProtocolURLString() {
		return "https://wiki.vg/Protocol#Vehicle_Move_.28serverbound.29";
	}

}
