package io.github.riesenpilz.nms.packet.playIn;

import org.bukkit.entity.Player;

import io.github.riesenpilz.nms.block.MovingBlock;
import io.github.riesenpilz.nms.entity.player.Hand;
import io.github.riesenpilz.nms.reflections.Field;
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
		block = MovingBlock.getMovingBlockOf(packet.c());
		hand = Hand.getHand(packet.b());
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
