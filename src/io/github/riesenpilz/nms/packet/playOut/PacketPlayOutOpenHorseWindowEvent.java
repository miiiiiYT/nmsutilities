package io.github.riesenpilz.nms.packet.playOut;

import org.bukkit.entity.Player;

import net.minecraft.server.v1_16_R3.Packet;
import net.minecraft.server.v1_16_R3.PacketListenerPlayOut;

/**
 * https://wiki.vg/Protocol#Open_Horse_Window<br>
 * <br>
 * This packet is used exclusively for opening the horse GUI. Open Window is
 * used for all other GUIs.<br>
 * <br>
 * Packet ID: 0x1E<br>
 * State: Play<br>
 * Bound To: Client
 * 
 * @author Martin
 *
 */
public class PacketPlayOutOpenHorseWindowEvent extends PacketPlayOutEvent {

	public PacketPlayOutOpenHorseWindowEvent(Player injectedPlayer) {
		super(injectedPlayer);
	}

	@Override
	public Packet<PacketListenerPlayOut> getNMS() {
		return null;
	}

	@Override
	public int getPacketID() {
		return 0x1E;
	}

	@Override
	public String getProtocolURLString() {
		return "https://wiki.vg/Protocol#Open_Horse_Window";
	}
}
