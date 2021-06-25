package io.github.riesenpilz.nms.nbt;

public class NBTTagDouble extends NBTBase {

	public static final NBTType TYPE = NBTType.DOUBLE;

	private final double data;

	public NBTTagDouble(net.minecraft.server.v1_16_R3.NBTBase nms) throws IllegalAccessException {
		super(TYPE);
		if ((nms.getTypeId() != TYPE.getTypeId()))
			throw new IllegalAccessException("The type of the NBTBase has to be a double, but is a "
					+ super.getType().name().toLowerCase().replace("_", " "));
		data = ((net.minecraft.server.v1_16_R3.NBTTagDouble) nms).asDouble();
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
}
