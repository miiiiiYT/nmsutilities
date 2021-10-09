package io.github.riesenpilz.nmsUtilities.packet.playIn;

import org.apache.commons.lang.Validate;
import org.bukkit.entity.Player;

import io.github.riesenpilz.nmsUtilities.entity.player.Hand;
import net.minecraft.server.v1_16_R3.Packet;
import net.minecraft.server.v1_16_R3.PacketListenerPlayIn;
import net.minecraft.server.v1_16_R3.PacketPlayInBlockPlace;

/**
 * https://wiki.vg/Protocol#Player_Block_Placement
 * <p>
 * Upon placing a block, this packet is sent once.
 * <p>
 * The Cursor Position X/Y/Z fields (also known as in-block coordinates) are
 * calculated using raytracing. The unit corresponds to sixteen pixels in the
 * default resource pack. For example, let's say a slab is being placed against
 * the south face of a full block. The Cursor Position X will be higher if the
 * player was pointing near the right (east) edge of the face, lower if pointing
 * near the left. The Cursor Position Y will be used to determine whether it
 * will appear as a bottom slab (values 0.0–0.5) or as a top slab (values
 * 0.5-1.0). The Cursor Position Z should be 1.0 since the player was looking at
 * the southernmost part of the block.
 * <p>
 * Inside block is true when a player's head (specifically eyes) are inside of a
 * block's collision. In 1.13 and later versions, collision is rather
 * complicated and individual blocks can have multiple collision boxes. For
 * instance, a ring of vines has a non-colliding hole in the middle. This value
 * is only true when the player is directly in the box. In practice, though,
 * this value is only used by scaffolding to place in front of the player when
 * sneaking inside of it (other blocks will place behind when you intersect with
 * them -- try with glass for instance).
 * <p>
 * Packet ID: 0x2E<br>
 * State: Play<br>
 * Bound To: Server
 * 
 * @author Martin
 *
 */
public class PacketPlayInBlockPlaceEvent extends PacketPlayInEvent {

	/**
	 * The hand from which the block is placed.
	 */
	private Hand hand;

	public PacketPlayInBlockPlaceEvent(Player injectedPlayer, PacketPlayInBlockPlace packet) {
		super(injectedPlayer);
		Validate.notNull(packet);
		hand = Hand.getHand(packet.b());
	}

	public PacketPlayInBlockPlaceEvent(Player injectedPlayer, Hand hand) {
		super(injectedPlayer);
		Validate.notNull(hand);
		this.hand = hand;

	}

	public Hand getHand() {
		return hand;
	}

	@Override
	public Packet<PacketListenerPlayIn> getNMS() {
		return new PacketPlayInBlockPlace(hand.getNMS());
	}

	@Override
	public int getPacketID() {
		return 0x2E;
	}

	@Override
	public String getProtocolURLString() {
		return "https://wiki.vg/Protocol#Player_Block_Placement";
	}
}
