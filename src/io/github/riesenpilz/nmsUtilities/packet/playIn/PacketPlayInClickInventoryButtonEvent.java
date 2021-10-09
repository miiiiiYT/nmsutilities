package io.github.riesenpilz.nmsUtilities.packet.playIn;

import org.apache.commons.lang.Validate;
import org.bukkit.entity.Player;

import io.github.riesenpilz.nmsUtilities.reflections.Field;
import net.minecraft.server.v1_16_R3.Packet;
import net.minecraft.server.v1_16_R3.PacketListenerPlayIn;
import net.minecraft.server.v1_16_R3.PacketPlayInEnchantItem;

/**
 * https://wiki.vg/Protocol#Click_Window_Button
 * <p>
 * Used when clicking on window buttons. Until 1.14, this was only used by
 * enchantment tables.
 * <p>
 * Packet ID: 0x08<br>
 * State: Play<br>
 * Bound To: Server
 * 
 * @author Martin
 *
 */
public class PacketPlayInClickInventoryButtonEvent extends PacketPlayInInventoryEvent {

	/**
	 * Meaning depends on window type.
	 */
	private int buttonId;

	public PacketPlayInClickInventoryButtonEvent(Player injectedPlayer, PacketPlayInEnchantItem packet) {
		super(injectedPlayer, packet.b());
		Validate.notNull(packet);
		buttonId = packet.c();
	}

	public PacketPlayInClickInventoryButtonEvent(Player injectedPlayer, int inventoryId, int buttonId) {
		super(injectedPlayer, inventoryId);
		this.buttonId = buttonId;
	}

	public int getButtonId() {
		return buttonId;
	}

	@Override
	public Packet<PacketListenerPlayIn> getNMS() {
		final PacketPlayInEnchantItem packet = new PacketPlayInEnchantItem();
		Field.set(packet, "a", getInventoryId());
		Field.set(packet, "b", buttonId);
		return packet;
	}

	@Override
	public int getPacketID() {
		return 0x08;
	}

	@Override
	public String getProtocolURLString() {
		return "https://wiki.vg/Protocol#Click_Window_Button";
	}
}
