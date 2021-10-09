package io.github.riesenpilz.nmsUtilities.nbt;

import org.apache.commons.lang.Validate;
import org.apache.commons.lang.builder.ToStringBuilder;

public class NBTTagInt extends NBTBase {

	public static final NBTType TYPE = NBTType.INT;

	private final int data;

	public NBTTagInt(net.minecraft.server.v1_16_R3.NBTTagInt nms) {
		super(TYPE);
		Validate.notNull(nms);
		data = nms.asInt();
	}

	public static NBTTagInt getNBTTagIntOf(net.minecraft.server.v1_16_R3.NBTTagInt nms) {
		return new NBTTagInt(nms);
	}

	public NBTTagInt(int data) {
		super(TYPE);
		this.data = data;
	}

	@Override
	public Integer getData() {
		return data;
	}

	@Override
	public net.minecraft.server.v1_16_R3.NBTTagInt getNMS() {
		return net.minecraft.server.v1_16_R3.NBTTagInt.a(data);
	}

	@Override
	public NBTTagInt clone() {
		return new NBTTagInt(data);
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("data", data).toString();
	}
}
