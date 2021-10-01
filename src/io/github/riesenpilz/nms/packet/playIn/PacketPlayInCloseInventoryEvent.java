package io.github.riesenpilz.nms.packet.playIn;

import org.bukkit.entity.Player;

import net.minecraft.server.v1_16_R3.Packet;
import net.minecraft.server.v1_16_R3.PacketListenerPlayIn;
import net.minecraft.server.v1_16_R3.PacketPlayInCloseWindow;

/**
 * https://wiki.vg/Protocol#Close_Window_.28serverbound.29
 * <p>
 * This packet is sent by the client when closing a window.<br>
 * Notchian clients send a Close Window packet with Window ID 0 to close their
 * inventory even though there is never an Open Window packet for the inventory.
 * <p>
 * Packet ID: 0x0A<br>
 * State: Play<br>
 * Bound To: Server
 * 
 * @author Martin
 *
 */
public class PacketPlayInCloseInventoryEvent extends PacketPlayInInventoryEvent {

	public PacketPlayInCloseInventoryEvent(Player injectedPlayer, PacketPlayInCloseWindow packet) {
		super(injectedPlayer, packet, "id");

	}

	public PacketPlayInCloseInventoryEvent(Player injectedPlayer, int inventoryId) {
		super(injectedPlayer, inventoryId);
	}


	@Override
	public Packet<PacketListenerPlayIn> getNMS() {
		return new PacketPlayInCloseWindow(getInventoryId());
	}

	@Override
	public int getPacketID() {
		return 0x0A;
	}

	@Override
	public String getProtocolURLString() {
		return "https://wiki.vg/Protocol#Close_Window_.28serverbound.29";
	}
}
