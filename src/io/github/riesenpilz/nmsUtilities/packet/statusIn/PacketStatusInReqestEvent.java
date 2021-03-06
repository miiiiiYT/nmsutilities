package io.github.riesenpilz.nmsUtilities.packet.statusIn;

import org.bukkit.entity.Player;

import net.minecraft.server.v1_16_R3.Packet;
import net.minecraft.server.v1_16_R3.PacketStatusInListener;
import net.minecraft.server.v1_16_R3.PacketStatusInStart;

/**
 * https://wiki.vg/Protocol#Request<br>
 * <br>
 * Packet ID: 0x00<br>
 * State: Status<br>
 * Bound To: Server
 *
 * @author Martin
 *
 */
public class PacketStatusInReqestEvent extends PacketStatusInEvent {

	// No fields

	public PacketStatusInReqestEvent(Player injectedPlayer, PacketStatusInStart packet) {
		super(injectedPlayer);
	}

	public PacketStatusInReqestEvent(Player injectedPlayer) {
		super(injectedPlayer);
	}

	@Override
	public Packet<PacketStatusInListener> getNMS() {
		return new PacketStatusInStart();
	}

	@Override
	public int getPacketID() {
		return 0x00;
	}

	@Override
	public String getProtocolURLString() {
		return "https://wiki.vg/Protocol#Request";
	}

}
