package io.github.riesenpilz.nmsUtilities.packet.loginOut;

import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;

import io.github.riesenpilz.nmsUtilities.packet.PacketDataSerializer;
import io.github.riesenpilz.nmsUtilities.reflections.Field;
import net.minecraft.server.v1_16_R3.MinecraftKey;
import net.minecraft.server.v1_16_R3.Packet;
import net.minecraft.server.v1_16_R3.PacketLoginOutCustomPayload;
import net.minecraft.server.v1_16_R3.PacketLoginOutListener;

/**
 * https://wiki.vg/Protocol#Login_Plugin_Request
 * <p>
 * Used to implement a custom handshaking flow together with Login Plugin
 * Response.
 * <p>
 * Unlike plugin messages in "play" mode, these messages follow a lock-step
 * request/response scheme, where the client is expected to respond to a request
 * indicating whether it understood. The notchian client always responds that it
 * hasn't understood, and sends an empty payload.
 * <p>
 * Packet ID: 0x04<br>
 * State: Login<br>
 * Bound To: Client
 *
 * @author Martin
 *
 */
public class PacketLoginOutCustomPayloadEvent extends PacketLoginOutEvent {

	/**
	 * Generated by the server - should be unique to the connection.
	 */
	private int messageID;

	/**
	 * Name of the plugin channel used to send the data.
	 */
	private NamespacedKey channel;

	/**
	 * Any data, depending on the channel. The length of this array must be inferred
	 * from the packet length.
	 */
	private PacketDataSerializer data;

	public PacketLoginOutCustomPayloadEvent(Player injectedPlayer, int messageID, NamespacedKey channel,
			PacketDataSerializer data) {
		super(injectedPlayer);
		this.messageID = messageID;
		this.channel = channel;
		this.data = data;
	}

	@SuppressWarnings("deprecation")
	public PacketLoginOutCustomPayloadEvent(Player injectedPlayer, PacketLoginOutCustomPayload packet) {
		super(injectedPlayer);
		messageID = Field.get(packet, "a", int.class);
		final MinecraftKey nmsKey = Field.get(packet, "b", MinecraftKey.class);
		channel = new NamespacedKey(nmsKey.getNamespace(), nmsKey.getNamespace());
		data = new PacketDataSerializer(
				Field.get(packet, "c", net.minecraft.server.v1_16_R3.PacketDataSerializer.class));
	}

	public int getMessageID() {
		return messageID;
	}

	public NamespacedKey getChannel() {
		return channel;
	}

	public PacketDataSerializer getData() {
		return data;
	}

	@Override
	public Packet<PacketLoginOutListener> getNMS() {
		final PacketLoginOutCustomPayload packet = new PacketLoginOutCustomPayload();
		Field.set(packet, "a", messageID);
		Field.set(packet, "b", new MinecraftKey(channel.getNamespace(), channel.getKey()));
		Field.set(packet, "c", data.getNMS());
		return packet;
	}

	@Override
	public int getPacketID() {
		return 0x04;
	}

	@Override
	public String getProtocolURLString() {
		return "https://wiki.vg/Protocol#Login_Plugin_Request";
	}

}
