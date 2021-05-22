package io.github.riesenpilz.nms.packet.playIn;

import org.bukkit.entity.Player;
import org.bukkit.entity.minecart.CommandMinecart;

import io.github.riesenpilz.nms.entity.Entity;
import io.github.riesenpilz.nms.reflections.Field;
import net.minecraft.server.v1_16_R3.Packet;
import net.minecraft.server.v1_16_R3.PacketListenerPlayIn;
import net.minecraft.server.v1_16_R3.PacketPlayInSetCommandMinecart;

/**
 * https://wiki.vg/Protocol#Update_Command_Block_Minecart<br>
 * <br>
 * Packet ID: 0x27<br>
 * State: Play<br>
 * Bound To: Server
 * 
 * @author Martin
 *
 */
public class PacketPlayInUpdateCommandMinecartEvent extends PacketPlayInEvent {

	private CommandMinecart commandMinecart;
	private String command;

	/**
	 * If false, the output of the previous command will not be stored within the
	 * command block.
	 */
	private boolean trackOutput;

	public PacketPlayInUpdateCommandMinecartEvent(Player injectedPlayer, PacketPlayInSetCommandMinecart packet) {
		super(injectedPlayer);
		final int entityID = (int) new Field(PacketPlayInSetCommandMinecart.class, "a").get(packet);
		final org.bukkit.entity.Entity entity = new Entity(entityID, injectedPlayer.getWorld()).getEntity();
		commandMinecart = entity instanceof CommandMinecart ? (CommandMinecart) entity : null;
		command = packet.b();
		trackOutput = packet.c();
	}

	public PacketPlayInUpdateCommandMinecartEvent(Player injectedPlayer, CommandMinecart commandMinecart,
			String command, boolean trackOutput) {
		super(injectedPlayer);
		this.commandMinecart = commandMinecart;
		this.command = command;
		this.trackOutput = trackOutput;
	}

	public CommandMinecart getCommandMinecart() {
		return commandMinecart;
	}

	public String getCommand() {
		return command;
	}

	public boolean isTrackOutput() {
		return trackOutput;
	}

	@Override
	public Packet<PacketListenerPlayIn> getNMS() {
		final PacketPlayInSetCommandMinecart packet = new PacketPlayInSetCommandMinecart();
		new Field(PacketPlayInSetCommandMinecart.class, "a").set(packet, commandMinecart.getEntityId());
		new Field(PacketPlayInSetCommandMinecart.class, "b").set(packet, command);
		new Field(PacketPlayInSetCommandMinecart.class, "c").set(packet, trackOutput);
		return packet;
	}

	@Override
	public int getPacketID() {
		return 39;
	}

	@Override
	public String getProtocolURLString() {
		return "https://wiki.vg/Protocol#Query_Block_NBT";
	}
}
