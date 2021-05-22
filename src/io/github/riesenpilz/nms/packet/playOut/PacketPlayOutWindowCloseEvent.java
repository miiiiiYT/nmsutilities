package io.github.riesenpilz.nms.packet.playOut;

import org.bukkit.entity.Player;

import net.minecraft.server.v1_16_R3.Packet;
import net.minecraft.server.v1_16_R3.PacketListenerPlayOut;

/**
 * https://wiki.vg/Protocol#Close_Window_.28clientbound.29<br>
 * <br>
 * This packet is sent from the server to the client when a window is forcibly
 * closed, such as when a chest is destroyed while it's open.<br>
 * <br>
 * Packet ID: 0x12<br>
 * State: Play<br>
 * Bound To: Client
 * 
 * @author Martin
 *
 */
public class PacketPlayOutWindowCloseEvent extends PacketPlayOutEvent {

	public PacketPlayOutWindowCloseEvent(Player injectedPlayer) {
		super(injectedPlayer);
	}

	@Override
	public Packet<PacketListenerPlayOut> getNMS() {
		return null;
	}

	@Override
	public int getPacketID() {
		return 0x12;
	}

	@Override
	public String getProtocolURLString() {
		return "https://wiki.vg/Protocol#Close_Window_.28clientbound.29";
	}
}
