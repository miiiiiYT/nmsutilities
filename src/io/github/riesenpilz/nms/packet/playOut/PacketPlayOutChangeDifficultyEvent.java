package io.github.riesenpilz.nms.packet.playOut;

import org.bukkit.entity.Player;

import net.minecraft.server.v1_16_R3.Packet;
import net.minecraft.server.v1_16_R3.PacketListenerPlayOut;

/**
 * https://wiki.vg/Protocol#Server_Difficulty<br>
 * <br>
 * Changes the difficulty setting in the client's option menu<br>
 * <br>
 * Packet ID: 0x0D<br>
 * State: Play<br>
 * Bound To: Client
 * 
 * @author Martin
 *
 */
public class PacketPlayOutChangeDifficultyEvent extends PacketPlayOutEvent {

	public PacketPlayOutChangeDifficultyEvent(Player injectedPlayer) {
		super(injectedPlayer);
	}

	@Override
	public Packet<PacketListenerPlayOut> getNMS() {
		return null;
	}

	@Override
	public int getPacketID() {
		return 0x0D;
	}

	@Override
	public String getProtocolURLString() {
		return "https://wiki.vg/Protocol#Server_Difficulty";
	}
}
