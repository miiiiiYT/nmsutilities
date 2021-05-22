package io.github.riesenpilz.nms.packet.playOut;

import org.bukkit.entity.Player;

import net.minecraft.server.v1_16_R3.Packet;
import net.minecraft.server.v1_16_R3.PacketListenerPlayOut;

/**
 * https://wiki.vg/Protocol#Block_Action<br>
 * <br>
 * This packet is used for a number of actions and animations performed by
 * blocks, usually non-persistent.<br>
 * <br>
 * See Block Actions for a list of values.<br>
 * <br>
 * <i>This packet uses a block ID, not a block state.</i><br>
 * <br>
 * Packet ID: 0x0A<br>
 * State: Play<br>
 * Bound To: Client
 * 
 * @author Martin
 *
 */
public class PacketPlayOutBlockActionEvent extends PacketPlayOutEvent {

	public PacketPlayOutBlockActionEvent(Player injectedPlayer) {
		super(injectedPlayer);
	}

	@Override
	public Packet<PacketListenerPlayOut> getNMS() {
		return null;
	}

	@Override
	public int getPacketID() {
		return 0x0A;
	}

	@Override
	public String getProtocolURLString() {
		return "https://wiki.vg/Protocol#Block_Action";
	}
}
