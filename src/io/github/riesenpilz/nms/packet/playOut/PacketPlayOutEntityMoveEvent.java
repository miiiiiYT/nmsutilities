package io.github.riesenpilz.nms.packet.playOut;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import io.github.riesenpilz.nms.reflections.Field;
import net.minecraft.server.v1_16_R3.Packet;
import net.minecraft.server.v1_16_R3.PacketListenerPlayOut;
import net.minecraft.server.v1_16_R3.PacketPlayOutEntity.PacketPlayOutRelEntityMove;

/**
 * https://wiki.vg/Protocol#Entity_Position
 * <p>
 * This packet is sent by the server when an entity moves less then 8 blocks; if
 * an entity moves more than 8 blocks {@link PacketPlayOutPayerPositionEvent}
 * should be sent instead.
 * <p>
 * This packet allows at most 8 blocks movement in any direction, because short
 * range is from -32768 to 32767. And 32768 / (128 * 32) = 8.
 * <p>
 * Packet ID: 0x29<br>
 * State: Play<br>
 * Bound To: Client
 * 
 * @author Martin
 *
 */
public class PacketPlayOutEntityMoveEvent extends PacketPlayOutEntityEvent {

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
	private boolean onGround;

	public PacketPlayOutEntityMoveEvent(Player injectedPlayer, PacketPlayOutRelEntityMove packet) {
		super(injectedPlayer, packet);
		deltaX = Field.get(packet, "b", short.class);
		deltaY = Field.get(packet, "c", short.class);
		deltaZ = Field.get(packet, "d", short.class);
		onGround = Field.get(packet, "g", boolean.class);
	}

	public PacketPlayOutEntityMoveEvent(Player injectedPlayer, int entityId, short deltaX, short deltaY, short deltaZ,
			boolean onGround) {
		super(injectedPlayer, entityId);
		this.deltaX = deltaX;
		this.deltaY = deltaY;
		this.deltaZ = deltaZ;
		this.onGround = onGround;
	}

	public Location getFrom() {
		return getEntity().getLocation();
	}

	public Location getTo() {
		return getFrom().clone().add(((double) deltaX) / 128 / 32, ((double) deltaY) / 128 / 32,
				((double) deltaZ) / 128 / 32);
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

	public boolean isOnGround() {
		return onGround;
	}

	@Override
	public Packet<PacketListenerPlayOut> getNMS() {
		return new PacketPlayOutRelEntityMove(getEntityId(), deltaX, deltaY, deltaZ, onGround);
	}

	@Override
	public int getPacketID() {
		return 0x29;
	}

	@Override
	public String getProtocolURLString() {
		return "https://wiki.vg/Protocol#Entity_Position";
	}
}
