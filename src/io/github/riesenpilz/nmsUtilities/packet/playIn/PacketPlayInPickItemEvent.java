package io.github.riesenpilz.nmsUtilities.packet.playIn;

import org.apache.commons.lang.Validate;
import org.bukkit.entity.Player;

import io.github.riesenpilz.nmsUtilities.reflections.Field;
import net.minecraft.server.v1_16_R3.Packet;
import net.minecraft.server.v1_16_R3.PacketListenerPlayIn;
import net.minecraft.server.v1_16_R3.PacketPlayInPickItem;

/**
 * https://wiki.vg/Protocol#Pick_Item
 * <p>
 * Used to swap out an empty space on the hotbar with the item in the given
 * inventory slot. The Notchain client uses this for pick block functionality
 * (middle click) to retrieve items from the inventory.
 * <p>
 * The server will first search the player's hotbar for an empty slot, starting
 * from the current slot and looping around to the slot before it. If there are
 * no empty slots, it will start a second search from the current slot and find
 * the first slot that does not contain an enchanted item. If there still are no
 * slots that meet that criteria, then the server will use the currently
 * selected slot.
 * <p>
 * After finding the appropriate slot, the server swaps the items and then send
 * 3 packets: <br>
 * - Set Slot, with window ID set to -2 and slot set to the newly chosen slot
 * and the item set to the item that is now in that slot (which was previously
 * at the slot the client requested)<br>
 * - Set Slot, with window ID set to -2 and slot set to the slot the player
 * requested, with the item that is now in that slot and was previously on the
 * hotbar slot<br>
 * - Held Item Change, with the slot set to the newly chosen slot.
 * <p>
 * Packet ID: 0x18<br>
 * State: Play<br>
 * Bound To: Server<br>
 * 
 * @author Martin
 *
 */
public class PacketPlayInPickItemEvent extends PacketPlayInEvent {

	private int slot;

	public PacketPlayInPickItemEvent(Player injectedPlayer, PacketPlayInPickItem packet) {
		super(injectedPlayer);
		Validate.notNull(packet);
		slot = packet.b();
	}

	public PacketPlayInPickItemEvent(Player injectedPlayer, int slot) {
		super(injectedPlayer);
		this.slot = slot;
	}

	public int getSlot() {
		return slot;
	}

	@Override
	public Packet<PacketListenerPlayIn> getNMS() {
		final PacketPlayInPickItem packet = new PacketPlayInPickItem();
		Field.set(packet, "a", slot);
		return packet;
	}

	@Override
	public int getPacketID() {
		return 0x18;
	}

	@Override
	public String getProtocolURLString() {
		return "https://wiki.vg/Protocol#Pick_Item";
	}
}
