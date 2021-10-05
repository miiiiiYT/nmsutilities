package io.github.riesenpilz.nmsUtilities.packet.playOut;

import org.bukkit.entity.Player;

import io.github.riesenpilz.nmsUtilities.reflections.Field;
import net.minecraft.server.v1_16_R3.Packet;
import net.minecraft.server.v1_16_R3.PacketListenerPlayOut;
import net.minecraft.server.v1_16_R3.PacketPlayOutUpdateHealth;

/**
 * https://wiki.vg/Protocol#Update_Health
 * <p>
 * Sent by the server to update/set the health of the player it is sent to.
 * <p>
 * Food saturation acts as a food “overcharge”. Food values will not decrease
 * while the saturation is over zero. Players logging in automatically get a
 * saturation of 5.0. Eating food increases the saturation as well as the food
 * bar.
 * <p>
 * Packet ID: 0x52<br>
 * State: Play<br>
 * Bound To: Client
 * 
 * @author Martin
 *
 */
public class PacketPlayOutUpdateHealthEvent extends PacketPlayOutEvent {

	/**
	 * 0 or less = dead, 20 = full HP.
	 */
	private float health;
	
	/**
	 * 0–20.
	 */
    private int food;
    
    /**
     * Seems to vary from 0.0 to 5.0 in integer increments.
     */
    private float saturation;
	
	public PacketPlayOutUpdateHealthEvent(Player injectedPlayer, PacketPlayOutUpdateHealth packet) {
		super(injectedPlayer);
		health = Field.get(packet, "a", float.class);
		food = Field.get(packet, "b", int.class);
		saturation = Field.get(packet, "c", float.class);
	}

	public PacketPlayOutUpdateHealthEvent(Player injectedPlayer, float health, int food, float saturation) {
		super(injectedPlayer);
		this.health = health;
		this.food = food;
		this.saturation = saturation;
	}

	public float getHealth() {
		return health;
	}

	public int getFood() {
		return food;
	}

	public float getSaturation() {
		return saturation;
	}

	@Override
	public Packet<PacketListenerPlayOut> getNMS() {
		return new PacketPlayOutUpdateHealth(health, food, saturation);
	}

	@Override
	public int getPacketID() {
		return 0x52;
	}

	@Override
	public String getProtocolURLString() {
		return "https://wiki.vg/Protocol#Update_Health";
	}
}
