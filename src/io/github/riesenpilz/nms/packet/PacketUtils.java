package io.github.riesenpilz.nms.packet;

import org.bukkit.Location;
import org.bukkit.World;

import net.minecraft.server.v1_16_R3.BlockPosition;

public class PacketUtils {

	public static Location toLocation(BlockPosition pos, World world) {
		return new Location(world, pos.getX(), pos.getY(), pos.getZ());
	}

	public static BlockPosition toBlockPosition(Location loc) {
		return new BlockPosition(loc.getX(), loc.getY(), loc.getZ());
	}
}
