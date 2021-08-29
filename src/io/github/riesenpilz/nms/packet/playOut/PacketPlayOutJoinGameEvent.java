package io.github.riesenpilz.nms.packet.playOut;

import org.bukkit.entity.Player;

import net.minecraft.server.v1_16_R3.Packet;
import net.minecraft.server.v1_16_R3.PacketListenerPlayOut;
import net.minecraft.server.v1_16_R3.PacketPlayOutPlayerListHeaderFooter;

/**
 * https://wiki.vg/Protocol#Join_Game
 * <p>
 * See Protocol Encryption for information on logging in.
 * (https://wiki.vg/Protocol_Encryption)
 * <p>
 * Packet ID: 0x26<br>
 * State: Play<br>
 * Bound To: Client
 * 
 * @author Martin
 *
 */
public class PacketPlayOutJoinGameEvent extends PacketPlayOutEvent {

	public PacketPlayOutJoinGameEvent(Player injectedPlayer, PacketPlayOut) {
		super(injectedPlayer);
	}

	@Override
	public Packet<PacketListenerPlayOut> getNMS() {
		return null;
	}

	@Override
	public int getPacketID() {
		return 0x26;
	}

	@Override
	public String getProtocolURLString() {
		return "https://wiki.vg/Protocol#Join_Game";
	}
}
