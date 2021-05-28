package io.github.riesenpilz.nms.block;

import org.bukkit.block.TileState;

public class TileEntity extends Block{

	public TileEntity(TileState TileEntity) {
		super(TileEntity.getBlock());
	}
}
