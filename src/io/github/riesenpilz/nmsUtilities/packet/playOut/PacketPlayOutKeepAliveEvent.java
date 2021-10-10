package io.github.riesenpilz.nmsUtilities.packet.playOut;

import org.apache.commons.lang.Validate;
import org.bukkit.entity.Player;

import io.github.riesenpilz.nmsUtilities.reflections.Field;
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

	private long keepAliveId;

	public PacketPlayOutKeepAliveEvent(Player injectedPlayer, PacketPlayOutKeepAlive packet) {
		super(injectedPlayer);

		Validate.notNull(packet);

		keepAliveId = Field.get(packet, "a", long.class);
	}

	public PacketPlayOutKeepAliveEvent(Player injectedPlayer, long keepAliveId) {
		super(injectedPlayer);

		this.keepAliveId = keepAliveId;
	}

	public long getKeepAliveId() {
		return keepAliveId;
	}

	@Override
	public Packet<PacketListenerPlayOut> getNMS() {
		return new PacketPlayOutKeepAlive(keepAliveId);
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
