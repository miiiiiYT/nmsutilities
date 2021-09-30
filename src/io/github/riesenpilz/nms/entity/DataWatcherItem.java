package io.github.riesenpilz.nms.entity;

import java.util.Optional;
import java.util.OptionalInt;
import java.util.UUID;

import org.apache.commons.lang.Validate;

import net.minecraft.server.v1_16_R3.BlockPosition;
import net.minecraft.server.v1_16_R3.DataWatcher;
import net.minecraft.server.v1_16_R3.DataWatcherObject;
import net.minecraft.server.v1_16_R3.DataWatcherRegistry;
import net.minecraft.server.v1_16_R3.DataWatcherSerializer;
import net.minecraft.server.v1_16_R3.EntityPose;
import net.minecraft.server.v1_16_R3.EnumDirection;
import net.minecraft.server.v1_16_R3.IBlockData;
import net.minecraft.server.v1_16_R3.IChatBaseComponent;
import net.minecraft.server.v1_16_R3.ItemStack;
import net.minecraft.server.v1_16_R3.NBTTagCompound;
import net.minecraft.server.v1_16_R3.ParticleParam;
import net.minecraft.server.v1_16_R3.Vector3f;
import net.minecraft.server.v1_16_R3.VillagerData;

public class DataWatcherItem<T> {

	private int index;
	private T value;

	public DataWatcherItem(int index, T value) {
		super();
		this.index = index;
		this.value = value;
	}

	protected DataWatcherItem(DataWatcher.Item<T> nms) {
		index = nms.a().a();
		value = nms.b();
	}

	public static DataWatcherItem<?> getDataWatcherItemFrom(DataWatcher.Item<?> nms) {
		return new DataWatcherItem<>(nms);
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public T getValue() {
		return value;
	}

	public void setValue(T value) {
		this.value = value;
	}

	public DataWatcher.Item<T> getNMS() {
		return new DataWatcher.Item<>(new DataWatcherObject<>(index, getDataWatcherSerializer()), value);
	}

	@SuppressWarnings("unchecked")
	private DataWatcherSerializer<T> getDataWatcherSerializer() {
		if (value.getClass().equals(Byte.class))
			return (DataWatcherSerializer<T>) DataWatcherRegistry.a;
		if (value.getClass().equals(Integer.class))
			return (DataWatcherSerializer<T>) DataWatcherRegistry.b;
		if (value.getClass().equals(Float.class))
			return (DataWatcherSerializer<T>) DataWatcherRegistry.c;
		if (value.getClass().equals(String.class))
			return (DataWatcherSerializer<T>) DataWatcherRegistry.d;
		if (value.getClass().equals(IChatBaseComponent.class))
			return (DataWatcherSerializer<T>) DataWatcherRegistry.e;
		if (value.getClass().equals(Optional.class)) {//
			Validate.isTrue(!((Optional<?>) value).isEmpty());
			final Class<?> clazz = ((Optional<?>) value).get().getClass();
			if (clazz.equals(IChatBaseComponent.class))
				return (DataWatcherSerializer<T>) DataWatcherRegistry.f;
			if (clazz.equals(IBlockData.class))
				return (DataWatcherSerializer<T>) DataWatcherRegistry.h;
			if (value.getClass().equals(BlockPosition.class))
				return (DataWatcherSerializer<T>) DataWatcherRegistry.m;
			if (value.getClass().equals(UUID.class))
				return (DataWatcherSerializer<T>) DataWatcherRegistry.o;
		}
		if (value.getClass().equals(ItemStack.class))
			return (DataWatcherSerializer<T>) DataWatcherRegistry.g;
		if (value.getClass().equals(Boolean.class))
			return (DataWatcherSerializer<T>) DataWatcherRegistry.i;
		if (value.getClass().equals(ParticleParam.class))
			return (DataWatcherSerializer<T>) DataWatcherRegistry.j;
		if (value.getClass().equals(Vector3f.class))
			return (DataWatcherSerializer<T>) DataWatcherRegistry.k;
		if (value.getClass().equals(BlockPosition.class))
			return (DataWatcherSerializer<T>) DataWatcherRegistry.l;
		if (value.getClass().equals(EnumDirection.class))
			return (DataWatcherSerializer<T>) DataWatcherRegistry.n;
		if (value.getClass().equals(NBTTagCompound.class))
			return (DataWatcherSerializer<T>) DataWatcherRegistry.p;
		if (value.getClass().equals(VillagerData.class))
			return (DataWatcherSerializer<T>) DataWatcherRegistry.q;
		if (value.getClass().equals(OptionalInt.class))
			return (DataWatcherSerializer<T>) DataWatcherRegistry.r;
		if (value.getClass().equals(EntityPose.class))
			return (DataWatcherSerializer<T>) DataWatcherRegistry.s;
		return null;
	}

}
