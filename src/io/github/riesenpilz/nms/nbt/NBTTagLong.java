package io.github.riesenpilz.nms.nbt;

public class NBTTagLong extends NBTBase {

	public static final NBTType TYPE = NBTType.LONG;

	private final long data;

	public NBTTagLong(net.minecraft.server.v1_16_R3.NBTBase nms) throws IllegalAccessException {
		super(TYPE);
		if ((nms.getTypeId() != TYPE.getTypeId()))
			throw new IllegalAccessException("The type of the NBTBase has to be a long, but is a "
					+ super.getType().name().toLowerCase().replace("_", " "));
		data = ((net.minecraft.server.v1_16_R3.NBTTagLong) nms).asInt();
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
}
