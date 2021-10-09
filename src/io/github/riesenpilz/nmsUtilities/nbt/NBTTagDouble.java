package io.github.riesenpilz.nmsUtilities.nbt;

import org.apache.commons.lang.Validate;
import org.apache.commons.lang.builder.ToStringBuilder;

public class NBTTagDouble extends NBTBase {

	public static final NBTType TYPE = NBTType.DOUBLE;

	private final double data;

	public NBTTagDouble(net.minecraft.server.v1_16_R3.NBTTagDouble nms) {
		super(TYPE);
		Validate.notNull(nms);
		data = nms.asDouble();
	}

	public static NBTTagDouble getNBTTagDoubleOf(net.minecraft.server.v1_16_R3.NBTTagDouble nms) {
		Validate.notNull(nms);
		return new NBTTagDouble(nms);
	}

	public NBTTagDouble(double data) {
		super(TYPE);
		this.data = data;
	}

	@Override
	public Double getData() {
		return data;
	}

	@Override
	public net.minecraft.server.v1_16_R3.NBTTagDouble getNMS() {
		return net.minecraft.server.v1_16_R3.NBTTagDouble.a(data);
	}

	@Override
	public NBTTagDouble clone() {
		return new NBTTagDouble(data);
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("data", data).toString();
	}
}
