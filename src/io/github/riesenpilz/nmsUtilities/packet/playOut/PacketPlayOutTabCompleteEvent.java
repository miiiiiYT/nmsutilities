package io.github.riesenpilz.nmsUtilities.packet.playOut;

import org.apache.commons.lang.Validate;
import org.bukkit.entity.Player;

import com.mojang.brigadier.suggestion.Suggestions;

import io.github.riesenpilz.nmsUtilities.reflections.Field;
import net.minecraft.server.v1_16_R3.Packet;
import net.minecraft.server.v1_16_R3.PacketListenerPlayOut;
import net.minecraft.server.v1_16_R3.PacketPlayOutTabComplete;

/**
 * https://wiki.vg/Protocol#Tab-Complete_.28clientbound.29
 * <p>
 * The server responds with a list of auto-completions of the last word sent to
 * it. In the case of regular chat, this is a player username. Command names and
 * parameters are also supported. The client sorts these alphabetically before
 * listing them.
 * <p>
 * Packet ID: 0x0F<br>
 * State: Play<br>
 * Bound To: Client
 *
 * @author Martin
 *
 */
public class PacketPlayOutTabCompleteEvent extends PacketPlayOutEvent {

	private int transactionID;
	private Suggestions suggestions;

	public PacketPlayOutTabCompleteEvent(Player injectedPlayer, PacketPlayOutTabComplete packet) {
		super(injectedPlayer);

		Validate.notNull(packet);

		transactionID = Field.get(packet, "a", int.class);
		suggestions = Field.get(packet, "b", Suggestions.class);
	}

	public PacketPlayOutTabCompleteEvent(Player injectedPlayer, int transactionID, Suggestions suggestions) {
		super(injectedPlayer);

		Validate.notNull(suggestions);

		this.transactionID = transactionID;
		this.suggestions = suggestions;
	}

	public int getTransactionID() {
		return transactionID;
	}

	public Suggestions getSuggestions() {
		return suggestions;
	}

	@Override
	public Packet<PacketListenerPlayOut> getNMS() {
		return new PacketPlayOutTabComplete(transactionID, suggestions);
	}

	@Override
	public int getPacketID() {
		return 0x0F;
	}

	@Override
	public String getProtocolURLString() {
		return "https://wiki.vg/Protocol#Tab-Complete_.28clientbound.29";
	}
}
