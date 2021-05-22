package io.github.riesenpilz.nms.packet.statusOut;

import org.bukkit.entity.Player;

import io.github.riesenpilz.nms.reflections.Field;
import net.minecraft.server.v1_16_R3.Packet;
import net.minecraft.server.v1_16_R3.PacketStatusOutListener;
import net.minecraft.server.v1_16_R3.PacketStatusOutPong;

/**
 * https://wiki.vg/Protocol#Pong<br>
 * <br>
 * Packet ID: 0x01<br>
 * State: Status<br>
 * Bound To: Client
 * 
 * @author Martin
 *
 */
public class PacketStatusOutPongEvent extends PacketStatusOutEvent {

	public static final String PROTOCOL_URL = "https://wiki.vg/Protocol#Pong";
	public static final int PACKET_ID = 1;

	/**
	 * Should be the same as sent by the client.
	 */
	private long payload;

	public PacketStatusOutPongEvent(Player injectedPlayer, long payload) {
		super(injectedPlayer, PACKET_ID, PROTOCOL_URL);
		this.payload = payload;
	}

	public PacketStatusOutPongEvent(Player injectedPlayer, PacketStatusOutPong packet) {
		super(injectedPlayer, PACKET_ID, PROTOCOL_URL);
		payload = (long) new Field(PacketStatusOutPong.class, "a").get(packet);
	}

	public long getPayload() {
		return payload;
	}

	@Override
	public Packet<PacketStatusOutListener> getNMS() {
		return new PacketStatusOutPong(payload);
	}

}
