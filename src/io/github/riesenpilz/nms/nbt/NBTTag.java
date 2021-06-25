package io.github.riesenpilz.nms.nbt;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import com.mojang.brigadier.exceptions.CommandSyntaxException;

import io.github.riesenpilz.nms.reflections.Field;
import net.minecraft.server.v1_16_R3.MojangsonParser;
import net.minecraft.server.v1_16_R3.NBTCompressedStreamTools;
import net.minecraft.server.v1_16_R3.NBTTagCompound;

public class NBTTag extends NBTBase implements Iterable<String> {
	private final Map<String, NBTBase> contents;

	public NBTTag() {
		super(NBTType.NBT_TAG);
		contents = new HashMap<>();
	}

	public NBTTag(Map<String, NBTBase> contents) {
		super(NBTType.NBT_TAG);
		this.contents = contents;
	}

	public NBTTag(NBTTagCompound nms) {
		super(NBTType.NBT_TAG);
		contents = new HashMap<>();
		@SuppressWarnings("unchecked")
		Map<String, net.minecraft.server.v1_16_R3.NBTBase> nmsContents = Field.get(nms, "map", Map.class);
		for (String s : nmsContents.keySet()) {
			contents.put(s, NBTBase.get(nmsContents.get(s)));
		}
	}

	public NBTTag(InputStream inputstream) throws IOException {
		this(NBTCompressedStreamTools.a(inputstream));
	}

	public NBTTag(String s) throws CommandSyntaxException {
		this(MojangsonParser.parse(s));
	}

	public NBTTagCompound getNMS() {
		NBTTagCompound comp = new NBTTagCompound();
		for (String s : contents.keySet()) {
			comp.set(s, contents.get(s).getNMS());
		}
		return comp;
	}

	@Override
	public Iterator<String> iterator() {
		return contents.keySet().iterator();
	}

	// END
	public boolean isEnd(String key) {
		return hasKey(key) && contents.get(key).getType().equals(NBTType.END);
	}

	// BYTE
	public void setByte(String key, byte value) {
		set(key, new NBTTagByte(value));
	}

	public byte getByte(String key) {
		return ((NBTTagByte) contents.get(key)).getData();
	}

	public boolean isByte(String key) {
		return hasKey(key) && contents.get(key).getType().equals(NBTType.BYTE);
	}

	// SHORT
	public void setShort(String key, short value) {
		set(key, new NBTTagShort(value));
	}

	public short getShort(String key) {
		return ((NBTTagShort) contents.get(key)).getData();
	}

	public boolean isShort(String key) {
		return hasKey(key) && contents.get(key).getType().equals(NBTType.SHORT);
	}

	// INT
	public void setInt(String key, int value) {
		set(key, new NBTTagInt(value));
	}

	public int getInt(String key) {
		return ((NBTTagInt) contents.get(key)).getData();
	}

	public boolean isInt(String key) {
		return hasKey(key) && contents.get(key).getType().equals(NBTType.INT);
	}

	// LONG
	public void setLong(String key, long value) {
		set(key, new NBTTagLong(value));
	}

	public long getLong(String key) {
		return ((NBTTagLong) contents.get(key)).getData();
	}

	public boolean isLong(String key) {
		return hasKey(key) && contents.get(key).getType().equals(NBTType.LONG);
	}

	// FLOAT
	public void setFloat(String key, float value) {
		set(key, new NBTTagFloat(value));
	}

	public float getFloat(String key) {
		return ((NBTTagFloat) contents.get(key)).getData();
	}

	public boolean isFloat(String key) {
		return hasKey(key) && contents.get(key).getType().equals(NBTType.FLOAT);
	}

	// DOUBLE
	public void setDouble(String key, double value) {
		set(key, new NBTTagDouble(value));
	}

	public double getDouble(String key) {
		return ((NBTTagDouble) contents.get(key)).getData();
	}

	public boolean isDouble(String key) {
		return hasKey(key) && contents.get(key).getType().equals(NBTType.DOUBLE);
	}

	// BYTE_ARRAY
	public void setByteArray(String key, byte[] value) {
		set(key, new NBTTagByteArray(value));
	}

