package io.github.riesenpilz.nmsUtilities.packet.playOut;

import java.util.UUID;

import org.apache.commons.lang.Validate;
import org.bukkit.Art;
import org.bukkit.Location;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;

import io.github.riesenpilz.nmsUtilities.packet.PacketUtils;
import io.github.riesenpilz.nmsUtilities.reflections.Field;
import net.minecraft.server.v1_16_R3.BlockPosition;
import net.minecraft.server.v1_16_R3.EnumDirection;
import net.minecraft.server.v1_16_R3.Packet;
import net.minecraft.server.v1_16_R3.PacketListenerPlayOut;
import net.minecraft.server.v1_16_R3.PacketPlayOutSpawnEntityPainting;

/**
 * https://wiki.vg/Protocol#Spawn_Painting<br>
 * <br>
 * This packet shows location, name, and type of painting.<br>
 * <br>
 * Packet ID: 0x03<br>
 * State: Play<br>
 * Bound To: Client
 * 
 * @author Martin
 *
 */
public class PacketPlayOutSpawnPaintingEvent extends PacketPlayOutEntityEvent {

	private UUID uuid;

	/**
	 * Center coordinates.<br>
	 * Calculating the center of an image: given a (width × height) grid of cells,
	 * with (0, 0) being the top left corner, the center is (max(0, width / 2 - 1),
	 * height / 2). E.g. (1, 0) for a 2×1 painting, or (1, 2) for a 4×4 painting.
	 */
	private Location location;

	private BlockFace facing;

	private Art art;

	@SuppressWarnings("deprecation")
	public PacketPlayOutSpawnPaintingEvent(Player injectedPlayer, PacketPlayOutSpawnEntityPainting packet) {
		super(injectedPlayer, packet);

		uuid = Field.get(packet, "b", UUID.class);
		location = PacketUtils.toLocation(Field.get(packet, "c", BlockPosition.class), injectedPlayer.getWorld());
		facing = PacketUtils.toBlockFace(Field.get(packet, "d", EnumDirection.class));
		art = Art.getById(Field.get(packet, "e", int.class));
	}

	public PacketPlayOutSpawnPaintingEvent(Player injectedPlayer, int entityId, UUID uuid, Location location,
			BlockFace facing, Art art) {
		super(injectedPlayer, entityId);

		Validate.notNull(uuid);
		Validate.notNull(location);
		Validate.notNull(facing);
		Validate.notNull(art);

		this.uuid = uuid;
		this.location = location;
		this.facing = facing;
		this.art = art;
	}

	public UUID getUuid() {
		return uuid;
	}

	public Location getLocation() {
		return location;
	}

	public BlockFace getFacing() {
		return facing;
	}

	public Art getArt() {
		return art;
	}

	@SuppressWarnings("deprecation")
	@Override
	public Packet<PacketListenerPlayOut> getNMS() {
		final PacketPlayOutSpawnEntityPainting packet = new PacketPlayOutSpawnEntityPainting();
		Field.set(packet, "a", getEntityId());
		Field.set(packet, "b", uuid);
		Field.set(packet, "c", PacketUtils.toBlockPosition(location));
		Field.set(packet, "d", PacketUtils.toEnumDirection(facing));
		Field.set(packet, "e", art.getId());
		return packet;
	}

	@Override
	public int getPacketID() {
		return 0x03;
	}

	@Override
	public String getProtocolURLString() {
		return "https://wiki.vg/Protocol#Spawn_Painting";
	}
}
