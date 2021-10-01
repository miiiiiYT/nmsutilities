package io.github.riesenpilz.nms.packet.playOut;

import org.bukkit.Difficulty;
import org.bukkit.entity.Player;

import io.github.riesenpilz.nms.packet.PacketUtils;
import io.github.riesenpilz.nms.reflections.Field;
import net.minecraft.server.v1_16_R3.EnumDifficulty;
import net.minecraft.server.v1_16_R3.Packet;
import net.minecraft.server.v1_16_R3.PacketListenerPlayOut;
import net.minecraft.server.v1_16_R3.PacketPlayOutServerDifficulty;

/**
 * https://wiki.vg/Protocol#Server_Difficulty<br>
 * <br>
 * Changes the difficulty setting in the client's option menu<br>
 * <br>
 * Packet ID: 0x0D<br>
 * State: Play<br>
 * Bound To: Client
 * 
 * @author Martin
 *
 */
public class PacketPlayOutDifficultyEvent extends PacketPlayOutEvent {

	private Difficulty difficulty;
	private boolean looked;

	public PacketPlayOutDifficultyEvent(Player injectedPlayer, PacketPlayOutServerDifficulty packet) {
		super(injectedPlayer);
		difficulty = PacketUtils.toDifficulty(Field.get(packet, "a", EnumDifficulty.class));
		looked = Field.get(packet, "b", boolean.class);
	}

	public Difficulty getDifficulty() {
		return difficulty;
	}

	public boolean isLooked() {
		return looked;
	}

	@Override
	public Packet<PacketListenerPlayOut> getNMS() {
		return new PacketPlayOutServerDifficulty(PacketUtils.toEnumDifficulty(difficulty), looked);
	}

	@Override
	public int getPacketID() {
		return 0x0D;
	}

	@Override
	public String getProtocolURLString() {
		return "https://wiki.vg/Protocol#Server_Difficulty";
	}
}
