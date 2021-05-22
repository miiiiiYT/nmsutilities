package io.github.riesenpilz.nms.packet.playOut;

import org.bukkit.entity.Player;

import net.minecraft.server.v1_16_R3.Packet;
import net.minecraft.server.v1_16_R3.PacketListenerPlayOut;

/**
 * https://wiki.vg/Protocol#Window_Items<br>
 * <br>
 * Sent by the server when items in multiple slots (in a window) are
 * added/removed. This includes the main inventory, equipped armour and crafting
 * slots. This packet with Window ID set to "0" is sent during the player
 * joining sequence to initialise the player's inventory.<br>
 * <br>
 * Packet ID: 0x13<br>
 * State: Play<br>
 * Bound To: Client
 * 
 * @author Martin
 *
 */
public class PacketPlayOutWindowItemsEvent extends PacketPlayOutEvent {

	public PacketPlayOutWindowItemsEvent(Player injectedPlayer) {
		super(injectedPlayer);
	}

	@Override
	public Packet<PacketListenerPlayOut> getNMS() {
		return null;
	}

	@Override
	public int getPacketID() {
		return 0x13;
	}

	@Override
	public String getProtocolURLString() {
		return "https://wiki.vg/Protocol#Window_Items";
	}
}
