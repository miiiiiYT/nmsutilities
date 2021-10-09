package io.github.riesenpilz.nmsUtilities.entity;

import java.util.Optional;
import java.util.OptionalInt;
import java.util.UUID;

import org.apache.commons.lang.Validate;

import com.google.common.collect.ImmutableMap;

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

/**
 * Represents a {@link DataWatcher.Item}. Only used by packets.
 * 
 * @see PacketPlayOutEntityMetadataEvent
 * @param <T> the type of the value
 */
public class DataWatcherItem<T> {

	private int index;
	private T value;

	public DataWatcherItem(int index, T value) {
		this.index = index;
		Validate.notNull(value);
		this.value = value;
	}

	protected DataWatcherItem(DataWatcher.Item<T> nms) {
		Validate.notNull(nms);
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
		Validate.notNull(value);
		this.value = value;
	}

	public DataWatcher.Item<T> getNMS() {
		return new DataWatcher.Item<>(new DataWatcherObject<>(index, getDataWatcherSerializer()), value);
	}

	private static ImmutableMap<Class<?>, DataWatcherSerializer<?>> serializer = new ImmutableMap.Builder<Class<?>, DataWatcherSerializer<?>>()
			.put(Byte.class, DataWatcherRegistry.a).put(Integer.class, DataWatcherRegistry.b)
			.put(Float.class, DataWatcherRegistry.c).put(String.class, DataWatcherRegistry.d)
			.put(IChatBaseComponent.class, DataWatcherRegistry.e).put(ItemStack.class, DataWatcherRegistry.g)
			.put(Boolean.class, DataWatcherRegistry.i).put(ParticleParam.class, DataWatcherRegistry.j)
			.put(Vector3f.class, DataWatcherRegistry.k).put(BlockPosition.class, DataWatcherRegistry.l)
			.put(EnumDirection.class, DataWatcherRegistry.n).put(NBTTagCompound.class, DataWatcherRegistry.p)
			.put(VillagerData.class, DataWatcherRegistry.q).put(OptionalInt.class, DataWatcherRegistry.r)
			.put(EntityPose.class, DataWatcherRegistry.s).build();

	@SuppressWarnings("unchecked")
	private DataWatcherSerializer<T> getDataWatcherSerializer() {
		if (value.getClass().equals(Optional.class)) {
			Validate.isTrue(!((Optional<?>) value).isEmpty());
			final Class<?> clazz = ((Optional<?>) value).get().getClass();
			if (clazz.equals(IChatBaseComponent.class))
				return (DataWatcherSerializer<T>) DataWatcherRegistry.f;
			if (clazz.equals(IBlockData.class))
				return (DataWatcherSerializer<T>) DataWatcherRegistry.h;
			if (clazz.getClass().equals(BlockPosition.class))
				return (DataWatcherSerializer<T>) DataWatcherRegistry.m;
			if (clazz.getClass().equals(UUID.class))
				return (DataWatcherSerializer<T>) DataWatcherRegistry.o;
		}
		return (DataWatcherSerializer<T>) serializer.get(value.getClass());
	}

}
