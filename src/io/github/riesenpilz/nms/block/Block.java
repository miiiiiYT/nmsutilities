package io.github.riesenpilz.nms.block;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Container;
import org.bukkit.block.data.Directional;
import org.bukkit.craftbukkit.v1_16_R3.block.CraftBlock;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import io.github.riesenpilz.nms.inventory.ItemStack;
import io.github.riesenpilz.nms.nbt.NBTBase;
import io.github.riesenpilz.nms.nbt.NBTTag;
import io.github.riesenpilz.nms.nbt.NBTTagList;
import io.github.riesenpilz.nms.world.ServerWorld;
import io.github.riesenpilz.nms.world.chunk.Chunk;
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

	@Deprecated
	public JsonElement getTag(String key) {
		return getTags().get(key);
	}

	@Deprecated
	public JsonObject getTags() {
		final JsonObject jsonObject = new ServerWorld(block.getWorld()).getConfig("BlockData");
		final String path = block.getLocation().getBlockX() + "." + block.getLocation().getBlockY() + "."
				+ block.getLocation().getBlockZ();
		return jsonObject.has(path) ? jsonObject.getAsJsonObject(path) : new JsonObject();
	}

	@Deprecated
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

	@Deprecated
	public void setTags(JsonObject config) {
		final ServerWorld world = new ServerWorld(block.getWorld());
		final JsonObject jsonObject = world.getConfig("BlockData");
		final String path = block.getLocation().getBlockX() + "." + block.getLocation().getBlockY() + "."
				+ block.getLocation().getBlockZ();
		jsonObject.add(path, config);
		world.setConfig("BlockData", jsonObject);
	}

	public IBlockData getNMS() {
		return ((CraftBlock) block).getNMS();
	}

	public Chunk getChunk() {
		return new Chunk(block.getChunk());
	}

	public NBTTag getNBTTag() {
		final NBTTag allNBTTags = getChunk().getAllNBTTags();
		final NBTTag blockTags = allNBTTags.getOrDefNBTTag("block", new NBTTag());
		return blockTags.getOrDefNBTTag(getLocation().toString(), new NBTTag());
	}

	public void setNBTTag(NBTTag tag) {
		final NBTTag allNBTTags = getChunk().getAllNBTTags();
		final NBTTag blockTags = allNBTTags.getOrDefNBTTag("block", new NBTTag());
		blockTags.setNBTTag(getLocation().toString(), tag);
	}

	public void removeNBTTag() {
		final NBTTag allNBTTags = getChunk().getAllNBTTags();
		final NBTTag blockTags = allNBTTags.getOrDefNBTTag("block", new NBTTag());
		blockTags.remove(getLocation().toString());
	}

	public void setDrops(org.bukkit.inventory.ItemStack[] drops) {
		final NBTTagList dropList = getNBTTag().getOrDefNBTTagList("drops", new NBTTagList());
		for (final org.bukkit.inventory.ItemStack itemStack : drops)
			dropList.add(new ItemStack(itemStack).toTag());
	}

	public List<ItemStack> getDrops() {
		final List<ItemStack> drops = new ArrayList<>();
		if (getNBTTag().isNBTTagList("drops"))
			for (final NBTBase itemStack : getNBTTag().getNBTTagList("drops"))
				drops.add(ItemStack.getItemStack((NBTTag) itemStack));
		return drops;
	}

	public boolean hasDrops() {
		return getNBTTag().isNBTTagList("drops") && getNBTTag().getNBTTagList("drops").size() > 0;
	}

	public Location getLocation() {
		return block.getLocation();
	}
}