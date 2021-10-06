package io.github.riesenpilz.nmsUtilities.packet.playOut;

import org.bukkit.entity.Player;

import io.github.riesenpilz.nmsUtilities.reflections.Field;
import net.minecraft.server.v1_16_R3.Packet;
import net.minecraft.server.v1_16_R3.PacketListenerPlayOut;
import net.minecraft.server.v1_16_R3.PacketPlayOutUpdateTime;

/**
 * https://wiki.vg/Protocol#Time_Update
 * <p>
 * Time is based on ticks, where 20 ticks happen every second. There are 24000
 * ticks in a day, making Minecraft days exactly 20 minutes long.
 * <p>
 * The time of day is based on the timestamp modulo 24000. 0 is sunrise, 6000 is
 * noon, 12000 is sunset, and 18000 is midnight.
 * <p>
 * The default SMP server increments the time by 20 every second.
 * <p>
 * Packet ID: 0x58<br>
 * State: Play<br>
 * Bound To: Client
 * 
 * @author Martin
 *
 */
public class PacketPlayOutTimeUpdateEvent extends PacketPlayOutEvent {

	/**
	 * In ticks; not changed by server commands
	 */
	private long worldAge;

	/**
	 * The world (or region) time, in ticks. If negative the sun will stop moving at
	 * the Math.abs of the time.
	 */
	private long time;

	public PacketPlayOutTimeUpdateEvent(Player injectedPlayer, PacketPlayOutUpdateTime packet) {
		super(injectedPlayer);
		
		worldAge = Field.get(packet, "a", long.class);
		time = Field.get(packet, "b", long.class);
	}

	public PacketPlayOutTimeUpdateEvent(Player injectedPlayer, long worldAge, long time) {
		super(injectedPlayer);
		this.worldAge = worldAge;
		this.time = time;
	}

	public long getWorldAge() {
		return worldAge;
	}

	public long getTime() {
		return time;
	}

	@Override
	public Packet<PacketListenerPlayOut> getNMS() {
		return new PacketPlayOutUpdateTime(worldAge, time, true);
	}

	@Override
	public int getPacketID() {
		return 0x58;
	}

	@Override
	public String getProtocolURLString() {
		return "https://wiki.vg/Protocol#Time_Update";
	}
}
