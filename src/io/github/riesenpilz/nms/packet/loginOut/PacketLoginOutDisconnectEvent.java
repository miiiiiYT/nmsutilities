package io.github.riesenpilz.nms.packet.loginOut;

import org.bukkit.entity.Player;

import io.github.riesenpilz.nms.reflections.Field;
import net.minecraft.server.v1_16_R3.IChatBaseComponent;
import net.minecraft.server.v1_16_R3.Packet;
import net.minecraft.server.v1_16_R3.PacketLoginOutDisconnect;
import net.minecraft.server.v1_16_R3.PacketLoginOutListener;

/**
 * https://wiki.vg/Protocol#Disconnect_.28login.29<br>
 * <br>
 * Packet ID: 0x00<br>
 * State: Login<br>
 * Bound To: Client
 * 
 * @author Martin
 *
 */
public class PacketLoginOutDisconnectEvent extends PacketLoginOutEvent {

	public static final String PROTOCOL_URL = "https://wiki.vg/Protocol#Disconnect_.28login.29";
	public static final int PACKET_ID = 0;

	private IChatBaseComponent reason;

	public PacketLoginOutDisconnectEvent(Player injectedPlayer, IChatBaseComponent reason) {
		super(injectedPlayer, PACKET_ID, PROTOCOL_URL);
		this.reason = reason;
	}

	public PacketLoginOutDisconnectEvent(Player injectedPlayer, PacketLoginOutDisconnect packet) {
		super(injectedPlayer, PACKET_ID, PROTOCOL_URL);
		reason = (IChatBaseComponent) new Field(PacketLoginOutDisconnect.class, "a").get(packet);
	}

	public IChatBaseComponent getReason() {
		return reason;
	}

	@Override
	public Packet<PacketLoginOutListener> getNMS() {
		return new PacketLoginOutDisconnect();
	}

}
