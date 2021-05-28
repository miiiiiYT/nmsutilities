package io.github.riesenpilz.nms.block;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Container;
import org.bukkit.block.data.Directional;
import org.bukkit.craftbukkit.v1_16_R3.block.CraftBlock;
import org.bukkit.inventory.ItemStack;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import io.github.riesenpilz.nms.nbt.NBTTag;
import io.github.riesenpilz.nms.world.ServerWorld;
import net.minecraft.server.v1_16_R3.IBlockData;

public class Block {
	private final org.bukkit.block.Block block;

	public Block(Location location) {
		block = location.getBlock();
	}

	public Block(org.bukkit.block.Block block) {
		this.block = block;
	}

	public BlockData getBlockData() {
		return new BlockData(block);
	}
	
	public List<ItemStack> getDrops() {
		final List<ItemStack> drops = new ArrayList<>();
		if (getTags().has("drops"))
			for (final JsonElement s : getTags().getAsJsonArray("drops"))
				drops.add(io.github.riesenpilz.nms.inventory.ItemStack.getItemStack(new NBTTag(s.getAsString()))
						.getItemStack());
		return drops;
	}

	public BlockFace getFacing() {
		return block instanceof Directional ? ((Directional) block).getFacing() : BlockFace.NORTH;
	}

	public Material getMaterial() {
		return block.getType();
	}

	public String getName() {
		if (!(block instanceof Container))
			return null;
		final Container container = (Container) block;
		return container.getCustomName();
	}

	public JsonElement getTag(String key) {
		return getTags().get(key);
	}

	public JsonObject getTags() {
		final JsonObject jsonObject = new ServerWorld(block.getWorld()).getConfig("BlockData");
		final String path = block.getLocation().getBlockX() + "." + block.getLocation().getBlockY() + "."
				+ block.getLocation().getBlockZ();
		return jsonObject.has(path) ? jsonObject.getAsJsonObject(path) : new JsonObject();
	}

	public void removeTags() {
		final ServerWorld world = new ServerWorld(block.getWorld());
		final JsonObject jsonObject = world.getConfig("BlockData");
		final String path = block.getLocation().getBlockX() + "." + block.getLocation().getBlockY() + "."
				+ block.getLocation().getBlockZ();
		if (jsonObject.has(path)) {
			jsonObject.remove(path);
			world.setConfig("BlockData", jsonObject);
		}
	}

	public void setDrops(List<ItemStack> drops) {
		final JsonObject config = getTags();
		final JsonArray jsonArray = new JsonArray();
		for (final ItemStack itemStack : drops)
			jsonArray.add(new io.github.riesenpilz.nms.inventory.ItemStack(itemStack).toTag().toString());
		config.add("drops", jsonArray);
		setTags(config);
	}

	public void setFacing(BlockFace blockFace) {
		if (block instanceof Directional)
			((Directional) block).setFacing(blockFace);
	}

	public void setMaterial(Material material) {
		block.setType(material);
	}

	public void setName(String name) {
		if (!(block instanceof Container))
			return;
		final Container container = (Container) block;
		container.setCustomName(name);
	}

	public void setTags(JsonObject config) {
		final ServerWorld world = new ServerWorld(block.getWorld());
		final JsonObject jsonObject = world.getConfig("BlockData");
		final String path = block.getLocation().getBlockX() + "." + block.getLocation().getBlockY() + "."
				+ block.getLocation().getBlockZ();
		jsonObject.add(path, config);
		world.setConfig("BlockData", jsonObject);
	}
	public IBlockData getNMS() {
		return ((CraftBlock)block).getNMS();
	}
}