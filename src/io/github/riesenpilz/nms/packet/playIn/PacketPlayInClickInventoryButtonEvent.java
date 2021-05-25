package io.github.riesenpilz.nms.packet.playIn;

import org.bukkit.entity.Player;

import io.github.riesenpilz.nms.reflections.Field;
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
public class PacketPlayInClickInventoryButtonEvent extends PacketPlayInEvent {

	/**
	 * The ID of the window sent by Open Window.
	 */
	private int windowID;

	/**
	 * Meaning depends on window type.
	 */
	private int buttonID;

	public PacketPlayInClickInventoryButtonEvent(Player injectedPlayer, PacketPlayInEnchantItem packet) {
		super(injectedPlayer);
		windowID = packet.b();
		buttonID = packet.c();
	}

	public PacketPlayInClickInventoryButtonEvent(Player injectedPlayer, int windowID, int buttonID) {
		super(injectedPlayer);
		this.windowID = windowID;
		this.buttonID = buttonID;
	}

	public int getWindowID() {
		return windowID;
	}

	public int getButtonID() {
		return buttonID;
	}

	@Override
	public Packet<PacketListenerPlayIn> getNMS() {
		final PacketPlayInEnchantItem packet = new PacketPlayInEnchantItem();
		Field.set(packet, "a", windowID);
		Field.set(packet, "b", buttonID);
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
