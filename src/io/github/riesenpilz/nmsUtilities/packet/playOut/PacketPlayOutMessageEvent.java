package io.github.riesenpilz.nmsUtilities.packet.playOut;

import java.util.UUID;

import org.apache.commons.lang.Validate;
import org.bukkit.entity.Player;

import io.github.riesenpilz.nmsUtilities.packet.HasText;
import io.github.riesenpilz.nmsUtilities.reflections.Field;
import net.md_5.bungee.api.ChatMessageType;
import net.minecraft.server.v1_16_R3.IChatBaseComponent;
import net.minecraft.server.v1_16_R3.Packet;
import net.minecraft.server.v1_16_R3.PacketListenerPlayOut;
import net.minecraft.server.v1_16_R3.PacketPlayOutChat;

/**
 * https://wiki.vg/Protocol#Chat_Message_.28clientbound.29
 * <p>
 * Identifying the difference between Chat/System Message is important as it
 * helps respect the user's chat visibility options. See processing chat for
 * more info about these positions.
 * <p>
 * Packet ID: 0x0E<br>
 * State: Play<br>
 * Bound To: Client
 * 
 * @author Martin
 *
 */
public class PacketPlayOutMessageEvent extends PacketPlayOutEvent implements HasText {

	private IChatBaseComponent message;
	private ChatMessageType type;

	/**
	 * Used by the Notchian client for the disableChat launch option. Setting both
	 * longs to 0 will always display the message regardless of the setting.
	 */
	private UUID sender;

	public PacketPlayOutMessageEvent(Player injectedPlayer, PacketPlayOutChat packet) {
		super(injectedPlayer);

		Validate.notNull(packet);

		message = Field.get(packet, "a", IChatBaseComponent.class);
		switch (Field.get(packet, "b", net.minecraft.server.v1_16_R3.ChatMessageType.class)) {
		case CHAT:
			type = ChatMessageType.CHAT;
			break;
		case GAME_INFO:
			type = ChatMessageType.ACTION_BAR;
			break;
		case SYSTEM:
			type = ChatMessageType.SYSTEM;
			break;
		}
		sender = Field.get(packet, "c", UUID.class);
	}

	public PacketPlayOutMessageEvent(Player injectedPlayer, IChatBaseComponent message, ChatMessageType type,
			UUID sender) {
		super(injectedPlayer);

		Validate.notNull(message);
		Validate.notNull(type);
		Validate.notNull(sender);

		this.message = message;
		this.type = type;
		this.sender = sender;
	}

	@Override
	public IChatBaseComponent getText() {
		return message;
	}

	public ChatMessageType getType() {
		return type;
	}

	public UUID getSender() {
		return sender;
	}

	@Override
	public Packet<PacketListenerPlayOut> getNMS() {
		net.minecraft.server.v1_16_R3.ChatMessageType nmsType = null;
		switch (type) {
		case CHAT:
			nmsType = net.minecraft.server.v1_16_R3.ChatMessageType.CHAT;
			break;
		case ACTION_BAR:
			nmsType = net.minecraft.server.v1_16_R3.ChatMessageType.GAME_INFO;
			break;
		case SYSTEM:
			nmsType = net.minecraft.server.v1_16_R3.ChatMessageType.SYSTEM;
			break;
		}
		return new PacketPlayOutChat(message, nmsType, sender);
	}

	@Override
	public int getPacketID() {
		return 0x0E;
	}

	@Override
	public String getProtocolURLString() {
		return "https://wiki.vg/Protocol#Chat_Message_.28clientbound.29";
	}
}
