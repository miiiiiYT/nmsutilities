package io.github.riesenpilz.nmsUtilities.nbt;

import java.util.List;

import org.bukkit.craftbukkit.libs.org.apache.commons.lang3.ArrayUtils;

public class NBTTagLongArray extends NBTBase {

	public static final NBTType TYPE = NBTType.LONG_ARRAY;

	private long[] data;

	public NBTTagLongArray(net.minecraft.server.v1_16_R3.NBTTagLongArray nms) {
		super(TYPE);
		data = nms.getLongs();
	}

	public static NBTTagLongArray getNBTTagLongArrayOf(net.minecraft.server.v1_16_R3.NBTTagLongArray nms) {
		return new NBTTagLongArray(nms);
	}

	public NBTTagLongArray(long[] data) {
		super(TYPE);
		this.data = data;
	}

	public NBTTagLongArray(List<Long> list) {
		this(listToArray(list));
	}

	public static long[] listToArray(List<Long> list) {
		long[] longArray = new long[list.size()];

		for (int i = 0; i < list.size(); ++i) {
			Long b = (Long) list.get(i);
			longArray[i] = b == null ? 0 : b;
		}
		return longArray;
	}

	public int size() {
		return data.length;
	}

	@Override
	public long[] getData() {
		return data;
	}

	public NBTTagLong get(int i) {
		return new NBTTagLong(data[i]);
	}

	public NBTTagLong set(int i, NBTTagLong nbtTagLong) {
		long oldLong = data[i];

		data[i] = nbtTagLong.getData();
		return new NBTTagLong(oldLong);
	}

	public void add(int i, NBTTagLong nbtTagLong) {
		data = ArrayUtils.add(data, i, nbtTagLong.getData());
	}

	public void clear() {
		data = new long[0];
	}

	public NBTTagLong remove(int i) {
		long j = data[i];

		this.data = ArrayUtils.remove(data, i);
		return new NBTTagLong(j);
	}

	@Override
	public net.minecraft.server.v1_16_R3.NBTTagLongArray getNMS() {
		return new net.minecraft.server.v1_16_R3.NBTTagLongArray(data);
	}

	@Override
	protected NBTTagLongArray clone() {
		return new NBTTagLongArray(ArrayUtils.clone(data));
	}

}
