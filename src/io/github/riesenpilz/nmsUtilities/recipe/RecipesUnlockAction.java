package io.github.riesenpilz.nmsUtilities.recipe;

import org.apache.commons.lang.Validate;

import io.github.riesenpilz.nmsUtilities.packet.playOut.PacketPlayOutRecipesUnlockEvent;
import net.minecraft.server.v1_16_R3.PacketPlayOutRecipes;

/**
 * Represents {@link PacketPlayOutRecipes.Action}. Only used by packets.
 *
 * @see PacketPlayOutRecipesUnlockEvent
 */
public enum RecipesUnlockAction {

	/**
	 * All the recipes in list 1 will be tagged as displayed, and all the recipes in
	 * list 2 will be added to the recipe book. Recipes that aren't tagged will be
	 * shown in the notification.
	 */
	INIT(PacketPlayOutRecipes.Action.INIT),

	/**
	 * All the recipes in the list are added to the recipe book and their icons will
	 * be shown in the notification.
	 */
	ADD(PacketPlayOutRecipes.Action.ADD),

	/**
	 * Remove all the recipes in the list. This allows them to be re-displayed when
	 * they are re-added.
	 */
	REMOVE(PacketPlayOutRecipes.Action.REMOVE);

	private final PacketPlayOutRecipes.Action nms;

	private RecipesUnlockAction(PacketPlayOutRecipes.Action nms) {
		this.nms = nms;
	}

	public PacketPlayOutRecipes.Action getNMS() {
		return nms;
	}

	public static RecipesUnlockAction getAction(PacketPlayOutRecipes.Action nms) {
		Validate.notNull(nms);
		for (RecipesUnlockAction action : values())
			if (action.getNMS().equals(nms))
				return action;
		throw new IllegalArgumentException();
	}
}
