package io.github.riesenpilz.nmsUtilities.packet.playOut;

import org.apache.commons.lang.Validate;
import org.bukkit.entity.Player;

import io.github.riesenpilz.nmsUtilities.reflections.Field;
import net.minecraft.server.v1_16_R3.Packet;
import net.minecraft.server.v1_16_R3.PacketListenerPlayOut;
import net.minecraft.server.v1_16_R3.PacketPlayOutUnloadChunk;

/**
 * https://wiki.vg/Protocol#Unload_Chunk
 * <p>
 * Tells the client to unload a chunk column.
 * <p>
 * It is legal to send this packet even if the given chunk is not currently
 * loaded.
 * <p>
 * Packet ID: 0x1C<br>
 * State: Play<br>
 * Bound To: Client
 */
public class PacketPlayOutUnloadChunkEvent extends PacketPlayOutEvent {

	/**
	 * Block coordinate divided by 16, rounded down.
	 */
	private int chunkX;

	/**
	 * Block coordinate divided by 16, rounded down.
	 */
	private int chunkZ;

	public PacketPlayOutUnloadChunkEvent(Player injectedPlayer, PacketPlayOutUnloadChunk packet) {
		super(injectedPlayer);

		Validate.notNull(packet);

		chunkX = Field.get(packet, "a", int.class);
		chunkZ = Field.get(packet, "b", int.class);
	}

	public PacketPlayOutUnloadChunkEvent(Player injectedPlayer, int chunkX, int chunkZ) {
		super(injectedPlayer);

		this.chunkX = chunkX;
		this.chunkZ = chunkZ;
	}

	public int getChunkX() {
		return chunkX;
	}

	public int getChunkZ() {
		return chunkZ;
	}

	@Override
	public Packet<PacketListenerPlayOut> getNMS() {
		return new PacketPlayOutUnloadChunk(chunkX, chunkZ);
	}

	@Override
	public int getPacketID() {
		return 0x1C;
	}

	@Override
	public String getProtocolURLString() {
		return "https://wiki.vg/Protocol#Unload_Chunk";
	}
}
