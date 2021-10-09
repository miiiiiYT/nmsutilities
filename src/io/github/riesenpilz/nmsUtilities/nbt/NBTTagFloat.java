package io.github.riesenpilz.nmsUtilities.nbt;

import org.apache.commons.lang.Validate;
import org.apache.commons.lang.builder.ToStringBuilder;

public class NBTTagFloat extends NBTBase {

	public static final NBTType TYPE = NBTType.FLOAT;

	private final float data;

	public NBTTagFloat(net.minecraft.server.v1_16_R3.NBTTagFloat nms) {
		super(TYPE);
		Validate.notNull(nms);
		data = nms.asFloat();
	}

	public static NBTTagFloat getNBTTagFloatOf(net.minecraft.server.v1_16_R3.NBTTagFloat nms) {
		return new NBTTagFloat(nms);
	}

	public NBTTagFloat(float data) {
		super(TYPE);
		this.data = data;
	}

	@Override
	public Float getData() {
		return data;
	}

	@Override
	public net.minecraft.server.v1_16_R3.NBTTagFloat getNMS() {
		return net.minecraft.server.v1_16_R3.NBTTagFloat.a(data);
	}

	@Override
	public NBTTagFloat clone() {
		return new NBTTagFloat(data);
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("data", data).toString();
	}
}
