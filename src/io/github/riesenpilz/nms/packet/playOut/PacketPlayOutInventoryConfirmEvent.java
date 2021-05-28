package io.github.riesenpilz.nms.packet.playOut;

import org.bukkit.entity.Player;

import io.github.riesenpilz.nms.reflections.Field;
import net.minecraft.server.v1_16_R3.Packet;
import net.minecraft.server.v1_16_R3.PacketListenerPlayOut;
import net.minecraft.server.v1_16_R3.PacketPlayOutTransaction;

/**
 * https://wiki.vg/Protocol#Window_Confirmation_.28clientbound.29
 * <p>
 * A packet from the server indicating whether a request from the client was
 * accepted, or whether there was a conflict (due to lag). If the packet was not
 * accepted, the client must respond with a serverbound window confirmation
 * packet.
 * <p>
 * Packet ID: 0x11<br>
 * State: Play<br>
 * Bound To: Client
 * 
 * @author Martin
 *
 */
public class PacketPlayOutInventoryConfirmEvent extends PacketPlayOutEvent {
	
	private int windowID;
	private short actionNumber;
	private boolean accept;
	public PacketPlayOutInventoryConfirmEvent(Player injectedPlayer, PacketPlayOutTransaction packet) {
		super(injectedPlayer);
		windowID = Field.get(packet, "a", int.class);
		actionNumber = Field.get(packet, "b", short.class);
		accept = Field.get(packet, "c", boolean.class);
	}

	public PacketPlayOutInventoryConfirmEvent(Player injectedPlayer, int windowID, short actionNumber, boolean accept) {
		super(injectedPlayer);
		this.windowID = windowID;
		this.actionNumber = actionNumber;
		this.accept = accept;
	}

	public int getWindowID() {
		return windowID;
	}

	public short getActionNumber() {
		return actionNumber;
	}

	public boolean isAccept() {
		return accept;
	}

	@Override
	public Packet<PacketListenerPlayOut> getNMS() {
		return new PacketPlayOutTransaction(windowID, actionNumber, accept);
	}

	@Override
	public int getPacketID() {
		return 0x11;
	}

	@Override
	public String getProtocolURLString() {
		return "https://wiki.vg/Protocol#Window_Confirmation_.28clientbound.29";
	}
}
