package io.github.riesenpilz.nms.packet.playOut;

import org.bukkit.entity.Player;

import net.minecraft.server.v1_16_R3.Packet;
import net.minecraft.server.v1_16_R3.PacketListenerPlayOut;
import net.minecraft.server.v1_16_R3.PacketPlayOutCombatEvent;

/**
 * https://wiki.vg/Protocol#End_Combat_Event
 * <p>
 * Unused by the Notchain client. This data was once used for twitch.tv metadata
 * circa 1.8.f
 * <p>
 * Packet ID: 0x33<br>
 * State: Play<br>
 * Bound To: Client
 * 
 * @author Martin
 *
 */
public class PacketPlayOutCombatEndEvent extends PacketPlayOutEvent {

	/**
	 * ID of the primary opponent of the ended combat, or -1 if there is no obvious
	 * primary opponent.
	 */
	private int entityID;
	/**
	 * Length of the combat in ticks.
	 */
	private int duration;

	public PacketPlayOutCombatEndEvent(Player injectedPlayer,
			PacketPlayOutCombatEvent packet) {
		super(injectedPlayer);
		entityID = packet.c;
		duration = packet.d;
	}

	public PacketPlayOutCombatEndEvent(Player injectedPlayer, int entityID, int duration) {
		super(injectedPlayer);
		this.entityID = entityID;
		this.duration = duration;
	}

	public int getEntityID() {
		return entityID;
	}

	public int getDuration() {
		return duration;
	}

	@Override
	public Packet<PacketListenerPlayOut> getNMS() {
		final PacketPlayOutCombatEvent packet = new PacketPlayOutCombatEvent();
		packet.a = PacketPlayOutCombatEvent.EnumCombatEventType.END_COMBAT;
		packet.c = entityID;
		packet.d = duration;
		return packet;
	}

	@Override
	public int getPacketID() {
		return 0x33;
	}

	@Override
	public String getProtocolURLString() {
		return "https://wiki.vg/Protocol#End_Combat_Event";
	}
}
