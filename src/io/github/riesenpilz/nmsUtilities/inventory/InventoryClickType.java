package io.github.riesenpilz.nmsUtilities.inventory;

public enum InventoryClickType {

	PICKUP(net.minecraft.server.v1_16_R3.InventoryClickType.PICKUP),
	QUICK_MOVE(net.minecraft.server.v1_16_R3.InventoryClickType.QUICK_MOVE),
	SWAP(net.minecraft.server.v1_16_R3.InventoryClickType.QUICK_MOVE),
	CLONE(net.minecraft.server.v1_16_R3.InventoryClickType.CLONE),
	THROW(net.minecraft.server.v1_16_R3.InventoryClickType.THROW),
	QUICK_CRAFT(net.minecraft.server.v1_16_R3.InventoryClickType.QUICK_CRAFT),
	PICKUP_ALL(net.minecraft.server.v1_16_R3.InventoryClickType.PICKUP_ALL);

	private net.minecraft.server.v1_16_R3.InventoryClickType nms;

	private InventoryClickType(net.minecraft.server.v1_16_R3.InventoryClickType nms) {
		this.nms = nms;
	}

	public net.minecraft.server.v1_16_R3.InventoryClickType getNMS() {
		return nms;
	}

	public static InventoryClickType getInventoryClickType(net.minecraft.server.v1_16_R3.InventoryClickType nms) {
		for (InventoryClickType type : values())
			if (type.getNMS().equals(nms))
				return type;
		return null;
	}
}
