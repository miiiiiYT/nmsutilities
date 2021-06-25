package io.github.riesenpilz.nms.nbt;

public class NBTTagFloat extends NBTBase {

	public static final NBTType TYPE = NBTType.FLOAT;

	private final float data;

	public NBTTagFloat(net.minecraft.server.v1_16_R3.NBTBase nms) throws IllegalAccessException {
		super(TYPE);
		if ((nms.getTypeId() != TYPE.getTypeId()))
			throw new IllegalAccessException("The type of the NBTBase has to be a float, but is a "
					+ super.getType().name().toLowerCase().replace("_", " "));
		data = ((net.minecraft.server.v1_16_R3.NBTTagFloat) nms).asFloat();
	}

	public NBTTagFloat(float data) {
		super(TYPE);
		this.data = data;
	}

	@Override
	public Float getData() {
		return data;
	}

	@Override
	public net.minecraft.server.v1_16_R3.NBTTagFloat getNMS() {
		return net.minecraft.server.v1_16_R3.NBTTagFloat.a(data);
	}
}
