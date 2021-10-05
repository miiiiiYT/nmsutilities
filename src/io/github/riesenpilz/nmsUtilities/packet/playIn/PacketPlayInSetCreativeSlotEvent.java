package io.github.riesenpilz.nmsUtilities.packet.playIn;

import org.bukkit.entity.Player;

import io.github.riesenpilz.nmsUtilities.inventory.ItemStack;
import io.github.riesenpilz.nmsUtilities.reflections.Field;
import net.minecraft.server.v1_16_R3.Packet;
import net.minecraft.server.v1_16_R3.PacketListenerPlayIn;
import net.minecraft.server.v1_16_R3.PacketPlayInSetCreativeSlot;

/**
 * https://wiki.vg/Protocol#Creative_Inventory_Action
 * <p>
 * While the user is in the standard inventory (i.e., not a crafting bench) in
 * Creative mode, the player will send this packet.
 * <p>
 * Clicking in the creative inventory menu is quite different from non-creative
 * inventory management. Picking up an item with the mouse actually deletes the
 * item from the server, and placing an item into a slot or dropping it out of
 * the inventory actually tells the server to create the item from scratch.
 * (This can be verified by clicking an item that you don't mind deleting, then
 * severing the connection to the server; the item will be nowhere to be found
 * when you log back in.) As a result of this implementation strategy, the
 * "Destroy Item" slot is just a client-side implementation detail that means "I
 * don't intend to recreate this item.". Additionally, the long listings of
 * items (by category, etc.) are a client-side interface for choosing which item
 * to create. Picking up an item from such listings sends no packets to the
 * server; only when you put it somewhere does it tell the server to create the
 * item in that location.
 * <p>
 * This action can be described as "set inventory slot". Picking up an item sets
 * the slot to item ID -1. Placing an item into an inventory slot sets the slot
 * to the specified item. Dropping an item (by clicking outside the window)
 * effectively sets slot -1 to the specified item, which causes the server to
 * spawn the item entity, etc.. All other inventory slots are numbered the same
 * as the non-creative inventory (including slots for the 2x2 crafting menu,
 * even though they aren't visible in the vanilla client).
 * <p>
 * Packet ID: 0x28<br>
 * State: Play<br>
 * Bound To: Server
 * 
 * @author Martin
 *
 */
public class PacketPlayInSetCreativeSlotEvent extends PacketPlayInEvent {

	private int slot;
	private ItemStack itemStack;

	public PacketPlayInSetCreativeSlotEvent(Player injectedPlayer, PacketPlayInSetCreativeSlot packet) {
		super(injectedPlayer);
		slot = packet.b();
		itemStack = ItemStack.getItemStackOf(packet.getItemStack());
	}

	public PacketPlayInSetCreativeSlotEvent(Player injectedPlayer, int slot, org.bukkit.inventory.ItemStack itemStack) {
		super(injectedPlayer);
		this.slot = slot;
		this.itemStack = ItemStack.getItemStackOf(itemStack);
	}

	public org.bukkit.inventory.ItemStack getItemStack() {
		return itemStack.getBukkit();
	}

	public int getSlot() {
		return slot;
	}

	@Override
	public Packet<PacketListenerPlayIn> getNMS() {
		final PacketPlayInSetCreativeSlot packet = new PacketPlayInSetCreativeSlot();
		Field.set(packet, "slot", slot);
		Field.set(packet, "b", itemStack.getNMS());
		return packet;
	}

	@Override
	public int getPacketID() {
		return 0x28;
	}

	@Override
	public String getProtocolURLString() {
		return "https://wiki.vg/Protocol#Creative_Inventory_Action";
	}
}
