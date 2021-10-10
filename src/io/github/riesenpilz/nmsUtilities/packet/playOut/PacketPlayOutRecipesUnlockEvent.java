package io.github.riesenpilz.nmsUtilities.packet.playOut;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.Validate;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;

import io.github.riesenpilz.nmsUtilities.packet.PacketUtils;
import io.github.riesenpilz.nmsUtilities.recipe.RecipesUnlockAction;
import io.github.riesenpilz.nmsUtilities.recipe.RecipeBookSetting;
import io.github.riesenpilz.nmsUtilities.reflections.Field;
import net.minecraft.server.v1_16_R3.MinecraftKey;
import net.minecraft.server.v1_16_R3.Packet;
import net.minecraft.server.v1_16_R3.PacketListenerPlayOut;
import net.minecraft.server.v1_16_R3.PacketPlayOutRecipes;
import net.minecraft.server.v1_16_R3.RecipeBookSettings;

/**
 * https://wiki.vg/Protocol#Unlock_Recipes
 * <p>
 * Packet ID: 0x39<br>
 * State: Play<br>
 * Bound To: Client
 *
 */
public class PacketPlayOutRecipesUnlockEvent extends PacketPlayOutEvent {

	private RecipesUnlockAction action;
	private List<NamespacedKey> recipeIds;

	/**
	 * Only present if {@link PacketPlayOutRecipesUnlockEvent#a} is INIT
	 */
	private List<NamespacedKey> recipeIds1;
	private Set<RecipeBookSetting> settings;

	public PacketPlayOutRecipesUnlockEvent(Player injectedPlayer, PacketPlayOutRecipes packet) {
		super(injectedPlayer);

		Validate.notNull(packet);

		action = RecipesUnlockAction.getAction(Field.get(packet, "a", PacketPlayOutRecipes.Action.class));

		@SuppressWarnings("unchecked")
		List<MinecraftKey> nmsRecipeIds = Field.get(packet, "b", List.class);
		recipeIds = new ArrayList<>();
		for (MinecraftKey minecraftKey : nmsRecipeIds)
			recipeIds.add(PacketUtils.toNamespacedKey(minecraftKey));

		@SuppressWarnings("unchecked")
		List<MinecraftKey> nmsRecipeIds1 = Field.get(packet, "c", List.class);
		recipeIds1 = new ArrayList<>();
		for (MinecraftKey minecraftKey : nmsRecipeIds1)
			recipeIds1.add(PacketUtils.toNamespacedKey(minecraftKey));

		settings = RecipeBookSetting.getRecipeBookSettingsOf(Field.get(packet, "d", RecipeBookSettings.class));
	}

	public PacketPlayOutRecipesUnlockEvent(Player injectedPlayer, RecipesUnlockAction action, List<NamespacedKey> recipeIds,
			List<NamespacedKey> recipeIds1, Set<RecipeBookSetting> settings) {
		super(injectedPlayer);
		this.action = action;
		this.recipeIds = recipeIds;
		this.recipeIds1 = recipeIds1;
		this.settings = settings;
	}

	public RecipesUnlockAction getAction() {
		return action;
	}

	public List<NamespacedKey> getRecipeIds() {
		return recipeIds;
	}

	public List<NamespacedKey> getRecipeIds1() {
		return recipeIds1;
	}

	public Set<RecipeBookSetting> getSettings() {
		return settings;
	}

	@Override
	public int getPacketID() {
		return 0x39;
	}

	@Override
	public String getProtocolURLString() {
		return "https://wiki.vg/Protocol#Unlock_Recipes";
	}

	@Override
	public Packet<PacketListenerPlayOut> getNMS() {

		List<MinecraftKey> nmsRecipeIds = new ArrayList<>();
		for (NamespacedKey namespacedKey : recipeIds)
			nmsRecipeIds.add(new MinecraftKey(namespacedKey.getNamespace(), namespacedKey.getKey()));

		List<MinecraftKey> nmsRecipeIds1 = new ArrayList<>();
		for (NamespacedKey namespacedKey : recipeIds1)
			nmsRecipeIds1.add(new MinecraftKey(namespacedKey.getNamespace(), namespacedKey.getKey()));

		return new PacketPlayOutRecipes(action.getNMS(), nmsRecipeIds, nmsRecipeIds1,
				RecipeBookSetting.getNMS(settings));
	}

}
