package io.github.riesenpilz.nmsUtilities.packet.playIn;

import org.apache.commons.lang.Validate;
import org.bukkit.entity.Player;

import io.github.riesenpilz.nmsUtilities.block.MovingBlock;
import io.github.riesenpilz.nmsUtilities.entity.player.Hand;
import io.github.riesenpilz.nmsUtilities.reflections.Field;
import net.minecraft.server.v1_16_R3.Packet;
import net.minecraft.server.v1_16_R3.PacketListenerPlayIn;
import net.minecraft.server.v1_16_R3.PacketPlayInUseItem;

/**
 * https://wiki.vg/Protocol#Use_Item
 * <p>
 * Sent when pressing the Use Item key (default: right click) with an item in
 * hand.
 * <p>
 * Packet ID: 0x2E<br>
 * State: Play<br>
 * Bound To: Server
 * 
 * @author Martin
 *
 */
public class PacketPlayInUseItemEvent extends PacketPlayInEvent {

	private MovingBlock block;

	/**
	 * Hand used for the animation.
	 */
	private Hand hand;

	public PacketPlayInUseItemEvent(Player injectedPlayer, PacketPlayInUseItem packet) {
		super(injectedPlayer);

		Validate.notNull(packet);

		block = MovingBlock.getMovingBlockOf(packet.c());
		hand = Hand.getHand(packet.b());
	}

	public PacketPlayInUseItemEvent(Player injectedPlayer, MovingBlock block, Hand hand) {
		super(injectedPlayer);

		Validate.notNull(block);
		Validate.notNull(hand);

		this.block = block;
		this.hand = hand;
	}

	@Override
	public Packet<PacketListenerPlayIn> getNMS() {
		final PacketPlayInUseItem packet = new PacketPlayInUseItem();
		Field.set(packet, "a", block.getNMS());
		Field.set(packet, "b", hand.getNMS());
		return packet;
	}

	@Override
	public int getPacketID() {
		return 0x2E;
	}

	@Override
	public String getProtocolURLString() {
		return "https://wiki.vg/Protocol#Use_Item";
	}

}
