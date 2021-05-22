package io.github.riesenpilz.nms.packet.statusIn;

import org.bukkit.entity.Player;

import io.github.riesenpilz.nms.reflections.Field;
import net.minecraft.server.v1_16_R3.Packet;
import net.minecraft.server.v1_16_R3.PacketStatusInListener;
import net.minecraft.server.v1_16_R3.PacketStatusInPing;

/**
 * https://wiki.vg/Protocol#Ping<br>
 * <br>
 * Packet ID: 0x01<br>
 * State: Status<br>
 * Bound To: Server
 * 
 * @author Martin
 *
 */
public class PacketStatusInPingEvent extends PacketStatusInEvent {

	public static final String PROTOCOL_URL = "https://wiki.vg/Protocol#Ping";
	public static final int PACKET_ID = 1;

	/**
	 * May be any number. Notchian clients use a system-dependent time value which
	 * is counted in milliseconds.
	 */
	private long payload;

	public PacketStatusInPingEvent(Player injectedPlayer, int payload) {
		super(injectedPlayer, PACKET_ID, PROTOCOL_URL);
		this.payload = payload;
	}

	public PacketStatusInPingEvent(Player injectedPlayer, PacketStatusInPing packet) {
		super(injectedPlayer, PACKET_ID, PROTOCOL_URL);
		payload = packet.b();
	}

	public long getPayload() {
		return payload;
	}

	@Override
	public Packet<PacketStatusInListener> getNMS() {
		final PacketStatusInPing packet = new PacketStatusInPing();
		new Field(PacketStatusInPing.class, "a").set(packet, payload);
		return packet;
	}

}
