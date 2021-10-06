package io.github.riesenpilz.nmsUtilities.packet.playOut;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import io.github.riesenpilz.nmsUtilities.block.BlockData;
import io.github.riesenpilz.nmsUtilities.packet.HasBlockPosition;
import io.github.riesenpilz.nmsUtilities.packet.PacketUtils;
import io.github.riesenpilz.nmsUtilities.reflections.Field;
import net.minecraft.server.v1_16_R3.Block;
import net.minecraft.server.v1_16_R3.BlockPosition;
import net.minecraft.server.v1_16_R3.Packet;
import net.minecraft.server.v1_16_R3.PacketListenerPlayOut;
import net.minecraft.server.v1_16_R3.PacketPlayOutBlockAction;

/**
 * https://wiki.vg/Protocol#Block_Action<br>
 * <br>
 * This packet is used for a number of actions and animations performed by
 * blocks, usually non-persistent.<br>
 * <br>
 * See Block Actions for a list of values.<br>
 * <br>
 * <i>This packet uses a block ID, not a block state.</i><br>
 * <br>
 * Packet ID: 0x0A<br>
 * State: Play<br>
 * Bound To: Client
 * 
 * @author Martin
 *
 */
public class PacketPlayOutBlockActionEvent extends PacketPlayOutEvent implements HasBlockPosition {

	private Location blockLocation;
	private int actionId;
	private int actionParam;
	private BlockData blockData;

	public PacketPlayOutBlockActionEvent(Player injectedPlayer, PacketPlayOutBlockAction packet) {
		super(injectedPlayer);
		blockLocation = PacketUtils.toLocation(Field.get(packet, "a", BlockPosition.class), injectedPlayer.getWorld());
		actionId = Field.get(packet, "b", int.class);
		actionParam = Field.get(packet, "c", int.class);
		blockData = BlockData.getBlockDataOf(Field.get(packet, "d", Block.class));
	}

	public PacketPlayOutBlockActionEvent(Player injectedPlayer, Location blockLocation, int actionId, int actionParam,
			BlockData blockData) {
		super(injectedPlayer);
		this.blockLocation = blockLocation;
		this.actionId = actionId;
		this.actionParam = actionParam;
		this.blockData = blockData;
	}

	public int getActionId() {
		return actionId;
	}

	public int getActionParam() {
		return actionParam;
	}

	public BlockData getBlockData() {
		return blockData;
	}

	@Override
	public Location getBlockLocation() {
		return blockLocation;
	}

	@Override
	public Packet<PacketListenerPlayOut> getNMS() {
		return new PacketPlayOutBlockAction(PacketUtils.toBlockPosition(blockLocation), blockData.getNMS(), actionParam, actionId);
	}

	@Override
	public int getPacketID() {
		return 0x0A;
	}

	@Override
	public String getProtocolURLString() {
		return "https://wiki.vg/Protocol#Block_Action";
	}

}
