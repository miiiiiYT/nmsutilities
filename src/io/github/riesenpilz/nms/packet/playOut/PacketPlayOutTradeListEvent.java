package io.github.riesenpilz.nms.packet.playOut;

import org.bukkit.entity.Player;

import net.minecraft.server.v1_16_R3.Packet;
import net.minecraft.server.v1_16_R3.PacketListenerPlayOut;

/**
 * https://wiki.vg/Protocol#Trade_List
 * <p>
 * The list of trades a villager NPC is offering.
 * <p>
 * Packet ID: 0x28<br>
 * State: Play<br>
 * Bound To: Client
 * 
 * @author Martin
 *
 */
public class PacketPlayOutTradeListEvent extends PacketPlayOutEvent {

	public PacketPlayOutTradeListEvent(Player injectedPlayer, PacketPlayOut) {
		super(injectedPlayer);
	}

	@Override
	public Packet<PacketListenerPlayOut> getNMS() {
		return null;
	}

	@Override
	public int getPacketID() {
		return 0x28;
	}

	@Override
	public String getProtocolURLString() {
		return "https://wiki.vg/Protocol#Trade_List";
	}
}
