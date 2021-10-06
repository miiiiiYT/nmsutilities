package io.github.riesenpilz.nmsUtilities.packet.playIn;

import org.bukkit.entity.Player;

import io.github.riesenpilz.nmsUtilities.inventory.InventoryClickType;
import io.github.riesenpilz.nmsUtilities.inventory.ItemStack;
import io.github.riesenpilz.nmsUtilities.reflections.Field;
import net.minecraft.server.v1_16_R3.Packet;
import net.minecraft.server.v1_16_R3.PacketListenerPlayIn;
import net.minecraft.server.v1_16_R3.PacketPlayInWindowClick;

/**
 * https://wiki.vg/Protocol#Click_Window
 * <p>
 * This packet is sent by the player when it clicks on a slot in a window.
 * <p>
 * Packet ID: 0x09<br>
 * State: Play<br>
 * Bound To: Server
 *
 * @author Martin
 *
 */
public class PacketPlayInInventoryClickEvent extends PacketPlayInInventoryEvent {

	public static final int OUT_OF_BOUND = -999;

	/**
	 * The clicked slot number. May be {@link #OUT_OF_BOUND}.
	 */
	private int slot;

	/**
	 * The button used in the click.
	 */
	private int button;

	/**
	 * A unique number for the action, implemented by Notchian as a counter,
	 * starting at 1 (different counter for every window ID). Used by the server to
	 * send back a Window Confirmation (clientbound).
	 */
	private short action;

	private ItemStack itemStack;

	private InventoryClickType clickType;

	public PacketPlayInInventoryClickEvent(Player injectedPlayer, PacketPlayInWindowClick packet) {
		super(injectedPlayer, packet.b());
		clickType = InventoryClickType.getInventoryClickType(packet.g());
		itemStack = ItemStack.getItemStackOf(packet.f());
		action = packet.e();
		slot = packet.c();
		button = packet.d();
	}

	public PacketPlayInInventoryClickEvent(Player injectedPlayer, InventoryClickType clickType, ItemStack itemStack,
			short action, int inventoryId, int slot, int button) {
		super(injectedPlayer, inventoryId);
		this.clickType = clickType;
		this.itemStack = itemStack;
		this.action = action;
		this.slot = slot;
		this.button = button;
	}

	public ItemStack getItemStack() {
		return itemStack;
	}

	public InventoryClickType getClickType() {
		return clickType;
	}

	public int getSlot() {
		return slot;
	}

	public short getAction() {
		return action;
	}

	public int getButton() {
		return button;
	}

	@Override
	public Packet<PacketListenerPlayIn> getNMS() {
		final PacketPlayInWindowClick packet = new PacketPlayInWindowClick();
		Field.set(packet, "a", getInventoryId());
		Field.set(packet, "slot", slot);
		Field.set(packet, "button", button);
		Field.set(packet, "d", action);
		Field.set(packet, "item", itemStack.getNMS());
		Field.set(packet, "shift", clickType.getNMS());
		return packet;
	}

	@Override
	public int getPacketID() {
		return 0x09;
	}

	@Override
	public String getProtocolURLString() {
		return "https://wiki.vg/Protocol#Click_Window";
	}

}
