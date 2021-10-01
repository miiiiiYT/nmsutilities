package io.github.riesenpilz.nms.packet.playOut;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import io.github.riesenpilz.nms.nbt.NBTTag;
import io.github.riesenpilz.nms.packet.HasBlockPosition;
import io.github.riesenpilz.nms.packet.PacketUtils;
import io.github.riesenpilz.nms.reflections.Field;
import net.minecraft.server.v1_16_R3.BlockPosition;
import net.minecraft.server.v1_16_R3.NBTTagCompound;
import net.minecraft.server.v1_16_R3.Packet;
import net.minecraft.server.v1_16_R3.PacketListenerPlayOut;
import net.minecraft.server.v1_16_R3.PacketPlayOutTileEntityData;

/**
 * https://wiki.vg/Protocol#Block_Entity_Data
 * <p>
 * Sets the block entity associated with the block at the given location.
 * <p>
 * Packet ID: 0x09<br>
 * State: Play<br>
 * Bound To: Client
 * 
 * @author Martin
 *
 */
public class PacketPlayOutBlockEntityDataEvent extends PacketPlayOutEvent implements HasBlockPosition {

	private Location blockLocation;
	private BlockAction action;
	private NBTTag nbtTag;

	public PacketPlayOutBlockEntityDataEvent(Player injectedPlayer, PacketPlayOutTileEntityData packet) {
		super(injectedPlayer);
		blockLocation = PacketUtils.toLocation(Field.get(packet, "a", BlockPosition.class), injectedPlayer.getWorld());
		action = BlockAction.getByID(Field.get(packet.getClass(), "b", int.class));
		nbtTag = NBTTag.getNBTTagOf(Field.get(packet.getClass(), "c", NBTTagCompound.class));
	}

	public PacketPlayOutBlockEntityDataEvent(Player injectedPlayer, Location blockLocation, BlockAction action,
			NBTTag nbtTag) {
		super(injectedPlayer);
		this.blockLocation = blockLocation;
		this.action = action;
		this.nbtTag = nbtTag;
	}

	@Override
	public Location getBlockLocation() {
		return blockLocation;
	}

	public BlockAction getAction() {
		return action;
	}

	public NBTTag getNbtTag() {
		return nbtTag;
	}

	@Override
	public Packet<PacketListenerPlayOut> getNMS() {
		return new PacketPlayOutTileEntityData(PacketUtils.toBlockPosition(blockLocation), getPacketID(),
				nbtTag.getNMS());
	}

	@Override
	public int getPacketID() {
		return 0x09;
	}

	@Override
	public String getProtocolURLString() {
		return "https://wiki.vg/Protocol#Block_Entity_Data";
	}

	public static enum BlockAction {
		/**
		 * Set data of a mob spawner (everything except for SpawnPotentials: current
		 * delay, min/max delay, mob to be spawned, spawn count, spawn range, etc.)
		 */
		MOB_SPAWNER(1),

		/**
		 * Set command block text (command and last execution status)
		 */
		COMMAND_BLOCK(2),

		/**
		 * Set the level, primary, and secondary powers of a beacon
		 */
		BEACON(3),

		/**
		 * Set rotation and skin of mob head
		 */
		MOB_HEAD(4),

		/**
		 * Declare a conduit
		 */
		COUNDIT(5),

		/**
		 * Set base color and patterns on a banner
		 */
		BANNER(6),

		/**
		 * Set the data for a Structure tile entity
		 */
		STRUCTURE_BLOCK(7),

		/**
		 * Set the destination for a end gateway
		 */
		END_GATEWAY(8),

		/**
		 * Set the text on a sign
		 */
		SIGN(9),

		/**
		 * Unused
		 */
		UNUSED(10),

		/**
		 * Declare a bed
		 */
		BED(11),

		/**
		 * Set data of a jigsaw block
		 */
		JIGSAW_BLOCK(12),

		/**
		 * Set items in a campfire
		 */
		CAMPFIRE(13),

		/**
		 * Beehive information
		 */
		BEEHIVE(14);

		private int id;

		private BlockAction(int id) {
			this.id = id;
		}

		public int getId() {
			return id;
		}

		public static BlockAction getByID(int id) {
			for (BlockAction action : values())
				if (action.getId() == id)
					return action;
			return null;
		}
	}
}
