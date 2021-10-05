package io.github.riesenpilz.nmsUtilities.nbt;

import javax.annotation.Nonnull;

import org.apache.commons.lang.Validate;

public class NBTTagByte extends NBTBase {

	public static final NBTType TYPE = NBTType.BYTE;

	private final byte data;

	protected NBTTagByte(net.minecraft.server.v1_16_R3.NBTTagByte nms) {
		super(TYPE);
		data = nms.asByte();
	}

	public static NBTTagByte getNBTTagByteOf(net.minecraft.server.v1_16_R3.NBTTagByte nms) {
		Validate.notNull(nms);
		return new NBTTagByte(nms);
	}

	public NBTTagByte(byte data) {
		super(TYPE);
		this.data = data;
	}

	@Nonnull
	@Override
	public Byte getData() {
		return data;
	}

	@Override
	public net.minecraft.server.v1_16_R3.NBTTagByte getNMS() {
		return net.minecraft.server.v1_16_R3.NBTTagByte.a(data);
	}

	@Override
	protected NBTTagByte clone() {
		return new NBTTagByte(data);
	}
}
