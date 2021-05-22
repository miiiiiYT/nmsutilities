package io.github.riesenpilz.nms.packet.playOut;

import org.bukkit.entity.Player;

import net.minecraft.server.v1_16_R3.Packet;
import net.minecraft.server.v1_16_R3.PacketListenerPlayOut;

/**
 * https://wiki.vg/Protocol#Window_Property<br>
 * <br>
 * This packet is used to inform the client that part of a GUI window should be
 * updated.<br>
 * <br>
 * Packet ID: 0x14<br>
 * State: Play<br>
 * Bound To: Client
 * 
 * @author Martin
 *
 */
public class PacketPlayOutWindowPropertyEvent extends PacketPlayOutEvent {

	public PacketPlayOutWindowPropertyEvent(Player injectedPlayer) {
		super(injectedPlayer);
	}

	@Override
	public Packet<PacketListenerPlayOut> getNMS() {
		return null;
	}

	@Override
	public int getPacketID() {
		return 0x14;
	}

	@Override
	public String getProtocolURLString() {
		return "https://wiki.vg/Protocol#Window_Property";
	}
}
