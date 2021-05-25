package io.github.riesenpilz.nms.packet.loginIn;

import org.bukkit.entity.Player;

import io.github.riesenpilz.nms.packet.PacketDataSerializer;
import io.github.riesenpilz.nms.reflections.Field;
import net.minecraft.server.v1_16_R3.Packet;
import net.minecraft.server.v1_16_R3.PacketLoginInCustomPayload;
import net.minecraft.server.v1_16_R3.PacketLoginInEncryptionBegin;
import net.minecraft.server.v1_16_R3.PacketLoginInListener;

/**
 * https://wiki.vg/Protocol#Login_Plugin_Response
 * <p>
 * Packet ID: 0x02<br>
 * State: Login<br>
 * Bound To: Server
 *
 * @author Martin
 *
 */
public class PacketLoginInCustomPayloadEvent extends PacketLoginInEvent {

	/**
	 * Should match ID from server.
	 */
	private int messageID;

	/**
	 * Any data, depending on the channel. The length of this array must be inferred
	 * from the packet length.
	 */
	private PacketDataSerializer data;

	public PacketLoginInCustomPayloadEvent(Player injectedPlayer, int messageID, PacketDataSerializer data) {
		super(injectedPlayer);
		this.messageID = messageID;
		this.data = data;
	}

	public PacketLoginInCustomPayloadEvent(Player injectedPlayer, PacketLoginInCustomPayload packet) {
		super(injectedPlayer);
		messageID = Field.get(packet, "a", int.class);
		data = new PacketDataSerializer(
				Field.get(packet, "a", net.minecraft.server.v1_16_R3.PacketDataSerializer.class));
	}

	public int getMessageID() {
		return messageID;
	}

	public PacketDataSerializer getData() {
		return data;
	}

	@Override
	public Packet<PacketLoginInListener> getNMS() {
		final PacketLoginInEncryptionBegin packet = new PacketLoginInEncryptionBegin();
		Field.set(packet, "a", messageID);
		Field.set(packet, "b", data.getNMS());
		return packet;
	}

	@Override
	public int getPacketID() {
		return 0x02;
	}

	@Override
	public String getProtocolURLString() {
		return "https://wiki.vg/Protocol#Login_Plugin_Response";
	}

}
