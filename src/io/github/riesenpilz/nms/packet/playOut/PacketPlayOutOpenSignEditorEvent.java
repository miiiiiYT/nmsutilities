package io.github.riesenpilz.nms.packet.playOut;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import io.github.riesenpilz.nms.packet.PacketUtils;
import io.github.riesenpilz.nms.packet.playIn.PacketPlayInUpdateSignEvent;
import io.github.riesenpilz.nms.reflections.Field;
import net.minecraft.server.v1_16_R3.BlockPosition;
import net.minecraft.server.v1_16_R3.Packet;
import net.minecraft.server.v1_16_R3.PacketListenerPlayOut;
import net.minecraft.server.v1_16_R3.PacketPlayOutOpenSignEditor;

/**
 * https://wiki.vg/Protocol#Open_Sign_Editor
 * <p>
 * Sent when the client has placed a sign and is allowed to send
 * {@link PacketPlayInUpdateSignEvent}. There must already be a sign at the
 * given location (which the client does not do automatically) - send a
 * {@link PacketPlayOutBlockChangeEvent} first.
 * <p>
 * Packet ID: 0x2F<br>
 * State: Play<br>
 * Bound To: Client
 * 
 * @author Martin
 *
 */
public class PacketPlayOutOpenSignEditorEvent extends PacketPlayOutEvent {

	private Location blockPosition;

	public PacketPlayOutOpenSignEditorEvent(Player injectedPlayer, PacketPlayOutOpenSignEditor packet) {
		super(injectedPlayer);
		PacketUtils.toLocation(Field.get(packet, "a", BlockPosition.class), injectedPlayer.getWorld());
	}

	public PacketPlayOutOpenSignEditorEvent(Player injectedPlayer, Location blockPosition) {
		super(injectedPlayer);
		this.blockPosition = blockPosition;
	}

	public Location getBlockPosition() {
		return blockPosition;
	}

	@Override
	public Packet<PacketListenerPlayOut> getNMS() {
		return new PacketPlayOutOpenSignEditor(PacketUtils.toBlockPosition(blockPosition));
	}

	@Override
	public int getPacketID() {
		return 0x2F;
	}

	@Override
	public String getProtocolURLString() {
		return "https://wiki.vg/Protocol#Open_Sign_Editor";
	}
}