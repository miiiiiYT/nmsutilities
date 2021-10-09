package io.github.riesenpilz.nmsUtilities.world;

import org.apache.commons.lang.Validate;

import net.minecraft.server.v1_16_R3.WorldServer;

/**
 * Represents a {@link net.minecraft.server.v1_16_R3.World}
 */
public class World {
	private net.minecraft.server.v1_16_R3.World world;

	protected World(net.minecraft.server.v1_16_R3.World nms) {
		Validate.notNull(nms);
		world = nms;
	}

	public static World getWorldOf(net.minecraft.server.v1_16_R3.World nms) {
		return new World(nms);
	}

	public static World getWorldOf(org.bukkit.World bukkit) {
		return ServerWorld.getWorldOf(bukkit).getWorld();
	}

	public net.minecraft.server.v1_16_R3.World getNMS() {
		return world;
	}

	public org.bukkit.World getBukkit() {
		return getServerWorld().getBukkit();
	}

	public ServerWorld getServerWorld() {
		Validate.isTrue(isServerWorld());
		return ServerWorld.getWorldOf((WorldServer) world);
	}

	public boolean isServerWorld() {
		return world instanceof WorldServer;
	}
}
