package io.github.riesenpilz.nms.packet.playOut;

import org.bukkit.entity.Player;

import net.minecraft.server.v1_16_R3.IChatBaseComponent;
import net.minecraft.server.v1_16_R3.Packet;
import net.minecraft.server.v1_16_R3.PacketListenerPlayOut;
import net.minecraft.server.v1_16_R3.PacketPlayOutCombatEvent;

/**
 * https://wiki.vg/Protocol#Death_Combat_Event
 * <p>
 * Used to send a respawn screen.
 * <p>
 * Packet ID: 0x35<br>
 * State: Play<br>
 * Bound To: Client
 * 
 * @author Martin
 *
 */
public class PacketPlayOutDeathEvent extends PacketPlayOutEvent {

	/**
	 * Entity ID of the player that died (should match the client's entity ID).
	 */
	private int playerID;

	/**
	 * The killing entity's ID, or -1 if there is no obvious killer.
	 */
	private int entityID;

	/**
	 * The death message.
	 */
	private IChatBaseComponent message;

	public PacketPlayOutDeathEvent(Player injectedPlayer,
			net.minecraft.server.v1_16_R3.PacketPlayOutCombatEvent packet) {
		super(injectedPlayer);
		playerID = packet.b;
		entityID = packet.c;
		message = packet.e;
	}

	public PacketPlayOutDeathEvent(Player injectedPlayer, int playerID, int entityID, IChatBaseComponent message) {
		super(injectedPlayer);
		this.playerID = playerID;
		this.entityID = entityID;
		this.message = message;
	}

	public int getPlayerID() {
		return playerID;
	}

	public int getEntityID() {
		return entityID;
	}

	public IChatBaseComponent getMessage() {
		return message;
	}

	@Override
	public Packet<PacketListenerPlayOut> getNMS() {
		final PacketPlayOutCombatEvent packet = new PacketPlayOutCombatEvent();
		packet.a = net.minecraft.server.v1_16_R3.PacketPlayOutCombatEvent.EnumCombatEventType.END_COMBAT;
		packet.b = playerID;
		packet.c = entityID;
		packet.e = message;
		return packet;
	}

	@Override
	public int getPacketID() {
		return 0x35;
	}

	@Override
	public String getProtocolURLString() {
		return "https://wiki.vg/Protocol#Death_Combat_Event";
	}
}
