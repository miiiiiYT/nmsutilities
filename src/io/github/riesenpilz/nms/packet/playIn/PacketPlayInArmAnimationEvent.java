package io.github.riesenpilz.nms.packet.playIn;

import java.net.MalformedURLException;
import java.net.URL;

import org.bukkit.entity.Player;

import io.github.riesenpilz.nms.entity.player.Hand;
import net.minecraft.server.v1_16_R3.Packet;
import net.minecraft.server.v1_16_R3.PacketListenerPlayIn;
import net.minecraft.server.v1_16_R3.PacketPlayInArmAnimation;

/**
 * https://wiki.vg/Protocol#Animation_.28serverbound.29<br>
 * <br>
 * Sent when the player's arm swings.<br>
 * <br>
 * Packet ID: 0x2C<br>
 * State: Play<br>
 * Bound To: Server
 * 
 * @author Martin
 *
 */
public class PacketPlayInArmAnimationEvent extends PacketPlayInEvent {

	public static final URL PROTOCOL_URL = getURL();
	public static final int PACKET_ID = 43;

	private static URL getURL() {
		try {
			return new URL("https://wiki.vg/Protocol#Animation_.28serverbound.29");
		} catch (MalformedURLException ignored) {
		}
		return null;
	}

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
		return 43;
	}

	@Override
	public String getProtocolURLString() {
		return "https://wiki.vg/Protocol#Animation_.28serverbound.29";
	}
}
