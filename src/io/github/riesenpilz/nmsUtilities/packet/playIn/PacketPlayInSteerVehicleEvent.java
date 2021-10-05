package io.github.riesenpilz.nmsUtilities.packet.playIn;

import org.bukkit.entity.Player;

import io.github.riesenpilz.nmsUtilities.reflections.Field;
import net.minecraft.server.v1_16_R3.Packet;
import net.minecraft.server.v1_16_R3.PacketListenerPlayIn;
import net.minecraft.server.v1_16_R3.PacketPlayInSteerVehicle;

/**
 * https://wiki.vg/Protocol#Steer_Vehicle
 * <p>
 * Also known as 'Input' packet.
 * <p>
 * Packet ID: 0x1D<br>
 * State: Play<br>
 * Bound To: Server
 *
 * @author Martin
 *
 */
public class PacketPlayInSteerVehicleEvent extends PacketPlayInEvent {

	/**
	 * Positive = to the left of the player
	 */
	private float sideways;

	/**
	 * Positive = forward
	 */
	private float forward;
	private boolean jump;
	private boolean unmount;

	public PacketPlayInSteerVehicleEvent(Player injectedPlayer, PacketPlayInSteerVehicle packet) {
		super(injectedPlayer);
		sideways = packet.b();
		forward = packet.c();
		jump = packet.d();
		unmount = packet.e();
	}

	public PacketPlayInSteerVehicleEvent(Player injectedPlayer, float sideways, float forward, boolean jump,
			boolean unmount) {
		super(injectedPlayer);
		this.sideways = sideways;
		this.forward = forward;
		this.jump = jump;
		this.unmount = unmount;
	}

	public float getSideways() {
		return sideways;
	}

	public float getForward() {
		return forward;
	}

	public boolean isJump() {
		return jump;
	}

	public boolean isUnmount() {
		return unmount;
	}

	@Override
	public Packet<PacketListenerPlayIn> getNMS() {
		final PacketPlayInSteerVehicle packet = new PacketPlayInSteerVehicle();
		Field.set(packet, "a", sideways);
		Field.set(packet, "b", forward);
		Field.set(packet, "c", jump);
		Field.set(packet, "d", unmount);
		return packet;
	}

	@Override
	public int getPacketID() {
		return 0x1D;
	}

	@Override
	public String getProtocolURLString() {
		return "https://wiki.vg/Protocol#Steer_Vehicle";
	}

}
