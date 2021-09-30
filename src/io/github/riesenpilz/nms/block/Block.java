package io.github.riesenpilz.nms.block;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import org.apache.commons.lang.Validate;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Container;
import org.bukkit.block.data.Directional;
import org.bukkit.craftbukkit.v1_16_R3.block.CraftBlock;

import io.github.riesenpilz.nms.inventory.ItemStack;
import io.github.riesenpilz.nms.nbt.NBTBase;
import io.github.riesenpilz.nms.nbt.NBTTag;
import io.github.riesenpilz.nms.nbt.NBTTagList;
import io.github.riesenpilz.nms.world.chunk.Chunk;
import net.minecraft.server.v1_16_R3.IBlockData;

/**
 * Represents a {@link org.bukkit.block.Block}
 *
 */
public class Block {

	/**
	 * Provides all variables
	 */
	private final org.bukkit.block.Block block;

	/**
	 * Creates a block with a {@link Location}.
	 * 
	 * @param location the {@link Location} of the block to use
	 */
	protected Block(Location location) {
		Validate.notNull(location);
		block = location.getBlock();
	}

	/**
	 * Creates a block with a {@link org.bukkit.block.Block}.
	 * 
	 * @param bukkitBlock the {@link org.bukkit.block.Block} to use
	 */
	protected Block(org.bukkit.block.Block bukkitBlock) {
		Validate.notNull(bukkitBlock);
		this.block = bukkitBlock;
	}

	/**
	 * Gets the block of the specified {@link Location}.
	 * 
	 * @param location the location of the returned block
	 * @return a new instance of {@link Block} with the given location
	 */
	public static Block getBlockOf(Location location) {
		return new Block(location);
	}

	/**
	 * Gets the block of the specified {@link org.bukkit.block.Block}.
	 * 
	 * @param bukkitBlock the {@link org.bukkit.block.Block} of the returned block
	 * @return a new instance of {@link Block} with the given block
	 */
	public static Block getBlockOf(org.bukkit.block.Block bukkitBlock) {
		return new Block(bukkitBlock);
	}

	/**
	 * Gets the wrapper for the NMS block.
	 * 
	 * @return the {@link BlockData} of the block
	 */
	public BlockData getBlockData() {
		return BlockData.getBlockDataOf(block);
	}

	/**
	 * Gets the Facing of this block. If it don't have a facing it will return
	 * {@link BlockFace#NORTH}.
	 * 
	 * @return the {@link BlockFace} of this block
	 */
	public BlockFace getFacing() {
		return block instanceof Directional ? ((Directional) block).getFacing() : BlockFace.NORTH;
	}

	/**
	 * Sets the facing of this block. Will only set the facing of it has one.
	 * 
	 * @param blockFace the new facing
	 */
	public void setFacing(BlockFace blockFace) {
		if (block instanceof Directional)
			((Directional) block).setFacing(blockFace);
	}

	/**
	 * Represents {@link org.bukkit.block.Block#getType()}
	 * 
	 * @return the {@link Material} of this block
	 */
	public Material getMaterial() {
		return block.getType();
	}

	/**
	 * Represents {@link org.bukkit.block.Block#setType()}
	 * 
	 * @param material the new {@link Material} of this block
	 */
	public void setMaterial(Material material) {
		block.setType(material);
	}

	/**
	 * Gets the custom name for this block. If it isn't a {@link Container} it will
	 * return NULL.
	 * 
	 * @return the custom name for this block
	 */
	@Nullable
	public String getName() {
		if (!(block instanceof Container))
			return null;
		final Container container = (Container) block;
		return container.getCustomName();
	}

	/**
	 * Sets the custom name for this block. Will only set the name if its a
	 * {@link Container}.
	 * 
	 * @param name the new custom name for this block
	 */
	public void setName(@Nullable String name) {
		if (!(block instanceof Container))
			return;
		final Container container = (Container) block;
		container.setCustomName(name);
	}

