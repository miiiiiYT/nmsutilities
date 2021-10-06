package io.github.riesenpilz.nmsUtilities.block;

import org.apache.commons.lang.Validate;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.BlockFace;
import org.bukkit.craftbukkit.v1_16_R3.block.CraftBlock;
import org.bukkit.util.Vector;

import io.github.riesenpilz.nmsUtilities.packet.PacketUtils;
import net.minecraft.server.v1_16_R3.MovingObjectPositionBlock;

public class MovingBlock {
	private MovingObjectPositionBlock nms;

	protected MovingBlock(MovingObjectPositionBlock nms) {
		Validate.notNull(nms);
		this.nms = nms;
	}

	public MovingBlock(Vector velocity, BlockFace face, Location location, boolean flag) {
		Validate.notNull(velocity);
		Validate.notNull(face);
		Validate.notNull(location);
		Validate.notNull(flag);
		nms = new MovingObjectPositionBlock(PacketUtils.toVec3D(velocity), CraftBlock.blockFaceToNotch(face),
				PacketUtils.toBlockPosition(location), flag);
	}

	public static MovingBlock getMovingBlockOf(MovingObjectPositionBlock nms) {
		return new MovingBlock(nms);
	}

	public void setVelocity(Vector velocity) {
		Validate.notNull(velocity);
		nms = new MovingObjectPositionBlock(PacketUtils.toVec3D(velocity), nms.getDirection(), nms.getBlockPosition(),
				nms.d());
	}

	public Vector getVelocity() {
		return PacketUtils.toVetor(nms.getPos());
	}

	public void setLocation(Location location) {
		Validate.notNull(location);
		nms = nms.a(PacketUtils.toBlockPosition(location));
	}

	public Location getLoation(World world) {
		Validate.notNull(world);
		return PacketUtils.toLocation(nms.getBlockPosition(), world);
	}

	public void setFacing(BlockFace face) {
		Validate.notNull(face);
		nms = nms.a(CraftBlock.blockFaceToNotch(face));
	}

	public BlockFace getFacing() {
		return CraftBlock.notchToBlockFace(nms.getDirection());
	}

	public MovingObjectPositionBlock getNMS() {
		return nms;
	}

}
