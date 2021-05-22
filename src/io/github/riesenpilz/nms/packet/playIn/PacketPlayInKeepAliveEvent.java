package io.github.riesenpilz.nms.packet.playIn;

import org.bukkit.entity.Player;

import io.github.riesenpilz.nms.reflections.Field;
import net.minecraft.server.v1_16_R3.Packet;
import net.minecraft.server.v1_16_R3.PacketListenerPlayIn;
import net.minecraft.server.v1_16_R3.PacketPlayInKeepAlive;

/**
 * https://wiki.vg/Protocol#Keep_Alive_.28serverbound.29<br>
 * <br>
 * The server will frequently send out a keep-alive, each containing a random
 * ID. The client must respond with the same packet.<br>
 * <br>
 * Packet ID: 0x10<br>
 * State: Play<br>
 * Bound To: Server
 * 
 * @author Martin
 *
 */
public class PacketPlayInKeepAliveEvent extends PacketPlayInEvent {

	public long keepAliveID;

	public PacketPlayInKeepAliveEvent(Player injectedPlayer, PacketPlayInKeepAlive packet) {
		super(injectedPlayer);
		keepAliveID = packet.b();
	}

	public PacketPlayInKeepAliveEvent(Player injectedPlayer, long keepAliveID) {
		super(injectedPlayer);
		this.keepAliveID = keepAliveID;
	}

	public long getKeepAliveID() {
		return keepAliveID;
	}

	@Override
	public Packet<PacketListenerPlayIn> getNMS() {
		final PacketPlayInKeepAlive packet = new PacketPlayInKeepAlive();
		new Field(PacketPlayInKeepAlive.class, "a").set(packet, keepAliveID);
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
