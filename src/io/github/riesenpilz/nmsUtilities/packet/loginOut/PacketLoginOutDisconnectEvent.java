package io.github.riesenpilz.nmsUtilities.packet.loginOut;

import org.bukkit.craftbukkit.v1_16_R3.util.CraftChatMessage;
import org.bukkit.entity.Player;

import io.github.riesenpilz.nmsUtilities.reflections.Field;
import net.minecraft.server.v1_16_R3.IChatBaseComponent;
import net.minecraft.server.v1_16_R3.Packet;
import net.minecraft.server.v1_16_R3.PacketLoginOutDisconnect;
import net.minecraft.server.v1_16_R3.PacketLoginOutListener;

/**
 * https://wiki.vg/Protocol#Disconnect_.28login.29
 * <p>
 * Packet ID: 0x00<br>
 * State: Login<br>
 * Bound To: Client
 *
 * @author Martin
 *
 */
public class PacketLoginOutDisconnectEvent extends PacketLoginOutEvent {

	private String reason;

	public PacketLoginOutDisconnectEvent(Player injectedPlayer, String reason) {
		super(injectedPlayer);
		this.reason = reason;
	}

	public PacketLoginOutDisconnectEvent(Player injectedPlayer, PacketLoginOutDisconnect packet) {
		super(injectedPlayer);
		reason = CraftChatMessage.fromComponent(Field.get(packet, "a", IChatBaseComponent.class));
	}

	public String getReason() {
		return reason;
	}

	@Override
	public Packet<PacketLoginOutListener> getNMS() {
		return new PacketLoginOutDisconnect(CraftChatMessage.fromStringOrNull(reason));
	}

	@Override
	public int getPacketID() {
		return 0x00;
	}

	@Override
	public String getProtocolURLString() {
		return "https://wiki.vg/Protocol#Disconnect_.28login.29";
	}

}
