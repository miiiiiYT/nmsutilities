package io.github.riesenpilz.nms.packet.playOut;

import org.bukkit.entity.Player;

import net.minecraft.server.v1_16_R3.Packet;
import net.minecraft.server.v1_16_R3.PacketListenerPlayOut;

/**
 * https://wiki.vg/Protocol#Ping_2
 * <p>
 * Unknown what this packet does just yet, not used by the Notchian server or
 * client. Most likely added as a replacement to the removed window confirmation
 * packet.
 * <p>
 * Packet ID: 0x30<br>
 * State: Play<br>
 * Bound To: Client
 * 
 * @author Martin
 *
 */
public class PacketPlayOutPingEvent extends PacketPlayOutEvent {

	public PacketPlayOutPingEvent(Player injectedPlayer, PacketPlayOut) {
		super(injectedPlayer);
	}

	@Override
	public Packet<PacketListenerPlayOut> getNMS() {
		return null;
	}

	@Override
	public int getPacketID() {
		return 0x30;
	}

	@Override
	public String getProtocolURLString() {
		return "https://wiki.vg/Protocol#Ping_2";
	}
}
