package io.github.riesenpilz.nmsUtilities.nbt;

import org.apache.commons.lang.builder.ToStringBuilder;

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

	@Override
	public NBTTagEnd clone() {
		return new NBTTagEnd();
	}
	@Override
	public String toString() {
		return new ToStringBuilder(this).toString();
	}
}
