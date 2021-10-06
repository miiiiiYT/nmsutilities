package io.github.riesenpilz.nmsUtilities.packet.playOut;

import org.bukkit.entity.Player;

import io.github.riesenpilz.nmsUtilities.nbt.NBTTag;
import io.github.riesenpilz.nmsUtilities.packet.playIn.PacketPlayInBlockNBTQueryEvent;
import io.github.riesenpilz.nmsUtilities.packet.playIn.PacketPlayInEntityNBTQueryEvent;
import io.github.riesenpilz.nmsUtilities.reflections.Field;
import net.minecraft.server.v1_16_R3.NBTTagCompound;
import net.minecraft.server.v1_16_R3.Packet;
import net.minecraft.server.v1_16_R3.PacketListenerPlayOut;
import net.minecraft.server.v1_16_R3.PacketPlayOutNBTQuery;

/**
 * https://wiki.vg/Protocol#NBT_Query_Response
 * <p>
 * Sent in response to {@link PacketPlayInBlockNBTQueryEvent} or
 * {@link PacketPlayInEntityNBTQueryEvent}.
 * <p>
 * Packet ID: 0x5F<br>
 * State: Play<br>
 * Bound To: Client
 * 
 * @author Martin
 *
 */
public class PacketPlayOutNBTQueryResponseEvent extends PacketPlayOutEvent {

	/**
	 * Can be compared to the one sent in the original query packet.
	 */
	private int transactionId;

	/**
	 * The NBT of the block or entity. May be a TAG_END (0) in which case no NBT is
	 * present.
	 */
	private NBTTag tag;

	public PacketPlayOutNBTQueryResponseEvent(Player injectedPlayer, PacketPlayOutNBTQuery packet) {
		super(injectedPlayer);
		
		transactionId = Field.get(packet, "a", int.class);
		tag = NBTTag.getNBTTagOf(Field.get(packet, "b", NBTTagCompound.class));
	}

	public PacketPlayOutNBTQueryResponseEvent(Player injectedPlayer, int transactionId, NBTTag tag) {
		super(injectedPlayer);
		this.transactionId = transactionId;
		this.tag = tag;
	}

	public int getTransactionId() {
		return transactionId;
	}

	public NBTTag getTag() {
		return tag;
	}

	@Override
	public Packet<PacketListenerPlayOut> getNMS() {
		return new PacketPlayOutNBTQuery(transactionId, tag.getNMS());
	}

	@Override
	public int getPacketID() {
		return 0x5F;
	}

	@Override
	public String getProtocolURLString() {
		return "https://wiki.vg/Protocol#NBT_Query_Response";
	}
}
