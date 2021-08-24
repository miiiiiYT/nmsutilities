package io.github.riesenpilz.nms.packet.playOut;

import org.bukkit.entity.Player;

import net.minecraft.server.v1_16_R3.Packet;
import net.minecraft.server.v1_16_R3.PacketListenerPlayOut;
import net.minecraft.server.v1_16_R3.PacketPlayOutEntityEffect;

/**
 * https://wiki.vg/Protocol#Effect
 * <p>
 * Sent when a client is to play a sound or particle effect.
 * <p>
 * By default, the Minecraft client adjusts the volume of sound effects based on
 * distance. The final boolean field is used to disable this, and instead the
 * effect is played from 2 blocks away in the correct direction. Currently this
 * is only used for effect 1023 (wither spawn), effect 1028 (enderdragon death),
 * and effect 1038 (end portal opening); it is ignored on other effects.
 * <p>
 * Packet ID: 0x23<br>
 * State: Play<br>
 * Bound To: Client
 * 
 * @author Martin
 *
 */
public class PacketPlayOutEffectEvent extends PacketPlayOutEvent {

	public PacketPlayOutEffectEvent(Player injectedPlayer, PacketPlayOutEntityEffect packet) {
		super(injectedPlayer);
	}

	@Override
	public Packet<PacketListenerPlayOut> getNMS() {
		return ;
	}

	@Override
	public int getPacketID() {
		return 0x23;
	}

	@Override
	public String getProtocolURLString() {
		return "https://wiki.vg/Protocol#Effect";
	}
}
