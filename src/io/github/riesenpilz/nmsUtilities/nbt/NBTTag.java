package io.github.riesenpilz.nmsUtilities.nbt;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.annotation.Nullable;

import org.apache.commons.lang.Validate;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.google.common.collect.Maps;
import com.mojang.brigadier.exceptions.CommandSyntaxException;

import io.github.riesenpilz.nmsUtilities.reflections.Field;
import net.minecraft.server.v1_16_R3.MojangsonParser;
import net.minecraft.server.v1_16_R3.NBTCompressedStreamTools;
import net.minecraft.server.v1_16_R3.NBTTagCompound;

public class NBTTag extends NBTBase implements Iterable<Entry<String, NBTBase>> {
	private final HashMap<String, NBTBase> contents;

	/**
	 * Constructs an empty NBTTag
	 */
	public NBTTag() {
		super(NBTType.NBT_TAG);
		contents = new HashMap<>();
	}

	/**
	 * Constructs an NBTTag with the given contents
	 * 
	 * @param contents
	 */
	public NBTTag(HashMap<String, NBTBase> contents) {
		super(NBTType.NBT_TAG);
		Validate.notNull(contents);

		this.contents = contents;
	}

	/**
	 * Gets the contents of the given {@link NBTTagCompound} and converts it to
	 * contents for this {@link NBTTag}
	 * 
	 * @param nms the NBTTagCompound
	 */
	protected NBTTag(@Nullable NBTTagCompound nms) {
		super(NBTType.NBT_TAG);

		contents = new HashMap<>();
		if (nms == null)
			return;
		@SuppressWarnings("unchecked")
		Map<String, net.minecraft.server.v1_16_R3.NBTBase> nmsContents = Field.get(nms, "map", Map.class);
		for (Entry<String, net.minecraft.server.v1_16_R3.NBTBase> entry : nmsContents.entrySet())
			if (entry.getValue() != null)
				contents.put(entry.getKey(), NBTBase.getNBTBaseOf(entry.getValue()));
	}

	/**
	 * Converts the given {@link NBTTagCompound} to a NBTTag
	 * 
	 * @param nms the NBTTagCompound to convert
	 * @return the new NBTTag
	 */
	public static NBTTag getNBTTagOf(@Nullable NBTTagCompound nms) {
		return new NBTTag(nms);
	}

	/**
	 * Converts a NBTTag inputStream to a NBTTag
	 * 
	 * @param inputStream the inputStream to convert
	 * @return the new NBTTag
	 * @throws IOException
	 */
	public static NBTTag getNBTTagOf(InputStream inputStream) throws IOException {
		Validate.notNull(inputStream);
		return new NBTTag(NBTCompressedStreamTools.a(inputStream));
	}

	/**
	 * Converts a NBTTag string to a NBTTag
	 * 
	 * @param nbtTagString
	 * @throws CommandSyntaxException
	 * @see NBTTag#toString()
	 */
	public NBTTag(String nbtTagString) throws CommandSyntaxException {
		this(MojangsonParser.parse(nbtTagString));
	}

	/**
	 * Gets the NMS variant of this instance
	 */
	public NBTTagCompound getNMS() {
		NBTTagCompound nms = new NBTTagCompound();
		for (Entry<String, NBTBase> entry : this)
			if (entry.getValue() != null)
				nms.set(entry.getKey(), entry.getValue().getNMS());
		return nms;
	}

	@Override
	public Iterator<Entry<String, NBTBase>> iterator() {
		return contents.entrySet().iterator();
	}

	// END
	public boolean isEnd(String key) {
		return hasKey(key) && get(key).is(NBTType.END);
	}

	// BYTE
	public void setByte(String key, byte value) {
		set(key, new NBTTagByte(value));
	}

	public byte getByte(String key) {
		Validate.isTrue(get(key) instanceof NBTTagByte);
		return (byte) get(key).getData();
	}

	public boolean isByte(String key) {
		return hasKeyWithValueType(key, NBTType.BYTE);
	}

	// SHORT
	public void setShort(String key, short value) {
		set(key, new NBTTagShort(value));
	}

	public short getShort(String key) {
		Validate.isTrue(get(key) instanceof NBTTagShort);
		return (short) get(key).getData();
	}

	public boolean isShort(String key) {
		return hasKeyWithValueType(key, NBTType.SHORT);
	}

	// INT
	public void setInt(String key, int value) {
		set(key, new NBTTagInt(value));
	}

	public int getInt(String key) {
		Validate.isTrue(get(key) instanceof NBTTagInt);
		return (int) get(key).getData();
	}

	public boolean isInt(String key) {
		return hasKeyWithValueType(key, NBTType.INT);
	}

	// LONG
	public void setLong(String key, long value) {
		set(key, new NBTTagLong(value));
	}

	public long getLong(String key) {
		Validate.isTrue(get(key) instanceof NBTTagLong);
		return (long) get(key).getData();
	}

	public boolean isLong(String key) {
		return hasKeyWithValueType(key, NBTType.LONG);
	}

	// FLOAT
	public void setFloat(String key, float value) {
		set(key, new NBTTagFloat(value));
	}

