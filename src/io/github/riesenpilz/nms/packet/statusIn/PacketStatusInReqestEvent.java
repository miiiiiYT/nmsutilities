package io.github.riesenpilz.nms.packet.statusIn;

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
public class PacketStatusInReqestEvent extends PacketStatusInEvent{

	public static final String PROTOCOL_URL = "https://wiki.vg/Protocol#Request";
	public static final int PACKET_ID = 0;

	
	public PacketStatusInReqestEvent(Player injectedPlayer) {
		super(injectedPlayer, PACKET_ID, PROTOCOL_URL);
	}
	
	public PacketStatusInReqestEvent(Player injectedPlayer, PacketStatusInStart packet) {
		super(injectedPlayer, PACKET_ID, PROTOCOL_URL);
	}

	@Override
	public Packet<PacketStatusInListener> getNMS() {
		return new PacketStatusInStart();
	}

}
