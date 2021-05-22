package io.github.riesenpilz.nms.packet.loginIn;

import org.bukkit.entity.Player;

import io.github.riesenpilz.nms.reflections.Field;
import net.minecraft.server.v1_16_R3.Packet;
import net.minecraft.server.v1_16_R3.PacketDataSerializer;
import net.minecraft.server.v1_16_R3.PacketLoginInCustomPayload;
import net.minecraft.server.v1_16_R3.PacketLoginInEncryptionBegin;
import net.minecraft.server.v1_16_R3.PacketLoginInListener;

/**
 * https://wiki.vg/Protocol#Login_Plugin_Response<br>
 * <br>
 * Packet ID: 0x02<br>
 * State: Login<br>
 * Bound To: Server
 * 
 * @author Martin
 *
 */
public class PacketLoginInCustomPayloadEvent extends PacketLoginInEvent {

	public static final String PROTOCOL_URL = "https://wiki.vg/Protocol#Login_Plugin_Response";
	public static final int PACKET_ID = 2;

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
		super(injectedPlayer, PACKET_ID, PROTOCOL_URL);
		this.messageID = messageID;
		this.data = data;
	}

	public PacketLoginInCustomPayloadEvent(Player injectedPlayer, PacketLoginInCustomPayload packet) {
		super(injectedPlayer, PACKET_ID, PROTOCOL_URL);
		messageID = (int) new Field(PacketLoginInEncryptionBegin.class, "a").get(packet);
		data = (PacketDataSerializer) new Field(PacketLoginInEncryptionBegin.class, "b").get(packet);
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
		new Field(PacketLoginInEncryptionBegin.class, "a").set(packet, messageID);
		new Field(PacketLoginInEncryptionBegin.class, "b").set(packet, data);
		return packet;
	}

}
