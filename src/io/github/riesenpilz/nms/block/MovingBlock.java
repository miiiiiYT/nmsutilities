package io.github.riesenpilz.nms.block;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.BlockFace;
import org.bukkit.craftbukkit.v1_16_R3.block.CraftBlock;
import org.bukkit.util.Vector;

import io.github.riesenpilz.nms.packet.PacketUtils;
import net.minecraft.server.v1_16_R3.MovingObjectPositionBlock;

public class MovingBlock {
	private MovingObjectPositionBlock movingBlock;

	public MovingBlock(MovingObjectPositionBlock nms) {
		movingBlock = nms;
	}

	public void setVelocity(Vector velocity) {
		movingBlock = new MovingObjectPositionBlock(PacketUtils.toVec3D(velocity), movingBlock.getDirection(),
				movingBlock.getBlockPosition(), movingBlock.d());
	}

	public Vector getVelocity() {
		return PacketUtils.toVetor(movingBlock.getPos());
	}

	public void setLocation(Location location) {
		movingBlock = movingBlock.a(PacketUtils.toBlockPosition(location));
	}

	public Location getLoation(World world) {
		return PacketUtils.toLocation(movingBlock.getBlockPosition(), world);
	}

	public void setFacing(BlockFace face) {
		movingBlock = movingBlock.a(CraftBlock.blockFaceToNotch(face));
	}

	public BlockFace getFacing() {
		return CraftBlock.notchToBlockFace(movingBlock.getDirection());
	}

	public MovingObjectPositionBlock getNMS() {
		return movingBlock;
	}

}
