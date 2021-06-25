package io.github.riesenpilz.nms.nbt;

public class NBTTagShort extends NBTBase {

	public static final NBTType TYPE = NBTType.SHORT;

	private final short data;

	public NBTTagShort(net.minecraft.server.v1_16_R3.NBTBase nms) throws IllegalAccessException {
		super(TYPE);
		if ((nms.getTypeId() != TYPE.getTypeId()))
			throw new IllegalAccessException("The type of the NBTBase has to be a short, but is a "
					+ super.getType().name().toLowerCase().replace("_", " "));
		data = ((net.minecraft.server.v1_16_R3.NBTTagShort) nms).asShort();
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
}
