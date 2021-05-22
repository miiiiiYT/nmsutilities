package io.github.riesenpilz.nms.packet.playIn;

import org.bukkit.entity.Player;

import io.github.riesenpilz.nms.reflections.Field;
import net.minecraft.server.v1_16_R3.Packet;
import net.minecraft.server.v1_16_R3.PacketListenerPlayIn;
import net.minecraft.server.v1_16_R3.PacketPlayInCloseWindow;

/**
 * https://wiki.vg/Protocol#Close_Window_.28serverbound.29<br>
 * <br>
 * This packet is sent by the client when closing a window.<br>
 * Notchian clients send a Close Window packet with Window ID 0 to close their
 * inventory even though there is never an Open Window packet for the
 * inventory.<br>
 * <br>
 * Packet ID: 0x0A<br>
 * State: Play<br>
 * Bound To: Server
 * 
 * @author Martin
 *
 */
public class PacketPlayInCloseWindowEvent extends PacketPlayInEvent {

	/**
	 * This is the ID of the window that was closed. 0 for player inventory.
	 */
	private int windowID;

	public PacketPlayInCloseWindowEvent(Player injectedPlayer, PacketPlayInCloseWindow packet) {
		super(injectedPlayer);
		windowID = (int) new Field(PacketPlayInCloseWindow.class, "id").get(packet);

	}

	public PacketPlayInCloseWindowEvent(Player injectedPlayer, int windowID) {
		super(injectedPlayer);
		this.windowID = windowID;
	}

	public int getWindowID() {
		return windowID;
	}

	@Override
	public Packet<PacketListenerPlayIn> getNMS() {
		return new PacketPlayInCloseWindow(windowID);
	}

	@Override
	public int getPacketID() {
		return 10;
	}

	@Override
	public String getProtocolURLString() {
		return "https://wiki.vg/Protocol#Close_Window_.28serverbound.29";
	}
}
