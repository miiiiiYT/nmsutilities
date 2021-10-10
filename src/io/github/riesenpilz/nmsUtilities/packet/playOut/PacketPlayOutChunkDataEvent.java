package io.github.riesenpilz.nmsUtilities.packet.playOut;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.Validate;
import org.bukkit.entity.Player;

import io.github.riesenpilz.nmsUtilities.nbt.NBTTag;
import io.github.riesenpilz.nmsUtilities.reflections.Field;
import net.minecraft.server.v1_16_R3.NBTTagCompound;
import net.minecraft.server.v1_16_R3.Packet;
import net.minecraft.server.v1_16_R3.PacketListenerPlayOut;
import net.minecraft.server.v1_16_R3.PacketPlayOutMapChunk;

/**
 * https://wiki.vg/Protocol#Chunk_Data
 * <p>
 * The server only sends skylight information for chunk pillars in the
 * Overworld, it's up to the client to know in which dimension the player is
 * currently located. You can also infer this information from the primary
 * bitmask and the amount of uncompressed bytes sent. This packet also sends all
 * block entities in the chunk (though sending them is not required; it is still
 * legal to send them with Update Block Entity later).
 * <p>
 * Note that the Notchian client requires an Update View Position packet when it
 * crosses a chunk border, otherwise it'll only display render distance + 2
 * chunks around the chunk it spawned in.
 * <p>
 * The compacted array format has been adjusted so that individual entries no
 * longer span across multiple longs, affecting the main data array and
 * heightmaps.
 * <p>
 * Packet ID: 0x22<br>
 * State: Play<br>
 * Bound To: Client
 * 
 * @author Martin
 *
 */
public class PacketPlayOutChunkDataEvent extends PacketPlayOutEvent {
	/**
	 * Chunk coordinate (block coordinate divided by 16, rounded down)
	 */
	private int chunkX;
	private int chunkZ;
	private int c;

	/**
	 * Compound containing one long array named MOTION_BLOCKING, which is a
	 * heightmap for the highest solid block at each position in the chunk (as a
	 * compacted long array with 256 entries at 9 bits per entry totaling 36 longs).
	 * The Notchian server also adds a WORLD_SURFACE long array, the purpose of
	 * which is unknown, but it's not required for the chunk to be accepted.
	 */
	private NBTTag heightmaps;

	/**
	 * 1024 biome IDs, ordered by x then z then y, in 4󫶘 blocks.
	 */
	private int[] biomes;

	private byte[] data;

	/**
	 * All block entities in the chunk. Use the x, y, and z tags in the NBT to
	 * determine their positions.
	 */
	private List<NBTTag> blockEntities;
	private boolean h;

	public PacketPlayOutChunkDataEvent(Player injectedPlayer, PacketPlayOutMapChunk packet) {
		super(injectedPlayer);

		Validate.notNull(packet);

		chunkX = Field.get(packet, "a", int.class);
		chunkZ = Field.get(packet, "b", int.class);
		c = Field.get(packet, "c", int.class);
		heightmaps = NBTTag.getNBTTagOf(Field.get(packet, "d", NBTTagCompound.class));
		biomes = Field.get(packet, "e", int[].class);
		data = Field.get(packet, "f", byte[].class);
		@SuppressWarnings("unchecked")
		final List<NBTTagCompound> nmsBlockEntities = Field.get(packet, "g", List.class);
		blockEntities = new ArrayList<>();
		for (NBTTagCompound nmsTag : nmsBlockEntities)
			blockEntities.add(NBTTag.getNBTTagOf(nmsTag));
		h = Field.get(packet, "h", boolean.class);
	}

	public PacketPlayOutChunkDataEvent(Player injectedPlayer, int chunkX, int chunkZ, int c, NBTTag heightmaps,
			int[] biomes, byte[] data, List<NBTTag> blockEntities, boolean h) {
		super(injectedPlayer);

		Validate.notNull(heightmaps);
		Validate.notNull(biomes);
		Validate.notNull(data);
		Validate.notNull(blockEntities);

		this.chunkX = chunkX;
		this.chunkZ = chunkZ;
		this.c = c;
		this.heightmaps = heightmaps;
		this.biomes = biomes;
		this.data = data;
		this.blockEntities = blockEntities;
		this.h = h;
	}

	public int getChunkX() {
		return chunkX;
	}

	public int getChunkZ() {
		return chunkZ;
	}

	public int getC() {
		return c;
	}

	public NBTTag getHeightmaps() {
		return heightmaps;
	}

	public int[] getBiomes() {
		return biomes;
	}

	public byte[] getData() {
		return data;
	}

	public List<NBTTag> getBlockEntities() {
		return blockEntities;
	}

	public boolean isH() {
		return h;
	}

	@Override
	public Packet<PacketListenerPlayOut> getNMS() {
		final PacketPlayOutMapChunk packet = new PacketPlayOutMapChunk();
		Field.set(packet, "a", chunkX);
		Field.set(packet, "b", chunkZ);
		Field.set(packet, "c", c);
		Field.set(packet, "d", heightmaps.getNMS());
		Field.set(packet, "e", biomes);
		Field.set(packet, "f", data);
		final List<NBTTagCompound> nmsBlockEntities = new ArrayList<>();
		for (NBTTag tag : blockEntities)
			nmsBlockEntities.add(tag.getNMS());
		Field.set(packet, "g", nmsBlockEntities);
		Field.set(packet, "h", h);
		return packet;
	}

	@Override
	public int getPacketID() {
		return 0x22;
	}

	@Override
	public String getProtocolURLString() {
		return "https://wiki.vg/Protocol#Chunk_Data";
	}
}
