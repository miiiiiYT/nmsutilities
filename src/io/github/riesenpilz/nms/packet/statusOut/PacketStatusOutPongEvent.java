package io.github.riesenpilz.nms.packet.statusOut;

import org.bukkit.entity.Player;

import io.github.riesenpilz.nms.reflections.Field;
import net.minecraft.server.v1_16_R3.Packet;
import net.minecraft.server.v1_16_R3.PacketStatusOutListener;
import net.minecraft.server.v1_16_R3.PacketStatusOutPong;

/**
 * https://wiki.vg/Protocol#Pong
 * <p>
 * Packet ID: 0x01<br>
 * State: Status<br>
 * Bound To: Client
 *
 * @author Martin
 *
 */
public class PacketStatusOutPongEvent extends PacketStatusOutEvent {

	/**
	 * Should be the same as sent by the client.
	 */
	private long payload;

	public PacketStatusOutPongEvent(Player injectedPlayer, long payload) {
		super(injectedPlayer);
		this.payload = payload;
	}

	public PacketStatusOutPongEvent(Player injectedPlayer, PacketStatusOutPong packet) {
		super(injectedPlayer);
		payload = Field.get(packet, "a", long.class);
	}

	public long getPayload() {
		return payload;
	}

	@Override
	public Packet<PacketStatusOutListener> getNMS() {
		return new PacketStatusOutPong(payload);
	}

	@Override
	public int getPacketID() {
		return 0x01;
	}

	@Override
	public String getProtocolURLString() {
		return "https://wiki.vg/Protocol#Pong";
	}

}
