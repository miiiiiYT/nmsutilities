package io.github.riesenpilz.nmsUtilities.inventory;

import java.util.Collection;
import java.util.UUID;

import javax.annotation.Nullable;

import org.apache.commons.lang.Validate;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.craftbukkit.libs.org.apache.commons.codec.binary.Base64;
import org.bukkit.craftbukkit.v1_16_R3.inventory.CraftItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import com.google.common.collect.Iterables;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;

import io.github.riesenpilz.nmsUtilities.nbt.NBTBase;
import io.github.riesenpilz.nmsUtilities.nbt.NBTTag;
import io.github.riesenpilz.nmsUtilities.nbt.NBTTagList;
import io.github.riesenpilz.nmsUtilities.nbt.NBTType;
import io.github.riesenpilz.nmsUtilities.reflections.Field;

public class ItemStack {
	private net.minecraft.server.v1_16_R3.ItemStack nms;

	protected ItemStack(@Nullable org.bukkit.inventory.ItemStack bukkit) {
		nms = nms == null ? net.minecraft.server.v1_16_R3.ItemStack.b : CraftItemStack.asNMSCopy(bukkit);
	}

	public ItemStack(@Nullable Material material) {
		nms = CraftItemStack.asNMSCopy(new org.bukkit.inventory.ItemStack(material == null ? Material.AIR : material));
	}

	protected ItemStack(@Nullable net.minecraft.server.v1_16_R3.ItemStack nms) {
		this.nms = nms == null ? net.minecraft.server.v1_16_R3.ItemStack.b : nms;
	}

	public static ItemStack getItemStackOf(@Nullable org.bukkit.inventory.ItemStack bukkit) {
		return new ItemStack(bukkit);
	}

	public static ItemStack getItemStackOf(@Nullable net.minecraft.server.v1_16_R3.ItemStack nms) {
		return new ItemStack(nms);
	}

	public net.minecraft.server.v1_16_R3.ItemStack getNMS() {
		return nms;
	}

	public NBTTag getTag() {
		return NBTTag.getNBTTagOf(getNMS().getOrCreateTag());
	}

	public void setTag(NBTTag nbtTag) {
		Validate.notNull(nbtTag);
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

	public NBTTag toNBTTag() {
		NBTTag tag = new NBTTag();
		tag.setNBTTag("tag", getTag());
		tag.setString("id", getBukkit().getType().name());
		tag.setInt("count", getBukkit().getAmount());
		return tag;
	}

	public void setMaterial(Material material) {
		final org.bukkit.inventory.ItemStack bukkit = getBukkit();
		bukkit.setType(material);
		changeItemStack(bukkit);
	}

	public void setAmount(int amount) {
		nms.setCount(amount);
	}

	public int getAmount() {
		return nms.getCount();
	}

	public Material getMaterial() {
		return getBukkit().getType();
	}

	public static ItemStack getItemStack(NBTTag nbtTag) {
		ItemStack itemStack = new ItemStack(Material.getMaterial(nbtTag.getString("id")));
		itemStack.getNMS().setCount(nbtTag.getInt("count"));
		itemStack.setTag(nbtTag.getNBTTag("tag"));
		return itemStack;
	}

	public ItemMeta getItemMeta() {
		return getBukkit().getItemMeta();
	}

	public void setItemMeta(ItemMeta itemMeta) {
		final org.bukkit.inventory.ItemStack itemStack2 = getBukkit();
		itemStack2.setItemMeta(itemMeta);
		changeItemStack(itemStack2);
	}

	public org.bukkit.inventory.ItemStack getBukkit() {
		return CraftItemStack.asBukkitCopy(getNMS());
	}

	private void changeItemStack(org.bukkit.inventory.ItemStack bukkit) {
		this.nms = CraftItemStack.asNMSCopy(bukkit);
	}

	public void dropNaturally(Location location) {
		location.getWorld().dropItemNaturally(location, getBukkit());
	}

	public void drop(Location location) {
		location.getWorld().dropItem(location, getBukkit());
	}

	private static final Base64 base64 = new Base64();

	public void setSkullTexture(String url) {
		Validate.isTrue(getMaterial().equals(Material.PLAYER_HEAD));
		GameProfile profile = new GameProfile(UUID.randomUUID(), null);
		byte[] encodedData = base64.encode(String.format("{textures:{SKIN:{url:\"%s\"}}}", url).getBytes());
		profile.getProperties().put("textures", new Property("textures", new String(encodedData)));
		SkullMeta headMeta = (SkullMeta) getItemMeta();
		Field.set(headMeta, "profile", profile);
		setItemMeta(headMeta);
	}

	public String getSkullTexture() {
		Validate.isTrue(getMaterial().equals(Material.PLAYER_HEAD));

		GameProfile profile = Field.get((SkullMeta) getItemMeta(), "profile", GameProfile.class);
		Collection<Property> properties = profile.getProperties().get("textures");
		Property property = Iterables.getFirst(properties, null);
		Validate.notNull(property, "Skull doesnt has a texture");
		byte[] decodedData = base64.decode(property.getValue().getBytes());
		return new String(decodedData).substring("{textures:{SKIN:{url:\"".length() - 1, "\"}}}".length() - 1);
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
