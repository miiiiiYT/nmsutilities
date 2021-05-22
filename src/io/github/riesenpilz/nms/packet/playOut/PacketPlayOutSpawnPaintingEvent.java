package io.github.riesenpilz.nms.packet.playOut;

import java.util.UUID;

import org.bukkit.Art;
import org.bukkit.Location;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;

import io.github.riesenpilz.nms.reflections.Field;
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
public class PacketPlayOutSpawnPaintingEvent extends PacketPlayOutEvent {

	private int entityID;
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

	public PacketPlayOutSpawnPaintingEvent(Player injectedPlayer, int entityID, UUID uuid, Location location,
			BlockFace facing, Art art) {
		super(injectedPlayer);
		this.entityID = entityID;
		this.uuid = uuid;
		this.location = location;
		this.facing = facing;
		this.art = art;
	}

	@SuppressWarnings("deprecation")
	public PacketPlayOutSpawnPaintingEvent(Player injectedPlayer, PacketPlayOutSpawnEntityPainting packet) {
		super(injectedPlayer);
		entityID = (int) new Field(PacketPlayOutSpawnEntityPainting.class, "a").get(packet);
		uuid = (UUID) new Field(PacketPlayOutSpawnEntityPainting.class, "b").get(packet);
		final BlockPosition pos = (BlockPosition) new Field(PacketPlayOutSpawnEntityPainting.class, "c").get(packet);
		location = new Location(injectedPlayer.getWorld(), pos.getX(), pos.getY(), pos.getZ());
		facing = BlockFace
				.valueOf(((EnumDirection) new Field(PacketPlayOutSpawnEntityPainting.class, "d").get(packet)).name());
		art = Art.getById((int) new Field(PacketPlayOutSpawnEntityPainting.class, "e").get(pos));
	}

	public int getEntityID() {
		return entityID;
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
		new Field(PacketPlayOutSpawnEntityPainting.class, "a").set(packet, entityID);
		new Field(PacketPlayOutSpawnEntityPainting.class, "b").set(packet, uuid);
		new Field(PacketPlayOutSpawnEntityPainting.class, "c").set(packet,
				new BlockPosition(location.getX(), location.getYaw(), location.getZ()));
		new Field(PacketPlayOutSpawnEntityPainting.class, "d").set(packet, EnumDirection.valueOf(facing.name()));
		new Field(PacketPlayOutSpawnEntityPainting.class, "e").set(packet, art.getId());
		return packet;
	}

	@Override
	public int getPacketID() {
		return 3;
	}

	@Override
	public String getProtocolURLString() {
		return "https://wiki.vg/Protocol#Spawn_Painting";
	}
}
