package io.github.riesenpilz.nms.packet.playOut;

import org.bukkit.entity.Player;

import net.minecraft.server.v1_16_R3.Packet;
import net.minecraft.server.v1_16_R3.PacketListenerPlayOut;

/**
 * https://wiki.vg/Protocol#Declare_Commands<br>
 * <br>
 * Lists all of the commands on the server, and how they are parsed.<br>
 * <br>
 * This is a directed graph, with one root node. Each redirect or child node
 * must refer only to nodes that have already been declared.<br>
 * <br>
 * Packet ID: 0x10<br>
 * State: Play<br>
 * Bound To: Client
 * 
 * @author Martin
 *
 */
public class PacketPlayOutListCommandsEvent extends PacketPlayOutEvent {

	public PacketPlayOutListCommandsEvent(Player injectedPlayer) {
		super(injectedPlayer);
	}

	@Override
	public Packet<PacketListenerPlayOut> getNMS() {
		return null;
	}

	@Override
	public int getPacketID() {
		return 0x10;
	}

	@Override
	public String getProtocolURLString() {
		return "https://wiki.vg/Protocol#Declare_Commands";
	}
}
