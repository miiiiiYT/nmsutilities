package io.github.riesenpilz.nms.nbt;

public class NBTTagEnd extends NBTBase {
	
	public NBTTagEnd() {
		super(NBTType.END);
	}

	@Override
	public net.minecraft.server.v1_16_R3.NBTTagEnd getNMS() {
		return net.minecraft.server.v1_16_R3.NBTTagEnd.b;
	}

	@Override
	public Object getData() {
		return null;
	}
}
