package io.github.riesenpilz.nms.packet.playOut;

import org.bukkit.entity.Player;

import net.minecraft.server.v1_16_R3.Packet;
import net.minecraft.server.v1_16_R3.PacketListenerPlayOut;

/**
 * https://wiki.vg/Protocol#Block_Change<br>
 * <br>
 * Fired whenever a block is changed within the render distance.<br>
 * <br>
 * <i> Changing a block in a chunk that is not loaded is not a stable action.
 * The Notchian client currently uses a shared empty chunk which is modified for
 * all block changes in unloaded chunks; while in 1.9 this chunk never renders
 * in older versions the changed block will appear in all copies of the empty
 * chunk. Servers should avoid sending block changes in unloaded chunks and
 * clients should ignore such packets.</i><br>
 * <br>
 * Packet ID: <br>
 * State: Play<br>
 * Bound To: Client
 * 
 * @author Martin
 *
 */
public class PacketPlayOutBlockChangeEvent extends PacketPlayOutEvent {

	public PacketPlayOutBlockChangeEvent(Player injectedPlayer) {
		super(injectedPlayer);
	}

	@Override
	public Packet<PacketListenerPlayOut> getNMS() {
		return null;
	}

	@Override
	public int getPacketID() {
		return 0x0B;
	}

	@Override
	public String getProtocolURLString() {
		return "https://wiki.vg/Protocol#Block_Change";
	}
}
