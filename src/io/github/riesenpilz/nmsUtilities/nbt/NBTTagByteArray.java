package io.github.riesenpilz.nmsUtilities.nbt;

import java.util.List;

import org.apache.commons.lang.Validate;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.bukkit.craftbukkit.libs.org.apache.commons.lang3.ArrayUtils;

public class NBTTagByteArray extends NBTBase {

	public static final NBTType TYPE = NBTType.BYTE_ARRAY;

	private byte[] data;

	public NBTTagByteArray(net.minecraft.server.v1_16_R3.NBTTagByteArray nms) {
		super(TYPE);
		Validate.notNull(nms);
		data = nms.getBytes();
	}

	public static NBTTagByteArray getNBTTagByteArrayOf(net.minecraft.server.v1_16_R3.NBTTagByteArray nms) {
		Validate.notNull(nms);
		return new NBTTagByteArray(nms);
	}

	public NBTTagByteArray(byte[] data) {
		super(TYPE);
		Validate.notNull(data);
		this.data = data;
	}

	public NBTTagByteArray(List<Byte> list) {
		this(listToArray(list));
	}

	private static byte[] listToArray(List<Byte> list) {
		Validate.notNull(list);
		
		byte[] byteArray = new byte[list.size()];

		for (int i = 0; i < list.size(); ++i) {
			Byte b = (Byte) list.get(i);
			byteArray[i] = b == null ? 0 : b;
		}
		return byteArray;
	}

	public int size() {
		return data.length;
	}

	@Override
	public byte[] getData() {
		return data;
	}

	public NBTTagByte get(int i) {
		return new NBTTagByte(data[i]);
	}

	public NBTTagByte set(int i, NBTTagByte nbtTagByte) {
		Validate.notNull(nbtTagByte);
		
		byte oldByte = data[i];

		data[i] = nbtTagByte.getData();
		return new NBTTagByte(oldByte);
	}

	public void add(int i, NBTTagByte nbtTagByte) {
		Validate.notNull(nbtTagByte);
		
		data = ArrayUtils.add(data, i, nbtTagByte.getData());
	}

	public void clear() {
		data = new byte[0];
	}

	public NBTTagByte remove(int i) {
		byte j = data[i];

		this.data = ArrayUtils.remove(data, i);
		return new NBTTagByte(j);
	}

	@Override
	public net.minecraft.server.v1_16_R3.NBTTagByteArray getNMS() {
		return new net.minecraft.server.v1_16_R3.NBTTagByteArray(data);
	}

	@Override
	public NBTTagByteArray clone() {
		return new NBTTagByteArray(ArrayUtils.clone(data));
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("data", data).toString();
	}
}
