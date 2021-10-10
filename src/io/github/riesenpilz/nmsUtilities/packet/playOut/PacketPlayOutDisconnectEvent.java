package io.github.riesenpilz.nmsUtilities.packet.playOut;

import org.apache.commons.lang.Validate;
import org.bukkit.entity.Player;

import io.github.riesenpilz.nmsUtilities.packet.HasText;
import io.github.riesenpilz.nmsUtilities.reflections.Field;
import net.minecraft.server.v1_16_R3.IChatBaseComponent;
import net.minecraft.server.v1_16_R3.Packet;
import net.minecraft.server.v1_16_R3.PacketListenerPlayOut;
import net.minecraft.server.v1_16_R3.PacketPlayOutKickDisconnect;

/**
 * https://wiki.vg/Protocol#Disconnect_.28play.29
 * <p>
 * Sent by the server before it disconnects a client. The client assumes that
 * the server has already closed the connection by the time the packet arrives.
 * <p>
 * Packet ID: 0x19<br>
 * State: Play<br>
 * Bound To: Client
 * 
 * @author Martin
 *
 */
public class PacketPlayOutDisconnectEvent extends PacketPlayOutEvent implements HasText {

	/**
	 * Displayed to the client when the connection terminates.
	 */
	private IChatBaseComponent reason;

	public PacketPlayOutDisconnectEvent(Player injectedPlayer, PacketPlayOutKickDisconnect packet) {
		super(injectedPlayer);

		Validate.notNull(packet);

		reason = Field.get(packet, "a", IChatBaseComponent.class);
	}

	public PacketPlayOutDisconnectEvent(Player injectedPlayer, IChatBaseComponent reason) {
		super(injectedPlayer);

		Validate.notNull(reason);

		this.reason = reason;
	}

	@Override
	public IChatBaseComponent getText() {
		return reason;
	}

	@Override
	public Packet<PacketListenerPlayOut> getNMS() {
		return new PacketPlayOutKickDisconnect(reason);
	}

	@Override
	public int getPacketID() {
		return 0x19;
	}

	@Override
	public String getProtocolURLString() {
		return "https://wiki.vg/Protocol#Disconnect_.28play.29";
	}
}
