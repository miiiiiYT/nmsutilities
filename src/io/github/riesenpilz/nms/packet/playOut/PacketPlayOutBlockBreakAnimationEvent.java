package io.github.riesenpilz.nms.packet.playOut;

import org.bukkit.entity.Player;

import net.minecraft.server.v1_16_R3.Packet;
import net.minecraft.server.v1_16_R3.PacketListenerPlayOut;

/**
 * https://wiki.vg/Protocol#Block_Break_Animation<br>
 * <br>
 * 0–9 are the displayable destroy stages and each other number means that there
 * is no animation on this coordinate.<br>
 * <br>
 * Block break animations can still be applied on air; the animation will remain
 * visible although there is no block being broken. However, if this is applied
 * to a transparent block, odd graphical effects may happen, including water
 * losing its transparency. (An effect similar to this can be seen in normal
 * gameplay when breaking ice blocks)<br>
 * <br>
 * If you need to display several break animations at the same time you have to
 * give each of them a unique Entity ID. The entity ID does not need to
 * correspond to an actual entity on the client. It is valid to use a randomly
 * generated number.<br>
 * <br>
 * Packet ID: 0x08<br>
 * State: Play<br>
 * Bound To: Client
 *
 * @author Martin
 *
 */
public class PacketPlayOutBlockBreakAnimationEvent extends PacketPlayOutEvent {

	public PacketPlayOutBlockBreakAnimationEvent(Player injectedPlayer) {
		super(injectedPlayer);
	}

	@Override
	public Packet<PacketListenerPlayOut> getNMS() {
		return null;
	}

	@Override
	public int getPacketID() {
		return 0x08;
	}

	@Override
	public String getProtocolURLString() {
		return "https://wiki.vg/Protocol#Block_Break_Animation";
	}
}
