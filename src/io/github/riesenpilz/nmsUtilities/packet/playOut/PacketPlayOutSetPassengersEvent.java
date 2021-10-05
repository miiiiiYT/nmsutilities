package io.github.riesenpilz.nmsUtilities.packet.playOut;

import org.bukkit.entity.Player;

import io.github.riesenpilz.nmsUtilities.reflections.Field;
import net.minecraft.server.v1_16_R3.Packet;
import net.minecraft.server.v1_16_R3.PacketListenerPlayOut;
import net.minecraft.server.v1_16_R3.PacketPlayOutMount;

/**
 * https://wiki.vg/Protocol#Set_Passengers
 * <p>
 * Packet ID: 0x54<br>
 * State: Play<br>
 * Bound To: Client
 * 
 * @author Martin
 *
 */
public class PacketPlayOutSetPassengersEvent extends PacketPlayOutEvent {

	/**
	 * Vehicle's EID.
	 */
	private int vehicleId;
	
	/**
	 * EIDs of entity's passengers.
	 */
    private int[] passengers;
	
	public PacketPlayOutSetPassengersEvent(Player injectedPlayer, PacketPlayOutMount packet) {
		super(injectedPlayer);
		
		vehicleId = Field.get(packet, "a", int.class);
		passengers = Field.get(packet, "b", int[].class);
	}

	public PacketPlayOutSetPassengersEvent(Player injectedPlayer, int vehicleId, int[] passengers) {
		super(injectedPlayer);
		this.vehicleId = vehicleId;
		this.passengers = passengers;
	}

	public int getVehicleId() {
		return vehicleId;
	}

	public int[] getPassengers() {
		return passengers;
	}

	@Override
	public Packet<PacketListenerPlayOut> getNMS() {
		final PacketPlayOutMount packet = new PacketPlayOutMount();
		Field.set(packet, "a", vehicleId);
		Field.set(packet, "b", passengers);
		return packet;
	}

	@Override
	public int getPacketID() {
		return 0x54;
	}

	@Override
	public String getProtocolURLString() {
		return "https://wiki.vg/Protocol#Set_Passengers";
	}
}
