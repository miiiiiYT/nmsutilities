package io.github.riesenpilz.nms.nbt;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Iterator;

import net.minecraft.server.v1_16_R3.NBTBase;
import net.minecraft.server.v1_16_R3.NBTTagCompound;
import net.minecraft.server.v1_16_R3.NBTTagDouble;
import net.minecraft.server.v1_16_R3.NBTTagFloat;
import net.minecraft.server.v1_16_R3.NBTTagInt;
import net.minecraft.server.v1_16_R3.NBTTagString;

public class NBTTagList implements Iterable<NBTBase> {
    private final net.minecraft.server.v1_16_R3.NBTTagList nbtTagList;

    public NBTTagList(net.minecraft.server.v1_16_R3.NBTTagList nbtTagList) {
        this.nbtTagList = nbtTagList;
    }

    public NBTTagList() {
        this.nbtTagList = new net.minecraft.server.v1_16_R3.NBTTagList();
    }

    public net.minecraft.server.v1_16_R3.NBTTagList getNBTTagList() {
        return nbtTagList;
    }

    public void setString(int position, String value) {
        try {
            Class<NBTTagString> clazz = NBTTagString.class;
            Constructor<NBTTagString> constructor = clazz.getDeclaredConstructor(String.class);
            constructor.setAccessible(true);
            nbtTagList.set(position, constructor.newInstance(value));
        } catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException
                | IllegalArgumentException | InvocationTargetException ignored) {
        }
    }

    public String getString(int position) {
        return nbtTagList.getString(position);
    }

    public void setInt(int position, int value) {
        try {
            Class<NBTTagInt> clazz = NBTTagInt.class;
            Constructor<NBTTagInt> constructor = clazz.getDeclaredConstructor(int.class);
            constructor.setAccessible(true);
            nbtTagList.set(position, constructor.newInstance(value));
        } catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException
                | IllegalArgumentException | InvocationTargetException ignored) {
        }
    }

    public int getInt(int position) {
        return nbtTagList.e(position);
    }

    public void setDouble(int position, double value) {
        try {
            Class<NBTTagDouble> clazz = NBTTagDouble.class;
            Constructor<NBTTagDouble> constructor = clazz.getDeclaredConstructor(double.class);
            constructor.setAccessible(true);
            nbtTagList.set(position, constructor.newInstance(value));
        } catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException
                | IllegalArgumentException | InvocationTargetException ignored) {
        }
    }

    public double getDouble(int position) {
        return nbtTagList.h(position);
    }

    public void setFloat(int position, float value) {
        try {
            Class<NBTTagFloat> clazz = NBTTagFloat.class;
            Constructor<NBTTagFloat> constructor = clazz.getDeclaredConstructor(float.class);
            constructor.setAccessible(true);
            nbtTagList.set(position, constructor.newInstance(value));
        } catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException
                | IllegalArgumentException | InvocationTargetException ignored) {
        }

    }

    public void addNBTTag(NBTTag nbtTag) {
        nbtTagList.add(nbtTag.getNBTTagCompound());
    }

    public void addNBTTagList(NBTTagList nbtTagList) {
        this.nbtTagList.add(nbtTagList.getNBTTagList());
    }

    public float getFloat(int position) {
        return nbtTagList.i(position);
    }

    public void setNBTTag(int position, NBTTag value) {
        nbtTagList.set(position, value.getNBTTagCompound());
    }

    public NBTTag getNBTTag(int position) {
        return nbtTagList.get(position) instanceof NBTTagCompound
                ? new NBTTag((NBTTagCompound) nbtTagList.get(position))
                : new NBTTag();
    }

    public void setNBTTagList(int position, NBTTagList value) {
        nbtTagList.set(position, value.getNBTTagList());
    }

    public NBTTagList getNBTTagList(int position) {
        return nbtTagList.get(position) instanceof net.minecraft.server.v1_16_R3.NBTTagList
                ? new NBTTagList((net.minecraft.server.v1_16_R3.NBTTagList) nbtTagList.get(position))
                : new NBTTagList();
    }

    public void remove(int position) {
        nbtTagList.remove(position);
    }

    public void remove(Object o) {
        nbtTagList.remove(o);
    }

    public boolean contains(Object o) {
        return nbtTagList.contains(o);
    }

    public NBTTagList clone() {
        return new NBTTagList(nbtTagList.clone());
    }

    @Override
    public String toString() {
        return nbtTagList.toString();
    }

    @Override
    public Iterator<NBTBase> iterator() {
        return nbtTagList.iterator();
    }

    public int size() {
        return nbtTagList.size();
    }
    public boolean isEmpty() {
        return nbtTagList.isEmpty();
    }
}
