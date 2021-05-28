package io.github.riesenpilz.nms.world;

import net.minecraft.server.v1_16_R3.WorldServer;

public class World {
	private net.minecraft.server.v1_16_R3.World world;

	public World(net.minecraft.server.v1_16_R3.World nms) {
		world = nms;
	}

	public net.minecraft.server.v1_16_R3.World getNMS() {
		return world;
	}

	public org.bukkit.World getWorld() {
		return isServerWorld() ? new ServerWorld((WorldServer)world).getWorld():null;
	}
	
	public boolean isServerWorld() {
		return world instanceof WorldServer;
	}
}
