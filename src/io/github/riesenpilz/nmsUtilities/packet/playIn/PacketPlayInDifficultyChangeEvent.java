package io.github.riesenpilz.nmsUtilities.packet.playIn;

import org.apache.commons.lang.Validate;
import org.bukkit.Difficulty;
import org.bukkit.entity.Player;

import io.github.riesenpilz.nmsUtilities.packet.PacketUtils;
import net.minecraft.server.v1_16_R3.Packet;
import net.minecraft.server.v1_16_R3.PacketListenerPlayIn;
import net.minecraft.server.v1_16_R3.PacketPlayInDifficultyChange;

/**
 * https://wiki.vg/Protocol#Set_Difficulty
 * <p>
 * Must have at least op level 2 to use. Appears to only be used on
 * singleplayer; the difficulty buttons are still disabled in multiplayer..
 * <p>
 * Packet ID: 0x02<br>
 * State: Play<br>
 * Bound To: Server
 * 
 * @author Martin
 *
 */
public class PacketPlayInDifficultyChangeEvent extends PacketPlayInEvent {

	private Difficulty difficulty;

	public PacketPlayInDifficultyChangeEvent(Player injectedPlayer, PacketPlayInDifficultyChange packet) {
		super(injectedPlayer);
		Validate.notNull(packet);
		difficulty = PacketUtils.toDifficulty(packet.b());
	}

	public PacketPlayInDifficultyChangeEvent(Player injectedPlayer, Difficulty difficulty) {
		super(injectedPlayer);
		Validate.notNull(difficulty);
		this.difficulty = difficulty;
	}

	public Difficulty getDifficulty() {
		return difficulty;
	}

	@Override
	public Packet<PacketListenerPlayIn> getNMS() {
		return new PacketPlayInDifficultyChange(PacketUtils.toEnumDifficulty(difficulty));
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
