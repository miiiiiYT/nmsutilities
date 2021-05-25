package io.github.riesenpilz.nms.packet.statusOut;

import org.bukkit.entity.Player;

import io.github.riesenpilz.nms.packet.ServerPing;
import io.github.riesenpilz.nms.reflections.Field;
import net.minecraft.server.v1_16_R3.Packet;
import net.minecraft.server.v1_16_R3.PacketStatusOutListener;
import net.minecraft.server.v1_16_R3.PacketStatusOutServerInfo;

/**
 * https://wiki.vg/Protocol#Response
 * <p>
 * Packet ID: 0x00<br>
 * State: Status<br>
 * Bound To: Client
 * 
 * @author Martin
 *
 */
public class PacketStatusOutResponseEvent extends PacketStatusOutEvent {

	 private ServerPing ping;

	public PacketStatusOutResponseEvent(Player injectedPlayer, PacketStatusOutServerInfo packet) {
		super(injectedPlayer);
		ping = new ServerPing(Field.get(packet, "a", net.minecraft.server.v1_16_R3.ServerPing.class));
	}
	
	public PacketStatusOutResponseEvent(Player injectedPlayer, ServerPing ping) {
		super(injectedPlayer);
		this.ping = ping;
	}

	public ServerPing getPing() {
		return ping;
	}

	@Override
	public Packet<PacketStatusOutListener> getNMS() {
		return new PacketStatusOutServerInfo(ping.getNMS());
	}

	@Override
	public int getPacketID() {
		return 0x00;
	}

	@Override
	public String getProtocolURLString() {
		return "https://wiki.vg/Protocol#Response";
	}

}
