package io.github.riesenpilz.nms.packet.playOut;

import org.bukkit.entity.Player;

import io.github.riesenpilz.nms.reflections.Field;
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

	private short velocityX;
	private short velocityY;
	private short velocityZ;
	
	public PacketPlayOutEntityVelocityEvent(Player injectedPlayer, PacketPlayOutEntityVelocity packet) {
		super(injectedPlayer, packet);
		
		velocityX = Field.get(packet, "b", short.class);
		velocityY = Field.get(packet, "c", short.class);
		velocityZ = Field.get(packet, "d", short.class);
	}

	public PacketPlayOutEntityVelocityEvent(Player injectedPlayer, int entityId, short velocityX, short velocityY,
			short velocityZ) {
		super(injectedPlayer, entityId);
		this.velocityX = velocityX;
		this.velocityY = velocityY;
		this.velocityZ = velocityZ;
	}


	public short getVelocityX() {
		return velocityX;
	}

	public short getVelocityY() {
		return velocityY;
	}

	public short getVelocityZ() {
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
