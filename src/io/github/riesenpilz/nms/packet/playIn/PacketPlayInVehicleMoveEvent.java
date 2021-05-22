package io.github.riesenpilz.nms.packet.playIn;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import io.github.riesenpilz.nms.reflections.Field;
import net.minecraft.server.v1_16_R3.Packet;
import net.minecraft.server.v1_16_R3.PacketListenerPlayIn;
import net.minecraft.server.v1_16_R3.PacketPlayInVehicleMove;

/**
 * https://wiki.vg/Protocol#Vehicle_Move_.28serverbound.29<br>
 * <br>
 * Sent when a player moves in a vehicle. Note that all fields use absolute
 * positioning and do not allow for relative positioning.<br>
 * <br>
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
		new Field(PacketPlayInVehicleMove.class, "a").set(packet, locationTo.getX());
		new Field(PacketPlayInVehicleMove.class, "b").set(packet, locationTo.getY());
		new Field(PacketPlayInVehicleMove.class, "c").set(packet, locationTo.getZ());
		new Field(PacketPlayInVehicleMove.class, "d").set(packet, locationTo.getYaw());
		new Field(PacketPlayInVehicleMove.class, "e").set(packet, locationTo.getPitch());
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
