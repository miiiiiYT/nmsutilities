package io.github.riesenpilz.nmsUtilities.packet.playIn;

import org.apache.commons.lang.Validate;
import org.bukkit.entity.Player;

import io.github.riesenpilz.nmsUtilities.reflections.Field;
import net.minecraft.server.v1_16_R3.Packet;
import net.minecraft.server.v1_16_R3.PacketListenerPlayIn;
import net.minecraft.server.v1_16_R3.PacketPlayInFlying;
import net.minecraft.server.v1_16_R3.PacketPlayInFlying.PacketPlayInLook;
import net.minecraft.server.v1_16_R3.PacketPlayInFlying.PacketPlayInPosition;
import net.minecraft.server.v1_16_R3.PacketPlayInFlying.PacketPlayInPositionLook;

/**
 * https://wiki.vg/Protocol#Player_Movement
 * <p>
 * This packet as well as Player Position, Player Look, and Player Position And
 * Look are called the “serverbound movement packets”. Vanilla clients will send
 * Player Position once every 20 ticks even for a stationary player.
 * <p>
 * This packet is used to indicate whether the player is on ground
 * (walking/swimming), or airborne (jumping/falling).
 * <p>
 * When dropping from sufficient height, fall damage is applied when this state
 * goes from false to true. The amount of damage applied is based on the point
 * where it last changed from true to false. Note that there are several
 * movement related packets containing this state.
 * <p>
 * Packet ID: 0x15<br>
 * State: Play<br>
 * Bound To: Server
 * 
 * @author Martin
 *
 */
public class PacketPlayInMovementEvent extends PacketPlayInEvent {

	/**
	 * True if the client is on the ground, false otherwise.
	 */
	protected boolean onGround;

	public PacketPlayInMovementEvent(Player injectedPlayer, PacketPlayInFlying packet) {
		super(injectedPlayer);
		Validate.notNull(packet);
		this.onGround = packet.b();
	}

	public PacketPlayInMovementEvent(Player injectedPlayer, boolean onGround) {
		super(injectedPlayer);
		this.onGround = onGround;
	}

	public boolean isOnGround() {
		return onGround;
	}

	@Override
	public Packet<PacketListenerPlayIn> getNMS() {
		final PacketPlayInFlying packet = new PacketPlayInFlying();
		Field.set(packet, "f", onGround);
		return packet;
	}

	@Override
	public int getPacketID() {
		return 0x15;
	}

	@Override
	public String getProtocolURLString() {
		return "https://wiki.vg/Protocol#Player_Movement";
	}

	/**
	 * https://wiki.vg/Protocol#Player_Rotation
	 * <p>
	 * Updates the direction the player is looking in.
	 * <p>
	 * Yaw is measured in degrees, and does not follow classical trigonometry rules.
	 * The unit circle of yaw on the XZ-plane starts at (0, 1) and turns
	 * counterclockwise, with 90 at (-1, 0), 180 at (0,-1) and 270 at (1, 0).
	 * Additionally, yaw is not clamped to between 0 and 360 degrees; any number is
	 * valid, including negative numbers and numbers greater than 360.
	 * <p>
	 * Pitch is measured in degrees, where 0 is looking straight ahead, -90 is
	 * looking straight up, and 90 is looking straight down.
	 * <p>
	 * The yaw and pitch of player (in degrees), standing at point (x0, y0, z0) and
	 * looking towards point (x, y, z) can be calculated with:<br>
	 * <code>dx = x-x0<br>
	dy = y-y0<br>
	dz = z-z0<br>
	r = sqrt( dx*dx + dy*dy + dz*dz )<br>
	yaw = -atan2(dx,dz)/PI*180<br>
	if yaw {@code<} 0 then <br>
	yaw = 360 + yaw<br>
	pitch = -arcsin(dy/r)/PI*180</code>
	 * <p>
	 * You can get a unit vector from a given yaw/pitch via:<br>
	 * <code>x = -cos(pitch) * sin(yaw)<br>
	y = -sin(pitch)<br>
	z =  cos(pitch) * cos(yaw)</code>
	 * <p>
	 * Packet ID: 0x14<br>
	 * State: Play<br>
	 * Bound To: Server
	 * 
	 * @author Martin
	 *
	 */
	public static class PacketPlayInLookEvent extends PacketPlayInMovementEvent {

		/**
		 * Absolute rotation on the X Axis, in degrees.
		 */
		private float yaw;

		/**
		 * 
		 * Absolute rotation on the Y Axis, in degrees.
		 */
		private float pitch;

		public PacketPlayInLookEvent(Player injectedPlayer, PacketPlayInLook packet) {
			super(injectedPlayer, packet.b());
			yaw = packet.a(0f);
			pitch = packet.b(0f);
		}

		public PacketPlayInLookEvent(Player injectedPlayer, boolean onGround, float yaw, float pitch) {
			super(injectedPlayer, onGround);
			this.yaw = yaw;
			this.pitch = pitch;
		}

		@Override
		public Packet<PacketListenerPlayIn> getNMS() {
			final PacketPlayInLook packet = new PacketPlayInLook();
			Field.set(packet, "yaw", yaw);
			Field.set(packet, "pitch", pitch);
			Field.set(packet, "f", onGround);
			return packet;
		}

		public float getYaw() {
			return yaw;
		}

