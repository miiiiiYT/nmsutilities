package io.github.riesenpilz.nmsUtilities.packet.playIn;

import org.bukkit.entity.Player;

import io.github.riesenpilz.nmsUtilities.reflections.Field;
import net.minecraft.server.v1_16_R3.Packet;
import net.minecraft.server.v1_16_R3.PacketListenerPlayIn;
import net.minecraft.server.v1_16_R3.PacketPlayInDifficultyLock;

/**
 * https://wiki.vg/Protocol#Lock_Difficulty
 * <p>
 * Must have at least op level 2 to use. Appears to only be used on
 * singleplayer; the difficulty buttons are still disabled in multiplayer.
 * <p>
 * Packet ID: 0x11<br>
 * State: Play<br>
 * Bound To: Server
 * 
 * @author Martin
 *
 */
public class PacketPlayInDifficultyLockEvent extends PacketPlayInEvent {

	private boolean lock;

	public PacketPlayInDifficultyLockEvent(Player injectedPlayer, PacketPlayInDifficultyLock packet) {
		super(injectedPlayer);
		lock = packet.b();
	}

	public PacketPlayInDifficultyLockEvent(Player injectedPlayer, boolean lock) {
		super(injectedPlayer);
		this.lock = lock;
	}

	public boolean isLock() {
		return lock;
	}

	@Override
	public Packet<PacketListenerPlayIn> getNMS() {
		final PacketPlayInDifficultyLock packet = new PacketPlayInDifficultyLock();
		Field.set(packet, "a", lock);
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
