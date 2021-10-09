package io.github.riesenpilz.nmsUtilities.packet.playIn;

import org.apache.commons.lang.Validate;
import org.bukkit.entity.Player;

import net.minecraft.server.v1_16_R3.Packet;
import net.minecraft.server.v1_16_R3.PacketListenerPlayIn;
import net.minecraft.server.v1_16_R3.PacketPlayInChat;

/**
 * https://wiki.vg/Protocol#Chat_Message_.28serverbound.29
 * <p>
 * Used to send a chat message to the server. The message may not be longer than
 * 256 characters or else the server will kick the client.
 * <p>
 * If the message starts with a /, the server will attempt to interpret it as a
 * command. Otherwise, the server will broadcast the same chat message to all
 * players on the server (including the player that sent the message), prepended
 * with player's name. Specifically, it will respond with a translate chat
 * component, "chat.type.text" with the first parameter set to the display name
 * of the player (including some chat component logic to support clicking the
 * name to send a PM) and the second parameter set to the message. See
 * processing chat for more information.
 * <p>
 * Packet ID: 0x03<br>
 * State: Play<br>
 * Bound To: Server
 * 
 * @author Martin
 *
 */
public class PacketPlayInChatEvent extends PacketPlayInEvent {

	/**
	 * The client sends the raw input, not a Chat component. (256 chars max)
	 */
	private String message;

	public PacketPlayInChatEvent(Player injectedPlayer, PacketPlayInChat packet) {
		super(injectedPlayer);
		Validate.notNull(packet);
		message = packet.b();
	}

	public PacketPlayInChatEvent(Player injectedPlayer, String message) {
		super(injectedPlayer);
		Validate.notNull(message);
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	@Override
	public Packet<PacketListenerPlayIn> getNMS() {
		return new PacketPlayInChat(message);
	}

	@Override
	public int getPacketID() {
		return 0x03;
	}

	@Override
	public String getProtocolURLString() {
		return "https://wiki.vg/Protocol#Chat_Message_.28serverbound.29";
	}
}
