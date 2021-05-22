package io.github.riesenpilz.nms.packet.playIn;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import io.github.riesenpilz.nms.reflections.Field;
import net.minecraft.server.v1_16_R3.BlockPosition;
import net.minecraft.server.v1_16_R3.Packet;
import net.minecraft.server.v1_16_R3.PacketListenerPlayIn;
import net.minecraft.server.v1_16_R3.PacketPlayInTileNBTQuery;

/**
 * https://wiki.vg/Protocol#Query_Block_NBT<br>
 * <br>
 * Used when Shift+F3+I is pressed while looking at a block.<br>
 * <br>
 * Packet ID: 0x01<br>
 * State: Play<br>
 * Bound To: Server
 * 
 * @author Martin
 *
 */
public class PacketPlayInBlockNBTQueryEvent extends PacketPlayInEvent {

	/**
	 * An incremental ID so that the client can verify that the response matches.
	 */
	private int transactionID;

	/**
	 * The location of the block to check.
	 */
	private Location blockLocation;

	public PacketPlayInBlockNBTQueryEvent(Player injectedPlayer, PacketPlayInTileNBTQuery packet) {
		super(injectedPlayer);
	}

	public PacketPlayInBlockNBTQueryEvent(Player injectedPlayer, Location blockLocation, int transactionID) {
		super(injectedPlayer);
		this.transactionID = transactionID;
		this.blockLocation = blockLocation;
	}

	public int getTransactionID() {
		return transactionID;
	}

	public Location getBlockLocation() {
		return blockLocation;
	}

	@Override
	public Packet<PacketListenerPlayIn> getNMS() {
		final PacketPlayInTileNBTQuery packet = new PacketPlayInTileNBTQuery();
		new Field(PacketPlayInTileNBTQuery.class, "a").set(packet, transactionID);
		new Field(PacketPlayInTileNBTQuery.class, "b").set(packet,
				new BlockPosition(blockLocation.getX(), blockLocation.getY(), blockLocation.getZ()));
		return packet;
	}

	@Override
	public int getPacketID() {
		return 1;
	}

	@Override
	public String getProtocolURLString() {
		return "https://wiki.vg/Protocol#Query_Block_NBT";
	}
}
