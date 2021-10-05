package io.github.riesenpilz.nmsUtilities.packet.playOut;

import org.bukkit.entity.Player;

import io.github.riesenpilz.nmsUtilities.reflections.Field;
import net.minecraft.server.v1_16_R3.Packet;
import net.minecraft.server.v1_16_R3.PacketListenerPlayOut;
import net.minecraft.server.v1_16_R3.PacketPlayOutEntity.PacketPlayOutEntityLook;

/**
 * https://wiki.vg/Protocol#Entity_Rotation
 * <p>
 * This packet is sent by the server when an entity rotates.
 * <p>
 * Packet ID: 0x2B<br>
 * State: Play<br>
 * Bound To: Client
 * 
 * @author Martin
 *
 */
public class PacketPlayOutEntityRotationEvent extends PacketPlayOutEntityEvent {

	/**
	 * New angle, not a delta.
	 */
	private byte yaw;

	/**
	 * New angle, not a delta.
	 */
	private byte pitch;
	private boolean onGround;

	public PacketPlayOutEntityRotationEvent(Player injectedPlayer, PacketPlayOutEntityLook packet) {
		super(injectedPlayer, Field.getFromSuper(packet, "a", int.class));
		yaw = Field.getFromSuper(packet, "e", byte.class);
		pitch = Field.getFromSuper(packet, "f", byte.class);
		onGround = Field.getFromSuper(packet, "g", boolean.class);
	}

	public PacketPlayOutEntityRotationEvent(Player injectedPlayer, int entityId, byte yaw, byte pitch,
			boolean onGround) {
		super(injectedPlayer, entityId);
		this.yaw = yaw;
		this.pitch = pitch;
		this.onGround = onGround;
	}

	public byte getYaw() {
		return yaw;
	}

	public byte getPitch() {
		return pitch;
	}

	public boolean isOnGround() {
		return onGround;
	}

	@Override
	public Packet<PacketListenerPlayOut> getNMS() {
		return new PacketPlayOutEntityLook(getEntityId(), yaw, pitch, onGround);
	}

	@Override
	public int getPacketID() {
		return 0x2B;
	}

	@Override
	public String getProtocolURLString() {
		return "https://wiki.vg/Protocol#Entity_Rotation";
	}
}
