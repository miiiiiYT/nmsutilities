package io.github.riesenpilz.nms.packet.playIn;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import io.github.riesenpilz.nms.packet.PacketUtils;
import io.github.riesenpilz.nms.reflections.Field;
import net.minecraft.server.v1_16_R3.Packet;
import net.minecraft.server.v1_16_R3.PacketListenerPlayIn;
import net.minecraft.server.v1_16_R3.PacketPlayInTileNBTQuery;

/**
 * https://wiki.vg/Protocol#Query_Block_NBT
 * <p>
 * Used when Shift+F3+I is pressed while looking at a block.
 * <p>
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
		transactionID = packet.b();
		blockLocation = PacketUtils.toLocation(packet.c(), injectedPlayer.getWorld());
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
		Field.set(packet, "a", transactionID);
		Field.set(packet, "b", PacketUtils.toBlockPosition(blockLocation));
		return packet;
	}

	@Override
	public int getPacketID() {
		return 0x01;
	}

	@Override
	public String getProtocolURLString() {
		return "https://wiki.vg/Protocol#Query_Block_NBT";
	}
}
