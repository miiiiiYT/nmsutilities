package io.github.riesenpilz.nmsUtilities.nbt;

import java.util.List;
import java.util.Map;

import javax.annotation.Nullable;

public enum NBTType {
	END((byte) 0, null), BYTE((byte) 1, byte.class), SHORT((byte) 2, short.class), INT((byte) 3, int.class),
	LONG((byte) 4, long.class), FLOAT((byte) 5, float.class), DOUBLE((byte) 6, double.class),
	BYTE_ARRAY((byte) 7, byte[].class), STRING((byte) 8, String.class), NBT_TAG_LIST((byte) 9, List.class),
	NBT_TAG((byte) 10, Map.class), INT_ARRAY((byte) 11, int.class), LONG_ARRAY((byte) 12, long[].class);

	private final byte typeId;

	@Nullable
	private final Class<?> clazz;

	private NBTType(byte typeId, Class<?> clazz) {
		this.typeId = typeId;
		this.clazz = clazz;
	}

	public static NBTType getType(byte typeId) {
		for (NBTType type : values())
			if (type.typeId == typeId)
				return type;
		throw new IllegalArgumentException(typeId + " is not a valid typeID.");
	}

	public byte getTypeId() {
		return typeId;
	}

	@Nullable
	public Class<?> getClazz() {
		return clazz;
	}
}
