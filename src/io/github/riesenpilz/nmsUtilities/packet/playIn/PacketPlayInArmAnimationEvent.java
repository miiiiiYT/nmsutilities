package io.github.riesenpilz.nmsUtilities.packet.playIn;

import org.bukkit.entity.Player;

import io.github.riesenpilz.nmsUtilities.entity.player.Hand;
import net.minecraft.server.v1_16_R3.Packet;
import net.minecraft.server.v1_16_R3.PacketListenerPlayIn;
import net.minecraft.server.v1_16_R3.PacketPlayInArmAnimation;

/**
 * https://wiki.vg/Protocol#Animation_.28serverbound.29<p>
 * Sent when the player's arm swings.<p>
 * Packet ID: 0x2C<br>
 * State: Play<br>
 * Bound To: Server
 * 
 * @author Martin
 *
 */
public class PacketPlayInArmAnimationEvent extends PacketPlayInEvent {

	/**
	 * Hand used for the animation.
	 */
	private Hand hand;

	public PacketPlayInArmAnimationEvent(Player injectedPlayer, PacketPlayInArmAnimation packet) {
		super(injectedPlayer);
		hand = Hand.getHand(packet.b());
	}

	public PacketPlayInArmAnimationEvent(Player injectedPlayer, Hand hand) {
		super(injectedPlayer);
		this.hand = hand;

	}

	public Hand getHand() {
		return hand;
	}

	@Override
	public Packet<PacketListenerPlayIn> getNMS() {
		return new PacketPlayInArmAnimation(hand.getNMS());
	}

	@Override
	public int getPacketID() {
		return 0x2C;
	}

	@Override
	public String getProtocolURLString() {
		return "https://wiki.vg/Protocol#Animation_.28serverbound.29";
	}
}
