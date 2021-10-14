package io.github.riesenpilz.nmsUtilities.packet.playOut;

import org.apache.commons.lang.Validate;
import org.bukkit.entity.Player;

import io.github.riesenpilz.nmsUtilities.entity.livingEntity.player.Hand;
import io.github.riesenpilz.nmsUtilities.reflections.Field;
import net.minecraft.server.v1_16_R3.EnumHand;
import net.minecraft.server.v1_16_R3.Packet;
import net.minecraft.server.v1_16_R3.PacketListenerPlayOut;
import net.minecraft.server.v1_16_R3.PacketPlayOutOpenBook;

/**
 * https://wiki.vg/Protocol#Open_Book
 * <p>
 * Sent when a player right clicks with a signed book. This tells the client to
 * open the book GUI.
 * <p>
 * Packet ID: 0x2E<br>
 * State: Play<br>
 * Bound To: Client
 * 
 * @author Martin
 *
 */
public class PacketPlayOutOpenBookEvent extends PacketPlayOutEvent {

	private Hand hand;

	public PacketPlayOutOpenBookEvent(Player injectedPlayer, PacketPlayOutOpenBook packet) {
		super(injectedPlayer);

		Validate.notNull(packet);

		hand = Hand.getHand(Field.get(packet, "a", EnumHand.class));
	}

	public PacketPlayOutOpenBookEvent(Player injectedPlayer, Hand hand) {
		super(injectedPlayer);

		Validate.notNull(hand);

		this.hand = hand;
	}

	public Hand getHand() {
		return hand;
	}

	@Override
	public Packet<PacketListenerPlayOut> getNMS() {
		return new PacketPlayOutOpenBook(hand.getNMS());
	}

	@Override
	public int getPacketID() {
		return 0x2E;
	}

	@Override
	public String getProtocolURLString() {
		return "https://wiki.vg/Protocol#Open_Book";
	}
}
