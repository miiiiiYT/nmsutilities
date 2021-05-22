package io.github.riesenpilz.nms.packet.playIn;

import org.bukkit.entity.Player;

import io.github.riesenpilz.nms.reflections.Field;
import net.minecraft.server.v1_16_R3.Packet;
import net.minecraft.server.v1_16_R3.PacketListenerPlayIn;
import net.minecraft.server.v1_16_R3.PacketPlayInTeleportAccept;

/**
 * https://wiki.vg/Protocol#Teleport_Confirm<br>
 * <br>
 * Sent by client as confirmation of Player Position And Look.<br>
 * <br>
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
	private int teleportID;

	public PacketPlayInTeleportAcceptEvent(Player injectedPlayer, PacketPlayInTeleportAccept packet) {
		super(injectedPlayer);
		teleportID = packet.b();
	}

	public PacketPlayInTeleportAcceptEvent(Player injectedPlayer, int teleportID) {
		super(injectedPlayer);
		this.teleportID = teleportID;
	}

	public int getTeleportID() {
		return teleportID;
	}

	@Override
	public Packet<PacketListenerPlayIn> getNMS() {
		final PacketPlayInTeleportAccept packet = new PacketPlayInTeleportAccept();
		new Field(PacketPlayInTeleportAccept.class, "a").set(packet, teleportID);
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
