package io.github.riesenpilz.nmsUtilities.packet.playIn;

import org.bukkit.entity.Player;

import io.github.riesenpilz.nmsUtilities.reflections.Field;
import net.minecraft.server.v1_16_R3.Packet;
import net.minecraft.server.v1_16_R3.PacketListenerPlayIn;
import net.minecraft.server.v1_16_R3.PacketPlayInSetCommandMinecart;

/**
 * https://wiki.vg/Protocol#Update_Command_Block_Minecart
 * <p>
 * Packet ID: 0x27<br>
 * State: Play<br>
 * Bound To: Server
 *
 * @author Martin
 *
 */
public class PacketPlayInUpdateCommandMinecartEvent extends PacketPlayInEntityEvent {

	private String command;

	/**
	 * If false, the output of the previous command will not be stored within the
	 * command block.
	 */
	private boolean trackOutput;

	public PacketPlayInUpdateCommandMinecartEvent(Player injectedPlayer, PacketPlayInSetCommandMinecart packet) {
		super(injectedPlayer, packet);
		command = packet.b();
		trackOutput = packet.c();
	}

	public PacketPlayInUpdateCommandMinecartEvent(Player injectedPlayer, int entityId, String command,
			boolean trackOutput) {
		super(injectedPlayer, entityId);
		this.command = command;
		this.trackOutput = trackOutput;
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
		Field.set(packet, "a", getEntityId());
		Field.set(packet, "b", command);
		Field.set(packet, "c", trackOutput);
		return packet;
	}

	@Override
	public int getPacketID() {
		return 0x27;
	}

	@Override
	public String getProtocolURLString() {
		return "https://wiki.vg/Protocol#Query_Block_NBT";
	}
}
