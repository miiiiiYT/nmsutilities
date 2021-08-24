package io.github.riesenpilz.nms.packet.playOut;

import org.bukkit.entity.Player;

import io.github.riesenpilz.nms.reflections.Field;
import net.minecraft.server.v1_16_R3.Packet;
import net.minecraft.server.v1_16_R3.PacketListenerPlayOut;
import net.minecraft.server.v1_16_R3.PacketPlayOutKeepAlive;

/**
 * https://wiki.vg/Protocol#Keep_Alive_.28clientbound.29
 * <p>
 * The server will frequently send out a keep-alive, each containing a random
 * ID. The client must respond with the same payload (see serverbound Keep
 * Alive). If the client does not respond to them for over 30 seconds, the
 * server kicks the client. Vice versa, if the server does not send any
 * keep-alives for 20 seconds, the client will disconnect and yields a "Timed
 * out" exception.
 * <p>
 * The Notchian server uses a system-dependent time in milliseconds to generate
 * the keep alive ID value.
 * <p>
 * Packet ID: 0x21<br>
 * State: Play<br>
 * Bound To: Client
 * 
 * @author Martin
 *
 */
public class PacketPlayOutKeepAliveEvent extends PacketPlayOutEvent {
	
	private long keepAliveID;
	
	public PacketPlayOutKeepAliveEvent(Player injectedPlayer, PacketPlayOutKeepAlive packet) {
		super(injectedPlayer);
		keepAliveID = Field.get(packet, "a", long.class);
	}

	public PacketPlayOutKeepAliveEvent(Player injectedPlayer, long keepAliveID) {
		super(injectedPlayer);
		this.keepAliveID = keepAliveID;
	}

	public long getKeepAliveID() {
		return keepAliveID;
	}

	@Override
	public Packet<PacketListenerPlayOut> getNMS() {
		return new PacketPlayOutKeepAlive(keepAliveID);
	}

	@Override
	public int getPacketID() {
		return 0x21;
	}

	@Override
	public String getProtocolURLString() {
		return "https://wiki.vg/Protocol#Keep_Alive_.28clientbound.29";
	}
}
