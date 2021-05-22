package io.github.riesenpilz.nms.block;

import net.minecraft.server.v1_16_R3.TileEntityCommand.Type;

public class CommandBlock {

	public static enum CommandBlockType {

		SEQUENCE(Type.SEQUENCE),
		AUTO(Type.AUTO),
		REDSTONE(Type.REDSTONE);

		private Type nms;

		private CommandBlockType(Type nms) {
			this.nms = nms;
		}

		public Type getNMS() {
			return nms;
		}

		public static CommandBlockType getType(Type nms) {
			for (CommandBlockType type : CommandBlockType.values())
				if (type.getNMS().equals(nms))
					return type;
			return null;
		}
	}
}
