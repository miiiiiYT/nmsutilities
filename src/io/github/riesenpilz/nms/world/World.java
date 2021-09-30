package io.github.riesenpilz.nms.world;

import org.apache.commons.lang.Validate;

import net.minecraft.server.v1_16_R3.WorldServer;

public class World {
	private net.minecraft.server.v1_16_R3.World world;

	protected World(net.minecraft.server.v1_16_R3.World nms) {
		Validate.notNull(nms);
		world = nms;
	}

	public static World getWorldOf(net.minecraft.server.v1_16_R3.World nms) {
		return new World(nms);
	}
	
	public net.minecraft.server.v1_16_R3.World getNMS() {
		return world;
	}

	public org.bukkit.World getBukkit() {
		return isServerWorld() ? new ServerWorld((WorldServer)world).getBukkit():null;
	}
	
	public boolean isServerWorld() {
		return world instanceof WorldServer;
	}
}
