package io.github.riesenpilz.nmsUtilities.packet.playOut;

import org.bukkit.entity.Player;

import io.github.riesenpilz.nmsUtilities.reflections.Field;
import net.minecraft.server.v1_16_R3.Packet;
import net.minecraft.server.v1_16_R3.PacketListenerPlayOut;
import net.minecraft.server.v1_16_R3.PacketPlayOutViewCentre;

/**
 * https://wiki.vg/Protocol#Update_View_Position
 * <p>
 * Updates the client's location. This is used to determine what chunks should
 * remain loaded and if a chunk load should be ignored; chunks outside of the
 * view distance may be unloaded.
 * <p>
 * Sent whenever the player moves across a chunk border horizontally, and also
 * (according to testing) for any integer change in the vertical axis, even if
 * it doesn't go across a chunk section border.
 * <p>
 * Packet ID: 0x49<br>
 * State: Play<br>
 * Bound To: Client
 * 
 * @author Martin
 *
 */
public class PacketPlayOutUpdateViewPositionEvent extends PacketPlayOutEvent {

	/**
	 * Chunk X coordinate of the player's position.
	 */
	private int chunkX;

	/**
	 * Chunk Z coordinate of the player's position.
	 */
	private int chunkZ;

	public PacketPlayOutUpdateViewPositionEvent(Player injectedPlayer, PacketPlayOutViewCentre packet) {
		super(injectedPlayer);
		chunkX = Field.get(packet, "a", int.class);
		chunkZ = Field.get(packet, "b", int.class);
	}

	public PacketPlayOutUpdateViewPositionEvent(Player injectedPlayer, int chunkX, int chunkZ) {
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
		return new PacketPlayOutViewCentre(chunkX, chunkZ);
	}

	@Override
	public int getPacketID() {
		return 0x49;
	}

	@Override
	public String getProtocolURLString() {
		return "https://wiki.vg/Protocol#Update_View_Position";
	}
}
