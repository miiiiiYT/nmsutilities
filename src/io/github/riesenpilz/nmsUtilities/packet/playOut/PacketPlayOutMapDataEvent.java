package io.github.riesenpilz.nmsUtilities.packet.playOut;

import java.util.ArrayList;
import java.util.Collection;

import org.apache.commons.lang.Validate;
import org.bukkit.entity.Player;

import io.github.riesenpilz.nmsUtilities.map.MapIcon;
import io.github.riesenpilz.nmsUtilities.reflections.Field;
import net.minecraft.server.v1_16_R3.Packet;
import net.minecraft.server.v1_16_R3.PacketListenerPlayOut;
import net.minecraft.server.v1_16_R3.PacketPlayOutMap;

/**
 * https://wiki.vg/Protocol#Map_Data
 * <p>
 * Updates a rectangular area on a map item.
 * <p>
 * Packet ID: 0x27<br>
 * State: Play<br>
 * Bound To: Client
 * 
 * @author Martin
 *
 */
public class PacketPlayOutMapDataEvent extends PacketPlayOutEvent {

	private int mapID;

	/**
	 * From 0 for a fully zoomed-in map (1 block per pixel) to 4 for a fully
	 * zoomed-out map (16 blocks per pixel)
	 */
	private byte scale;

	/**
	 * True if the map has been locked in a cartography table
	 */
	private boolean locked;

	/**
	 * Specifies whether player and item frame icons are shown
	 */
	private boolean trackingPosition;
	private MapIcon[] icons;

	/**
	 * Number of columns updated
	 */
	private int columns;

	/**
	 * Only if Columns is more than 0; number of rows updated
	 */
	private int rows;

	/**
	 * Only if Columns is more than 0; x offset of the westernmost column
	 */
	private int x;

	/**
	 * Only if Columns is more than 0; z offset of the northernmost row
	 */
	private int z;

	/**
	 * Only if Columns is more than 0; see Map item format
	 * (http://minecraft.fandom.com/wiki/Map_item_format)
	 */
	private byte[] data;

	public PacketPlayOutMapDataEvent(Player injectedPlayer, PacketPlayOutMap packet) {
		super(injectedPlayer);

		Validate.notNull(packet);

		mapID = Field.get(packet, "a", int.class);
		scale = Field.get(packet, "b", byte.class);
		locked = Field.get(packet, "c", boolean.class);
		trackingPosition = Field.get(packet, "d", boolean.class);
		net.minecraft.server.v1_16_R3.MapIcon[] nmsIcons = Field.get(packet, "e",
				net.minecraft.server.v1_16_R3.MapIcon[].class);
		icons = new MapIcon[nmsIcons.length];
		for (int i = 0; i < nmsIcons.length; i++)
			icons[i] = new MapIcon(nmsIcons[i]);
		columns = Field.get(packet, "f", int.class);
		rows = Field.get(packet, "g", int.class);
		x = Field.get(packet, "h", int.class);
		z = Field.get(packet, "i", int.class);
		data = Field.get(packet, "j", byte[].class);
	}

	public PacketPlayOutMapDataEvent(Player injectedPlayer, int mapID, byte scale, boolean locked,
			boolean trackingPosition, MapIcon[] icons, int columns, int rows, int x, int z, byte[] data) {
		super(injectedPlayer);

		Validate.notNull(icons);
		Validate.notNull(data);

		this.mapID = mapID;
		this.scale = scale;
		this.locked = locked;
		this.trackingPosition = trackingPosition;
		this.icons = icons;
		this.columns = columns;
		this.rows = rows;
		this.x = x;
		this.z = z;
		this.data = data;
	}

	public int getMapID() {
		return mapID;
	}

	public byte getScale() {
		return scale;
	}

	public boolean isLocked() {
		return locked;
	}

	public boolean isTrackingPosition() {
		return trackingPosition;
	}

	public MapIcon[] getIcons() {
		return icons;
	}

	public int getColumns() {
		return columns;
	}

	public int getRows() {
		return rows;
	}

	public int getX() {
		return x;
	}

	public int getZ() {
		return z;
	}

	public byte[] getData() {
		return data;
	}

	@Override
	public Packet<PacketListenerPlayOut> getNMS() {
		Collection<net.minecraft.server.v1_16_R3.MapIcon> nmsIcons = new ArrayList<>();
		for (int i = 0; i < icons.length; i++)
			nmsIcons.add(icons[i].getNMS());
		final PacketPlayOutMap packet = new PacketPlayOutMap(mapID, scale, locked, trackingPosition, nmsIcons, data,
				columns, rows, x, z);
		Field.set(packet, "j", data);
		return packet;
	}

	@Override
	public int getPacketID() {
		return 0x27;
	}

	@Override
	public String getProtocolURLString() {
		return "https://wiki.vg/Protocol#Map_Data";
	}
}
