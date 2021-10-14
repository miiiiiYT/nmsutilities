package io.github.riesenpilz.nmsUtilities.packet.playOut;

import org.apache.commons.lang.Validate;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import io.github.riesenpilz.nmsUtilities.block.BlockData;
import io.github.riesenpilz.nmsUtilities.entity.livingEntity.player.DigType;
import io.github.riesenpilz.nmsUtilities.packet.HasBlockPosition;
import io.github.riesenpilz.nmsUtilities.packet.PacketUtils;
import io.github.riesenpilz.nmsUtilities.reflections.Field;
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
	private BlockData blockData;

	/**
	 * Only {@link DigType#START_DESTROY_BLOCK},
	 * {@link DigType#CANCEL_DESTROY_BLOCK} and {@link DigType#FINISH_DESTROY_BLOCK}
	 * are used.
	 */
	private DigType status;

	public PacketPlayOutPlayerDiggingEvent(Player injectedPlayer, PacketPlayOutBlockBreak packet) {
		super(injectedPlayer);

		Validate.notNull(packet);

		status = DigType.getPlayerDigType(Field.get(packet, "a", EnumPlayerDigType.class));
		blockLocation = PacketUtils.toLocation(Field.get(packet, "c", BlockPosition.class), injectedPlayer.getWorld());
		blockData = BlockData.getBlockDataOf(Field.get(packet, "d", IBlockData.class));
	}

	public PacketPlayOutPlayerDiggingEvent(Player injectedPlayer, Location blockLocation, BlockData blockData,
			DigType status) {
		super(injectedPlayer);

		Validate.notNull(blockLocation);
		Validate.notNull(blockData);
		Validate.notNull(status);

		this.blockLocation = blockLocation;
		this.blockData = blockData;
		this.status = status;
	}

	public Location getBlockLocation() {
		return blockLocation;
	}

	public BlockData getBlockData() {
		return blockData;
	}

	public DigType getStatus() {
		return status;
	}

	@Override
	public Packet<PacketListenerPlayOut> getNMS() {
		return new PacketPlayOutBlockBreak(PacketUtils.toBlockPosition(blockLocation),
				blockData.getNMS().getBlockData(), status.getNMS(), true, null/* Unused */);
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
