package io.github.riesenpilz.nms.packet.playIn;

import org.bukkit.Location;
import org.bukkit.block.BlockFace;
import org.bukkit.craftbukkit.v1_16_R3.block.CraftBlock;
import org.bukkit.entity.Player;

import io.github.riesenpilz.nms.entity.player.DigType;
import io.github.riesenpilz.nms.packet.PacketUtils;
import io.github.riesenpilz.nms.reflections.Field;
import net.minecraft.server.v1_16_R3.Packet;
import net.minecraft.server.v1_16_R3.PacketListenerPlayIn;
import net.minecraft.server.v1_16_R3.PacketPlayInBlockDig;

/**
 * https://wiki.vg/Protocol#Player_Digging
 * <p>
 * Sent when the player mines a block. A Notchian server only accepts digging
 * packets with coordinates within a 6-unit radius between the center of the
 * block and 1.5 units from the player's feet (not their eyes).
 * <p>
 * Packet ID: 0x1B<br>
 * State: Play<br>
 * Bound To: Server
 *
 * @author Martin
 *
 */
public class PacketPlayInBlockDigEvent extends PacketPlayInEvent {

	private Location blockLocation;

	/**
	 * The face being hit
	 */
	private BlockFace blockFace;

	private DigType digType;

	public PacketPlayInBlockDigEvent(Player injectedPlayer, Location blockLocation, BlockFace blockFace,
			DigType digType) {
		super(injectedPlayer);
		this.blockLocation = blockLocation;
		this.blockFace = blockFace;
		this.digType = digType;
	}

	public PacketPlayInBlockDigEvent(Player injectedPlayer, PacketPlayInBlockDig packet) {
		super(injectedPlayer);
		blockLocation = new Location(injectedPlayer.getWorld(), packet.b().getX(), packet.b().getY(),
				packet.b().getZ());
		blockFace = CraftBlock.notchToBlockFace(packet.c());
		digType = DigType.getPlayerDigType(packet.d());
	}

	public BlockFace getBlockFace() {
		return blockFace;
	}

	public Location getBlockLocation() {
		return blockLocation;
	}

	public DigType getDigType() {
		return digType;
	}

	@Override
	public Packet<PacketListenerPlayIn> getNMS() {
		final PacketPlayInBlockDig packet = new PacketPlayInBlockDig();
		Field.set(packet, "a", PacketUtils.toBlockPosition(blockLocation));
		Field.set(packet, "b", CraftBlock.blockFaceToNotch(blockFace));
		Field.set(packet, "c", digType.getNMS());
		return packet;
	}

	@Override
	public int getPacketID() {
		return 0x1B;
	}

	@Override
	public String getProtocolURLString() {
		return "https://wiki.vg/Protocol#Player_Digging";
	}
}
