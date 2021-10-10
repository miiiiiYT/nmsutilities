package io.github.riesenpilz.nmsUtilities.advancemts;

import org.apache.commons.lang.Validate;
import org.bukkit.NamespacedKey;

import io.github.riesenpilz.nmsUtilities.packet.PacketUtils;
import io.github.riesenpilz.nmsUtilities.reflections.Field;
import net.minecraft.server.v1_16_R3.AdvancementRewards;
import net.minecraft.server.v1_16_R3.CustomFunction;
import net.minecraft.server.v1_16_R3.MinecraftKey;

/**
 * Represents {@link AdvancementRewards}. Only used by packets.
 * 
 * @see PacketPlayOutAdvancementsEvent
 *
 */
public class Rewards {
	private int xp;
	private NamespacedKey[] loot;
	private NamespacedKey[] recipes;
	private CustomFunction.a function;

	public Rewards(int xp, NamespacedKey[] loot, NamespacedKey[] recipes, CustomFunction.a function) {

		Validate.notNull(loot);
		Validate.notNull(recipes);
		Validate.notNull(function);

		this.xp = xp;
		this.loot = loot;
		this.recipes = recipes;
		this.function = function;
	}

	protected Rewards(AdvancementRewards nms) {

		Validate.notNull(nms);

		xp = Field.get(nms, "b", int.class);

		MinecraftKey[] nmsLoot = Field.get(nms, "c", MinecraftKey[].class);
		loot = new NamespacedKey[nmsLoot.length];
		for (int i = 0; i < nmsLoot.length; i++)
			loot[i] = PacketUtils.toNamespacedKey(nmsLoot[i]);

		MinecraftKey[] nmsRecipes = Field.get(nms, "d", MinecraftKey[].class);
		recipes = new NamespacedKey[nmsRecipes.length];
		for (int i = 0; i < nmsRecipes.length; i++)
			recipes[i] = PacketUtils.toNamespacedKey(nmsRecipes[i]);

		function = Field.get(nms, "e", CustomFunction.a.class);

	}

	public int getXp() {
		return xp;
	}

	public void setXp(int xp) {
		this.xp = xp;
	}

	public NamespacedKey[] getLoot() {
		return loot;
	}

	public void setLoot(NamespacedKey[] loot) {
		Validate.notNull(loot);
		this.loot = loot;
	}

	public NamespacedKey[] getRecipes() {
		return recipes;
	}

	public void setRecipes(NamespacedKey[] recipes) {
		Validate.notNull(recipes);
		this.recipes = recipes;
	}

	public CustomFunction.a getFunction() {
		return function;
	}

	public void setFunction(CustomFunction.a function) {
		Validate.notNull(function);
		this.function = function;
	}

	public static Rewards getRewardsOf(AdvancementRewards nms) {
		return new Rewards(nms);
	}

	public AdvancementRewards getNMS() {
		MinecraftKey[] nmsLoot = new MinecraftKey[loot.length];
		MinecraftKey[] nmsRecipes = new MinecraftKey[recipes.length];

		for (int i = 0; i < loot.length; i++)
			nmsLoot[i] = PacketUtils.toMinecraftKey(loot[i]);
		for (int i = 0; i < recipes.length; i++)
			nmsRecipes[i] = PacketUtils.toMinecraftKey(recipes[i]);

		return new AdvancementRewards(xp, nmsLoot, nmsRecipes, function);
	}
}
