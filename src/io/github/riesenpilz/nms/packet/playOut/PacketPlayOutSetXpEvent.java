package io.github.riesenpilz.nms.packet.playOut;

import org.bukkit.entity.Player;

import io.github.riesenpilz.nms.reflections.Field;
import net.minecraft.server.v1_16_R3.Packet;
import net.minecraft.server.v1_16_R3.PacketListenerPlayOut;
import net.minecraft.server.v1_16_R3.PacketPlayOutExperience;

/**
 * https://wiki.vg/Protocol#Set_Experience
 * <p>
 * Sent by the server when the client should change experience levels
 * <p>
 * Packet ID: 0x51<br>
 * State: Play<br>
 * Bound To: Client
 * 
 * @author Martin
 *
 */
public class PacketPlayOutSetXpEvent extends PacketPlayOutEvent {

	/**
	 * Between 0 and 1.
	 */
	private float experienceBar;
	private int level;

	/**
	 * See Experience#Leveling up on the Minecraft Wiki for Total Experience to
	 * Level conversion.(https://minecraft.fandom.com/wiki/Experience#Leveling_up)
	 */
	private int totalExperience;

	public PacketPlayOutSetXpEvent(Player injectedPlayer, PacketPlayOutExperience packet) {
		super(injectedPlayer);
		experienceBar = Field.get(packet, "a", float.class);
		level = Field.get(packet, "b", int.class);
		totalExperience = Field.get(packet, "c", int.class);
	}

	public PacketPlayOutSetXpEvent(Player injectedPlayer, float experienceBar, int level, int totalExperience) {
		super(injectedPlayer);
		this.experienceBar = experienceBar;
		this.level = level;
		this.totalExperience = totalExperience;
	}

	public float getExperienceBar() {
		return experienceBar;
	}

	public int getLevel() {
		return level;
	}

	public int getTotalExperience() {
		return totalExperience;
	}

	@Override
	public Packet<PacketListenerPlayOut> getNMS() {
		return new PacketPlayOutExperience(experienceBar, level, totalExperience);
	}

	@Override
	public int getPacketID() {
		return 0x51;
	}

	@Override
	public String getProtocolURLString() {
		return "https://wiki.vg/Protocol#Set_Experience";
	}
}
