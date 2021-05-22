package io.github.riesenpilz.nms.packet.playIn;

import org.bukkit.entity.Player;

import io.github.riesenpilz.nms.entity.player.Hand;
import io.github.riesenpilz.nms.reflections.Field;
import net.minecraft.server.v1_16_R3.Packet;
import net.minecraft.server.v1_16_R3.PacketListenerPlayIn;
import net.minecraft.server.v1_16_R3.PacketPlayInUseItem;

/**
 * https://wiki.vg/Protocol#Use_Item<br>
 * <br>
 * Sent when pressing the Use Item key (default: right click) with an item in
 * hand.<br>
 * <br>
 * Packet ID: 0x2E<br>
 * State: Play<br>
 * Bound To: Server
 * 
 * @author Martin
 *
 */
public class PacketPlayInUseItemEvent extends PacketPlayInEvent {

	// private MovingObjectPositionBlock a;TODO

	/**
	 * Hand used for the animation.
	 */
	private Hand hand;

	public PacketPlayInUseItemEvent(Player injectedPlayer, PacketPlayInUseItem packet) {
		super(injectedPlayer);
		hand = Hand.getHand(packet.b());
	}

	public PacketPlayInUseItemEvent(Player injectedPlayer, Hand hand) {
		super(injectedPlayer);
	}

	public Hand getHand() {
		return hand;
	}

	@Override
	public Packet<PacketListenerPlayIn> getNMS() {
		final PacketPlayInUseItem packet = new PacketPlayInUseItem();
		new Field(PacketPlayInUseItem.class, "a").set(packet, hand.getNMS());
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
