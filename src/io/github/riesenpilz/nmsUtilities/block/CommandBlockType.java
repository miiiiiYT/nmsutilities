package io.github.riesenpilz.nmsUtilities.block;

import org.apache.commons.lang.Validate;

import net.minecraft.server.v1_16_R3.TileEntityCommand.Type;

/**
 * Represents {@link TileEntityCommand.Type}. Only used by packets.
 * 
 * @see PacketPlayInUpdateCommandBlockEvent
 *
 */
public enum CommandBlockType {

	/**
	 * Green command blocks
	 */
	SEQUENCE(Type.SEQUENCE),

	/**
	 * Purple command blocks
	 */
	AUTO(Type.AUTO),

	/**
	 * Yellow command blocks
	 */
	REDSTONE(Type.REDSTONE);

	private final Type nms;

	private CommandBlockType(Type nms) {
		this.nms = nms;
	}

	public Type getNMS() {
		return nms;
	}

	public static CommandBlockType getType(Type nms) {
		Validate.notNull(nms);
		for (CommandBlockType type : CommandBlockType.values())
			if (type.getNMS().equals(nms))
				return type;
		throw new IllegalArgumentException();
	}
}
