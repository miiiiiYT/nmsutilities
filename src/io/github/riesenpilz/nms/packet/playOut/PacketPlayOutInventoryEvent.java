package io.github.riesenpilz.nms.packet.playOut;

import org.bukkit.entity.Player;

import io.github.riesenpilz.nms.reflections.Field;
import net.minecraft.server.v1_16_R3.Packet;
import net.minecraft.server.v1_16_R3.PacketListenerPlayOut;

public abstract class PacketPlayOutInventoryEvent extends PacketPlayOutEvent{

	/**
	 * A unique id number for the window to be displayed. Notchian server
	 * implementation is a counter, starting at 1.
	 * Player inventory is 0.
	 */
	private int inventoryId;
	
	public PacketPlayOutInventoryEvent(Player injectedPlayer, int inventoryId) {
		super(injectedPlayer);
		this.inventoryId = inventoryId;
	}

	protected PacketPlayOutInventoryEvent(Player injectedPlayer, Packet<PacketListenerPlayOut> packet) {
		this(injectedPlayer, packet, "a");
	}

	protected PacketPlayOutInventoryEvent(Player injectedPlayer, Packet<PacketListenerPlayOut> packet,
			String fieldName) {
		super(injectedPlayer);
		inventoryId = Field.get(packet, fieldName, int.class);
	}
	
	public int getInventoryId() {
		return inventoryId;
	}

}
