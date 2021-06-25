package io.github.riesenpilz.nms.nbt;

public class NBTTagString extends NBTBase {

	public static final NBTType TYPE = NBTType.STRING;

	private final String data;

	public NBTTagString(net.minecraft.server.v1_16_R3.NBTBase nms) throws IllegalAccessException {
		super(TYPE);
		if (!(nms.getTypeId() == TYPE.getTypeId()))
			throw new IllegalAccessException("The type of the NBTBase has to be a string, but is a "
					+ super.getType().name().toLowerCase().replace("_", " "));
		data = ((net.minecraft.server.v1_16_R3.NBTTagString) nms).asString();
	}

	public NBTTagString(String data) {
		super(TYPE);
		this.data = data;
	}

	public String getData() {
		return data;
	}

	@Override
	public net.minecraft.server.v1_16_R3.NBTTagString getNMS() {
		return net.minecraft.server.v1_16_R3.NBTTagString.a(data);
	}
}
