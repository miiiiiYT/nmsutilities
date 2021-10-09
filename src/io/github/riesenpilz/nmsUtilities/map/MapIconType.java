package io.github.riesenpilz.nmsUtilities.map;

import org.apache.commons.lang.Validate;

import net.minecraft.server.v1_16_R3.MapIcon.Type;

/**
 * Represents {@link net.minecraft.server.v1_16_R3.MapIcon.Type}. Only used by
 * packets.
 * 
 * @see MapIcon
 *
 */
public enum MapIconType {
	PLAYER(Type.PLAYER, false), FRAME(Type.FRAME, true), RED_MARKER(Type.RED_MARKER, false),
	BLUE_MARKER(Type.BLUE_MARKER, false), TARGET_X(Type.TARGET_X, true), TARGET_POINT(Type.TARGET_POINT, true),
	PLAYER_OFF_MAP(Type.PLAYER_OFF_MAP, false), PLAYER_OFF_LIMITS(Type.PLAYER_OFF_LIMITS, false),
	MANSION(Type.MANSION, true, 5393476), MONUMENT(Type.MONUMENT, true, 3830373), BANNER_WHITE(Type.BANNER_WHITE, true),
	BANNER_ORANGE(Type.BANNER_ORANGE, true), BANNER_MAGENTA(Type.BANNER_MAGENTA, true),
	BANNER_LIGHT_BLUE(Type.BANNER_LIGHT_BLUE, true), BANNER_YELLOW(Type.BANNER_YELLOW, true),
	BANNER_LIME(Type.BANNER_LIME, true), BANNER_PINK(Type.BANNER_PINK, true), BANNER_GRAY(Type.BANNER_GRAY, true),
	BANNER_LIGHT_GRAY(Type.BANNER_LIGHT_GRAY, true), BANNER_CYAN(Type.BANNER_CYAN, true),
	BANNER_PURPLE(Type.BANNER_PURPLE, true), BANNER_BLUE(Type.BANNER_BLUE, true), BANNER_BROWN(Type.BANNER_BROWN, true),
	BANNER_GREEN(Type.BANNER_GREEN, true), BANNER_RED(Type.BANNER_RED, true), BANNER_BLACK(Type.BANNER_BLACK, true),
	RED_X(Type.RED_X, true);

	private final Type nms;

	private final boolean C;
	private final int D;

	private MapIconType(Type nms, boolean flag) {
		this(nms, flag, -1);
	}

	private MapIconType(Type nms, boolean flag, int i) {
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

	public Type getNMS() {
		return nms;
	}

	public static MapIconType getType(Type nms) {
		Validate.notNull(nms);
		for (MapIconType type : values())
			if (type.getNMS().equals(nms))
				return type;
		throw new IllegalArgumentException();
	}
}
