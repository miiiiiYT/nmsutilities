package io.github.riesenpilz.nms.packet.playOut;

import org.bukkit.entity.Player;

import io.github.riesenpilz.nms.reflections.Field;
import net.minecraft.server.v1_16_R3.Packet;
import net.minecraft.server.v1_16_R3.PacketListenerPlayOut;
import net.minecraft.server.v1_16_R3.PacketPlayOutViewDistance;

/**
 * https://wiki.vg/Protocol#Update_View_Distance
 * <p>
 * Sent by the integrated singleplayer server when changing render distance.
 * Does not appear to be used by the dedicated server, as view-distance in
 * server.properties cannot be changed at runtime.
 * <p>
 * Packet ID: 0x4A<br>
 * State: Play<br>
 * Bound To: Client
 * 
 * @author Martin
 *
 */
public class PacketPlayOutUpdateViewDistanceEvent extends PacketPlayOutEvent {

	/**
	 * Render distance (2-32).
	 */
	private int viewDistance;

	public PacketPlayOutUpdateViewDistanceEvent(Player injectedPlayer, PacketPlayOutViewDistance packet) {
		super(injectedPlayer);

		viewDistance = Field.get(packet, "a", int.class);
	}

	public PacketPlayOutUpdateViewDistanceEvent(Player injectedPlayer, int viewDistance) {
		super(injectedPlayer);
		this.viewDistance = viewDistance;
	}

	public int getViewDistance() {
		return viewDistance;
	}

	@Override
	public Packet<PacketListenerPlayOut> getNMS() {
		return new PacketPlayOutViewDistance(viewDistance);
	}

	@Override
	public int getPacketID() {
		return 0x4A;
	}

	@Override
	public String getProtocolURLString() {
		return "https://wiki.vg/Protocol#Update_View_Distance";
	}
}
