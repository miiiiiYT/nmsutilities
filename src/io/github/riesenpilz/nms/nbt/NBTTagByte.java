package io.github.riesenpilz.nms.nbt;

public class NBTTagByte extends NBTBase {

	public static final NBTType TYPE = NBTType.BYTE;

	private final byte data;

	public NBTTagByte(net.minecraft.server.v1_16_R3.NBTBase nms) throws IllegalAccessException {
		super(TYPE);
		if (!(nms.getTypeId() == TYPE.getTypeId()))
			throw new IllegalAccessException("The type of the NBTBase has to be a byte, but is a "
					+ super.getType().name().toLowerCase().replace("_", " "));
		data = ((net.minecraft.server.v1_16_R3.NBTTagByte) nms).asByte();
	}

	public NBTTagByte(byte data) {
		super(TYPE);
		this.data = data;
	}

	@Override
	public Byte getData() {
		return data;
	}

	@Override
	public net.minecraft.server.v1_16_R3.NBTTagByte getNMS() {
		return net.minecraft.server.v1_16_R3.NBTTagByte.a(data);
	}
}
