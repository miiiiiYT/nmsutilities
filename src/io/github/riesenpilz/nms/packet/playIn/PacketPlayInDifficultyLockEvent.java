package io.github.riesenpilz.nms.packet.playIn;

import org.bukkit.entity.Player;

import io.github.riesenpilz.nms.reflections.Field;
import net.minecraft.server.v1_16_R3.Packet;
import net.minecraft.server.v1_16_R3.PacketListenerPlayIn;
import net.minecraft.server.v1_16_R3.PacketPlayInDifficultyLock;

/**
 * https://wiki.vg/Protocol#Lock_Difficulty<br>
 * <br>
 * Must have at least op level 2 to use. Appears to only be used on
 * singleplayer; the difficulty buttons are still disabled in multiplayer.<br>
 * <br>
 * Packet ID: 0x11<br>
 * State: Play<br>
 * Bound To: Server
 * 
 * @author Martin
 *
 */
public class PacketPlayInDifficultyLockEvent extends PacketPlayInEvent {

	private boolean look;

	public PacketPlayInDifficultyLockEvent(Player injectedPlayer, PacketPlayInDifficultyLock packet) {
		super(injectedPlayer);
		look = packet.b();
	}

	public PacketPlayInDifficultyLockEvent(Player injectedPlayer, boolean look) {
		super(injectedPlayer);
		this.look = look;
	}

	public boolean isLook() {
		return look;
	}

	@Override
	public Packet<PacketListenerPlayIn> getNMS() {
		final PacketPlayInDifficultyLock packet = new PacketPlayInDifficultyLock();
		new Field(PacketPlayInDifficultyLock.class, "a").set(packet, look);
		return packet;
	}

	@Override
	public int getPacketID() {
		return 0x11;
	}

	@Override
	public String getProtocolURLString() {
		return "https://wiki.vg/Protocol#Lock_Difficulty";
	}
}
