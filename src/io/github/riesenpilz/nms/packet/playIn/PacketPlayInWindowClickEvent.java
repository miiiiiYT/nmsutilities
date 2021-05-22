package io.github.riesenpilz.nms.packet.playIn;

import org.bukkit.entity.Player;

import io.github.riesenpilz.nms.inventory.InventoryClickType;
import io.github.riesenpilz.nms.inventory.ItemStack;
import io.github.riesenpilz.nms.reflections.Field;
import net.minecraft.server.v1_16_R3.Packet;
import net.minecraft.server.v1_16_R3.PacketListenerPlayIn;
import net.minecraft.server.v1_16_R3.PacketPlayInWindowClick;

/**
 * https://wiki.vg/Protocol#Click_Window<br>
 * <br>
 * This packet is sent by the player when it clicks on a slot in a window.<br>
 * <br>
 * Packet ID: 0x09<br>
 * State: Play<br>
 * Bound To: Server
 * 
 * @author Martin
 *
 */
public class PacketPlayInWindowClickEvent extends PacketPlayInEvent {

	public static final int OUT_OF_BOUND = -999;

	/**
	 * The ID of the window which was clicked. 0 for player inventory.
	 */
	private int windowID;

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

	public PacketPlayInWindowClickEvent(Player injectedPlayer, PacketPlayInWindowClick packet) {
		super(injectedPlayer);
		clickType = InventoryClickType.getInventoryClickType(packet.g());
		itemStack = new ItemStack(packet.f());
		action = packet.e();
		windowID = packet.b();
		slot = packet.c();
		button = packet.d();
	}

	public PacketPlayInWindowClickEvent(Player injectedPlayer, InventoryClickType clickType, ItemStack itemStack,
			short action, int windowID, int slot, int button) {
		super(injectedPlayer);
		this.clickType = clickType;
		this.itemStack = itemStack;
		this.action = action;
		this.windowID = windowID;
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

	public int getWindowID() {
		return windowID;
	}

	@Override
	public Packet<PacketListenerPlayIn> getNMS() {
		final PacketPlayInWindowClick packet = new PacketPlayInWindowClick();
		new Field(PacketPlayInWindowClick.class, "a").set(packet, windowID);
		new Field(PacketPlayInWindowClick.class, "slot").set(packet, slot);
		new Field(PacketPlayInWindowClick.class, "button").set(packet, button);
		new Field(PacketPlayInWindowClick.class, "d").set(packet, action);
		new Field(PacketPlayInWindowClick.class, "item").set(packet, itemStack.getNMS());
		new Field(PacketPlayInWindowClick.class, "shift").set(packet, clickType.getNMS());
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
