package io.github.riesenpilz.nms.inventory;

import io.github.riesenpilz.nms.nbt.NBTTag;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.Objects;

public class PlayerWorldInventory {

    public PlayerWorldInventory(Player player) {
        setContents(player.getInventory().getContents());
        setXp(player.getTotalExperience());
        setFoodLevel(player.getFoodLevel());
        setSaturation(player.getSaturation());
        setSelectedSlot(player.getInventory().getHeldItemSlot());
        setLocation(player.getLocation());
        setHealth(player.getHealth());
    }

    @Override
    public String toString() {
        return toJson().toString();
    }

    public JsonObject toJson() {
        JsonObject jsonObject = new JsonObject();
        JsonArray jsonArray = new JsonArray();
        for (ItemStack itemStack : getContents())
            jsonArray.add(itemStack == null ? "" : new io.github.riesenpilz.nms.inventory.ItemStack(itemStack).toTag().toString());
        jsonObject.add("contents", jsonArray);
        jsonObject.addProperty("xp", getXp());
        jsonObject.addProperty("foodLevel", getFoodLevel());
        jsonObject.addProperty("saturation", getSaturation());
        jsonObject.addProperty("selectedSlot", getSelectedSlot());
        jsonObject.addProperty("health", getHealth());
        if (getLocation() != null) {
            JsonObject jsonObject2 = new JsonObject();
            jsonObject2.addProperty("world", Objects.requireNonNull(getLocation().getWorld()).getName());
            jsonObject2.addProperty("x", getLocation().getX());
            jsonObject2.addProperty("y", getLocation().getY());
            jsonObject2.addProperty("z", getLocation().getZ());
            jsonObject2.addProperty("yaw", getLocation().getYaw());
            jsonObject2.addProperty("pitch", getLocation().getPitch());
            jsonObject.add("location", jsonObject2);
        }
        return jsonObject;
    }

    public void fromJson(JsonObject jsonObject) {
        if (jsonObject.has("contents")) {
            JsonArray jsonArray = jsonObject.getAsJsonArray("contents");
            for (int i = 0; i < jsonArray.size(); i++)
                contents[i] = jsonArray.get(i).getAsString().isEmpty() ? null
                        : io.github.riesenpilz.nms.inventory.ItemStack.getItemStack(new NBTTag(jsonArray.get(i).getAsString())).getItemStack();
        }
        if (jsonObject.has("xp"))
            setXp(jsonObject.get("xp").getAsInt());
        if (jsonObject.has("foodLevel"))
            setFoodLevel(jsonObject.get("foodLevel").getAsInt());
        if (jsonObject.has("saturation"))
            setSaturation(jsonObject.get("saturation").getAsFloat());
        if (jsonObject.has("selectedSlot"))
            setSelectedSlot(jsonObject.get("selectedSlot").getAsInt());
        if (jsonObject.has("health"))
            setHealth(jsonObject.get("health").getAsDouble());
        if (jsonObject.has("location")) {
            final JsonObject location = jsonObject.getAsJsonObject("location");
            World world = Bukkit.getWorld(location.get("world").getAsString());
            double x = location.get("x").getAsDouble();
            double y = location.get("y").getAsDouble();
            double z = location.get("z").getAsDouble();
            float yaw = location.get("yaw").getAsFloat();
            float pitch = location.get("pitch").getAsFloat();
            setLocation(new Location(world, x, y, z, yaw, pitch));
        }
    }

    public PlayerWorldInventory(String string) {
        JsonObject jsonObject = new Gson().fromJson(string, JsonObject.class);
        fromJson(jsonObject);
    }

    public PlayerWorldInventory(JsonObject jsonObject) {
        fromJson(jsonObject);
    }

    public void setToPlayer(Player player) {
        player.getInventory().setContents(getContents());
        player.getInventory().setHeldItemSlot(getSelectedSlot());
        player.setFoodLevel(getFoodLevel());
        player.setSaturation(getSaturation());
        player.setExp(getXp());
        player.setHealth(getHealth());
        if (getLocation() != null)
            player.teleport(getLocation());
    }

