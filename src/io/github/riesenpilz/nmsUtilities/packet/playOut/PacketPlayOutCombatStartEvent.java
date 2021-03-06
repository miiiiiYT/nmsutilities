package io.github.riesenpilz.nmsUtilities.packet.playOut;

import javax.annotation.Nullable;

import org.bukkit.entity.Player;

import net.minecraft.server.v1_16_R3.Packet;
import net.minecraft.server.v1_16_R3.PacketListenerPlayOut;
import net.minecraft.server.v1_16_R3.PacketPlayOutCombatEvent;

/**
 * https://wiki.vg/Protocol#Enter_Combat_Event
 * <p>
 * Unused by the Notchain client. This data was once used for twitch.tv metadata
 * circa 1.8.
 * <p>
 * Packet ID: 0x34<br>
 * State: Play<br>
 * Bound To: Client
 * 
 * @author Martin
 *
 */
public class PacketPlayOutCombatStartEvent extends PacketPlayOutEvent {

	public PacketPlayOutCombatStartEvent(Player injectedPlayer, @Nullable PacketPlayOutCombatEvent packet) {
		super(injectedPlayer);
	}

	public PacketPlayOutCombatStartEvent(Player injectedPlayer) {
		super(injectedPlayer);
	}

	@Override
	public Packet<PacketListenerPlayOut> getNMS() {
		final PacketPlayOutCombatEvent packet = new PacketPlayOutCombatEvent();
		packet.a = PacketPlayOutCombatEvent.EnumCombatEventType.ENTER_COMBAT;
		return packet;
	}

	@Override
	public int getPacketID() {
		return 0x34;
	}

	@Override
	public String getProtocolURLString() {
		return "https://wiki.vg/Protocol#Enter_Combat_Event";
	}
}
