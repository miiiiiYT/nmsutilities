package io.github.riesenpilz.nms.packet.playIn;

import org.bukkit.Location;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;

import io.github.riesenpilz.nms.reflections.Field;
import net.minecraft.server.v1_16_R3.BlockPosition;
import net.minecraft.server.v1_16_R3.EnumDirection;
import net.minecraft.server.v1_16_R3.Packet;
import net.minecraft.server.v1_16_R3.PacketListenerPlayIn;
import net.minecraft.server.v1_16_R3.PacketPlayInBlockDig;
import net.minecraft.server.v1_16_R3.PacketPlayInBlockDig.EnumPlayerDigType;

/**
 * https://wiki.vg/Protocol#Player_Digging<br>
 * <br>
 * Sent when the player mines a block. A Notchian server only accepts digging
 * packets with coordinates within a 6-unit radius between the center of the
 * block and 1.5 units from the player's feet (not their eyes).<br>
 * <br>
 * Packet ID: 0x1B<br>
 * State: Play<br>
 * Bound To: Server
 * 
 * @author Martin
 *
 */
public class PacketPlayInBlockDigEvent extends PacketPlayInEvent {

	private Location blockLocation;

	/**
	 * The face being hit
	 */
	private BlockFace blockFace;

	private DigType digType;

	public PacketPlayInBlockDigEvent(Player injectedPlayer, Location blockLocation, BlockFace blockFace,
			DigType digType) {
		super(injectedPlayer);
		this.blockLocation = blockLocation;
		this.blockFace = blockFace;
		this.digType = digType;
	}

	public PacketPlayInBlockDigEvent(Player injectedPlayer, PacketPlayInBlockDig packet) {
		super(injectedPlayer);
		blockLocation = new Location(injectedPlayer.getWorld(), packet.b().getX(), packet.b().getY(),
				packet.b().getZ());
		switch (packet.c()) {
		case DOWN:
			blockFace = BlockFace.DOWN;
			break;
		case EAST:
			blockFace = BlockFace.EAST;
			break;
		case NORTH:
			blockFace = BlockFace.NORTH;
			break;
		case SOUTH:
			blockFace = BlockFace.SOUTH;
			break;
		case UP:
			blockFace = BlockFace.UP;
			break;
		case WEST:
			blockFace = BlockFace.WEST;
			break;
		}
		digType = DigType.getPlayerDigType(packet.d());
	}

	public BlockFace getBlockFace() {
		return blockFace;
	}

	public Location getBlockLocation() {
		return blockLocation;
	}

	public DigType getDigType() {
		return digType;
	}

	@Override
	public Packet<PacketListenerPlayIn> getNMS() {
		final PacketPlayInBlockDig packet = new PacketPlayInBlockDig();
		new Field(PacketPlayInBlockDig.class, "a").set(packet,
				new BlockPosition(blockLocation.getX(), blockLocation.getY(), blockLocation.getZ()));
		new Field(PacketPlayInBlockDig.class, "b").set(packet, getDirection());
		new Field(PacketPlayInBlockDig.class, "c").set(packet, digType.getNMS());
		return packet;
	}

	private EnumDirection getDirection() {
		switch (blockFace) {
		case DOWN:
			return EnumDirection.DOWN;
		case EAST:
			return EnumDirection.EAST;
		case NORTH:
			return EnumDirection.NORTH;
		case SOUTH:
			return EnumDirection.SOUTH;
		case UP:
			return EnumDirection.UP;
		case WEST:
			return EnumDirection.WEST;
		default:
			return EnumDirection.NORTH;
		}
	}

	public static enum DigType {

		START_DESTROY_BLOCK(EnumPlayerDigType.START_DESTROY_BLOCK),
		/**
		 * Sent when the player lets go of the Mine Block key (default: left click).
		 */
		CANCEL_DESTROY_BLOCK(EnumPlayerDigType.ABORT_DESTROY_BLOCK),
		/**
		 * Sent when the client thinks it is finished.
		 */
		FINISH_DESTROY_BLOCK(EnumPlayerDigType.STOP_DESTROY_BLOCK),
		/**
		 * Triggered by using the Drop Item key (default: Q) with the modifier to drop
		 * the entire selected stack (default: depends on OS). Location is always set to
		 * 0/0/0, Face is always set to {@link BlockFace#DOWN}.
		 */
		DROP_ALL_ITEMS(EnumPlayerDigType.DROP_ALL_ITEMS),
		/**
		 * Triggered by using the Drop Item key (default: Q). Location is always set to
		 * 0/0/0, Face is always set to {@link BlockFace#DOWN}.
		 */
		DROP_ITEM(EnumPlayerDigType.DROP_ITEM),
		/**
		 * Indicates that the currently held item should have its state updated such as
		 * eating food, pulling back bows, using buckets, etc. Location is always set to
		 * 0/0/0, Face is always set to {@link BlockFace#DOWN}.
		 */
		RELEASE_USE_ITEM(EnumPlayerDigType.RELEASE_USE_ITEM),
		/**
		 * Used to swap or assign an item to the second hand. Location is always set to
		 * 0/0/0, Face is always set to {@link BlockFace#DOWN}.
		 * 
		 */
		SWAP_ITEM_WITH_OFFHAND(EnumPlayerDigType.SWAP_ITEM_WITH_OFFHAND);

		private EnumPlayerDigType nms;

		private DigType(EnumPlayerDigType nms) {
			this.nms = nms;
		}

		public EnumPlayerDigType getNMS() {
			return nms;
		}

		public static DigType getPlayerDigType(EnumPlayerDigType nms) {
			for (DigType clickType : DigType.values())
				if (clickType.getNMS().equals(nms))
					return clickType;
			return null;
		}
	}

	@Override
	public int getPacketID() {
		return 0x1B;
	}

	@Override
	public String getProtocolURLString() {
		return "https://wiki.vg/Protocol#Player_Digging";
	}
}
