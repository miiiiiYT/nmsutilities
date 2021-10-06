package io.github.riesenpilz.nmsUtilities.packet.playIn;

import org.bukkit.entity.Player;

import io.github.riesenpilz.nmsUtilities.reflections.Field;
import net.minecraft.server.v1_16_R3.Packet;
import net.minecraft.server.v1_16_R3.PacketListenerPlayIn;

public abstract class PacketPlayInInventoryEvent extends PacketPlayInEvent {

	/**
	 * A unique id number for the window to be displayed. Notchian server
	 * implementation is a counter, starting at 1. Player inventory is 0.
	 */
	private int inventoryId;

	public PacketPlayInInventoryEvent(Player injectedPlayer, int inventoryId) {
		super(injectedPlayer);
		this.inventoryId = inventoryId;
	}

	protected PacketPlayInInventoryEvent(Player injectedPlayer, Packet<PacketListenerPlayIn> packet) {
		this(injectedPlayer, packet, "a");
	}

	protected PacketPlayInInventoryEvent(Player injectedPlayer, Packet<PacketListenerPlayIn> packet, String fieldName) {
		super(injectedPlayer);
		inventoryId = Field.get(packet, fieldName, int.class);
	}

	public int getInventoryId() {
		return inventoryId;
	}

}
