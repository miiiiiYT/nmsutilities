package io.github.riesenpilz.nmsUtilities.inventory;

import org.apache.commons.lang.Validate;

import io.github.riesenpilz.nmsUtilities.packet.playIn.PacketPlayInRecipeSettingsEvent;
import io.github.riesenpilz.nmsUtilities.packet.playOut.PacketPlayOutRecipesUnlockEvent;

/**
 * Represents {@link net.minecraft.server.v1_16_R3.RecipeBookType}.
 * Only used by packets.
 * 
 * @see PacketPlayInRecipeSettingsEvent
 * @see PacketPlayOutRecipesUnlockEvent
 *
 */
public enum RecipeBookType {
	CRAFTING(net.minecraft.server.v1_16_R3.RecipeBookType.CRAFTING),
	FURNACE(net.minecraft.server.v1_16_R3.RecipeBookType.FURNACE),
	BLAST_FURNACE(net.minecraft.server.v1_16_R3.RecipeBookType.BLAST_FURNACE),
	SMOKER(net.minecraft.server.v1_16_R3.RecipeBookType.SMOKER);

	private final net.minecraft.server.v1_16_R3.RecipeBookType nms;

	private RecipeBookType(net.minecraft.server.v1_16_R3.RecipeBookType nms) {
		this.nms = nms;
	}

	public net.minecraft.server.v1_16_R3.RecipeBookType getNMS() {
		return nms;
	}

	public static RecipeBookType getRecipeBookType(net.minecraft.server.v1_16_R3.RecipeBookType nms) {
		Validate.notNull(nms);
		for (RecipeBookType type : values())
			if (type.getNMS().equals(nms))
				return type;
		throw new IllegalArgumentException();
	}
}
