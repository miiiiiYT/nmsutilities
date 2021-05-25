package io.github.riesenpilz.nms.packet.playIn;

import org.bukkit.entity.Player;

import io.github.riesenpilz.nms.reflections.Field;
import net.minecraft.server.v1_16_R3.Packet;
import net.minecraft.server.v1_16_R3.PacketListenerPlayIn;
import net.minecraft.server.v1_16_R3.PacketPlayInTabComplete;

/**
 * https://wiki.vg/Protocol#Tab-Complete_.28serverbound.29
 * <p>
 * Sent when the client needs to tab-complete a minecraft:ask_server suggestion
 * type.
 * <p>
 * Packet ID: 0x06<br>
 * State: Play<br>
 * Bound To: Server
 *
 * @author Martin
 *
 */
public class PacketPlayInTabCompleteEvent extends PacketPlayInEvent {

	/**
	 * The id of the transaction that the server will send back to the client in the
	 * response of this packet. Client generates this and increments it each time it
	 * sends another tab completion that doesn't get a response.
	 */
	private int transactionId;

	/**
	 * All text behind the cursor without the / (e.g. to the left of the cursor in
	 * left-to-right languages like English). (32500 chars)
	 */
	private String toComplete;

	public PacketPlayInTabCompleteEvent(Player injectedPlayer, PacketPlayInTabComplete packet) {
		super(injectedPlayer);
		transactionId = packet.b();
		toComplete = packet.c();
	}

	public PacketPlayInTabCompleteEvent(Player injectedPlayer, int transactionId, String toComplete) {
		super(injectedPlayer);
		this.transactionId = transactionId;
		this.toComplete = toComplete;
	}

	public int getTransactionId() {
		return transactionId;
	}

	public String getToComplete() {
		return toComplete;
	}

	@Override
	public Packet<PacketListenerPlayIn> getNMS() {
		final PacketPlayInTabComplete packet = new PacketPlayInTabComplete();
		Field.set(packet, "a", transactionId);
		Field.set(packet, "b", toComplete);
		return packet;
	}

	@Override
	public int getPacketID() {
		return 0x06;
	}

	@Override
	public String getProtocolURLString() {
		return "https://wiki.vg/Protocol#Tab-Complete_.28serverbound.29";
	}
}
