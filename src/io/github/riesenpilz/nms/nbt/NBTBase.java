package io.github.riesenpilz.nms.nbt;

import net.minecraft.server.v1_16_R3.NBTTagCompound;

public abstract class NBTBase {
	private final NBTType type;

	public NBTBase(NBTType type) {
		this.type = type;
	}

	public NBTType getType() {
		return type;
	}
	public abstract Object getData();
	public abstract net.minecraft.server.v1_16_R3.NBTBase getNMS();

	public static NBTBase get(net.minecraft.server.v1_16_R3.NBTBase nbtBase) {
		try {
			switch (NBTType.getType(nbtBase.getTypeId())) {
			case BYTE:
				return new NBTTagByte(nbtBase);
			case BYTE_ARRAY:
				return new NBTTagByteArray(nbtBase);
			case DOUBLE:
				return new NBTTagDouble(nbtBase);
			case END:
				return new NBTTagEnd();
			case FLOAT:
				return new NBTTagFloat(nbtBase);
			case INT:
				return new NBTTagInt(nbtBase);
			case INT_ARRAY:
				return new NBTTagIntArray(nbtBase);
			case LONG:
				return new NBTTagLong(nbtBase);
			case LONG_ARRAY:
				return new NBTTagLongArray(nbtBase);
			case NBT_TAG:
				return new NBTTag((NBTTagCompound) nbtBase);
			case NBT_TAG_LIST:
				return new NBTTagList((net.minecraft.server.v1_16_R3.NBTTagList)nbtBase);
			case SHORT:
				return new NBTTagShort(nbtBase);
			case STRING:
				return new NBTTagString(nbtBase);
			}
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return null;
	}
}
