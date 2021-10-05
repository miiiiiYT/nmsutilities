package io.github.riesenpilz.nmsUtilities.packet.playIn;

import org.bukkit.entity.Player;

import io.github.riesenpilz.nmsUtilities.reflections.Field;
import net.minecraft.server.v1_16_R3.Packet;
import net.minecraft.server.v1_16_R3.PacketListenerPlayIn;
import net.minecraft.server.v1_16_R3.PacketPlayInKeepAlive;

/**
 * https://wiki.vg/Protocol#Keep_Alive_.28serverbound.29
 * <p>
 * The server will frequently send out a keep-alive, each containing a random
 * ID. The client must respond with the same packet.
 * <p>
 * Packet ID: 0x10<br>
 * State: Play<br>
 * Bound To: Server
 * 
 * @author Martin
 *
 */
public class PacketPlayInKeepAliveEvent extends PacketPlayInEvent {

	public long keepAliveId;

	public PacketPlayInKeepAliveEvent(Player injectedPlayer, PacketPlayInKeepAlive packet) {
		super(injectedPlayer);
		keepAliveId = packet.b();
	}

	public PacketPlayInKeepAliveEvent(Player injectedPlayer, long keepAliveId) {
		super(injectedPlayer);
		this.keepAliveId = keepAliveId;
	}

	public long getKeepAliveId() {
		return keepAliveId;
	}

	@Override
	public Packet<PacketListenerPlayIn> getNMS() {
		final PacketPlayInKeepAlive packet = new PacketPlayInKeepAlive();
		Field.set(packet, "a", keepAliveId);
		return packet;
	}

	@Override
	public int getPacketID() {
		return 0x10;
	}

	@Override
	public String getProtocolURLString() {
		return "https://wiki.vg/Protocol#Keep_Alive_.28serverbound.29";
	}

}
