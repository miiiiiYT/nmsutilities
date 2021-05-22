package io.github.riesenpilz.nms.packet.playOut;

import org.bukkit.entity.Player;

import net.minecraft.server.v1_16_R3.Packet;
import net.minecraft.server.v1_16_R3.PacketListenerPlayOut;

/**
 * https://wiki.vg/Protocol#Block_Entity_Data<br>
 * <br>
 * Sets the block entity associated with the block at the given location.<br>
 * <br>
 * Packet ID: 0x09<br>
 * State: Play<br>
 * Bound To: Client
 * 
 * @author Martin
 *
 */
public class PacketPlayOutSetBlockDataEvent extends PacketPlayOutEvent {

	public PacketPlayOutSetBlockDataEvent(Player injectedPlayer) {
		super(injectedPlayer);
	}

	@Override
	public Packet<PacketListenerPlayOut> getNMS() {
		return null;
	}

	@Override
	public int getPacketID() {
		return 0x09;
	}

	@Override
	public String getProtocolURLString() {
		return "https://wiki.vg/Protocol#Block_Entity_Data";
	}
}
