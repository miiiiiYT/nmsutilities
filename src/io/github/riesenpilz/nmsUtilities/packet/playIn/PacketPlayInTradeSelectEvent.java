package io.github.riesenpilz.nmsUtilities.packet.playIn;

import org.bukkit.entity.Player;

import io.github.riesenpilz.nmsUtilities.reflections.Field;
import net.minecraft.server.v1_16_R3.Packet;
import net.minecraft.server.v1_16_R3.PacketListenerPlayIn;
import net.minecraft.server.v1_16_R3.PacketPlayInTrSel;

/**
 * https://wiki.vg/Protocol#Select_Trade
 * <p>
 * When a player selects a specific trade offered by a villager NPC.
 * <p>
 * Packet ID: 0x23<br>
 * State: Play<br>
 * Bound To: Server
 *
 * @author Martin
 *
 */
public class PacketPlayInTradeSelectEvent extends PacketPlayInEvent {

	/**
	 * The selected slot in the players current (trading) inventory. (Was a full
	 * Integer for the plugin message).
	 */
	private int slot;

	public PacketPlayInTradeSelectEvent(Player injectedPlayer, PacketPlayInTrSel packet) {
		super(injectedPlayer);
		slot = packet.b();
	}

	public PacketPlayInTradeSelectEvent(Player injectedPlayer, int slot) {
		super(injectedPlayer);
		this.slot = slot;
	}

	public int getSlot() {
		return slot;
	}

	@Override
	public Packet<PacketListenerPlayIn> getNMS() {
		final PacketPlayInTrSel packet = new PacketPlayInTrSel();
		Field.set(packet, "a", slot);
		return packet;
	}

	@Override
	public int getPacketID() {
		return 0x23;
	}

	@Override
	public String getProtocolURLString() {
		return "https://wiki.vg/Protocol#Select_Trade";
	}

}
