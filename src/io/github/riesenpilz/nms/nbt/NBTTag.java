package io.github.riesenpilz.nms.nbt;

import java.util.Iterator;
import java.util.Set;

import com.mojang.brigadier.exceptions.CommandSyntaxException;

import net.minecraft.server.v1_16_R3.MojangsonParser;
import net.minecraft.server.v1_16_R3.NBTBase;
import net.minecraft.server.v1_16_R3.NBTTagCompound;

public class NBTTag implements Iterable<String> {
    private final NBTTagCompound nbtTagCompound;

    public NBTTag(NBTTagCompound nbtTagCompound) {
        this.nbtTagCompound = nbtTagCompound;
    }

    public NBTTag(String string) {
        try {
            nbtTagCompound = MojangsonParser.parse(string);
        } catch (CommandSyntaxException e) {
            throw new IllegalArgumentException("no nbtTagCompound: " + string, e.getCause());
        }
    }

    public NBTTag() {
        this.nbtTagCompound = new NBTTagCompound();
    }

    public NBTTagCompound getNBTTagCompound() {
        return nbtTagCompound;
    }

    public void setString(String key, String value) {
        nbtTagCompound.setString(key, value);
    }

    public String getString(String key) {
        return nbtTagCompound.getString(key);
    }

    public void setInt(String key, int value) {
        nbtTagCompound.setInt(key, value);
    }

    public int getInt(String key) {
        return nbtTagCompound.getInt(key);
    }

    public void setBoolean(String key, boolean value) {
        nbtTagCompound.setBoolean(key, value);
    }

    public boolean getBoolean(String key) {
        return nbtTagCompound.getBoolean(key);
    }

    public void setDouble(String key, double value) {
        nbtTagCompound.setDouble(key, value);
    }

    public double getDouble(String key) {
        return nbtTagCompound.getDouble(key);
    }

    public void setFloat(String key, float value) {
        nbtTagCompound.setFloat(key, value);
    }

    public float getFloat(String key) {
        return nbtTagCompound.getFloat(key);
    }

    public void setNBTTag(String key, NBTTag value) {
        nbtTagCompound.set(key, value.getNBTTagCompound());
    }

    public NBTTag getNBTTag(String key) {
        return nbtTagCompound.get(key) instanceof NBTTagCompound ? new NBTTag((NBTTagCompound) nbtTagCompound.get(key))
                : new NBTTag();
    }

    public void setNBTTagList(String key, NBTTagList value) {
        nbtTagCompound.set(key, value.getNBTTagList());
    }

    public NBTTagList getNBTTagList(String key) {
        return nbtTagCompound.get(key) instanceof net.minecraft.server.v1_16_R3.NBTTagList
                ? new NBTTagList((net.minecraft.server.v1_16_R3.NBTTagList) nbtTagCompound.get(key))
                : new NBTTagList();
    }

    public Set<String> getKeys() {
        return nbtTagCompound.getKeys();
    }

    public void remove(String key) {
        nbtTagCompound.remove(key);
    }

    public boolean hasKey(String key) {
        return nbtTagCompound.hasKey(key);
    }

    @Override
    public NBTTag clone() {
        return new NBTTag(nbtTagCompound.clone());
    }

    @Override
    public String toString() {
        return nbtTagCompound.toString();
    }

    @Override
    public Iterator<String> iterator() {
        return getKeys().iterator();
    }

    public boolean isEmpty() {
        return nbtTagCompound.isEmpty();
    }

    public void setByte(String key, byte value) {
        nbtTagCompound.setByte(key, value);
    }

    public byte getByte(String key) {
        return nbtTagCompound.getByte(key);
    }

    public void set(String key, NBTBase value) {
        nbtTagCompound.set(key, value);
    }

    public NBTBase get(String key) {
        return nbtTagCompound.get(key);
    }
}
