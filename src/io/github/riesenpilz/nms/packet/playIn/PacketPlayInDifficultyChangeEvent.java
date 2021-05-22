package io.github.riesenpilz.nms.packet.playIn;

import org.bukkit.Difficulty;
import org.bukkit.entity.Player;

import net.minecraft.server.v1_16_R3.EnumDifficulty;
import net.minecraft.server.v1_16_R3.Packet;
import net.minecraft.server.v1_16_R3.PacketListenerPlayIn;
import net.minecraft.server.v1_16_R3.PacketPlayInDifficultyChange;

/**
 * https://wiki.vg/Protocol#Set_Difficulty<br>
 * <br>
 * Must have at least op level 2 to use. Appears to only be used on
 * singleplayer; the difficulty buttons are still disabled in multiplayer..<br>
 * <br>
 * Packet ID: 0x02<br>
 * State: Play<br>
 * Bound To: Server
 * 
 * @author Martin
 *
 */
public class PacketPlayInDifficultyChangeEvent extends PacketPlayInEvent {

	/**
	 * New difficulty
	 */
	private Difficulty difficulty;

	@SuppressWarnings("deprecation")
	public PacketPlayInDifficultyChangeEvent(Player injectedPlayer, PacketPlayInDifficultyChange packet) {
		super(injectedPlayer);
		Difficulty.getByValue(packet.b().a());
	}

	public PacketPlayInDifficultyChangeEvent(Player injectedPlayer, Difficulty difficulty) {
		super(injectedPlayer);
		this.difficulty = difficulty;
	}

	public Difficulty getDifficulty() {
		return difficulty;
	}

	@SuppressWarnings("deprecation")
	@Override
	public Packet<PacketListenerPlayIn> getNMS() {
		return new PacketPlayInDifficultyChange(EnumDifficulty.getById(difficulty.getValue()));
	}

	@Override
	public int getPacketID() {
		return 0x02;
	}

	@Override
	public String getProtocolURLString() {
		return "https://wiki.vg/Protocol#Set_Difficulty";
	}
}
