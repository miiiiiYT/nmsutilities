package io.github.riesenpilz.nms.packet.playOut;

import org.bukkit.entity.Player;

import net.minecraft.server.v1_16_R3.Packet;
import net.minecraft.server.v1_16_R3.PacketListenerPlayOut;
import net.minecraft.server.v1_16_R3.PacketPlayOutCloseWindow;

/**
 * https://wiki.vg/Protocol#Close_Window_.28clientbound.29
 * <p>
 * This packet is sent from the server to the client when a window is forcibly
 * closed, such as when a chest is destroyed while it's open.
 * <p>
 * Packet ID: 0x12<br>
 * State: Play<br>
 * Bound To: Client
 *
 * @author Martin
 *
 */
public class PacketPlayOutInventoryCloseEvent extends PacketPlayOutInventoryEvent {

	public PacketPlayOutInventoryCloseEvent(Player injectedPlayer, PacketPlayOutCloseWindow packet) {
		super(injectedPlayer, packet);
	}

	public PacketPlayOutInventoryCloseEvent(Player injectedPlayer, int inventoryId) {
		super(injectedPlayer, inventoryId);
	}

	@Override
	public Packet<PacketListenerPlayOut> getNMS() {
		return new PacketPlayOutCloseWindow(getInventoryId());
	}

	@Override
	public int getPacketID() {
		return 0x12;
	}

	@Override
	public String getProtocolURLString() {
		return "https://wiki.vg/Protocol#Close_Window_.28clientbound.29";
	}
}
