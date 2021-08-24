package io.github.riesenpilz.nms.packet.playOut;

import java.util.List;

import org.bukkit.entity.Player;

import io.github.riesenpilz.nms.nbt.NBTTag;
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
	}

	@Override
	public Packet<PacketListenerPlayOut> getNMS() {
		return null;
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
