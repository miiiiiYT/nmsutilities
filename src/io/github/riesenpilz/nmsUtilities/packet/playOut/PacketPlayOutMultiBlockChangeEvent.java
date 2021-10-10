package io.github.riesenpilz.nmsUtilities.packet.playOut;

import org.apache.commons.lang.Validate;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import io.github.riesenpilz.nmsUtilities.block.BlockData;
import io.github.riesenpilz.nmsUtilities.packet.PacketUtils;
import io.github.riesenpilz.nmsUtilities.reflections.Field;
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

	private Location position;

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

		Validate.notNull(packet);

		position = PacketUtils.toLocation(Field.get(packet, "a", SectionPosition.class), injectedPlayer.getWorld());
		relativePositions = Field.get(packet, "b", short[].class);
		IBlockData[] iBlockDatas = Field.get(packet, "c", IBlockData[].class);
		blockDatas = new BlockData[iBlockDatas.length];
		for (int i = 0; i < blockDatas.length; i++)
			blockDatas[i] = BlockData.getBlockDataOf(iBlockDatas[i]);
		updateLight = Field.get(packet, "d", boolean.class);
	}

	public PacketPlayOutMultiBlockChangeEvent(Player injectedPlayer, Location position, short[] relativePositions,
			BlockData[] blockDatas, boolean updateLight) {
		super(injectedPlayer);

		Validate.notNull(position);
		Validate.notNull(relativePositions);
		Validate.notNull(blockDatas);

		this.position = position;
		this.relativePositions = relativePositions;
		this.blockDatas = blockDatas;
		this.updateLight = updateLight;
	}

	public Location getPosition() {
		return position;
	}

	public short[] getRelativePositions() {
		return relativePositions;
	}

	public BlockData[] getBlockDatas() {
		return blockDatas;
	}

	public boolean isUpdateLight() {
		return updateLight;
	}

	@Override
	public Packet<PacketListenerPlayOut> getNMS() {
		final PacketPlayOutMultiBlockChange packet = new PacketPlayOutMultiBlockChange();
		Field.set(packet, "a", PacketUtils.toSectionPosition(position));
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
