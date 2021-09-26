package io.github.riesenpilz.nms.nbt;

public class NBTTagString extends NBTBase {

	public static final NBTType TYPE = NBTType.STRING;

	private final String data;

	public NBTTagString(net.minecraft.server.v1_16_R3.NBTTagString nms) {
		super(TYPE);
		data = nms.asString();
	}

	public static NBTTagString getNBTTagStringOf(net.minecraft.server.v1_16_R3.NBTTagString nms) {
		return new NBTTagString(nms);
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

	@Override
	protected NBTTagString clone() {
		return new NBTTagString(new String(data));
	}

}
