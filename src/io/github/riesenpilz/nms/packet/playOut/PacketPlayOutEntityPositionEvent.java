package io.github.riesenpilz.nms.packet.playOut;

import java.util.HashSet;
import java.util.Set;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import io.github.riesenpilz.nms.reflections.Field;
import net.minecraft.server.v1_16_R3.Packet;
import net.minecraft.server.v1_16_R3.PacketListenerPlayOut;
import net.minecraft.server.v1_16_R3.PacketPlayOutPosition;
import net.minecraft.server.v1_16_R3.PacketPlayOutPosition.EnumPlayerTeleportFlags;

/**
 * https://wiki.vg/Protocol#Player_Position_And_Look_.28clientbound.29
 * <p>
 * Updates the player's position on the server. This packet will also close the
 * “Downloading Terrain” screen when joining/respawning.
 * <p>
 * If the distance between the last known position of the player on the server
 * and the new position set by this packet is greater than 100 meters, the
 * client will be kicked for “You moved too quickly :( (Hacking?)”.
 * <p>
 * Also if the fixed-point number of X or Z is set greater than 3.2E7D the
 * client will be kicked for “Illegal position”.
 * <p>
 * Yaw is measured in degrees, and does not follow classical trigonometry rules.
 * The unit circle of yaw on the XZ-plane starts at (0, 1) and turns
 * counterclockwise, with 90 at (-1, 0), 180 at (0, -1) and 270 at (1, 0).
 * Additionally, yaw is not clamped to between 0 and 360 degrees; any number is
 * valid, including negative numbers and numbers greater than 360.
 * <p>
 * Pitch is measured in degrees, where 0 is looking straight ahead, -90 is
 * looking straight up, and 90 is looking straight down.
 * <p>
 * Packet ID: 0x38<br>
 * State: Play<br>
 * Bound To: Client
 * 
 * @author Martin
 *
 */
public class PacketPlayOutEntityPositionEvent extends PacketPlayOutEvent {

	/**
	 * the target location. The different values can be relative or absolute
	 * depending on
	 * {@link PacketPlayOutEntityPositionEvent#absoluteOrRelativeFlags}. E.g
	 * if X is set, the x value is relative and not absolute.
	 */
	private Location location;

	private Set<PlayerTeleportFlags> absoluteOrRelativeFlags;
	private int teleportId;

	public PacketPlayOutEntityPositionEvent(Player injectedPlayer, PacketPlayOutPosition packet) {
		super(injectedPlayer);
		location = new Location(injectedPlayer.getWorld(), Field.get(packet, "a", double.class),
				Field.get(packet, "b", double.class), Field.get(packet, "c", double.class),
				Field.get(packet, "d", float.class), Field.get(packet, "e", float.class));
		@SuppressWarnings("unchecked")
		Set<EnumPlayerTeleportFlags> nmsAbsoluteOrRelativeFlags = Field.get(packet, "d", Set.class);
		for (EnumPlayerTeleportFlags nmsAbsoluteOrRelativeFlag : nmsAbsoluteOrRelativeFlags)
			absoluteOrRelativeFlags.add(PlayerTeleportFlags.getPlayerTeleportFlags(nmsAbsoluteOrRelativeFlag));

		teleportId = Field.get(packet, "g", int.class);
	}

	public PacketPlayOutEntityPositionEvent(Player injectedPlayer, Location location,
			Set<PlayerTeleportFlags> absoluteOrRelativeFlags, int teleportId) {
		super(injectedPlayer);
		this.location = location;
		this.absoluteOrRelativeFlags = absoluteOrRelativeFlags;
		this.teleportId = teleportId;
	}

	public Location getLocation() {
		return location;
	}

	public Set<PlayerTeleportFlags> getAbsoluteOrRelativeFlags() {
		return absoluteOrRelativeFlags;
	}

	public int getTeleportId() {
		return teleportId;
	}

	@Override
	public Packet<PacketListenerPlayOut> getNMS() {
		Set<EnumPlayerTeleportFlags> nmsAbsoluteOrRelativeFlags = new HashSet<>();
		for (PlayerTeleportFlags absoluteOrRelativeFlag : absoluteOrRelativeFlags)
			nmsAbsoluteOrRelativeFlags.add(absoluteOrRelativeFlag.getNMS());
		return new PacketPlayOutPosition(location.getX(), location.getY(), location.getZ(), location.getYaw(),
				location.getPitch(), null, teleportId);
	}

	@Override
	public int getPacketID() {
		return 0x38;
	}

	@Override
	public String getProtocolURLString() {
		return "https://wiki.vg/Protocol#Player_Position_And_Look_.28clientbound.29";
	}

	public static enum PlayerTeleportFlags {

		X(EnumPlayerTeleportFlags.X), Y(EnumPlayerTeleportFlags.Y), Z(EnumPlayerTeleportFlags.Z),
		Y_ROT(EnumPlayerTeleportFlags.Y_ROT), X_ROT(EnumPlayerTeleportFlags.X_ROT);

		private EnumPlayerTeleportFlags nms;

		private PlayerTeleportFlags(EnumPlayerTeleportFlags nms) {
			this.nms = nms;
		}

		public EnumPlayerTeleportFlags getNMS() {
			return nms;
		}

		public static PlayerTeleportFlags getPlayerTeleportFlags(EnumPlayerTeleportFlags nms) {
			for (PlayerTeleportFlags flag : values())
				if (flag.getNMS().equals(nms))
					return flag;
			return null;
		}
	}
}
