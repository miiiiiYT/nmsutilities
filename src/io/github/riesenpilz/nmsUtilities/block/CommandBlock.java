package io.github.riesenpilz.nmsUtilities.block;

import javax.annotation.Nullable;

import net.minecraft.server.v1_16_R3.TileEntityCommand.Type;

public class CommandBlock {

	public static enum CommandBlockType {

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

		private Type nms;

		private CommandBlockType(Type nms) {
			this.nms = nms;
		}

		public Type getNMS() {
			return nms;
		}

		/**
		 * Gets the equivalent for the NMS type. If type is NULL it returns NULL.
		 * 
		 * @param nms the NMS command block type
		 * @return the equivalent for the NMS type
		 */
		@Nullable
		public static CommandBlockType getType(@Nullable Type nms) {
			for (CommandBlockType type : CommandBlockType.values())
				if (type.getNMS().equals(nms))
					return type;
			return null;
		}
	}
}
