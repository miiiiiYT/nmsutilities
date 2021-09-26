package io.github.riesenpilz.nms.nbt;

public class NBTTagLong extends NBTBase {

	public static final NBTType TYPE = NBTType.LONG;

	private final long data;

	public NBTTagLong(net.minecraft.server.v1_16_R3.NBTTagLong nms) {
		super(TYPE);
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
	protected NBTTagLong clone() {
		return new NBTTagLong(data);
	}

	
}
