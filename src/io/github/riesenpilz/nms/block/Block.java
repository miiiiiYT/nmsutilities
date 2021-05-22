package io.github.riesenpilz.nms.block;

import java.util.ArrayList;
import java.util.List;

import io.github.riesenpilz.nms.nbt.NBTTag;
import io.github.riesenpilz.nms.world.World;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Container;
import org.bukkit.block.data.Directional;
import org.bukkit.inventory.ItemStack;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class Block {
    private final org.bukkit.block.Block block;

    public Block(Location location) {
        block = location.getBlock();
    }

    public Block(org.bukkit.block.Block block) {
        this.block = block;
    }

    public void setMaterial(Material material) {
        block.setType(material);
    }

    public Material getMaterial() {
        return block.getType();
    }

    public void setFacing(BlockFace blockFace) {
        if (block instanceof Directional)
            ((Directional) block).setFacing(blockFace);
    }

    public BlockFace getFacing() {
        return block instanceof Directional ? ((Directional) block).getFacing() : BlockFace.NORTH;
    }

    public JsonElement getTag(String key) {
        return getTags().get(key);
    }

    public void setDrops(List<ItemStack> drops) {
        JsonObject config = getTags();
        JsonArray jsonArray = new JsonArray();
        for (ItemStack itemStack : drops) {
            jsonArray.add(new io.github.riesenpilz.nms.inventory.ItemStack(itemStack).toTag().toString());
        }
        config.add("drops", jsonArray);
        setTags(config);
    }

    public void setName(String name) {
        if (!(block instanceof Container))
            return;
        Container container = (Container) block;
        container.setCustomName(name);
    }

    public String getName() {
        if (!(block instanceof Container))
            return null;
        Container container = (Container) block;
        return container.getCustomName();
    }

    public List<ItemStack> getDrops() {
        List<ItemStack> drops = new ArrayList<>();
        if (getTags().has("drops"))
            for (JsonElement s : getTags().getAsJsonArray("drops"))
                drops.add(io.github.riesenpilz.nms.inventory.ItemStack.getItemStack(new NBTTag(s.getAsString())).getItemStack());
        return drops;
    }

    public JsonObject getTags() {
        JsonObject jsonObject = new World(block.getWorld()).getConfig("BlockData");
        String path = block.getLocation().getBlockX() + "." + block.getLocation().getBlockY() + "."
                + block.getLocation().getBlockZ();
        return jsonObject.has(path) ? jsonObject.getAsJsonObject(path) : new JsonObject();
    }

    public void setTags(JsonObject config) {
        final World world = new World(block.getWorld());
        JsonObject jsonObject = world.getConfig("BlockData");
        String path = block.getLocation().getBlockX() + "." + block.getLocation().getBlockY() + "."
                + block.getLocation().getBlockZ();
        jsonObject.add(path, config);
        world.setConfig("BlockData", jsonObject);
    }

    public void removeTags() {
        final World world = new World(block.getWorld());
        JsonObject jsonObject = world.getConfig("BlockData");
        String path = block.getLocation().getBlockX() + "." + block.getLocation().getBlockY() + "."
                + block.getLocation().getBlockZ();
        if (jsonObject.has(path)) {
            jsonObject.remove(path);
            world.setConfig("BlockData", jsonObject);
        }
    }

}