    /**
     * The {@link Inventory} contents.
     */
    private ItemStack[] contents = new ItemStack[41];
    /**
     * The xp.
     */
    private int xp = 0;

    private int foodLevel = 20;

    private float saturation = 20;

    private int selectedSlot = 0;

    private Location location;

    private double health = 20;

    public PlayerWorldInventory() {
    }

    /**
     * Gets the {@link PlayerWorldInventory#contents}.
     *
     * @return {@link PlayerWorldInventory#contents}.
     */
    public ItemStack[] getContents() {
        return contents;
    }

    /**
     * Sets the {@link PlayerWorldInventory#contents}.
     *
     * @param contents the new {@link PlayerWorldInventory#contents}.
     */
    public void setContents(ItemStack[] contents) {
        this.contents = contents;
    }

    /**
     * Gets the {@link PlayerWorldInventory#xp}.
     *
     * @return {@link PlayerWorldInventory#xp}.
     */
    public int getXp() {
        return xp;
    }

    public void setXp(int xp) {
        this.xp = xp;
    }

    /**
     * Sets the hotbar slots (0-8 of {@link PlayerWorldInventory#contents}).
     *
     * @param hotbar the new hotbar {@link ItemStack}s.
     */
    public void setHotbar(ItemStack[] hotbar) {
        System.arraycopy(hotbar, 0, contents, 0, hotbar.length);
    }

    /**
     * Sets the storage contents slots (9-35 of
     * {@link PlayerWorldInventory#contents}).
     *
     * @param contents the new storage contents {@link ItemStack}s.
     */
    public void setStorageContents(ItemStack[] contents) {
        System.arraycopy(contents, 0, this.contents, 9, contents.length);
    }

    /**
     * Sets the armor slots (36-40 of {@link PlayerWorldInventory#contents}).
     *
     * @param armor the new armor {@link ItemStack}s.
     */
    public void setArmor(ItemStack[] armor) {
        System.arraycopy(armor, 0, contents, 36, armor.length);
    }

    /**
     * Sets the offHand slot (41 of {@link PlayerWorldInventory#contents}).
     *
     * @param offHand the new offHand {@link ItemStack}.
     */
    public void setOffHand(ItemStack offHand) {
        contents[41] = offHand;
    }

    /**
     * Sets the {@link ItemStack} of a specific slot.
     *
     * @param slot      the slot where the {@link ItemStack} will be located.
     * @param itemStack the {@link ItemStack} to set.
     */
    public void setSlot(int slot, ItemStack itemStack) {
        contents[slot] = itemStack;
    }

    /**
     * Gets the {@link ItemStack} in the slot.
     *
     * @param slot the where the {@link ItemStack} is located.
     * @return the {@link ItemStack} in the slot.
     */
    public ItemStack getSlot(int slot) {
        return contents[slot];
    }

    /**
     * @return the selectedSlot
     */
    public int getSelectedSlot() {
        return selectedSlot;
    }

    /**
     * @param selectedSlot the selectedSlot to set
     */
    public void setSelectedSlot(int selectedSlot) {
        this.selectedSlot = selectedSlot;
    }

    /**
     * @return the saturation
     */
    public float getSaturation() {
        return saturation;
    }

    /**
     * @param saturation the saturation to set
     */
    public void setSaturation(float saturation) {
        this.saturation = saturation;
    }

    /**
     * @return the foodLevel
     */
    public int getFoodLevel() {
        return foodLevel;
    }

    /**
     * @param foodLevel the foodLevel to set
     */
    public void setFoodLevel(int foodLevel) {
        this.foodLevel = foodLevel;
    }

    /**
     * @return the location
     */
    public Location getLocation() {
        return location;
    }

    /**
     * @param location the location to set
     */
    public void setLocation(Location location) {
        this.location = location;
    }

    /**
     * @return the health
     */
    public double getHealth() {
        return health;
    }

    /**
     * @param health the health to set
     */
    public void setHealth(double health) {
        this.health = health;
    }
}
