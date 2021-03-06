package io.github.riesenpilz.nmsUtilities.packet.playIn;

import org.apache.commons.lang.Validate;
import org.bukkit.entity.Player;

import net.minecraft.server.v1_16_R3.Packet;
import net.minecraft.server.v1_16_R3.PacketListenerPlayIn;
import net.minecraft.server.v1_16_R3.PacketPlayInBoatMove;

/**
 * https://wiki.vg/Protocol#Steer_Boat
 * <p>
 * Used to visually update whether boat paddles are turning. The server will
 * update the Boat entity metadata to match the values here.
 * <p>
 * Packet ID: 0x17<br>
 * State: Play<br>
 * Bound To: Server
 * 
 * @author Martin
 *
 */
public class PacketPlayInSteerBoatEvent extends PacketPlayInEvent {

	private boolean leftPaddleTurning;
	private boolean rightPaddleTurning;

	public PacketPlayInSteerBoatEvent(Player injectedPlayer, PacketPlayInBoatMove packet) {
		super(injectedPlayer);
		Validate.notNull(packet);
		leftPaddleTurning = packet.b();
		rightPaddleTurning = packet.c();
	}

	public PacketPlayInSteerBoatEvent(Player injectedPlayer, boolean leftPaddleTurning, boolean rightPaddleTurning) {
		super(injectedPlayer);
		this.leftPaddleTurning = leftPaddleTurning;
		this.rightPaddleTurning = rightPaddleTurning;
	}

	public boolean isLeftPaddleTurning() {
		return leftPaddleTurning;
	}

	public boolean isRightPaddleTurning() {
		return rightPaddleTurning;
	}

	@Override
	public Packet<PacketListenerPlayIn> getNMS() {
		return new PacketPlayInBoatMove(leftPaddleTurning, rightPaddleTurning);
	}

	@Override
	public int getPacketID() {
		return 0x17;
	}

	@Override
	public String getProtocolURLString() {
		return "https://wiki.vg/Protocol#Steer_Boat";
	}
}
