package io.github.riesenpilz.nmsUtilities.map;

import io.github.riesenpilz.nmsUtilities.reflections.Field;
import net.minecraft.server.v1_16_R3.IChatBaseComponent;

public class MapIcon {

	private net.minecraft.server.v1_16_R3.MapIcon nms;

	public MapIcon(net.minecraft.server.v1_16_R3.MapIcon icon) {
		nms = icon;
	}

	 public MapIcon(MapIcon.Type type, byte x, byte y, byte rotation, IChatBaseComponent name) {
	        nms = new net.minecraft.server.v1_16_R3.MapIcon(type.getNMS(), x, y, rotation, name);
	    }
	
	public Type getType() {
		return Type.getType(nms.getType());
	}

	public void setType(Type type) {
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

	public enum Type {
		PLAYER(net.minecraft.server.v1_16_R3.MapIcon.Type.PLAYER, false),
		FRAME(net.minecraft.server.v1_16_R3.MapIcon.Type.FRAME, true),
		RED_MARKER(net.minecraft.server.v1_16_R3.MapIcon.Type.RED_MARKER, false),
		BLUE_MARKER(net.minecraft.server.v1_16_R3.MapIcon.Type.BLUE_MARKER, false),
		TARGET_X(net.minecraft.server.v1_16_R3.MapIcon.Type.TARGET_X, true),
		TARGET_POINT(net.minecraft.server.v1_16_R3.MapIcon.Type.TARGET_POINT, true),
		PLAYER_OFF_MAP(net.minecraft.server.v1_16_R3.MapIcon.Type.PLAYER_OFF_MAP, false),
		PLAYER_OFF_LIMITS(net.minecraft.server.v1_16_R3.MapIcon.Type.PLAYER_OFF_LIMITS, false),
		MANSION(net.minecraft.server.v1_16_R3.MapIcon.Type.MANSION, true, 5393476),
		MONUMENT(net.minecraft.server.v1_16_R3.MapIcon.Type.MONUMENT, true, 3830373),
		BANNER_WHITE(net.minecraft.server.v1_16_R3.MapIcon.Type.BANNER_WHITE, true),
		BANNER_ORANGE(net.minecraft.server.v1_16_R3.MapIcon.Type.BANNER_ORANGE, true),
		BANNER_MAGENTA(net.minecraft.server.v1_16_R3.MapIcon.Type.BANNER_MAGENTA, true),
		BANNER_LIGHT_BLUE(net.minecraft.server.v1_16_R3.MapIcon.Type.BANNER_LIGHT_BLUE, true),
		BANNER_YELLOW(net.minecraft.server.v1_16_R3.MapIcon.Type.BANNER_YELLOW, true),
		BANNER_LIME(net.minecraft.server.v1_16_R3.MapIcon.Type.BANNER_LIME, true),
		BANNER_PINK(net.minecraft.server.v1_16_R3.MapIcon.Type.BANNER_PINK, true),
		BANNER_GRAY(net.minecraft.server.v1_16_R3.MapIcon.Type.BANNER_GRAY, true),
		BANNER_LIGHT_GRAY(net.minecraft.server.v1_16_R3.MapIcon.Type.BANNER_LIGHT_GRAY, true),
		BANNER_CYAN(net.minecraft.server.v1_16_R3.MapIcon.Type.BANNER_CYAN, true),
		BANNER_PURPLE(net.minecraft.server.v1_16_R3.MapIcon.Type.BANNER_PURPLE, true),
		BANNER_BLUE(net.minecraft.server.v1_16_R3.MapIcon.Type.BANNER_BLUE, true),
		BANNER_BROWN(net.minecraft.server.v1_16_R3.MapIcon.Type.BANNER_BROWN, true),
		BANNER_GREEN(net.minecraft.server.v1_16_R3.MapIcon.Type.BANNER_GREEN, true),
		BANNER_RED(net.minecraft.server.v1_16_R3.MapIcon.Type.BANNER_RED, true),
		BANNER_BLACK(net.minecraft.server.v1_16_R3.MapIcon.Type.BANNER_BLACK, true),
		RED_X(net.minecraft.server.v1_16_R3.MapIcon.Type.RED_X, true);

		private final net.minecraft.server.v1_16_R3.MapIcon.Type nms;

		private final boolean C;
		private final int D;

		private Type(net.minecraft.server.v1_16_R3.MapIcon.Type nms, boolean flag) {
			this(nms, flag, -1);
		}

		private Type(net.minecraft.server.v1_16_R3.MapIcon.Type nms, boolean flag, int i) {
			this.nms = nms;
			C = flag;
			D = i;
		}

		public boolean getC() {
			return C;
		}

		public int getD() {
			return D;
		}

		public boolean hasD() {
			return D >= 0;
		}

		public net.minecraft.server.v1_16_R3.MapIcon.Type getNMS() {
			return nms;
		}

		public static Type getType(net.minecraft.server.v1_16_R3.MapIcon.Type nms) {
			for (Type type : values())
				if (type.getNMS().equals(nms))
					return type;
			return null;
		}
	}

	public net.minecraft.server.v1_16_R3.MapIcon getNMS() {
		return nms;
	}
}
