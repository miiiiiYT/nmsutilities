package io.github.riesenpilz.nmsUtilities.nbt;

import org.apache.commons.lang.Validate;
import org.apache.commons.lang.builder.ToStringBuilder;

public class NBTTagLong extends NBTBase {

	public static final NBTType TYPE = NBTType.LONG;

	private final long data;

	public NBTTagLong(net.minecraft.server.v1_16_R3.NBTTagLong nms) {
		super(TYPE);
		Validate.notNull(nms);
		data = nms.asLong();
	}

	public static NBTTagLong getNBTTagLongOf(net.minecraft.server.v1_16_R3.NBTTagLong nms) {
		return new NBTTagLong(nms);
	}

	public NBTTagLong(long data) {
		super(TYPE);
		this.data = data;
	}

	@Override
	public Long getData() {
		return data;
	}

	@Override
	public net.minecraft.server.v1_16_R3.NBTTagLong getNMS() {
		return net.minecraft.server.v1_16_R3.NBTTagLong.a(data);
	}

	@Override
	public NBTTagLong clone() {
		return new NBTTagLong(data);
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("data", data).toString();
	}
}
