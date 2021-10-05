package io.github.riesenpilz.nmsUtilities.packet.playOut;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;

import io.github.riesenpilz.nmsUtilities.reflections.Field;
import net.minecraft.server.v1_16_R3.DimensionManager;
import net.minecraft.server.v1_16_R3.EnumGamemode;
import net.minecraft.server.v1_16_R3.Packet;
import net.minecraft.server.v1_16_R3.PacketListenerPlayOut;
import net.minecraft.server.v1_16_R3.PacketPlayOutRespawn;
import net.minecraft.server.v1_16_R3.ResourceKey;
import net.minecraft.server.v1_16_R3.World;

/**
 * https://wiki.vg/Protocol#Respawn
 * <p>
 * To change the player's dimension (overworld/nether/end), send them a respawn
 * packet with the appropriate dimension, followed by prechunks/chunks for the
 * new dimension, and finally a position and look packet. You do not need to
 * unload chunks, the client will do it automatically.
 * <p>
 * <i> Avoid changing player's dimension to same dimension they were already in
 * unless they are dead. If you change the dimension to one they are already in,
 * weird bugs can occur, such as the player being unable to attack other players
 * in new world (until they die and respawn).
 * <p>
 * If you must respawn a player in the same dimension without killing them, send
 * two respawn packets, one to a different world and then another to the world
 * you want. You do not need to complete the first respawn; it only matters that
 * you send two packets.</i>
 * <p>
 * Packet ID: 0x3D<br>
 * State: Play<br>
 * Bound To: Client
 * 
 * @author Martin
 *
 */
public class PacketPlayOutRespawnEvent extends PacketPlayOutEvent {

	private DimensionManager dimension;
	private ResourceKey<World> worldName;

	/**
	 * First 8 bytes of the SHA-256 hash of the world's seed. Used client side for
	 * biome noise.
	 */
	private long hashedSeed;
	private GameMode gameMode;
	private GameMode previousGameMode;

	/**
	 * True if the world is a debug mode world; debug mode worlds cannot be modified
	 * and have predefined blocks.
	 */
	private boolean isDebug;

	/**
	 * True if the world is a superflat world; flat worlds have different void fog
	 * and a horizon at y=0 instead of y=63.
	 */
	private boolean isFlat;

	/**
	 * If false, metadata is reset on the respawned player entity. Set to true for
	 * dimension changes (including the dimension change triggered by sending client
	 * status perform respawn to exit the end poem/credits), and false for normal
	 * respawns.
	 */
	private boolean copyMetadata;

	@SuppressWarnings({ "deprecation", "unchecked" })
	public PacketPlayOutRespawnEvent(Player injectedPlayer, PacketPlayOutRespawn packet) {
		super(injectedPlayer);

		dimension = Field.get(packet, "a", DimensionManager.class);
		worldName = Field.get(packet, "b", ResourceKey.class);
		hashedSeed = Field.get(packet, "c", long.class);
		gameMode = GameMode.getByValue(Field.get(packet, "d", EnumGamemode.class).getId());
		previousGameMode = GameMode.getByValue(Field.get(packet, "e", EnumGamemode.class).getId());
		isDebug = Field.get(packet, "f", boolean.class);
		isFlat = Field.get(packet, "g", boolean.class);
		copyMetadata = Field.get(packet, "h", boolean.class);
	}

	

	public PacketPlayOutRespawnEvent(Player injectedPlayer, DimensionManager dimension, ResourceKey<World> worldName,
			long hashedSeed, GameMode gameMode, GameMode previousGameMode, boolean isDebug, boolean isFlat,
			boolean copyMetadata) {
		super(injectedPlayer);
		this.dimension = dimension;
		this.worldName = worldName;
		this.hashedSeed = hashedSeed;
		this.gameMode = gameMode;
		this.previousGameMode = previousGameMode;
		this.isDebug = isDebug;
		this.isFlat = isFlat;
		this.copyMetadata = copyMetadata;
	}



	public DimensionManager getDimension() {
		return dimension;
	}

	public ResourceKey<World> getWorldName() {
		return worldName;
	}

	public long getHashedSeed() {
		return hashedSeed;
	}

	public GameMode getGameMode() {
		return gameMode;
	}

	public GameMode getPreviousGameMode() {
		return previousGameMode;
	}

	public boolean isDebug() {
		return isDebug;
	}

	public boolean isFlat() {
		return isFlat;
	}

	public boolean isCopyMetadata() {
		return copyMetadata;
	}

	@SuppressWarnings("deprecation")
	@Override
	public Packet<PacketListenerPlayOut> getNMS() {
		return new PacketPlayOutRespawn(dimension, worldName, hashedSeed, EnumGamemode.getById(gameMode.getValue()), EnumGamemode.getById(previousGameMode.getValue()), isFlat, isDebug, copyMetadata);
	}

	@Override
	public int getPacketID() {
		return 0x3D;
	}

	@Override
	public String getProtocolURLString() {
		return "https://wiki.vg/Protocol#Respawn";
	}
}
