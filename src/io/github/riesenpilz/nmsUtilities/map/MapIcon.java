package io.github.riesenpilz.nmsUtilities.map;

import org.apache.commons.lang.Validate;

import io.github.riesenpilz.nmsUtilities.reflections.Field;
import net.minecraft.server.v1_16_R3.IChatBaseComponent;

/**
 * Represents a {@link net.minecraft.server.v1_16_R3.MapIcon}. Only used by
 * packets.
 * 
 * @see PacketPlayOutMapDataEvent
 *
 */
public class MapIcon {

	private net.minecraft.server.v1_16_R3.MapIcon nms;

	public MapIcon(net.minecraft.server.v1_16_R3.MapIcon icon) {
		Validate.notNull(icon);
		nms = icon;
	}

	public MapIcon(MapIconType type, byte x, byte y, byte rotation, IChatBaseComponent name) {
		Validate.notNull(type);
		nms = new net.minecraft.server.v1_16_R3.MapIcon(type.getNMS(), x, y, rotation, name);
	}

	public MapIconType getType() {
		return MapIconType.getType(nms.getType());
	}

	public void setType(MapIconType type) {
		Validate.notNull(type);
		nms = new net.minecraft.server.v1_16_R3.MapIcon(type.getNMS(), nms.getX(), nms.getY(), nms.getRotation(),
				nms.getName());
	}

	public byte getX() {
		return nms.getX();
	}

	public void setX(byte x) {
		Field.set(nms, "x", x);
	}

	public byte getY() {
		return nms.getY();
	}

	public void setY(byte y) {
		Field.set(nms, "y", y);
	}

	public byte getRotation() {
		return nms.getRotation();
	}

	public void setRotation(byte rotation) {
		Field.set(nms, "rotation", rotation);
	}

	public IChatBaseComponent getName() {
		return nms.getName();
	}

	public void setName(IChatBaseComponent name) {
		nms = new net.minecraft.server.v1_16_R3.MapIcon(nms.getType(), nms.getX(), nms.getY(), nms.getRotation(), name);
	}

	public net.minecraft.server.v1_16_R3.MapIcon getNMS() {
		return nms;
	}
}
