package io.github.riesenpilz.nmsUtilities.packet.playIn;

import org.apache.commons.lang.Validate;
import org.bukkit.entity.Player;

import io.github.riesenpilz.nmsUtilities.reflections.Field;
import net.minecraft.server.v1_16_R3.Packet;
import net.minecraft.server.v1_16_R3.PacketListenerPlayIn;
import net.minecraft.server.v1_16_R3.PacketPlayInTeleportAccept;

/**
 * https://wiki.vg/Protocol#Teleport_Confirm
 * <p>
 * Sent by client as confirmation of Player Position And Look.
 * <p>
 * Packet ID: 0x00<br>
 * State: Play<br>
 * Bound To: Server
 * 
 * 
 * @author Martin
 *
 */
public class PacketPlayInTeleportAcceptEvent extends PacketPlayInEvent {

	/**
	 * The ID given by the Player Position And Look packet.
	 */
	private int teleportId;

	public PacketPlayInTeleportAcceptEvent(Player injectedPlayer, PacketPlayInTeleportAccept packet) {
		super(injectedPlayer);
		Validate.notNull(packet);
		teleportId = packet.b();
	}

	public PacketPlayInTeleportAcceptEvent(Player injectedPlayer, int teleportId) {
		super(injectedPlayer);
		this.teleportId = teleportId;
	}

	public int getTeleportId() {
		return teleportId;
	}

	@Override
	public Packet<PacketListenerPlayIn> getNMS() {
		final PacketPlayInTeleportAccept packet = new PacketPlayInTeleportAccept();
		Field.set(packet, "a", teleportId);
		return packet;
	}

	@Override
	public int getPacketID() {
		return 0x00;
	}

	@Override
	public String getProtocolURLString() {
		return "https://wiki.vg/Protocol#Teleport_Confirm";
	}
}
