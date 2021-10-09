package io.github.riesenpilz.nmsUtilities.nbt;

import org.apache.commons.lang.Validate;
import org.apache.commons.lang.builder.ToStringBuilder;

public class NBTTagShort extends NBTBase {

	public static final NBTType TYPE = NBTType.SHORT;

	private final short data;

	public NBTTagShort(net.minecraft.server.v1_16_R3.NBTTagShort nms) {
		super(TYPE);
		Validate.notNull(nms);
		data = nms.asShort();
	}

	public static NBTTagShort getNBTTagShortOf(net.minecraft.server.v1_16_R3.NBTTagShort nms) {
		return new NBTTagShort(nms);
	}

	public NBTTagShort(short data) {
		super(TYPE);
		this.data = data;
	}

	@Override
	public Short getData() {
		return data;
	}

	@Override
	public net.minecraft.server.v1_16_R3.NBTTagShort getNMS() {
		return net.minecraft.server.v1_16_R3.NBTTagShort.a(data);
	}

	@Override
	public NBTTagShort clone() {
		return new NBTTagShort(data);
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("data", data).toString();
	}
}
