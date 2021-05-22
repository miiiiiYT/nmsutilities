package io.github.riesenpilz.nms.packet.playOut;

import org.bukkit.entity.Player;

import net.minecraft.server.v1_16_R3.Packet;
import net.minecraft.server.v1_16_R3.PacketListenerPlayOut;

/**
 * https://wiki.vg/Protocol#Chat_Message_.28clientbound.29<br>
 * <br>
 * Identifying the difference between Chat/System Message is important as it
 * helps respect the user's chat visibility options. See processing chat for
 * more info about these positions.<br>
 * <br>
 * Packet ID: 0x0E<br>
 * State: Play<br>
 * Bound To: Client
 * 
 * @author Martin
 *
 */
public class PacketPlayOutChatMessageEvent extends PacketPlayOutEvent {

	public PacketPlayOutChatMessageEvent(Player injectedPlayer) {
		super(injectedPlayer);
	}

	@Override
	public Packet<PacketListenerPlayOut> getNMS() {
		return null;
	}

	@Override
	public int getPacketID() {
		return 0x0E;
	}

	@Override
	public String getProtocolURLString() {
		return "https://wiki.vg/Protocol#Chat_Message_.28clientbound.29";
	}
}
