package io.github.riesenpilz.nms.packet.playOut;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import io.github.riesenpilz.nms.block.BlockData;
import io.github.riesenpilz.nms.packet.PacketUtils;
import io.github.riesenpilz.nms.reflections.Field;
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
public class PacketPlayOutBlockActionEvent extends PacketPlayOutEvent {

	private Location blockLocation;
	private int actionID;
	private int actionParam;
	private BlockData blockData;

	public PacketPlayOutBlockActionEvent(Player injectedPlayer, PacketPlayOutBlockAction packet) {
		super(injectedPlayer);
		blockLocation = PacketUtils.toLocation((BlockPosition) new Field(packet.getClass(), "a").get(packet),
				injectedPlayer.getWorld());
		actionID = (int) new Field(packet.getClass(), "b").get(packet);
		actionParam = (int) new Field(packet.getClass(), "c").get(packet);
		blockData = new BlockData((Block) new Field(packet.getClass(), "d").get(packet));
	}

	public Location getBlockLocation() {
		return blockLocation;
	}

	public int getActionID() {
		return actionID;
	}

	public int getActionParam() {
		return actionParam;
	}

	public BlockData getBlockData() {
		return blockData;
	}

	@Override
	public Packet<PacketListenerPlayOut> getNMS() {
		return new PacketPlayOutBlockAction(PacketUtils.toBlockPosition(blockLocation), blockData.getNMS(), actionParam,
				actionID);
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
