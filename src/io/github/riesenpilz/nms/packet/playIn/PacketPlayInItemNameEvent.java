package io.github.riesenpilz.nms.packet.playIn;

import org.bukkit.entity.Player;

import net.minecraft.server.v1_16_R3.Packet;
import net.minecraft.server.v1_16_R3.PacketListenerPlayIn;
import net.minecraft.server.v1_16_R3.PacketPlayInItemName;

/**
 * https://wiki.vg/Protocol#Name_Item<br>
 * <br>
 * Sent as a player is renaming an item in an anvil (each keypress in the anvil
 * UI sends a new Name Item packet). If the new name is empty, then the item
 * loses its custom name (this is different from setting the custom name to the
 * normal name of the item). The item name may be no longer than 35 characters
 * long, and if it is longer than that, then the rename is silently ignored.<br>
 * <br>
 * Packet ID: 0x20<br>
 * State: Play<br>
 * Bound To: Server
 * 
 * @author Martin
 *
 */
public class PacketPlayInItemNameEvent extends PacketPlayInEvent {

	/**
	 * The new name of the item. (32767 chars)
	 */
	private String itemName;

	public PacketPlayInItemNameEvent(Player injectedPlayer, PacketPlayInItemName packet) {
		super(injectedPlayer);
		itemName = packet.b();
	}

	public PacketPlayInItemNameEvent(Player injectedPlayer, String itemName) {
		super(injectedPlayer);
		this.itemName = itemName;
	}

	public String getItemName() {
		return itemName;
	}

	@Override
	public Packet<PacketListenerPlayIn> getNMS() {
		return new PacketPlayInItemName(itemName);
	}

	@Override
	public int getPacketID() {
		return 0x20;
	}

	@Override
	public String getProtocolURLString() {
		return "https://wiki.vg/Protocol#Name_Item";
	}

}
