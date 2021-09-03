package io.github.riesenpilz.nms.packet.playOut;

import org.bukkit.entity.Player;

import io.github.riesenpilz.nms.reflections.Field;
import net.minecraft.server.v1_16_R3.Packet;
import net.minecraft.server.v1_16_R3.PacketListenerPlayOut;
import net.minecraft.server.v1_16_R3.PacketPlayOutEntityStatus;

/**
 * https://wiki.vg/Protocol#Entity_Status
 * <p>
 * Entity statuses generally trigger an animation for an entity. The available
 * statuses vary by the entity's type (and are available to subclasses of that
 * type as well).
 * <p>
 * Packet ID: 0x1A<br>
 * State: Play<br>
 * Bound To: Client
 *
 * @author Martin
 *
 */
public class PacketPlayOutEntityStatusEvent extends PacketPlayOutEvent {

	private int entityID;

	/**
	 * See Entity statuses for a list of which statuses are valid for each type of
	 * entity.
	 */
	private byte entityStatus;

	public PacketPlayOutEntityStatusEvent(Player injectedPlayer, PacketPlayOutEntityStatus packet) {
		super(injectedPlayer);
		entityID = Field.get(packet, "a", int.class);
		entityStatus = Field.get(packet, "b", byte.class);
	}

	public PacketPlayOutEntityStatusEvent(Player injectedPlayer, int entityID, byte entityStatus) {
		super(injectedPlayer);
		this.entityID = entityID;
		this.entityStatus = entityStatus;
	}

	public int getEntityID() {
		return entityID;
	}

	public byte getEntityStatus() {
		return entityStatus;
	}

	@Override
	public Packet<PacketListenerPlayOut> getNMS() {
		final PacketPlayOutEntityStatus packet = new PacketPlayOutEntityStatus();
		Field.set(packet, "a", entityID);
		Field.set(packet, "b", entityStatus);
		return packet;
	}

	@Override
	public int getPacketID() {
		return 0x1A;
	}

	@Override
	public String getProtocolURLString() {
		return "https://wiki.vg/Protocol#Entity_Status";
	}
}