	/**
	 * Gets the NMS version of this block.
	 * 
	 * @return the {@link IBlockData} of this block.
	 */
	public IBlockData getNMS() {
		return ((CraftBlock) block).getNMS();
	}

	/**
	 * Gets the Chunk wrapper of the {@link org.bukkit.Chunk} this block is in.
	 * 
	 * @return the chunk wrapper
	 */
	public Chunk getChunk() {
		return new Chunk(block.getChunk());
	}

	/**
	 * Gets the custom NBTTag of the {@link Location} of the block (will be removed
	 * if the block breaks or moved if the block moves by a piston) Only works if
	 * the {@link org.bukkit.Chunk} the block is in is loaded.
	 * 
	 * @return the custom NBTTag for this block
	 */
	public NBTTag getNBTTag() {
		final NBTTag allNBTTags = getChunk().getAllNBTTags();
		final NBTTag blockTags = allNBTTags.getOrDefNBTTag("block", new NBTTag());
		return blockTags.getOrDefNBTTag(getLocation().toString(), new NBTTag());
	}

	/**
	 * Sets the custom NBTTag of the {@link Location} of the block (will be removed
	 * if the block breaks or moved if the block moves by a piston) Only works if
	 * the {@link org.bukkit.Chunk} the block is in is loaded.
	 * 
	 * @param tag the custom NBTTag to set
	 */
	public void setNBTTag(@Nullable NBTTag tag) {
		final NBTTag allNBTTags = getChunk().getAllNBTTags();
		final NBTTag blockTags = allNBTTags.getOrDefNBTTag("block", new NBTTag());
		blockTags.setNBTTag(getLocation().toString(), tag);
	}

	/**
	 * Removes the NBTTag of the block. Not equivalent to
	 * {@code Block.setNBTTag(null))}
	 */
	public void removeNBTTag() {
		final NBTTag allNBTTags = getChunk().getAllNBTTags();
		final NBTTag blockTags = allNBTTags.getOrDefNBTTag("block", new NBTTag());
		blockTags.remove(getLocation().toString());
	}

	/**
	 * Sets drops that will drop instead of the normal drops if the block breaks.
	 * This is stored in {@code getNBTTag().getNBTTagList("drops")}
	 * 
	 * @param drops the new custom drops
	 */
	public void setDrops(@Nullable org.bukkit.inventory.ItemStack[] drops) {
		if (drops == null)
			getNBTTag().remove("drops");
		final NBTTagList dropList = getNBTTag().getOrDefNBTTagList("drops", new NBTTagList());
		for (final org.bukkit.inventory.ItemStack bukkit : drops)
			dropList.add(ItemStack.getItemStackOf(bukkit).toNBTTag());
	}

	/**
	 * Equivalent to {@code block.setDrops(null)}
	 */
	public void removeDrops() {
		setDrops(null);
	}

	/**
	 * Gets drops that will drop instead of the normal drops if the block breaks.
	 * This is stored in {@code getNBTTag().getNBTTagList("drops")}.
	 * 
	 * @return the custom drops
	 */
	public List<ItemStack> getDrops() {
		final List<ItemStack> drops = new ArrayList<>();
		if (getNBTTag().isNBTTagList("drops"))
			for (final NBTBase nbtItemStack : getNBTTag().getNBTTagList("drops"))
				if (ItemStack.isItemStackNBT(nbtItemStack))
					drops.add(ItemStack.getItemStack((NBTTag) nbtItemStack));
		return drops;
	}
	
	/**
	 * Tests if this block has custom drops.
	 * @return true if this block has custom drops
	 */
	public boolean hasDrops() {
		return getNBTTag().isNBTTagList("drops") && getNBTTag().getNBTTagList("drops").size() > 0;
	}

	/**
	 * Represents {@link org.bukkit.block.Block#getLocation()}
	 * 
	 * @return the {@link Location} of this block
	 */
	public Location getLocation() {
		return block.getLocation();
	}
	
	/**
	 * Gets the Bukkit block
	 * @return the Bukkit block
	 */
	public org.bukkit.block.Block getBukkit() {
		return block;
	}
}