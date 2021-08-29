package io.github.riesenpilz.nms.packet.playOut;

import org.bukkit.entity.Player;

import io.github.riesenpilz.nms.reflections.Field;
import net.minecraft.server.v1_16_R3.Packet;
import net.minecraft.server.v1_16_R3.PacketListenerPlayOut;
import net.minecraft.server.v1_16_R3.PacketPlayOutEntity.PacketPlayOutRelEntityMoveLook;

/**
 * https://wiki.vg/Protocol#Entity_Position_and_Rotation
 * <p>
 * This packet is sent by the server when an entity rotates and moves. Since a
 * short range is limited from -32768 to 32767, and movement is offset of
 * fixed-point numbers, this packet allows at most 8 blocks movement in any
 * direction. (-32768 / (32 * 128) == -8)
 * <p>
 * Packet ID: 0x2A<br>
 * State: Play<br>
 * Bound To: Client
 * 
 * @author Martin
 *
 */
public class PacketPlayOutEntityPositionAndLookEvent extends PacketPlayOutEvent {

	private int entityID;

	/**
	 * Change in X position as (currentX * 32 - prevX * 32) * 128.
	 */
	private short deltaX;

	/**
	 * Change in Y position as (currentY * 32 - prevY * 32) * 128.
	 */
	private short deltaY;

	/**
	 * Change in Z position as (currentZ * 32 - prevZ * 32) * 128.
	 */
	private short deltaZ;

	/**
	 * New angle, not a delta.
	 */
	private byte yaw;

	/**
	 * New angle, not a delta.
	 */
	private byte pitch;
	private boolean onGround;

	public PacketPlayOutEntityPositionAndLookEvent(Player injectedPlayer, PacketPlayOutRelEntityMoveLook packet) {
		super(injectedPlayer);
		entityID = Field.get(packet, "a", int.class);
		deltaX = Field.get(packet, "b", short.class);
		deltaY = Field.get(packet, "c", short.class);
		deltaZ = Field.get(packet, "d", short.class);
		yaw = Field.get(packet, "e", byte.class);
		pitch = Field.get(packet, "f", byte.class);
		onGround = Field.get(packet, "g", boolean.class);
	}

	

	public PacketPlayOutEntityPositionAndLookEvent(Player injectedPlayer, int entityID, short deltaX, short deltaY,
			short deltaZ, byte yaw, byte pitch, boolean onGround) {
		super(injectedPlayer);
		this.entityID = entityID;
		this.deltaX = deltaX;
		this.deltaY = deltaY;
		this.deltaZ = deltaZ;
		this.yaw = yaw;
		this.pitch = pitch;
		this.onGround = onGround;
	}



	public int getEntityID() {
		return entityID;
	}

	public short getDeltaX() {
		return deltaX;
	}

	public short getDeltaY() {
		return deltaY;
	}

	public short getDeltaZ() {
		return deltaZ;
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
		return new PacketPlayOutRelEntityMoveLook(entityID, deltaX, deltaY, deltaZ, yaw, pitch, onGround);
	}

	@Override
	public int getPacketID() {
		return 0x2A;
	}

	@Override
	public String getProtocolURLString() {
		return "https://wiki.vg/Protocol#Entity_Position_and_Rotation";
	}
}
