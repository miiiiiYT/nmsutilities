package io.github.riesenpilz.nmsUtilities.packet.playOut;

import java.util.HashSet;
import java.util.Set;

import org.bukkit.GameMode;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;

import io.github.riesenpilz.nmsUtilities.packet.PacketUtils;
import io.github.riesenpilz.nmsUtilities.reflections.Field;
import net.minecraft.server.v1_16_R3.DimensionManager;
import net.minecraft.server.v1_16_R3.EnumGamemode;
import net.minecraft.server.v1_16_R3.IRegistry;
import net.minecraft.server.v1_16_R3.IRegistryCustom;
import net.minecraft.server.v1_16_R3.IRegistryCustom.Dimension;
import net.minecraft.server.v1_16_R3.Packet;
import net.minecraft.server.v1_16_R3.PacketListenerPlayOut;
import net.minecraft.server.v1_16_R3.PacketPlayOutLogin;
import net.minecraft.server.v1_16_R3.ResourceKey;
import net.minecraft.server.v1_16_R3.World;

/**
 * https://wiki.vg/Protocol#Join_Game
 * <p>
 * See Protocol Encryption for information on logging in.
 * (https://wiki.vg/Protocol_Encryption)
 * <p>
 * Packet ID: 0x26<br>
 * State: Play<br>
 * Bound To: Client
 * 
 * @author Martin
 *
 */
public class PacketPlayOutJoinGameEvent extends PacketPlayOutEntityEvent {

	/**
	 * First 8 bytes of the SHA-256 hash of the world's seed. Used client side for
	 * biome noise
	 */
	private long hashedSeed;
	private boolean hardcore;
	private GameMode gamemode;
	private GameMode previousGamemode;
	private Set<NamespacedKey> worldNames;

	/**
	 * The full extent of these is still unknown, but the tag represents a dimension
	 * and biome registry.
	 */
	private IRegistryCustom.Dimension dimensionCodec;

	/**
	 * Valid dimensions are defined per dimension registry sent before this.
	 */
	private DimensionManager dimension;

	/**
	 * Name of the world being spawned into.
	 */
	private NamespacedKey worldName;

	/**
	 * Was once used by the client to draw the player list, but now is ignored.
	 */
	private int maxPlayers;

	/**
	 * Render distance (2-32).
	 */
	private int viewDistance;

	/**
	 * If true, a Notchian client shows reduced information on the debug screen. For
	 * servers in development, this should almost always be false.
	 */
	private boolean reducedDebugInfo;

	/**
	 * Set to false when the doImmediateRespawn gamerule is true.
	 */
	private boolean enableRespawnScreen;

	/**
	 * True if the world is a debug mode world; debug mode worlds cannot be modified
	 * and have predefined blocks.
	 */
	private boolean debug;

	/**
	 * True if the world is a superflat world; flat worlds have different void fog
	 * and a horizon at y=0 instead of y=63.
	 */
	private boolean flat;

	@SuppressWarnings("unchecked")
	public PacketPlayOutJoinGameEvent(Player injectedPlayer, PacketPlayOutLogin packet) {
		super(injectedPlayer, packet);

		hashedSeed = Field.get(packet, "b", long.class);
		hardcore = Field.get(packet, "c", boolean.class);
		gamemode = PacketUtils.toGameMode(Field.get(packet, "d", EnumGamemode.class));
		previousGamemode = PacketUtils.toGameMode(Field.get(packet, "e", EnumGamemode.class));
		final Set<ResourceKey<World>> nmsWorldNames = Field.get(packet, "f", Set.class);
		final Set<NamespacedKey> worlds = new HashSet<>();
		for (ResourceKey<World> nmsWorldName : nmsWorldNames)
			worlds.add(PacketUtils.toNamespacedKey(nmsWorldName.a()));
		dimensionCodec = Field.get(packet, "g", IRegistryCustom.Dimension.class);
		dimension = Field.get(packet, "h", DimensionManager.class);
		worldName = PacketUtils.toNamespacedKey(Field.get(packet, "i", ResourceKey.class).a());
		maxPlayers = Field.get(packet, "j", int.class);
		viewDistance = Field.get(packet, "k", int.class);
		reducedDebugInfo = Field.get(packet, "l", boolean.class);
		enableRespawnScreen = Field.get(packet, "m", boolean.class);
		debug = Field.get(packet, "n", boolean.class);
		flat = Field.get(packet, "o", boolean.class);
	}

	public PacketPlayOutJoinGameEvent(Player injectedPlayer, int playerId, long hashedSeed, boolean hardcore,
			GameMode gamemode, GameMode previousGamemode, Set<NamespacedKey> worldNames, Dimension dimensionCodec,
			DimensionManager dimension, NamespacedKey worldName, int maxPlayers, int viewDistance,
			boolean reducedDebugInfo, boolean enableRespawnScreen, boolean debug, boolean flat) {
		super(injectedPlayer, playerId);
		this.hashedSeed = hashedSeed;
		this.hardcore = hardcore;
		this.gamemode = gamemode;
		this.previousGamemode = previousGamemode;
		this.worldNames = worldNames;
		this.dimensionCodec = dimensionCodec;
		this.dimension = dimension;
		this.worldName = worldName;
		this.maxPlayers = maxPlayers;
		this.viewDistance = viewDistance;
		this.reducedDebugInfo = reducedDebugInfo;
		this.enableRespawnScreen = enableRespawnScreen;
		this.debug = debug;
		this.flat = flat;
	}

	public long getHashedSeed() {
		return hashedSeed;
	}

	public boolean isHardcore() {
		return hardcore;
	}

	public GameMode getGamemode() {
		return gamemode;
	}

	public GameMode getPreviousGamemode() {
		return previousGamemode;
	}

	public Set<NamespacedKey> getWorldNames() {
		return worldNames;
	}

	public IRegistryCustom.Dimension getDimensionCodec() {
		return dimensionCodec;
	}

	public DimensionManager getDimension() {
		return dimension;
	}

	public NamespacedKey getWorldName() {
		return worldName;
	}

	public int getMaxPlayers() {
		return maxPlayers;
	}

	public int getViewDistance() {
		return viewDistance;
	}

	public boolean isReducedDebugInfo() {
		return reducedDebugInfo;
	}

	public boolean isEnableRespawnScreen() {
		return enableRespawnScreen;
	}

	public boolean isDebug() {
		return debug;
	}

	public boolean isFlat() {
		return flat;
	}

	@Override
	public Packet<PacketListenerPlayOut> getNMS() {
		final Set<ResourceKey<World>> nmsWorldNames = new HashSet<>();
		for (NamespacedKey worldName : worldNames)
			nmsWorldNames.add(ResourceKey.a(IRegistry.L, PacketUtils.toMinecraftKey(worldName)));
		return new PacketPlayOutLogin(getEntityId(), PacketUtils.toEnumGamemode(gamemode),
				PacketUtils.toEnumGamemode(previousGamemode), hashedSeed, hardcore, nmsWorldNames, dimensionCodec,
				dimension, ResourceKey.a(IRegistry.L, PacketUtils.toMinecraftKey(worldName)), maxPlayers, viewDistance,
				reducedDebugInfo, enableRespawnScreen, debug, flat);
	}

	@Override
	public int getPacketID() {
		return 0x26;
	}

	@Override
	public String getProtocolURLString() {
		return "https://wiki.vg/Protocol#Join_Game";
	}
}
