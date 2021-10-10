package io.github.riesenpilz.nmsUtilities.recipe;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.Validate;

import com.mojang.datafixers.util.Pair;

import io.github.riesenpilz.nmsUtilities.inventory.RecipeBookType;
import io.github.riesenpilz.nmsUtilities.nbt.NBTTag;
import io.github.riesenpilz.nmsUtilities.packet.playOut.PacketPlayOutRecipesUnlockEvent;
import io.github.riesenpilz.nmsUtilities.reflections.Field;
import net.minecraft.server.v1_16_R3.NBTTagCompound;
import net.minecraft.server.v1_16_R3.RecipeBookSettings;

/**
 * Represents {@link RecipeBookSettings}. Only used by packets.
 *
 * @see PacketPlayOutRecipesUnlockEvent
 */
public class RecipeBookSetting {

	private RecipeBookType type;
	private boolean open;
	private boolean filtering;

	public RecipeBookSetting(RecipeBookType type, boolean open, boolean filtering) {

		Validate.notNull(type);

		this.type = type;
		this.open = open;
		this.filtering = filtering;
	}

	public static RecipeBookSettings getNMS(Set<RecipeBookSetting> recipeBookSettings) {

		Validate.notNull(recipeBookSettings);

		final RecipeBookSettings nms = new RecipeBookSettings();
		for (RecipeBookSetting setting : recipeBookSettings) {
			if (setting.open)
				nms.a(setting.type.getNMS(), true);
			if (setting.filtering)
				nms.a(setting.type.getNMS(), true);
		}
		return nms;
	}

	public static Set<RecipeBookSetting> getRecipeBookSettingsOf(RecipeBookSettings nms) {

		Validate.notNull(nms);

		Set<RecipeBookSetting> recipeBookSettings = new HashSet<>();
		@SuppressWarnings("unchecked")
		final Map<net.minecraft.server.v1_16_R3.RecipeBookType, Pair<String, String>> keys = Field
				.getConstant(RecipeBookSettings.class, "a", Map.class);

		final NBTTagCompound nmsTag = new NBTTagCompound();
		nms.b(nmsTag);
		final NBTTag tag = NBTTag.getNBTTagOf(nmsTag);

		keys.forEach((type, pair) -> {
			recipeBookSettings.add(new RecipeBookSetting(RecipeBookType.getRecipeBookType(type),
					tag.getBoolean(pair.getFirst()), tag.getBoolean(pair.getSecond())));
		});
		return recipeBookSettings;
	}

	public RecipeBookType getType() {
		return type;
	}

	public void setType(RecipeBookType type) {
		Validate.notNull(type);
		this.type = type;
	}

	public boolean isOpen() {
		return open;
	}

	public void setOpen(boolean open) {
		this.open = open;
	}

	public boolean isFiltering() {
		return filtering;
	}

	public void setFiltering(boolean filtering) {
		this.filtering = filtering;
	}
}
