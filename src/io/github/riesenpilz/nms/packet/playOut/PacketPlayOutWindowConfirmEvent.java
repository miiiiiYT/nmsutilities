package io.github.riesenpilz.nms.packet.playOut;

import org.bukkit.entity.Player;

import net.minecraft.server.v1_16_R3.Packet;
import net.minecraft.server.v1_16_R3.PacketListenerPlayOut;

/**
 * https://wiki.vg/Protocol#Window_Confirmation_.28clientbound.29
 * <p>
 * A packet from the server indicating whether a request from the client was
 * accepted, or whether there was a conflict (due to lag). If the packet was not
 * accepted, the client must respond with a serverbound window confirmation
 * packet.
 * <p>
 * Packet ID: 0x11<br>
 * State: Play<br>
 * Bound To: Client
 * 
 * @author Martin
 *
 */
public class PacketPlayOutWindowConfirmEvent extends PacketPlayOutEvent {

	public PacketPlayOutWindowConfirmEvent(Player injectedPlayer) {
		super(injectedPlayer);
	}

	@Override
	public Packet<PacketListenerPlayOut> getNMS() {
		return null;
	}

	@Override
	public int getPacketID() {
		return 0x11;
	}

	@Override
	public String getProtocolURLString() {
		return "https://wiki.vg/Protocol#Window_Confirmation_.28clientbound.29";
	}
}
