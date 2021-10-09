package io.github.riesenpilz.nmsUtilities.packet.playIn;

import org.apache.commons.lang.Validate;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import io.github.riesenpilz.nmsUtilities.block.CommandBlockType;
import io.github.riesenpilz.nmsUtilities.packet.HasBlockPosition;
import io.github.riesenpilz.nmsUtilities.packet.PacketUtils;
import io.github.riesenpilz.nmsUtilities.reflections.Field;
import net.minecraft.server.v1_16_R3.Packet;
import net.minecraft.server.v1_16_R3.PacketListenerPlayIn;
import net.minecraft.server.v1_16_R3.PacketPlayInSetCommandBlock;

/**
 * https://wiki.vg/Protocol#Update_Command_Block
 * <p>
 * Packet ID: 0x26<br>
 * State: Play<br>
 * Bound To: Server
 *
 * @author Martin
 *
 */
public class PacketPlayInUpdateCommandBlockEvent extends PacketPlayInEvent implements HasBlockPosition {

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
		Validate.notNull(packet);
		blockLocation = PacketUtils.toLocation(packet.b(), injectedPlayer.getWorld());
		command = packet.c();
		trackOutput = packet.d();
		conditional = packet.e();
		automatic = packet.f();
		commandBlockType = CommandBlockType.getType(packet.g());
	}

	public PacketPlayInUpdateCommandBlockEvent(Player injectedPlayer, Location blockLocation, String command,
			boolean trackOutput, boolean conditional, boolean automatic, CommandBlockType type) {
		super(injectedPlayer);

		Validate.notNull(blockLocation);
		Validate.notNull(command);
		Validate.notNull(type);
		
		this.blockLocation = blockLocation;
		this.command = command;
		this.trackOutput = trackOutput;
		this.conditional = conditional;
		this.automatic = automatic;
		commandBlockType = type;
	}

	@Override
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
		Field.set(packet, "a", PacketUtils.toBlockPosition(blockLocation));
		Field.set(packet, "b", command);
		Field.set(packet, "c", trackOutput);
		Field.set(packet, "d", conditional);
		Field.set(packet, "e", automatic);
		Field.set(packet, "f", commandBlockType);
		return packet;
	}

	@Override
	public int getPacketID() {
		return 0x26;
	}

	@Override
	public String getProtocolURLString() {
		return "https://wiki.vg/Protocol#Update_Command_Block";
	}

}
