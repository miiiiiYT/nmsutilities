package io.github.riesenpilz.nms.inventory;

public enum RecipeBookType {
	CRAFTING(net.minecraft.server.v1_16_R3.RecipeBookType.CRAFTING),
	FURNACE(net.minecraft.server.v1_16_R3.RecipeBookType.FURNACE),
	BLAST_FURNACE(net.minecraft.server.v1_16_R3.RecipeBookType.BLAST_FURNACE),
	SMOKER(net.minecraft.server.v1_16_R3.RecipeBookType.SMOKER);

	private net.minecraft.server.v1_16_R3.RecipeBookType nms;

	private RecipeBookType(net.minecraft.server.v1_16_R3.RecipeBookType nms) {
		this.nms = nms;
	}

	public net.minecraft.server.v1_16_R3.RecipeBookType getNMS() {
		return nms;
	}

	public static RecipeBookType getRecipeBookType(net.minecraft.server.v1_16_R3.RecipeBookType nms) {
		for (RecipeBookType type : values())
			if (type.getNMS().equals(nms))
				return type;
		return null;
	}
}
