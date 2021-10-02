package io.github.riesenpilz.nms.packet.playOut;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import io.github.riesenpilz.nms.block.BlockData;
import io.github.riesenpilz.nms.packet.HasBlockPosition;
import io.github.riesenpilz.nms.packet.PacketUtils;
import io.github.riesenpilz.nms.reflections.Field;
import net.minecraft.server.v1_16_R3.BlockPosition;
import net.minecraft.server.v1_16_R3.IBlockData;
import net.minecraft.server.v1_16_R3.Packet;
import net.minecraft.server.v1_16_R3.PacketListenerPlayOut;
import net.minecraft.server.v1_16_R3.PacketPlayOutBlockChange;

/**
 * https://wiki.vg/Protocol#Block_Change<br>
 * <br>
 * Fired whenever a block is changed within the render distance.<br>
 * <br>
 * <i> Changing a block in a chunk that is not loaded is not a stable action.
 * The Notchian client currently uses a shared empty chunk which is modified for
 * all block changes in unloaded chunks; while in 1.9 this chunk never renders
 * in older versions the changed block will appear in all copies of the empty
 * chunk. Servers should avoid sending block changes in unloaded chunks and
 * clients should ignore such packets.</i><br>
 * <br>
 * Packet ID: <br>
 * State: Play<br>
 * Bound To: Client
 *
 * @author Martin
 *
 */
public class PacketPlayOutBlockChangeEvent extends PacketPlayOutEvent implements HasBlockPosition {

	private Location blockLocation;
	private BlockData blockData;

	public PacketPlayOutBlockChangeEvent(Player injectedPlayer, PacketPlayOutBlockChange packet) {
		super(injectedPlayer);
		blockLocation = PacketUtils.toLocation(Field.get(packet, "a", BlockPosition.class), injectedPlayer.getWorld());
		blockData = BlockData.getBlockDataOf(Field.get(packet, "block", IBlockData.class));
	}

	public PacketPlayOutBlockChangeEvent(Player injectedPlayer, Location blockLocation, BlockData blockData) {
		super(injectedPlayer);
		this.blockLocation = blockLocation;
		this.blockData = blockData;
	}

	public Location getBlockLocation() {
		return blockLocation;
	}

	public BlockData getBlockData() {
		return blockData;
	}

	@Override
	public Packet<PacketListenerPlayOut> getNMS() {
		return new PacketPlayOutBlockChange(PacketUtils.toBlockPosition(blockLocation),
				blockData.getNMS().getBlockData());
	}

	@Override
	public int getPacketID() {
		return 0x0B;
	}

	@Override
	public String getProtocolURLString() {
		return "https://wiki.vg/Protocol#Block_Change";
	}
}
