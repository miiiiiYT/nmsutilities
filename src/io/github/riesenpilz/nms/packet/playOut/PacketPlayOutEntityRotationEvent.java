package io.github.riesenpilz.nms.packet.playOut;

import org.bukkit.entity.Player;

import io.github.riesenpilz.nms.reflections.Field;
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
public class PacketPlayOutEntityRotationEvent extends PacketPlayOutEvent {

	private int entityID;
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
		super(injectedPlayer);
		entityID = Field.get(packet, "a", int.class);
		yaw = Field.get(packet, "e", byte.class);
		pitch = Field.get(packet, "f", byte.class);
		onGround = Field.get(packet, "g", boolean.class);
	}

	public PacketPlayOutEntityRotationEvent(Player injectedPlayer, int entityID, byte yaw, byte pitch, boolean onGround) {
		super(injectedPlayer);
		this.entityID = entityID;
		this.yaw = yaw;
		this.pitch = pitch;
		this.onGround = onGround;
	}

	public int getEntityID() {
		return entityID;
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
		return new PacketPlayOutEntityLook(entityID, yaw, pitch, onGround);
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
