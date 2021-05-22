package io.github.riesenpilz.nms.packet.playIn;

import org.bukkit.entity.Player;

import io.github.riesenpilz.nms.entity.player.Hand;
import io.github.riesenpilz.nms.inventory.ItemStack;
import io.github.riesenpilz.nms.reflections.Field;
import net.minecraft.server.v1_16_R3.Packet;
import net.minecraft.server.v1_16_R3.PacketListenerPlayIn;
import net.minecraft.server.v1_16_R3.PacketPlayInBEdit;

/**
 * https://wiki.vg/Protocol#Edit_Book<br>
 * <br>
 * When editing a draft, the NBT section of the Slot contains this:<br>
 * <code>TAG_Compound(''): 1 entry<br>
{<br>
  TAG_List('pages'): 2 entries<br>
  {<br>
    TAG_String(0): 'Something on Page 1'<br>
    TAG_String(1): 'Something on Page 2'<br>
  }<br>
}</code> <br>
 * When signing the book, it instead looks like this:<br>
 * <code>TAG_Compound(''): 3 entires<br>
{<br>
  TAG_String('author'): 'Steve'<br>
  TAG_String('title'): 'A Wonderful Book'<br>
  TAG_List('pages'): 2 entries<br>
  {<br>
    TAG_String(0): 'Something on Page 1'<br>
    TAG_String(1): 'Something on Page 2'<br>
  }<br>
}</code><br>
 * <br>
 * Packet ID: 0x0C<br>
 * State: Play<br>
 * Bound To: Server
 * 
 * @author Martin
 *
 */
public class PacketPlayInEditBookEvent extends PacketPlayInEvent {

	private ItemStack book;

	/**
	 * True if the player is signing the book; false if the player is saving a
	 * draft.
	 */
	private boolean signing;
	private Hand hand;

	public PacketPlayInEditBookEvent(Player injectedPlayer, ItemStack book, boolean signing, Hand hand) {
		super(injectedPlayer);
		this.book = book;
		this.signing = signing;
		this.hand = hand;
	}

	public PacketPlayInEditBookEvent(Player injectedPlayer, PacketPlayInBEdit packet) {
		super(injectedPlayer);
		book = new ItemStack(packet.b());
		signing = packet.c();
		hand = Hand.getByIndex(packet.d());
	}

	public ItemStack getItemStack() {
		return book;
	}

	public boolean isSigning() {
		return signing;
	}

	public Hand getHand() {
		return hand;
	}

	@Override
	public Packet<PacketListenerPlayIn> getNMS() {
		final PacketPlayInBEdit packet = new PacketPlayInBEdit();
		new Field(PacketPlayInBEdit.class, "a").set(packet, book.getNMS());
		new Field(PacketPlayInBEdit.class, "b").set(packet, signing);
		new Field(PacketPlayInBEdit.class, "c").set(packet, hand.getIndex());
		return packet;
	}

	@Override
	public int getPacketID() {
		return 0x0C;
	}

	@Override
	public String getProtocolURLString() {
		return "https://wiki.vg/Protocol#Query_Block_NBT";
	}
}
