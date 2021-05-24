package io.github.riesenpilz.nms.packet.playOut;

import org.bukkit.entity.Player;

import net.minecraft.server.v1_16_R3.Packet;
import net.minecraft.server.v1_16_R3.PacketListenerPlayOut;

/**
 * https://wiki.vg/Protocol#Change_Game_State<br>
 * <br>
 * Used for a wide variety of game state things, from weather to bed use to
 * gamemode to demo messages.<br>
 * <br>
 * Packet ID: 0x1D<br>
 * State: Play<br>
 * Bound To: Client
 * 
 * @author Martin
 *
 */
public class PacketPlayOutChangeGameStateEvent extends PacketPlayOutEvent {

	public PacketPlayOutChangeGameStateEvent(Player injectedPlayer) {
		super(injectedPlayer);
	}

	@Override
	public Packet<PacketListenerPlayOut> getNMS() {
		return null;
	}

	@Override
	public int getPacketID() {
		return 0x1D;
	}

	@Override
	public String getProtocolURLString() {
		return "https://wiki.vg/Protocol#Change_Game_State";
	}
}
