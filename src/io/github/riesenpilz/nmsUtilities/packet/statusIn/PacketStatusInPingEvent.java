package io.github.riesenpilz.nmsUtilities.packet.statusIn;

import org.bukkit.entity.Player;

import io.github.riesenpilz.nmsUtilities.reflections.Field;
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

	/**
	 * May be any number. Notchian clients use a system-dependent time value which
	 * is counted in milliseconds.
	 */
	private long payload;

	public PacketStatusInPingEvent(Player injectedPlayer, int payload) {
		super(injectedPlayer);
		this.payload = payload;
	}

	public PacketStatusInPingEvent(Player injectedPlayer, PacketStatusInPing packet) {
		super(injectedPlayer);
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

	@Override
	public int getPacketID() {
		return 1;
	}

	@Override
	public String getProtocolURLString() {
		return "https://wiki.vg/Protocol#Ping";
	}

}
