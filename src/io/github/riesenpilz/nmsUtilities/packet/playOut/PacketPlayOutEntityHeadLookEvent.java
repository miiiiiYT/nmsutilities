package io.github.riesenpilz.nmsUtilities.packet.playOut;

import org.bukkit.entity.Player;

import io.github.riesenpilz.nmsUtilities.reflections.Field;
import net.minecraft.server.v1_16_R3.Packet;
import net.minecraft.server.v1_16_R3.PacketListenerPlayOut;
import net.minecraft.server.v1_16_R3.PacketPlayOutEntityHeadRotation;

/**
 * https://wiki.vg/Protocol#Entity_Head_Look
 * <p>
 * Changes the direction an entity's head is facing.
 * <p>
 * While sending the Entity Look packet changes the vertical rotation of the
 * head, sending this packet appears to be necessary to rotate the head
 * horizontally.
 * <p>
 * Packet ID: 0x3E<br>
 * State: Play<br>
 * Bound To: Client
 * 
 * @author Martin
 *
 */
public class PacketPlayOutEntityHeadLookEvent extends PacketPlayOutEntityEvent {

	/**
	 * New angle, not a delta.
	 */
	private byte angle;

	public PacketPlayOutEntityHeadLookEvent(Player injectedPlayer, PacketPlayOutEntityHeadRotation packet) {
		super(injectedPlayer, packet);

		angle = Field.get(packet, "b", byte.class);
	}

	public PacketPlayOutEntityHeadLookEvent(Player injectedPlayer, int entityId, byte angle) {
		super(injectedPlayer, entityId);

		this.angle = angle;
	}

	public byte getAngle() {
		return angle;
	}

	@Override
	public Packet<PacketListenerPlayOut> getNMS() {
		final PacketPlayOutEntityHeadRotation packet = new PacketPlayOutEntityHeadRotation();
		Field.set(packet, "a", getEntityId());
		Field.set(packet, "b", angle);
		return packet;
	}

	@Override
	public int getPacketID() {
		return 0x3E;
	}

	@Override
	public String getProtocolURLString() {
		return "https://wiki.vg/Protocol#Entity_Head_Look";
	}
}
