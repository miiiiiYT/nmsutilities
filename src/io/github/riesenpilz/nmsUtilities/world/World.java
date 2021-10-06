package io.github.riesenpilz.nmsUtilities.world;

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
		Validate.isTrue(isServerWorld());
		return ServerWorld.getWorldOf((WorldServer)world).getBukkit();
	}
	public ServerWorld getServerWorld() {
		Validate.isTrue(isServerWorld());
		return ServerWorld.getWorldOf((WorldServer)world);
	}
	public boolean isServerWorld() {
		return world instanceof WorldServer;
	}
}
