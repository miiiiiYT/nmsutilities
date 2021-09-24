package io.github.riesenpilz.nms.packet.playOut;

import org.bukkit.entity.Player;

import io.github.riesenpilz.nms.block.BlockData;
import io.github.riesenpilz.nms.reflections.Field;
import net.minecraft.server.v1_16_R3.IBlockData;
import net.minecraft.server.v1_16_R3.Packet;
import net.minecraft.server.v1_16_R3.PacketListenerPlayOut;
import net.minecraft.server.v1_16_R3.PacketPlayOutMultiBlockChange;
import net.minecraft.server.v1_16_R3.SectionPosition;

/**
 * https://wiki.vg/Protocol#Multi_Block_Change
 * <p>
 * Fired whenever 2 or more blocks are changed within the same chunk on the same
 * tick.
 * <p>
 * Packet ID: 0x3F<br>
 * State: Play<br>
 * Bound To: Client
 * 
 * @author Martin
 *
 */
public class PacketPlayOutMultiBlockChangeEvent extends PacketPlayOutEvent {

	private SectionPosition position;

	/**
	 * The relative block position in the chunk section (4 bits for x, z, and y,
	 * from left to right).
	 */
	private short[] relativePositions;
	private BlockData[] blockDatas;

	/**
	 * Always inverse the preceding Update Light packet's "Trust Edges" bool
	 */
	private boolean updateLight;

	public PacketPlayOutMultiBlockChangeEvent(Player injectedPlayer, PacketPlayOutMultiBlockChange packet) {
		super(injectedPlayer);
		
		position = Field.get(packet, "a", SectionPosition.class);
		relativePositions = Field.get(packet, "b", short[].class);
		IBlockData[] iBlockDatas = Field.get(packet, "c", IBlockData[].class);
		blockDatas = new BlockData[iBlockDatas.length];
		for (int i = 0; i < blockDatas.length; i++)
			blockDatas[i] = new BlockData(iBlockDatas[i]);
		updateLight = Field.get(packet, "d", boolean.class);
	}

	@Override
	public Packet<PacketListenerPlayOut> getNMS() {
		final PacketPlayOutMultiBlockChange packet = new PacketPlayOutMultiBlockChange();
		Field.set(packet, "a", position);
		Field.set(packet, "b", relativePositions);
		
		IBlockData[] iBlockDatas = new IBlockData[blockDatas.length];
		for (int i = 0; i < blockDatas.length; i++)
			iBlockDatas[i] = blockDatas[i].getNMS().getBlockData();
		
		Field.set(packet, "c", iBlockDatas);
		Field.set(packet, "d", updateLight);
		return packet;
	}

	@Override
	public int getPacketID() {
		return 0x3F;
	}

	@Override
	public String getProtocolURLString() {
		return "https://wiki.vg/Protocol#Multi_Block_Change";
	}
}
