package io.github.riesenpilz.nms.packet.playOut;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import io.github.riesenpilz.nms.entity.player.DigType;
import io.github.riesenpilz.nms.packet.HasBlockPosition;
import io.github.riesenpilz.nms.reflections.Field;
import net.minecraft.server.v1_16_R3.BlockPosition;
import net.minecraft.server.v1_16_R3.IBlockData;
import net.minecraft.server.v1_16_R3.Packet;
import net.minecraft.server.v1_16_R3.PacketListenerPlayOut;
import net.minecraft.server.v1_16_R3.PacketPlayInBlockDig.EnumPlayerDigType;
import net.minecraft.server.v1_16_R3.PacketPlayOutBlockBreak;

/**
 * https://wiki.vg/Protocol#Acknowledge_Player_Digging<br>
 * <br>
 * Packet ID: 0x07<br>
 * State: Play<br>
 * Bound To: Client
 *
 * @author Martin
 *
 */
public class PacketPlayOutPlayerDiggingEvent extends PacketPlayOutEvent implements HasBlockPosition {

	private Location blockLocation;
	private IBlockData blockData;

	/**
	 * Only {@link DigType#START_DESTROY_BLOCK},
	 * {@link DigType#CANCEL_DESTROY_BLOCK} and {@link DigType#FINISH_DESTROY_BLOCK}
	 * are used.
	 */
	private DigType status;

	/**
	 * True if the digging succeeded; false if the client should undo any changes it
	 * made locally.
	 */
	private boolean successful;

	public PacketPlayOutPlayerDiggingEvent(Player injectedPlayer, PacketPlayOutBlockBreak packet) {
		super(injectedPlayer);
		status = DigType.getPlayerDigType((EnumPlayerDigType) new Field(packet.getClass(), "a").get(packet));
		BlockPosition pos = (BlockPosition) new Field(packet.getClass(), "a").get(packet);
		blockLocation = new Location(injectedPlayer.getWorld(), pos.getX(), pos.getY(), pos.getZ());
		blockData = (IBlockData) new Field(packet.getClass(), "d").get(packet);
		successful = (boolean) new Field(packet.getClass(), "e").get(packet);
	}

	public Location getBlockLocation() {
		return blockLocation;
	}

	public IBlockData getBlockData() {
		return blockData;
	}

	public DigType getStatus() {
		return status;
	}

	public boolean isSuccessful() {
		return successful;
	}

	@Override
	public Packet<PacketListenerPlayOut> getNMS() {
		return new PacketPlayOutBlockBreak(
				new BlockPosition(blockLocation.getX(), blockLocation.getY(), blockLocation.getZ()), blockData,
				status.getNMS(), successful, null/* Unused */);
	}

	@Override
	public int getPacketID() {
		return 0x07;
	}

	@Override
	public String getProtocolURLString() {
		return "https://wiki.vg/Protocol#Acknowledge_Player_Digging";
	}
}
