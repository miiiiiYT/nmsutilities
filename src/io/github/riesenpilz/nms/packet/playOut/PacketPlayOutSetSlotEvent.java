package io.github.riesenpilz.nms.packet.playOut;

import org.bukkit.entity.Player;

import io.github.riesenpilz.nms.inventory.ItemStack;
import io.github.riesenpilz.nms.reflections.Field;
import net.minecraft.server.v1_16_R3.Packet;
import net.minecraft.server.v1_16_R3.PacketListenerPlayOut;
import net.minecraft.server.v1_16_R3.PacketPlayOutSetSlot;

/**
 * https://wiki.vg/Protocol#Set_Slot
 * <p>
 * Sent by the server when an item in a slot (in a window) is added/removed.
 * <p>
 * To set the cursor (the item currently dragged with the mouse), use -1 as
 * Window ID and as Slot.
 * <p>
 * This packet can only be used to edit the hotbar of the player's inventory if
 * window ID is set to 0 (slots 36 through 44). If the window ID is set to -2,
 * then any slot in the inventory can be used but no add item animation will be
 * played.
 * <p>
 * Packet ID: 0x15<br>
 * State: Play<br>
 * Bound To: Client
 * 
 * @author Martin
 *
 */
public class PacketPlayOutSetSlotEvent extends PacketPlayOutInventoryEvent {

	private int slot;
	private ItemStack itemStack;

	public PacketPlayOutSetSlotEvent(Player injectedPlayer, PacketPlayOutSetSlot packet) {
		super(injectedPlayer, packet);
		slot = Field.get(packet, "b", int.class);
		itemStack = ItemStack.getItemStackOf(Field.get(packet, "c", net.minecraft.server.v1_16_R3.ItemStack.class));
	}

	public PacketPlayOutSetSlotEvent(Player injectedPlayer, int inventoryId, int slot, ItemStack itemStack) {
		super(injectedPlayer, inventoryId);
		this.slot = slot;
		this.itemStack = itemStack;
	}

	public int getSlot() {
		return slot;
	}

	public ItemStack getItemStack() {
		return itemStack;
	}

	@Override
	public Packet<PacketListenerPlayOut> getNMS() {
		return new PacketPlayOutSetSlot(getInventoryId(), slot, itemStack.getNMS());
	}

	@Override
	public int getPacketID() {
		return 0x15;
	}

	@Override
	public String getProtocolURLString() {
		return "https://wiki.vg/Protocol#Set_Slot";
	}
}
