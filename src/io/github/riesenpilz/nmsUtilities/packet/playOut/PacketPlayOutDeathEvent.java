package io.github.riesenpilz.nmsUtilities.packet.playOut;

import org.bukkit.entity.Player;

import io.github.riesenpilz.nmsUtilities.packet.HasText;
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
public class PacketPlayOutDeathEvent extends PacketPlayOutEntityEvent implements HasText {

	/**
	 * The killing entity's ID, or -1 if there is no obvious killer.
	 */
	private int killingEntityId;

	/**
	 * The death message.
	 */
	private IChatBaseComponent message;

	public PacketPlayOutDeathEvent(Player injectedPlayer, PacketPlayOutCombatEvent packet) {
		super(injectedPlayer, packet.b);
		killingEntityId = packet.c;
		message = packet.e;
	}

	public PacketPlayOutDeathEvent(Player injectedPlayer, int diedEntity, int killingEntityId,
			IChatBaseComponent message) {
		super(injectedPlayer, diedEntity);
		this.killingEntityId = killingEntityId;
		this.message = message;
	}

	public int getKillingEntityId() {
		return killingEntityId;
	}

	@Override
	public IChatBaseComponent getText() {
		return message;
	}

	@Override
	public Packet<PacketListenerPlayOut> getNMS() {
		final PacketPlayOutCombatEvent packet = new PacketPlayOutCombatEvent();
		packet.a = PacketPlayOutCombatEvent.EnumCombatEventType.END_COMBAT;
		packet.b = getEntityId();
		packet.c = killingEntityId;
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