		public float getPitch() {
			return pitch;
		}

		@Override
		public int getPacketID() {
			return 0x14;
		}

		@Override
		public String getProtocolURLString() {
			return "https://wiki.vg/Protocol#Player_Rotation";
		}
	}

	/**
	 * https://wiki.vg/Protocol#Player_Position
	 * <p>
	 * Updates the player's XYZ position on the server.<br>
	 * Checking for moving too fast is achieved like this:<br>
	 * - Each server tick, the player's current position is stored<br>
	 * - When a player moves, the changes in x, y, and z coordinates are compared
	 * with the positions from the previous tick (x, y, z)<br>
	 * - Total movement distance squared is computed as x² + y² + z²<br>
	 * - The expected movement distance squared is computed as velocityX² +
	 * veloctyY² + velocityZ² - If the total movement distance squared value minus
	 * the expected movement distance squared value is more than 100 (300 if the
	 * player is using an elytra), they are moving too fast. If the player is moving
	 * too fast, it will be logged that "{@code <player>} moved too quickly! "
	 * followed by the change in x, y, and z, and the player will be teleported back
	 * to their current (before this packet) serverside position.<br>
	 * Also, if the absolute value of X or the absolute value of Z is a value
	 * greater than 3.2×107, or X, Y, or Z are not finite (either positive infinity,
	 * negative infinity, or NaN), the client will be kicked for “Invalid move
	 * player packet received”.
	 * <p>
	 * Packet ID: 0x12<br>
	 * State: Play<br>
	 * Bound To: Server
	 * 
	 * @author Martin
	 *
	 */
	public static class PacketPlayInPositionEvent extends PacketPlayInMovementEvent {

		/**
		 * Absolute position.
		 */
		private double x;

		/**
		 * Absolute feet position, normally Head Y - 1.62.
		 */
		private double y;

		/**
		 * Absolute position.
		 */
		private double z;

		public PacketPlayInPositionEvent(Player injectedPlayer, PacketPlayInPosition packet) {
			super(injectedPlayer, packet.b());
			x = packet.a(0);
			y = packet.b(0);
			y = packet.c(0);
		}

		public PacketPlayInPositionEvent(Player injectedPlayer, boolean onGround, double x, double y, double z) {
			super(injectedPlayer, onGround);
			this.x = x;
			this.y = y;
			this.z = z;
		}

		@Override
		public Packet<PacketListenerPlayIn> getNMS() {
			final PacketPlayInLook packet = new PacketPlayInLook();
			Field.set(packet, "x", x);
			Field.set(packet, "y", y);
			Field.set(packet, "z", z);
			Field.set(packet, "f", onGround);
			return packet;
		}

		public double getX() {
			return x;
		}

		public double getY() {
			return y;
		}

		public double getZ() {
			return z;
		}

		@Override
		public int getPacketID() {
			return 0x12;
		}

		@Override
		public String getProtocolURLString() {
			return "https://wiki.vg/Protocol#Player_Position";
		}
	}

	/**
	 * https://wiki.vg/Protocol#Player_Position_And_Rotation_.28serverbound.29
	 * <p>
	 * A combination of Player Rotation and Player Position.
	 * <p>
	 * Packet ID: 0x13<br>
	 * State: Play<br>
	 * Bound To: Server
	 * 
	 * @author Martin
	 *
	 */
	public static class PacketPlayInPositionLookEvent extends PacketPlayInMovementEvent {

		/**
		 * Absolute position.
		 */
		private double x;

		/**
		 * Absolute feet position, normally Head Y - 1.62.
		 */
		private double y;

		/**
		 * Absolute position.
		 */
		private double z;

		/**
		 * Absolute rotation on the X Axis, in degrees.
		 */
		private float yaw;

		/**
		 * 
		 * Absolute rotation on the Y Axis, in degrees.
		 */
		private float pitch;

		public PacketPlayInPositionLookEvent(Player injectedPlayer, PacketPlayInPositionLook packet) {
			super(injectedPlayer, packet.b());

			x = packet.a(0);
			y = packet.b(0);
			y = packet.c(0);

			yaw = packet.a(0f);
			pitch = packet.b(0f);
		}

		public PacketPlayInPositionLookEvent(Player injectedPlayer, boolean onGround, float yaw, float pitch, double x,
				double y, double z) {
			super(injectedPlayer, onGround);
			this.yaw = yaw;
			this.pitch = pitch;

			this.x = x;
			this.y = y;
			this.z = z;
		}

		@Override
		public Packet<PacketListenerPlayIn> getNMS() {
			final PacketPlayInLook packet = new PacketPlayInLook();
			Field.set(packet, "x", x);
			Field.set(packet, "y", y);
			Field.set(packet, "z", z);
			Field.set(packet, "yaw", yaw);
			Field.set(packet, "pitch", pitch);
			Field.set(packet, "f", onGround);
			return packet;
		}

		public double getX() {
			return x;
		}

		public double getY() {
			return y;
		}

		public double getZ() {
			return z;
		}

		public float getYaw() {
			return yaw;
		}

		public float getPitch() {
			return pitch;
		}

		@Override
		public int getPacketID() {
			return 19;
		}

		@Override
		public String getProtocolURLString() {
			return "https://wiki.vg/Protocol#Player_Position_And_Rotation_.28serverbound.29";
		}
	}
}
