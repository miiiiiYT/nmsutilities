package io.github.riesenpilz.nmsUtilities.packet.playIn;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import io.github.riesenpilz.nmsUtilities.packet.HasBlockPosition;
import io.github.riesenpilz.nmsUtilities.packet.PacketUtils;
import io.github.riesenpilz.nmsUtilities.reflections.Field;
import net.minecraft.server.v1_16_R3.Packet;
import net.minecraft.server.v1_16_R3.PacketListenerPlayIn;
import net.minecraft.server.v1_16_R3.PacketPlayInUpdateSign;

/**
 * https://wiki.vg/Protocol#Update_Sign
 * <p>
 * This message is sent from the client to the server when the “Done” button is
 * pushed after placing a sign.
 * <p>
 * The server only accepts this packet after Open Sign Editor, otherwise this
 * packet is silently ignored.
 * <p>
 * Packet ID: 0x2B<br>
 * State: Play<br>
 * Bound To: Server
 *
 * @author Martin
 *
 */
public class PacketPlayInUpdateSignEvent extends PacketPlayInEvent implements HasBlockPosition {

	private Location blockLocation;

	/**
	 * 4 Strings (384 chars max)
	 */
	private String[] text;

	public PacketPlayInUpdateSignEvent(Player injectedPlayer, PacketPlayInUpdateSign packet) {
		super(injectedPlayer);
		blockLocation = PacketUtils.toLocation(packet.b(), injectedPlayer.getWorld());
		text = packet.c();
	}

	public PacketPlayInUpdateSignEvent(Player injectedPlayer, Location blockLocation, String[] text) {
		super(injectedPlayer);
		this.blockLocation = blockLocation;
		this.text = text;
	}

	@Override
	public Location getBlockLocation() {
		return blockLocation;
	}

	public String[] getText() {
		return text;
	}

	@Override
	public Packet<PacketListenerPlayIn> getNMS() {
		final PacketPlayInUpdateSign packet = new PacketPlayInUpdateSign();
		Field.set(packet, "a", PacketUtils.toBlockPosition(blockLocation));
		Field.set(packet, "b", text);
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
