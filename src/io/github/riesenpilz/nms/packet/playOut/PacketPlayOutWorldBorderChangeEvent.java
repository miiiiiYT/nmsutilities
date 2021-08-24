package io.github.riesenpilz.nms.packet.playOut;

import org.bukkit.entity.Player;

import net.minecraft.server.v1_16_R3.Packet;
import net.minecraft.server.v1_16_R3.PacketListenerPlayOut;

/**
 * 
 * <p>
 * 
 * <p>
 * Packet ID: <br>
 * State: Play<br>
 * Bound To: Client
 * 
 * @author Martin
 *
 */
public class PacketPlayOutWorldBorderChangeEvent extends PacketPlayOutEvent {

	public PacketPlayOutWorldBorderChangeEvent(Player injectedPlayer) {
		super(injectedPlayer);
	}

	@Override
	public Packet<PacketListenerPlayOut> getNMS() {
		return null;
	}

	@Override
	public int getPacketID() {
		return ;
	}

	@Override
	public String getProtocolURLString() {
		return "";
	}
}
