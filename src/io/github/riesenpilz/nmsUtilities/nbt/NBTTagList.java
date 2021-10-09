package io.github.riesenpilz.nmsUtilities.nbt;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Nullable;

import org.apache.commons.lang.Validate;
import org.apache.commons.lang.builder.ToStringBuilder;

public class NBTTagList extends NBTBase implements Iterable<NBTBase> {

	public static final NBTType TYPE = NBTType.NBT_TAG_LIST;

	/**
	 * Can only contain one type of NBTBase!
	 */
	private final List<NBTBase> data;

	protected NBTTagList(@Nullable net.minecraft.server.v1_16_R3.NBTTagList nms) {
		super(TYPE);
		data = new ArrayList<>();
		if (nms != null)
			for (net.minecraft.server.v1_16_R3.NBTBase base : nms)
				data.add(NBTBase.getNBTBaseOf(base));
	}

	public static NBTTagList getNBTTagListOf(@Nullable net.minecraft.server.v1_16_R3.NBTTagList nms) {
		return new NBTTagList(nms);
	}

	public NBTTagList(List<NBTBase> list) {
		super(TYPE);
		Validate.notNull(list);
		data = list;
	}

	public NBTTagList() {
		this(new ArrayList<>());
	}

	public int size() {
		return data.size();
	}

	@Override
	public List<NBTBase> getData() {
		return data;
	}

	public NBTBase get(int i) {
		return data.get(i);
	}

	public NBTBase set(int i, NBTBase nbtBase) {
		return data.set(i, nbtBase);
	}

	public void add(int i, NBTBase nbtBase) {
		data.add(i, nbtBase);
	}

	public void clear() {
		data.clear();
	}

	public NBTBase remove(int i) {
		return data.remove(i);
	}

	@Override
	public net.minecraft.server.v1_16_R3.NBTTagList getNMS() {
		final net.minecraft.server.v1_16_R3.NBTTagList nms = new net.minecraft.server.v1_16_R3.NBTTagList();
		if (size() == 0)
			return nms;
		final NBTType type = data.get(0).getType();
		for (NBTBase base : data)
			if (base.getType() == type)
				nms.add(base.getNMS());
		return nms;
	}

	@Override
	public Iterator<NBTBase> iterator() {
		return data.iterator();
	}

	public void add(NBTBase base) {
		data.add(base);
	}

	@Override
	public NBTTagList clone() {
		List<NBTBase> newData = new ArrayList<>();
		Collections.copy(data, newData);
		return new NBTTagList(newData);
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("data", data).toString();
	}
}
