package io.github.riesenpilz.nms.packet.playOut;

import org.bukkit.entity.Player;

import net.minecraft.server.v1_16_R3.Packet;
import net.minecraft.server.v1_16_R3.PacketListenerPlayOut;

/**
 * https://wiki.vg/Protocol#Tab-Complete_.28clientbound.29<br>
 * <br>
 * The server responds with a list of auto-completions of the last word sent to
 * it. In the case of regular chat, this is a player username. Command names and
 * parameters are also supported. The client sorts these alphabetically before
 * listing them.<br>
 * <br>
 * Packet ID: 0x0F<br>
 * State: Play<br>
 * Bound To: Client
 * 
 * @author Martin
 *
 */
public class PacketPlayOutTabCompleteEvent extends PacketPlayOutEvent {

	public PacketPlayOutTabCompleteEvent(Player injectedPlayer) {
		super(injectedPlayer);
	}

	@Override
	public Packet<PacketListenerPlayOut> getNMS() {
		return null;
	}

	@Override
	public int getPacketID() {
		return 0x0F;
	}

	@Override
	public String getProtocolURLString() {
		return "https://wiki.vg/Protocol#Tab-Complete_.28clientbound.29";
	}
}
