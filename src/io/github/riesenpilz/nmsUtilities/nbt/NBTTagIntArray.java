package io.github.riesenpilz.nmsUtilities.nbt;

import java.util.List;

import org.apache.commons.lang.Validate;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.bukkit.craftbukkit.libs.org.apache.commons.lang3.ArrayUtils;

public class NBTTagIntArray extends NBTBase {

	public static final NBTType TYPE = NBTType.INT_ARRAY;

	private int[] data;

	public NBTTagIntArray(net.minecraft.server.v1_16_R3.NBTTagIntArray nms) {
		super(TYPE);
		Validate.notNull(nms);
		data = nms.getInts();
	}

	public static NBTTagIntArray getNBTTagIntArrayOf(net.minecraft.server.v1_16_R3.NBTTagIntArray nms) {
		return new NBTTagIntArray(nms);
	}

	public NBTTagIntArray(int[] data) {
		super(TYPE);
		Validate.notNull(data);
		this.data = data;
	}

	public NBTTagIntArray(List<Integer> list) {
		this(listToArray(list));
	}

	private static int[] listToArray(List<Integer> list) {
		Validate.notNull(list);

		int[] intArray = new int[list.size()];

		for (int i = 0; i < list.size(); ++i) {
			Integer b = (Integer) list.get(i);
			intArray[i] = b == null ? 0 : b;
		}
		return intArray;
	}

	public int size() {
		return data.length;
	}

	@Override
	public int[] getData() {
		return data;
	}

	public NBTTagInt get(int i) {
		return new NBTTagInt(data[i]);
	}

	public NBTTagInt set(int i, NBTTagInt nbtTagInt) {
		Validate.notNull(nbtTagInt);

		int oldInt = data[i];

		data[i] = nbtTagInt.getData();
		return new NBTTagInt(oldInt);
	}

	public void add(int i, NBTTagInt nbtTagInt) {
		Validate.notNull(nbtTagInt);
		this.data = ArrayUtils.add(data, i, nbtTagInt.getData());
	}

	public void clear() {
		data = new int[0];
	}

	public NBTTagInt remove(int i) {
		int j = data[i];

		this.data = ArrayUtils.remove(data, i);
		return new NBTTagInt(j);
	}

	@Override
	public net.minecraft.server.v1_16_R3.NBTTagIntArray getNMS() {
		return new net.minecraft.server.v1_16_R3.NBTTagIntArray(data);
	}

	@Override
	public NBTTagIntArray clone() {
		return new NBTTagIntArray(ArrayUtils.clone(data));
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("data", data).toString();
	}
}
