package io.github.riesenpilz.nmsUtilities.packet.playOut;

import org.bukkit.entity.Player;

import io.github.riesenpilz.nmsUtilities.reflections.Field;
import net.minecraft.server.v1_16_R3.Packet;
import net.minecraft.server.v1_16_R3.PacketListenerPlayOut;
import net.minecraft.server.v1_16_R3.PacketPlayOutEntityVelocity;

/**
 * https://wiki.vg/Protocol#Entity_Velocity
 * <p>
 * Velocity is believed to be in units of 1/8000 of a block per server tick
 * (50ms); for example, -1343 would move (-1343 / 8000) = -0.167875 blocks per
 * tick (or -3,3575 blocks per second).
 * <p>
 * Packet ID: 0x4F<br>
 * State: Play<br>
 * Bound To: Client
 * 
 * @author Martin
 *
 */
public class PacketPlayOutEntityVelocityEvent extends PacketPlayOutEntityEvent {

	private int velocityX;
	private int velocityY;
	private int velocityZ;

	public PacketPlayOutEntityVelocityEvent(Player injectedPlayer, PacketPlayOutEntityVelocity packet) {
		super(injectedPlayer, packet);

		velocityX = Field.get(packet, "b", int.class);
		velocityY = Field.get(packet, "c", int.class);
		velocityZ = Field.get(packet, "d", int.class);
	}

	public PacketPlayOutEntityVelocityEvent(Player injectedPlayer, int entityId, short velocityX, short velocityY,
			short velocityZ) {
		super(injectedPlayer, entityId);

		this.velocityX = velocityX;
		this.velocityY = velocityY;
		this.velocityZ = velocityZ;
	}

	public int getVelocityX() {
		return velocityX;
	}

	public int getVelocityY() {
		return velocityY;
	}

	public int getVelocityZ() {
		return velocityZ;
	}

	@Override
	public Packet<PacketListenerPlayOut> getNMS() {
		final PacketPlayOutEntityVelocity packet = new PacketPlayOutEntityVelocity();
		Field.set(packet, "a", getEntityId());
		Field.set(packet, "b", velocityX);
		Field.set(packet, "c", velocityY);
		Field.set(packet, "d", velocityZ);
		return packet;
	}

	@Override
	public int getPacketID() {
		return 0x4F;
	}

	@Override
	public String getProtocolURLString() {
		return "https://wiki.vg/Protocol#Entity_Velocity";
	}
}
