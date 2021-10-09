package io.github.riesenpilz.nmsUtilities.block;

import org.apache.commons.lang.Validate;

import net.minecraft.server.v1_16_R3.TileEntityStructure;

/**
 * Represents {@link TileEntityStructure.UpdateType}. Only used by packets.
 *
 * @see PacketPlayInUpdateStructureBlockEvent
 */
public enum StructureUpdateType {

	UPDATE_DATA(TileEntityStructure.UpdateType.UPDATE_DATA), SAVE_AREA(TileEntityStructure.UpdateType.SAVE_AREA),
	LOAD_AREA(TileEntityStructure.UpdateType.LOAD_AREA), SCAN_AREA(TileEntityStructure.UpdateType.SCAN_AREA);

	private final TileEntityStructure.UpdateType nms;

	StructureUpdateType(TileEntityStructure.UpdateType nms) {
		this.nms = nms;
	}

	public TileEntityStructure.UpdateType getNMS() {
		return nms;
	}

	public static StructureUpdateType getUpdateType(TileEntityStructure.UpdateType nms) {
		Validate.notNull(nms);
		for (StructureUpdateType type : values())
			if (type.getNMS().equals(nms))
				return type;
		throw new IllegalArgumentException();
	}
}
