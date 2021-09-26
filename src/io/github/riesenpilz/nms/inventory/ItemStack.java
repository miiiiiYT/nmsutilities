package io.github.riesenpilz.nms.inventory;

import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.craftbukkit.libs.org.apache.commons.codec.binary.Base64;
import org.bukkit.craftbukkit.v1_16_R3.inventory.CraftItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;

import io.github.riesenpilz.nms.nbt.NBTBase;
import io.github.riesenpilz.nms.nbt.NBTTag;
import io.github.riesenpilz.nms.nbt.NBTTagList;
import io.github.riesenpilz.nms.nbt.NBTType;
import io.github.riesenpilz.nms.reflections.Field;

public class ItemStack {
	private net.minecraft.server.v1_16_R3.ItemStack itemStack;

	public ItemStack(org.bukkit.inventory.ItemStack bukkitItemStack) {
		itemStack = CraftItemStack.asNMSCopy(bukkitItemStack);
	}

	public ItemStack(Material material) {
		itemStack = CraftItemStack.asNMSCopy(new org.bukkit.inventory.ItemStack(material));
	}

	public ItemStack(net.minecraft.server.v1_16_R3.ItemStack nms) {
		if (nms == null)
			nms = net.minecraft.server.v1_16_R3.ItemStack.b;
		itemStack = nms;
	}

	public net.minecraft.server.v1_16_R3.ItemStack getNMS() {
		return itemStack;
	}

	public NBTTag getTag() {
		return NBTTag.getNBTTagOf(getNMS().getOrCreateTag());
	}

	public void setTag(NBTTag nbtTag) {
		getNMS().setTag(nbtTag.getNMS());
	}

	public boolean isEnchanted() {
		return getNMS().hasEnchantments();
	}

	public void setEnchanted(boolean glow) {
		if (glow && !getNMS().hasEnchantments())
			getTag().setNBTTagList("ench", new NBTTagList());
		else if (!glow && getNMS().hasEnchantments())
			getNMS().removeTag("ench");
	}

	public NBTTag toTag() {
		NBTTag tag = new NBTTag();
		tag.setNBTTag("tag", getTag());
		tag.setString("id", getItemStack().getType().name());
		tag.setInt("count", getItemStack().getAmount());
		return tag;
	}

	public void setMaterial(Material material) {
		final org.bukkit.inventory.ItemStack itemStack2 = getItemStack();
		itemStack2.setType(material);
		setItemStack(itemStack2);
	}

	public void setAmount(int amount) {
		final org.bukkit.inventory.ItemStack itemStack2 = getItemStack();
		itemStack2.setAmount(amount);
		setItemStack(itemStack2);
	}

	public int getAmount() {
		return getItemStack().getAmount();
	}

	public Material getMaterial() {
		return getItemStack().getType();
	}

	public static ItemStack getItemStack(NBTTag nbtTag) {
		ItemStack itemStack = new ItemStack(Material.getMaterial(nbtTag.getString("id")));
		itemStack.getNMS().setCount(nbtTag.getInt("count"));
		itemStack.setTag(nbtTag.getNBTTag("tag"));
		return itemStack;
	}

	public ItemMeta getItemMeta() {
		return getItemStack().getItemMeta();
	}

	public void setItemMeta(ItemMeta itemMeta) {
		final org.bukkit.inventory.ItemStack itemStack2 = getItemStack();
		itemStack2.setItemMeta(itemMeta);
		setItemStack(itemStack2);
	}

	public org.bukkit.inventory.ItemStack getItemStack() {
		return CraftItemStack.asBukkitCopy(getNMS());
	}

	public void setItemStack(org.bukkit.inventory.ItemStack itemStack) {
		this.itemStack = CraftItemStack.asNMSCopy(itemStack);
	}

	public void dropNaturally(Location location) {
		location.getWorld().dropItemNaturally(location, getItemStack());
	}

	public void drop(Location location) {
		location.getWorld().dropItem(location, getItemStack());
	}

	private static final Base64 base64 = new Base64();

	public void setSkullTexture(String url) {
		if (!getMaterial().equals(Material.PLAYER_HEAD))
			return;
		GameProfile profile = new GameProfile(UUID.randomUUID(), null);
		byte[] encodedData = base64.encode(String.format("{textures:{SKIN:{url:\"%s\"}}}", url).getBytes());
		profile.getProperties().put("textures", new Property("textures", new String(encodedData)));
		ItemMeta headMeta = getItemMeta();
		Field.set(getItemMeta(), "profile", profile);
		setItemMeta(headMeta);
	}

	public static boolean isItemStackNBT(NBTBase nbtItemStack) {
		if (!nbtItemStack.getType().equals(NBTType.NBT_TAG))
			return false;
		NBTTag nbtTagItemStack = (NBTTag) nbtItemStack;
		return nbtTagItemStack.hasKeyWithValueType("id", NBTType.INT)
				&& nbtTagItemStack.hasKeyWithValueType("count", NBTType.INT)
				&& nbtTagItemStack.hasKeyWithValueType("tag", NBTType.NBT_TAG);
	}
}
