package io.github.riesenpilz.nmsUtilities.packet.playIn;

import org.apache.commons.lang.Validate;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import io.github.riesenpilz.nmsUtilities.packet.HasBlockPosition;
import io.github.riesenpilz.nmsUtilities.packet.PacketUtils;
import io.github.riesenpilz.nmsUtilities.reflections.Field;
import net.minecraft.server.v1_16_R3.Packet;
import net.minecraft.server.v1_16_R3.PacketListenerPlayIn;
import net.minecraft.server.v1_16_R3.PacketPlayInJigsawGenerate;

/**
 * https://wiki.vg/Protocol#Generate_Structure
 * <p>
 * Sent when Generate is pressed on the Jigsaw Block interface.
 * <p>
 * Packet ID: 0x0F<br>
 * State: Play<br>
 * Bound To: Server
 *
 * @author Martin
 *
 */
public class PacketPlayInGenerateStructureEvent extends PacketPlayInEvent implements HasBlockPosition {

	private Location blockLocation;

	/**
	 * Value of the levels slider/max depth to generate.
	 */
	private int levels;
	private boolean keepJigsaws;

	public PacketPlayInGenerateStructureEvent(Player injectedPlayer, PacketPlayInJigsawGenerate packet) {
		super(injectedPlayer);
		Validate.notNull(packet);
		blockLocation = PacketUtils.toLocation(packet.b(), injectedPlayer.getWorld());
		levels = packet.c();
		keepJigsaws = packet.d();
	}

	public PacketPlayInGenerateStructureEvent(Player injectedPlayer, Location blockLocation, int levels,
			boolean keepJigsaws) {
		super(injectedPlayer);
		Validate.notNull(blockLocation);
		this.blockLocation = blockLocation;
		this.levels = levels;
		this.keepJigsaws = keepJigsaws;
	}

	@Override
	public Location getBlockLocation() {
		return blockLocation;
	}

	public int getLevels() {
		return levels;
	}

	public boolean isKeepJigsaws() {
		return keepJigsaws;
	}

	@Override
	public Packet<PacketListenerPlayIn> getNMS() {
		final PacketPlayInJigsawGenerate packet = new PacketPlayInJigsawGenerate();
		Field.set(packet, "a", PacketUtils.toBlockPosition(blockLocation));
		Field.set(packet, "b", levels);
		Field.set(packet, "c", keepJigsaws);
		return packet;
	}

	@Override
	public int getPacketID() {
		return 0x0F;
	}

	@Override
	public String getProtocolURLString() {
		return "https://wiki.vg/Protocol#Generate_Structure";
	}
}
