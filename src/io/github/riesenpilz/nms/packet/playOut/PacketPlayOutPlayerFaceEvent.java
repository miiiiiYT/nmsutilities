package io.github.riesenpilz.nms.packet.playOut;

import org.bukkit.entity.Player;

import net.minecraft.server.v1_16_R3.Packet;
import net.minecraft.server.v1_16_R3.PacketListenerPlayOut;
import net.minecraft.server.v1_16_R3.PacketPlayOutEntityHeadRotation;

/**
 * https://wiki.vg/Protocol#Face_Player
 * <p>
 * Used to rotate the client player to face the given location or entity (for
 * {@code /teleport [<targets>] <x> <y> <z> facing}).
 * <p>
 * Packet ID: 0x37<br>
 * State: Play<br>
 * Bound To: Client
 * 
 * @author Martin
 *
 */
public class PacketPlayOutPlayerFaceEvent extends PacketPlayOutEvent {

	public PacketPlayOutPlayerFaceEvent(Player injectedPlayer, PacketPlayOut) {
		super(injectedPlayer);
	}

	@Override
	public Packet<PacketListenerPlayOut> getNMS() {
		return null;
	}

	@Override
	public int getPacketID() {
		return 0x37;
	}

	@Override
	public String getProtocolURLString() {
		return "https://wiki.vg/Protocol#Face_Player";
	}
}