	public byte[] getByteArray(String key) {
		return ((NBTTagByteArray) contents.get(key)).getData();
	}

	public boolean isByteArray(String key) {
		return hasKey(key) && contents.get(key).getType().equals(NBTType.BYTE_ARRAY);
	}

	// STRING
	public void setString(String key, String value) {
		set(key, new NBTTagString(value));
	}

	public String getString(String key) {
		return ((NBTTagString) contents.get(key)).getData();
	}

	public boolean isString(String key) {
		return hasKey(key) && contents.get(key).getType().equals(NBTType.STRING);
	}

	// NBT_TAG_LIST
	public void setNBTTagList(String key, NBTTagList value) {
		set(key, value);
	}

	public NBTTagList getNBTTagList(String key) {
		return ((NBTTagList) contents.get(key));
	}

	public NBTTagList getOrDefNBTTagList(String key, NBTTagList def) {
		final NBTTagList nbtTagList = (NBTTagList) contents.getOrDefault(key, def);
		if (!hasKey(key))
			set(key, nbtTagList);
		return nbtTagList;
	}

	public boolean isNBTTagList(String key) {
		return hasKey(key) && contents.get(key).getType().equals(NBTType.NBT_TAG_LIST);
	}

	// NBT_TAG
	public void setNBTTag(String key, NBTTag value) {
		set(key, value);
	}

	public NBTTag getNBTTag(String key) {
		return ((NBTTag) contents.get(key));
	}

	public NBTTag getOrDefNBTTag(String key, NBTTag def) {
		final NBTTag nbtTag = (NBTTag) contents.getOrDefault(key, def);
		if (nbtTag != contents.get(key))
			set(key, nbtTag);
		return nbtTag;
	}

	public boolean isNBTTag(String key) {
		return hasKey(key) && contents.get(key).getType().equals(NBTType.NBT_TAG);
	}

	// INT_ARRAY
	public void setIntArray(String key, int[] value) {
		set(key, new NBTTagIntArray(value));
	}

	public int[] getIntArray(String key) {
		return ((NBTTagIntArray) contents.get(key)).getData();
	}

	public boolean isIntArray(String key) {
		return hasKey(key) && contents.get(key).getType().equals(NBTType.INT_ARRAY);
	}

	// LONG_ARRAY
	public void setLongArray(String key, long[] value) {
		set(key, new NBTTagLongArray(value));
	}

	public long[] getLongArray(String key) {
		return ((NBTTagLongArray) contents.get(key)).getData();
	}

	public boolean isLongArray(String key) {
		return hasKey(key) && contents.get(key).getType().equals(NBTType.LONG_ARRAY);
	}

	// BOOLEAN
	public void setBoolean(String key, boolean value) {
		set(key, new NBTTagByte(value ? (byte) 1 : (byte) 0));
	}

	public boolean getBoolean(String key) {
		return ((NBTTagByte) contents.get(key)).getData() == 1;
	}

	public boolean isBoolean(String key) {
		final NBTBase nbtBase = contents.get(key);
		return hasKey(key) && nbtBase.getType().equals(NBTType.BYTE)
				&& (((NBTTagByte) nbtBase).getData() == 1 || ((NBTTagByte) nbtBase).getData() == 0);
	}

	public Set<String> getKeys() {
		return contents.keySet();
	}

	public void remove(String key) {
		contents.remove(key);
	}

	public boolean hasKey(String key) {
		return getKeys().contains(key);
	}

	@Override
	public NBTTag clone() {
		return new NBTTag(getNMS().clone());
	}

	@Override
	public String toString() {
		return getNMS().toString();
	}

	public boolean isEmpty() {
		return contents.isEmpty();
	}

	public void set(String key, NBTBase value) {
		contents.put(key, value);
	}

	public NBTBase get(String key) {
		return contents.get(key);
	}

	public void setALL(NBTTag tag) {
		for (String key : tag.getKeys()) {
			NBTBase value = tag.get(key);
			set(key, value);
		}
	}

	@Override
	public Map<String, NBTBase> getData() {
		return contents;
	}
}
