package io.github.riesenpilz.nmsUtilities.nbt;

import org.apache.commons.lang.Validate;

import net.minecraft.server.v1_16_R3.NBTTagCompound;

public abstract class NBTBase implements Cloneable {

	/**
	 * the type of the stored data
	 */
	private final NBTType type;

	/**
	 * Constructs a new NBTBase
	 * 
	 * @param type the type of the stored data
	 */
	public NBTBase(NBTType type) {
		this.type = type;
	}

	/**
	 * Gets the type of the NBTBase
	 * 
	 * @return the type of the NBTBase
	 */
	public NBTType getType() {
		return type;
	}
	
	public boolean is(NBTType type) {
		return this.type == type;
	}
	
	
	/**
	 * Gets the stored data. Should be a instance of {@code getType().getClazz()}.
	 * 
	 * @return the stored data
	 */
	public abstract Object getData();

	/**
	 * Gets the NMS version of the NBT class
	 * 
	 * @return the NMS version
	 */
	public abstract net.minecraft.server.v1_16_R3.NBTBase getNMS();

	/**
	 * Converts NMS NBT base to the corresponding wrapper
	 * 
	 * @param nms the NBT base to convert
	 * @return the NBT base wrapper
	 */
	public static NBTBase getNBTBaseOf(net.minecraft.server.v1_16_R3.NBTBase nms) {
		Validate.notNull(nms);
		switch (NBTType.getType(nms.getTypeId())) {
		case BYTE:
			return NBTTagByte.getNBTTagByteOf((net.minecraft.server.v1_16_R3.NBTTagByte) nms);
		case BYTE_ARRAY:
			return NBTTagByteArray.getNBTTagByteArrayOf((net.minecraft.server.v1_16_R3.NBTTagByteArray) nms);
		case DOUBLE:
			return NBTTagDouble.getNBTTagDoubleOf((net.minecraft.server.v1_16_R3.NBTTagDouble) nms);
		case END:
			return new NBTTagEnd();
		case FLOAT:
			return NBTTagFloat.getNBTTagFloatOf((net.minecraft.server.v1_16_R3.NBTTagFloat) nms);
		case INT:
			return NBTTagInt.getNBTTagIntOf((net.minecraft.server.v1_16_R3.NBTTagInt) nms);
		case INT_ARRAY:
			return NBTTagIntArray.getNBTTagIntArrayOf((net.minecraft.server.v1_16_R3.NBTTagIntArray) nms);
		case LONG:
			return NBTTagLong.getNBTTagLongOf((net.minecraft.server.v1_16_R3.NBTTagLong) nms);
		case LONG_ARRAY:
			return NBTTagLongArray.getNBTTagLongArrayOf((net.minecraft.server.v1_16_R3.NBTTagLongArray) nms);
		case NBT_TAG:
			return NBTTag.getNBTTagOf((NBTTagCompound) nms);
		case NBT_TAG_LIST:
			return NBTTagList.getNBTTagListOf((net.minecraft.server.v1_16_R3.NBTTagList) nms);
		case SHORT:
			return NBTTagShort.getNBTTagShortOf((net.minecraft.server.v1_16_R3.NBTTagShort) nms);
		case STRING:
			return NBTTagString.getNBTTagStringOf((net.minecraft.server.v1_16_R3.NBTTagString) nms);
		default:
			throw new IllegalArgumentException();
		}
	}

	@Override
	abstract protected NBTBase clone();
}
