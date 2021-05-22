package io.github.riesenpilz.nms.packet.playIn;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import io.github.riesenpilz.nms.reflections.Field;
import net.minecraft.server.v1_16_R3.BlockPosition;
import net.minecraft.server.v1_16_R3.Packet;
import net.minecraft.server.v1_16_R3.PacketListenerPlayIn;
import net.minecraft.server.v1_16_R3.PacketPlayInUpdateSign;

/**
 * https://wiki.vg/Protocol#Update_Sign<br>
 * <br>
 * This message is sent from the client to the server when the “Done” button is
 * pushed after placing a sign.<br>
 * <br>
 * The server only accepts this packet after Open Sign Editor, otherwise this
 * packet is silently ignored.<br>
 * <br>
 * Packet ID: 0x2B<br>
 * State: Play<br>
 * Bound To: Server
 * 
 * @author Martin
 *
 */
public class PacketPlayInUpdateSignEvent extends PacketPlayInEvent {

	private Location blockLocation;

	/**
	 * 4 Strings (384 chars)
	 */
	private String[] text;

	public PacketPlayInUpdateSignEvent(Player injectedPlayer, PacketPlayInUpdateSign packet) {
		super(injectedPlayer);
		blockLocation = new Location(injectedPlayer.getWorld(), packet.b().getX(), packet.b().getY(),
				packet.b().getZ());
		text = packet.c();
	}

	public PacketPlayInUpdateSignEvent(Player injectedPlayer, Location blockLocation, String[] text) {
		super(injectedPlayer);
		this.blockLocation = blockLocation;
		this.text = text;
	}

	public Location getBlockLocation() {
		return blockLocation;
	}

	public String[] getText() {
		return text;
	}

	@Override
	public Packet<PacketListenerPlayIn> getNMS() {
		final PacketPlayInUpdateSign packet = new PacketPlayInUpdateSign();
		new Field(PacketPlayInUpdateSign.class, "a").set(packet,
				new BlockPosition(blockLocation.getX(), blockLocation.getY(), blockLocation.getZ()));
		new Field(PacketPlayInUpdateSign.class, "b").set(packet, text);
		return packet;
	}

	@Override
	public int getPacketID() {
		return 0x2B;
	}

	@Override
	public String getProtocolURLString() {
		return "https://wiki.vg/Protocol#Update_Sign";
	}
}