	public float getFloat(String key) {
		Validate.isTrue(get(key) instanceof NBTTagFloat);
		return (float) get(key).getData();
	}

	public boolean isFloat(String key) {
		return hasKeyWithValueType(key, NBTType.FLOAT);
	}

	// DOUBLE
	public void setDouble(String key, double value) {
		set(key, new NBTTagDouble(value));
	}

	public double getDouble(String key) {
		Validate.isTrue(get(key) instanceof NBTTagDouble);
		return (double) get(key).getData();
	}

	public boolean isDouble(String key) {
		return hasKeyWithValueType(key, NBTType.DOUBLE);
	}

	// BYTE_ARRAY
	public void setByteArray(String key, byte[] value) {
		set(key, new NBTTagByteArray(value));
	}

	public byte[] getByteArray(String key) {
		return ((NBTTagByteArray) get(key)).getData();
	}

	public boolean isByteArray(String key) {
		return hasKeyWithValueType(key, NBTType.BYTE_ARRAY);
	}

	// STRING
	public void setString(String key, String value) {
		set(key, new NBTTagString(value));
	}

	public String getString(String key) {
		return ((NBTTagString) get(key)).getData();
	}

	public boolean isString(String key) {
		return hasKeyWithValueType(key, NBTType.STRING);
	}

	// NBT_TAG_LIST
	public void setNBTTagList(String key, NBTTagList value) {
		set(key, value);
	}

	public NBTTagList getNBTTagList(String key) {
		return ((NBTTagList) get(key));
	}

	public NBTTagList getOrDefNBTTagList(String key, NBTTagList def) {
		final NBTTagList nbtTagList = (NBTTagList) contents.getOrDefault(key, def);
		if (!hasKey(key))
			set(key, nbtTagList);
		return nbtTagList;
	}

	public boolean isNBTTagList(String key) {
		return hasKeyWithValueType(key, NBTType.NBT_TAG_LIST);
	}

	// NBT_TAG
	public void setNBTTag(String key, NBTTag value) {
		set(key, value);
	}

	public NBTTag getNBTTag(String key) {
		return ((NBTTag) get(key));
	}

	public NBTTag getOrDefNBTTag(String key, NBTTag def) {
		final NBTTag nbtTag = (NBTTag) contents.getOrDefault(key, def);
		if (nbtTag != get(key))
			set(key, nbtTag);
		return nbtTag;
	}

	public boolean isNBTTag(String key) {
		return hasKeyWithValueType(key, NBTType.NBT_TAG);
	}

	// INT_ARRAY
	public void setIntArray(String key, int[] value) {
		set(key, new NBTTagIntArray(value));
	}

	public int[] getIntArray(String key) {
		return ((NBTTagIntArray) get(key)).getData();
	}

	public boolean isIntArray(String key) {
		return hasKeyWithValueType(key, NBTType.INT_ARRAY);
	}

	// LONG_ARRAY
	public void setLongArray(String key, long[] value) {
		set(key, new NBTTagLongArray(value));
	}

	public long[] getLongArray(String key) {
		return ((NBTTagLongArray) get(key)).getData();
	}

	public boolean isLongArray(String key) {
		return hasKeyWithValueType(key, NBTType.LONG_ARRAY);
	}

	// BOOLEAN
	public void setBoolean(String key, boolean value) {
		set(key, new NBTTagByte(value ? (byte) 1 : (byte) 0));
	}

	public boolean getBoolean(String key) {
		Validate.isTrue(get(key) instanceof NBTTagByte);
		return (byte) get(key).getData() == 1;
	}

	public boolean isBoolean(String key) {
		final NBTBase nbtBase = getOrEnd(key);
		return nbtBase.is(NBTType.BYTE) && ((byte) nbtBase.getData() == 1 || (byte) nbtBase.getData() == 0);
	}

	public Set<String> getKeys() {
		return contents.keySet();
	}

	public void remove(String key) {
		contents.remove(key);
	}

	public boolean hasKey(String key) {
		return contents.containsKey(key);
	}

	/**
	 * Tests if the value of the given key is from the given type.
	 * 
	 * @param key  the key
	 * @param type the type to test
	 * @return true if the value of the given key is from the given type
	 */
	public boolean hasKeyWithValueType(String key, NBTType type) {
		return getOrEnd(key).is(type);
	}

	@Override
	public NBTTag clone() {
		HashMap<String, NBTBase> newContents = new HashMap<>();
		Maps.transformValues(contents, NBTBase::clone);
		return new NBTTag(newContents);
	}

	public boolean isEmpty() {
		return contents.isEmpty();
	}

	public void set(String key, NBTBase value) {
		contents.put(key, value);
	}

	@Nullable
	public NBTBase get(String key) {
		return contents.get(key);
	}

	public NBTBase getOrDef(String key, NBTBase def) {
		return hasKey(key) ? get(key) : def;
	}

	public NBTBase getOrEnd(String key) {
		return getOrDef(key, new NBTTagEnd());
	}

	public void setALL(NBTTag tag) {
		for (String key : tag.getKeys()) {
			NBTBase value = tag.get(key);
			set(key, value);
		}
	}

	@Override
	public HashMap<String, NBTBase> getData() {
		return contents;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("data", contents).toString();
	}
}
