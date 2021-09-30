package io.github.riesenpilz.nms.packet.playOut;

import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.Player;

import io.github.riesenpilz.nms.inventory.ItemStack;
import io.github.riesenpilz.nms.reflections.Field;
import net.minecraft.server.v1_16_R3.NonNullList;
import net.minecraft.server.v1_16_R3.Packet;
import net.minecraft.server.v1_16_R3.PacketListenerPlayOut;
import net.minecraft.server.v1_16_R3.PacketPlayOutWindowItems;

/**
 * https://wiki.vg/Protocol#Window_Items
 * <p>
 * Sent by the server when items in multiple slots (in a window) are
 * added/removed. This includes the main inventory, equipped armour and crafting
 * slots. This packet with Window ID set to "0" is sent during the player
 * joining sequence to initialise the player's inventory.
 * <p>
 * See inventory windows for further information about how slots are indexed.
 * <p>
 * Packet ID: 0x13<br>
 * State: Play<br>
 * Bound To: Client
 *
 * @author Martin
 *
 */
public class PacketPlayOutInventoryItemsEvent extends PacketPlayOutEvent {

	private int windowID;
	private List<ItemStack> itemStacks;

	public PacketPlayOutInventoryItemsEvent(Player injectedPlayer, PacketPlayOutWindowItems packet) {
		super(injectedPlayer);
		windowID = Field.get(packet, "a", int.class);
		@SuppressWarnings("unchecked")
		List<net.minecraft.server.v1_16_R3.ItemStack> itemStacks2 = Field.get(packet, "a", List.class);
		for (net.minecraft.server.v1_16_R3.ItemStack itemStack : itemStacks2)
			itemStacks.add(ItemStack.getItemStackOf(itemStack));
	}

	public PacketPlayOutInventoryItemsEvent(Player injectedPlayer, int windowID, List<ItemStack> itemStacks) {
		super(injectedPlayer);
		this.windowID = windowID;
		this.itemStacks = itemStacks;
	}

	public int getWindowID() {
		return windowID;
	}

	public List<ItemStack> getItemStacks() {
		return itemStacks;
	}

	@Override
	public Packet<PacketListenerPlayOut> getNMS() {
		NonNullList<net.minecraft.server.v1_16_R3.ItemStack> itemStacks2 = NonNullList.a(itemStacks.size(),
				net.minecraft.server.v1_16_R3.ItemStack.b);
		for (int i = 0; i < itemStacks.size(); i++) {
			ItemStack itemStack = itemStacks.get(i);
			itemStacks2.set(i, itemStack == null ? new ItemStack(Material.AIR).getNMS() : itemStack.getNMS());
		}
		return new PacketPlayOutWindowItems(windowID, itemStacks2);
	}

	@Override
	public int getPacketID() {
		return 0x13;
	}

	@Override
	public String getProtocolURLString() {
		return "https://wiki.vg/Protocol#Window_Items";
	}
}
