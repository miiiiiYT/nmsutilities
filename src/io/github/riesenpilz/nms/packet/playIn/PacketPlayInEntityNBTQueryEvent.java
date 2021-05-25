package io.github.riesenpilz.nms.packet.playIn;

import org.bukkit.entity.Player;

import io.github.riesenpilz.nms.reflections.Field;
import net.minecraft.server.v1_16_R3.Packet;
import net.minecraft.server.v1_16_R3.PacketListenerPlayIn;
import net.minecraft.server.v1_16_R3.PacketPlayInEntityNBTQuery;

/**
 * https://wiki.vg/Protocol#Query_Entity_NBT
 * <p>
 * Used when Shift+F3+I is pressed while looking at an entity.
 * <p>
 * Packet ID: 0x0D<br>
 * State: Play<br>
 * Bound To: Server
 *
 * @author Martin
 *
 */
public class PacketPlayInEntityNBTQueryEvent extends PacketPlayInEvent {

	/**
	 * An incremental ID so that the client can verify that the response matches.
	 */
	private int transactionID;

	/**
	 * The ID of the entity to query.
	 */
	private int entityID;

	public PacketPlayInEntityNBTQueryEvent(Player injectedPlayer, PacketPlayInEntityNBTQuery packet) {
		super(injectedPlayer);
		transactionID = packet.b();
		entityID = packet.c();
	}

	public PacketPlayInEntityNBTQueryEvent(Player injectedPlayer, int transactionID, int entityID) {
		super(injectedPlayer);
		this.transactionID = transactionID;
		this.entityID = entityID;
	}

	public int getTransactionID() {
		return transactionID;
	}

	public int getEntityID() {
		return entityID;
	}

	@Override
	public Packet<PacketListenerPlayIn> getNMS() {
		final PacketPlayInEntityNBTQuery packet = new PacketPlayInEntityNBTQuery();
		Field.set(packet, "a", transactionID);
		Field.set(packet, "b", entityID);
		return packet;
	}

	@Override
	public int getPacketID() {
		return 0x0D;
	}

	@Override
	public String getProtocolURLString() {
		return "https://wiki.vg/Protocol#Query_Entity_NBT";
	}
}
