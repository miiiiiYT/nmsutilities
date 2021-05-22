package io.github.riesenpilz.nms.packet.playIn;

import org.bukkit.entity.Player;

import io.github.riesenpilz.nms.reflections.Field;
import net.minecraft.server.v1_16_R3.Packet;
import net.minecraft.server.v1_16_R3.PacketListenerPlayIn;
import net.minecraft.server.v1_16_R3.PacketPlayInEnchantItem;

/**
 * https://wiki.vg/Protocol#Click_Window_Button<br>
 * <br>
 * Used when clicking on window buttons. Until 1.14, this was only used by
 * enchantment tables.<br>
 * <br>
 * Packet ID: 0x08<br>
 * State: Play<br>
 * Bound To: Server
 * 
 * @author Martin
 *
 */
public class PacketPlayInClickWindowButtonEvent extends PacketPlayInEvent {

	/**
	 * The ID of the window sent by Open Window.
	 */
	private int windowID;

	/**
	 * Meaning depends on window type.
	 */
	private int buttonID;

	public PacketPlayInClickWindowButtonEvent(Player injectedPlayer, PacketPlayInEnchantItem packet) {
		super(injectedPlayer);
		windowID = packet.b();
		buttonID = packet.c();
	}

	public PacketPlayInClickWindowButtonEvent(Player injectedPlayer, int windowID, int buttonID) {
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
		new Field(PacketPlayInEnchantItem.class, "a").set(packet, windowID);
		new Field(PacketPlayInEnchantItem.class, "b").set(packet, buttonID);
		return packet;
	}

	@Override
	public int getPacketID() {
		return 8;
	}

	@Override
	public String getProtocolURLString() {
		return "https://wiki.vg/Protocol#Click_Window_Button";
	}
}
