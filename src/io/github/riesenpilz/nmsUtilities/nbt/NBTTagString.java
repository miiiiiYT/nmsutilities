package io.github.riesenpilz.nmsUtilities.nbt;

import org.apache.commons.lang.Validate;
import org.apache.commons.lang.builder.ToStringBuilder;

public class NBTTagString extends NBTBase {

	public static final NBTType TYPE = NBTType.STRING;

	private final String data;

	public NBTTagString(net.minecraft.server.v1_16_R3.NBTTagString nms) {
		super(TYPE);
		Validate.notNull(nms);
		data = nms.asString();
	}

	public static NBTTagString getNBTTagStringOf(net.minecraft.server.v1_16_R3.NBTTagString nms) {
		return new NBTTagString(nms);
	}

	public NBTTagString(String data) {
		super(TYPE);
		Validate.notNull(data);
		this.data = data;
	}

	public String getData() {
		return data;
	}

	@Override
	public net.minecraft.server.v1_16_R3.NBTTagString getNMS() {
		return net.minecraft.server.v1_16_R3.NBTTagString.a(data);
	}

	@Override
	public NBTTagString clone() {
		return new NBTTagString(new String(data));
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("data", data).toString();
	}
}
