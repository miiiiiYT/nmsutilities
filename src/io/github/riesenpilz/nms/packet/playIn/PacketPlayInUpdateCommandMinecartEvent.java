package io.github.riesenpilz.nms.packet.playIn;

import org.bukkit.entity.Player;

import io.github.riesenpilz.nms.reflections.Field;
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
public class PacketPlayInUpdateCommandMinecartEvent extends PacketPlayInEvent {

	private int entityID;
	private String command;

	/**
	 * If false, the output of the previous command will not be stored within the
	 * command block.
	 */
	private boolean trackOutput;

	public PacketPlayInUpdateCommandMinecartEvent(Player injectedPlayer, PacketPlayInSetCommandMinecart packet) {
		super(injectedPlayer);
		entityID = Field.get(packet, "a", int.class);
		command = packet.b();
		trackOutput = packet.c();
	}

	public PacketPlayInUpdateCommandMinecartEvent(Player injectedPlayer, int entityID, String command,
			boolean trackOutput) {
		super(injectedPlayer);
		this.entityID = entityID;
		this.command = command;
		this.trackOutput = trackOutput;
	}

	public int getEntityID() {
		return entityID;
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
		Field.set(packet, "a", entityID);
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
