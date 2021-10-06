package io.github.riesenpilz.nmsUtilities.packet;

import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.World;

import io.github.riesenpilz.nmsUtilities.inventory.ItemStack;
import io.github.riesenpilz.nmsUtilities.nbt.NBTTag;
import io.netty.buffer.ByteBuf;
import net.minecraft.server.v1_16_R3.BlockPosition;
import net.minecraft.server.v1_16_R3.MinecraftKey;

public class PacketDataSerializer {
	private final net.minecraft.server.v1_16_R3.PacketDataSerializer dataSerializer;

	public PacketDataSerializer(net.minecraft.server.v1_16_R3.PacketDataSerializer nms) {
		dataSerializer = nms;
	}

	public PacketDataSerializer(ByteBuf byteBuf) {
		dataSerializer = new net.minecraft.server.v1_16_R3.PacketDataSerializer(byteBuf);
	}

	public net.minecraft.server.v1_16_R3.PacketDataSerializer getNMS() {
		return dataSerializer;
	}

	public Byte getNextByte() {
		return dataSerializer.readByte();
	}

	public int getNextInt() {
		return dataSerializer.i();
	}

	public boolean getNextBoolean() {
		return dataSerializer.readBoolean();
	}

	public <T extends Enum<T>> T getNextEnumValue(Class<T> enumClass) {
		return dataSerializer.a(enumClass);
	}

	@SuppressWarnings("deprecation")
	public NamespacedKey getNextNamespacedKey() {
		final MinecraftKey nmsKey = dataSerializer.p();
		return new NamespacedKey(nmsKey.getNamespace(), nmsKey.getKey());
	}

	public ItemStack getNextItemStack() {
		return ItemStack.getItemStackOf(dataSerializer.n());
	}

	public NBTTag getNextNBTTag() {
		return NBTTag.getNBTTagOf(dataSerializer.l());
	}

	public Location getNextBlockPosition(World world) {
		BlockPosition pos = dataSerializer.e();
		return new Location(world, pos.getX(), pos.getY(), pos.getZ());
	}

	public String getNextString(int length) {
		return dataSerializer.e(length);
	}

	public float getNextFloat() {
		return dataSerializer.readFloat();
	}

	public double getNextDouble() {
		return dataSerializer.readDouble();
	}
	
	public long getNextLong() {
		return dataSerializer.readLong();
	}
	
	public short getNextShort() {
		return dataSerializer.readShort();
	}
	
	public void setNextBoolean(boolean nextBoolean) {
		dataSerializer.writeBoolean(nextBoolean);
	}

	public void setNextByte(byte nextByte) {
		dataSerializer.writeByte(nextByte);
	}

	public void setNextItemStack(ItemStack itemStack) {
		dataSerializer.a(itemStack.getNMS());
	}

	public void setNextInt(int nextInt) {
		dataSerializer.d(nextInt);
	}

	public void setNextNamespacedKey(NamespacedKey key) {
		dataSerializer.a(new MinecraftKey(key.getNamespace(), key.getKey()));
	}

	public void setNextEnumValue(Enum<?> value) {
		dataSerializer.a(value);
	}

	public void setNextBlockPosition(Location location) {
		dataSerializer.a(new BlockPosition(location.getX(), location.getY(), location.getZ()));
	}

	public void setNextString(String string) {
		dataSerializer.a(string);
	}

	public void setNextString(String string, int length) {
		setNextString(string.substring(0, length));
	}

	public void setNextFloat(float nextFloat) {
		dataSerializer.writeFloat(nextFloat);
	}

	public void setNextDouble(double nextDouble) {
		dataSerializer.writeDouble(nextDouble);
	}

	public void setNextLong(long nextLong) {
		dataSerializer.writeLong(nextLong);
	}

	public void setNextShort(short nextShort) {
		dataSerializer.writeShort(nextShort);
	}

	public ByteBuf getByteBuf() {
		return dataSerializer;
	}
}
