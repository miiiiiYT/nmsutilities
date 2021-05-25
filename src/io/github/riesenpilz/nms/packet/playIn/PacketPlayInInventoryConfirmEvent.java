package io.github.riesenpilz.nms.packet.playIn;

import org.bukkit.entity.Player;

import io.github.riesenpilz.nms.reflections.Field;
import net.minecraft.server.v1_16_R3.Packet;
import net.minecraft.server.v1_16_R3.PacketListenerPlayIn;
import net.minecraft.server.v1_16_R3.PacketPlayInTransaction;

/**
 * https://wiki.vg/Protocol#Window_Confirmation_.28serverbound.29
 * <p>
 * If a confirmation sent by the client was not accepted, the server will reply
 * with a Window Confirmation (clientbound) packet with the Accepted field set
 * to false. When this happens, the client must send this packet to apologize
 * (as with movement), otherwise the server ignores any successive
 * confirmations.
 * <p>
 * Packet ID: 0x07<br>
 * State: Play<br>
 * Bound To: Server
 * 
 * @author Martin
 *
 */
public class PacketPlayInInventoryConfirmEvent extends PacketPlayInEvent {

	/**
	 * The ID of the window that the action occurred in.
	 */
	private int windowID;

	/**
	 * Every action that is to be accepted has a unique number. This number is an
	 * incrementing integer (starting at 1) with separate counts for each window ID.
	 */
	private short actionNumber;

	/**
	 * Whether the action was accepted.
	 */
	private boolean accepted;

	public PacketPlayInInventoryConfirmEvent(Player injectedPlayer, PacketPlayInTransaction packet) {
		super(injectedPlayer);
		windowID = packet.b();
		actionNumber = packet.c();
		accepted = Field.get(packet, "c", boolean.class);
	}

	public PacketPlayInInventoryConfirmEvent(Player injectedPlayer, int windowID, short actionNumber,
			boolean accepted) {
		super(injectedPlayer);
		this.windowID = windowID;
		this.actionNumber = actionNumber;
		this.accepted = accepted;
	}

	public int getWindowID() {
		return windowID;
	}

	public short getActionNumber() {
		return actionNumber;
	}

	public boolean isAccepted() {
		return accepted;
	}

	@Override
	public Packet<PacketListenerPlayIn> getNMS() {
		final PacketPlayInTransaction packet = new PacketPlayInTransaction();
		Field.set(packet, "a", windowID);
		Field.set(packet, "b", actionNumber);
		Field.set(packet, "c", accepted);
		return packet;
	}

	@Override
	public int getPacketID() {
		return 0x07;
	}

	@Override
	public String getProtocolURLString() {
		return "https://wiki.vg/Protocol#Window_Confirmation_.28serverbound.29";
	}

}
