package io.github.riesenpilz.nms.packet.playIn;

import org.bukkit.entity.Player;

import io.github.riesenpilz.nms.entity.Entity;
import io.github.riesenpilz.nms.reflections.Field;
import net.minecraft.server.v1_16_R3.Packet;
import net.minecraft.server.v1_16_R3.PacketListenerPlayIn;
import net.minecraft.server.v1_16_R3.PacketPlayInEntityNBTQuery;

/**
 * https://wiki.vg/Protocol#Query_Entity_NBT<br>
 * <br>
 * Used when Shift+F3+I is pressed while looking at an entity.<br>
 * <br>
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
	 * The Entity to query.
	 */
	private Entity entity;

	public PacketPlayInEntityNBTQueryEvent(Player injectedPlayer, PacketPlayInEntityNBTQuery packet) {
		super(injectedPlayer);
		transactionID = packet.b();
		entity = new Entity(packet.c(), injectedPlayer.getWorld());
	}

	public PacketPlayInEntityNBTQueryEvent(Player injectedPlayer, int transactionID, org.bukkit.entity.Entity entity) {
		super(injectedPlayer);
		this.transactionID = transactionID;
		this.entity = new Entity(entity);
	}

	public int getTransactionID() {
		return transactionID;
	}

	public Entity getEntity() {
		return entity;
	}

	@Override
	public Packet<PacketListenerPlayIn> getNMS() {
		final PacketPlayInEntityNBTQuery packet = new PacketPlayInEntityNBTQuery();
		new Field(PacketPlayInEntityNBTQuery.class, "a").set(packet, transactionID);
		new Field(PacketPlayInEntityNBTQuery.class, "b").set(packet, entity.getID());
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
