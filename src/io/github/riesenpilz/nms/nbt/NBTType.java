package io.github.riesenpilz.nms.nbt;

public enum NBTType {
	END((byte) 0), BYTE((byte) 1), SHORT((byte) 2), INT((byte) 3), LONG((byte) 4), FLOAT((byte) 5), DOUBLE((byte) 6),
	BYTE_ARRAY((byte) 7), STRING((byte) 8), NBT_TAG_LIST((byte) 9), NBT_TAG((byte) 10), INT_ARRAY((byte) 11),
	LONG_ARRAY((byte) 12);

	private final byte typeId;

	private NBTType(byte typeId) {
		this.typeId = typeId;
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
}
