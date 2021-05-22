package io.github.riesenpilz.nms.packet.playIn;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import io.github.riesenpilz.nms.block.CommandBlock.CommandBlockType;
import io.github.riesenpilz.nms.reflections.Field;
import net.minecraft.server.v1_16_R3.BlockPosition;
import net.minecraft.server.v1_16_R3.Packet;
import net.minecraft.server.v1_16_R3.PacketListenerPlayIn;
import net.minecraft.server.v1_16_R3.PacketPlayInSetCommandBlock;

/**
 * https://wiki.vg/Protocol#Update_Command_Block<br>
 * <br>
 * Packet ID: 0x26<br>
 * State: Play<br>
 * Bound To: Server
 * 
 * @author Martin
 *
 */
public class PacketPlayInUpdateCommandBlockEvent extends PacketPlayInEvent {

	private Location blockLocation;
	private String command;

	/**
	 * if false, the output of the previous command will not be stored within the
	 * command block
	 */
	private boolean trackOutput;
	private boolean conditional;
	private boolean automatic;
	private CommandBlockType commandBlockType;

	public PacketPlayInUpdateCommandBlockEvent(Player injectedPlayer, PacketPlayInSetCommandBlock packet) {
		super(injectedPlayer);
		blockLocation = new Location(injectedPlayer.getWorld(), packet.b().getX(), packet.b().getY(),
				packet.b().getZ());
		command = packet.c();
		trackOutput = packet.d();
		conditional = packet.e();
		automatic = packet.f();
		commandBlockType = CommandBlockType.getType(packet.g());
	}

	public PacketPlayInUpdateCommandBlockEvent(Player injectedPlayer, Location blockLocation, String command,
			boolean trackOutput, boolean conditional, boolean automatic, CommandBlockType type) {
		super(injectedPlayer);
		this.blockLocation = blockLocation;
		this.command = command;
		this.trackOutput = trackOutput;
		this.conditional = conditional;
		this.automatic = automatic;
		commandBlockType = type;
	}

	public Location getBlockLocation() {
		return blockLocation;
	}

	public String getCommand() {
		return command;
	}

	public CommandBlockType getCommandBlockType() {
		return commandBlockType;
	}

	public boolean isTrackOutput() {
		return trackOutput;
	}

	public boolean isConditional() {
		return conditional;
	}

	public boolean isAutomatic() {
		return automatic;
	}

	@Override
	public Packet<PacketListenerPlayIn> getNMS() {
		final PacketPlayInSetCommandBlock packet = new PacketPlayInSetCommandBlock();
		new Field(PacketPlayInSetCommandBlock.class, "a").set(packet,
				new BlockPosition(blockLocation.getX(), blockLocation.getY(), blockLocation.getZ()));
		new Field(PacketPlayInSetCommandBlock.class, "b").set(packet, command);
		new Field(PacketPlayInSetCommandBlock.class, "c").set(packet, trackOutput);
		new Field(PacketPlayInSetCommandBlock.class, "d").set(packet, conditional);
		new Field(PacketPlayInSetCommandBlock.class, "e").set(packet, automatic);
		new Field(PacketPlayInSetCommandBlock.class, "f").set(packet, commandBlockType);
		return packet;
	}

	@Override
	public int getPacketID() {
		return 38;
	}

	@Override
	public String getProtocolURLString() {
		return "https://wiki.vg/Protocol#Update_Command_Block";
	}

}
