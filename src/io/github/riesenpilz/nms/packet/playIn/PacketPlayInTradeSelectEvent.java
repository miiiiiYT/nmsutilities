package io.github.riesenpilz.nms.packet.playIn;

import org.bukkit.entity.Player;

import io.github.riesenpilz.nms.reflections.Field;
import net.minecraft.server.v1_16_R3.Packet;
import net.minecraft.server.v1_16_R3.PacketListenerPlayIn;
import net.minecraft.server.v1_16_R3.PacketPlayInTrSel;

/**
 * https://wiki.vg/Protocol#Select_Trade<br>
 * <br>
 * When a player selects a specific trade offered by a villager NPC.<br>
 * <br>
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
		new Field(PacketPlayInTrSel.class, "a").set(packet, slot);
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
