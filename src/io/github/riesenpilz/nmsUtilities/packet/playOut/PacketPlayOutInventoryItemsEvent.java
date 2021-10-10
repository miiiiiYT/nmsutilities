package io.github.riesenpilz.nmsUtilities.packet.playOut;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.Validate;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import io.github.riesenpilz.nmsUtilities.inventory.ItemStack;
import io.github.riesenpilz.nmsUtilities.reflections.Field;
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
public class PacketPlayOutInventoryItemsEvent extends PacketPlayOutInventoryEvent {

	private List<ItemStack> itemStacks;

	public PacketPlayOutInventoryItemsEvent(Player injectedPlayer, PacketPlayOutWindowItems packet) {
		super(injectedPlayer, packet);

		itemStacks = new ArrayList<>();
		@SuppressWarnings("unchecked")
		List<net.minecraft.server.v1_16_R3.ItemStack> nmsItemStacks = Field.get(packet, "b", List.class);
		for (net.minecraft.server.v1_16_R3.ItemStack nmsItemStack : nmsItemStacks)
			itemStacks.add(ItemStack.getItemStackOf(nmsItemStack));
	}

	public PacketPlayOutInventoryItemsEvent(Player injectedPlayer, int inventoryId, List<ItemStack> itemStacks) {
		super(injectedPlayer, inventoryId);

		Validate.notNull(itemStacks);

		this.itemStacks = itemStacks;
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
		return new PacketPlayOutWindowItems(getInventoryId(), itemStacks2);
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
