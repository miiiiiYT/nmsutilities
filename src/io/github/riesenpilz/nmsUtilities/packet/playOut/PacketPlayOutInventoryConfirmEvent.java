package io.github.riesenpilz.nmsUtilities.packet.playOut;

import org.bukkit.entity.Player;

import io.github.riesenpilz.nmsUtilities.reflections.Field;
import net.minecraft.server.v1_16_R3.Packet;
import net.minecraft.server.v1_16_R3.PacketListenerPlayOut;
import net.minecraft.server.v1_16_R3.PacketPlayOutTransaction;

/**
 * https://wiki.vg/Protocol#Window_Confirmation_.28clientbound.29
 * <p>
 * A packet from the server indicating whether a request from the client was
 * accepted, or whether there was a conflict (due to lag). If the packet was not
 * accepted, the client must respond with a serverbound window confirmation
 * packet.
 * <p>
 * Packet ID: 0x11<br>
 * State: Play<br>
 * Bound To: Client
 * 
 * @author Martin
 *
 */
public class PacketPlayOutInventoryConfirmEvent extends PacketPlayOutInventoryEvent {

	private short actionNumber;
	private boolean accept;

	public PacketPlayOutInventoryConfirmEvent(Player injectedPlayer, PacketPlayOutTransaction packet) {
		super(injectedPlayer, packet);

		actionNumber = Field.get(packet, "b", short.class);
		accept = Field.get(packet, "c", boolean.class);
	}

	public PacketPlayOutInventoryConfirmEvent(Player injectedPlayer, int inventoryId, short actionNumber,
			boolean accept) {
		super(injectedPlayer, inventoryId);

		this.actionNumber = actionNumber;
		this.accept = accept;
	}

	public short getActionNumber() {
		return actionNumber;
	}

	public boolean isAccept() {
		return accept;
	}

	@Override
	public Packet<PacketListenerPlayOut> getNMS() {
		return new PacketPlayOutTransaction(getInventoryId(), actionNumber, accept);
	}

	@Override
	public int getPacketID() {
		return 0x11;
	}

	@Override
	public String getProtocolURLString() {
		return "https://wiki.vg/Protocol#Window_Confirmation_.28clientbound.29";
	}
}
