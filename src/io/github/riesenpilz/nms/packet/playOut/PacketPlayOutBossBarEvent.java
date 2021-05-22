package io.github.riesenpilz.nms.packet.playOut;

import org.bukkit.entity.Player;

import net.minecraft.server.v1_16_R3.Packet;
import net.minecraft.server.v1_16_R3.PacketListenerPlayOut;

/**
 * https://wiki.vg/Protocol#Boss_Bar<br>
 * <br>
 * Packet ID: 0x0C<br>
 * State: Play<br>
 * Bound To: Client
 * 
 * @author Martin
 *
 */
public class PacketPlayOutBossBarEvent extends PacketPlayOutEvent {

	public PacketPlayOutBossBarEvent(Player injectedPlayer) {
		super(injectedPlayer);
	}

	@Override
	public Packet<PacketListenerPlayOut> getNMS() {
		return null;
	}

	@Override
	public int getPacketID() {
		return 0x0C;
	}

	@Override
	public String getProtocolURLString() {
		return "https://wiki.vg/Protocol#Boss_Bar";
	}
}
