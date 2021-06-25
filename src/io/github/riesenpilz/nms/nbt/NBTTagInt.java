package io.github.riesenpilz.nms.nbt;

public class NBTTagInt extends NBTBase {

	public static final NBTType TYPE = NBTType.INT;

	private final int data;

	public NBTTagInt(net.minecraft.server.v1_16_R3.NBTBase nms) throws IllegalAccessException {
		super(TYPE);
		if ((nms.getTypeId() != TYPE.getTypeId()))
			throw new IllegalAccessException("The type of the NBTBase has to be a integer, but is a "
					+ super.getType().name().toLowerCase().replace("_", " "));
		data = ((net.minecraft.server.v1_16_R3.NBTTagInt) nms).asInt();
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
}
