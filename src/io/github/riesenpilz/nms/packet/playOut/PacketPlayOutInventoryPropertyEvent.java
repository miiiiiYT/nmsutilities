package io.github.riesenpilz.nms.packet.playOut;

import org.bukkit.entity.Player;

import io.github.riesenpilz.nms.reflections.Field;
import net.minecraft.server.v1_16_R3.Packet;
import net.minecraft.server.v1_16_R3.PacketListenerPlayOut;
import net.minecraft.server.v1_16_R3.PacketPlayOutWindowData;

/**
 * https://wiki.vg/Protocol#Window_Property
 * <p>
 * This packet is used to inform the client that part of a GUI window should be
 * updated.
 * <p>
 * Packet ID: 0x14<br>
 * State: Play<br>
 * Bound To: Client
 *
 * @author Martin
 *
 */
public class PacketPlayOutInventoryPropertyEvent extends PacketPlayOutInventoryEvent {

	private int property;
	private int value;

	public PacketPlayOutInventoryPropertyEvent(Player injectedPlayer, PacketPlayOutWindowData packet) {
		super(injectedPlayer, packet);
		property = Field.get(packet, "b", int.class);
		value = Field.get(packet, "c", int.class);
	}

	public PacketPlayOutInventoryPropertyEvent(Player injectedPlayer, int inventoryId, int property, int value) {
		super(injectedPlayer, inventoryId);
		this.property = property;
		this.value = value;
	}

	public int getProperty() {
		return property;
	}

	public int getValue() {
		return value;
	}

	@Override
	public Packet<PacketListenerPlayOut> getNMS() {
		return new PacketPlayOutWindowData(getInventoryId(), value, property);
	}

	@Override
	public int getPacketID() {
		return 0x14;
	}

	@Override
	public String getProtocolURLString() {
		return "https://wiki.vg/Protocol#Window_Property";
	}
}
