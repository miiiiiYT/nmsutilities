package io.github.riesenpilz.nms.packet.loginOut;

import org.bukkit.entity.Player;

import io.github.riesenpilz.nms.reflections.Field;
import net.minecraft.server.v1_16_R3.Packet;
import net.minecraft.server.v1_16_R3.PacketLoginOutListener;
import net.minecraft.server.v1_16_R3.PacketLoginOutSetCompression;

/**
 * https://wiki.vg/Protocol#Set_Compression<br>
 * <br>
 * Enables compression. If compression is enabled, all following packets are
 * encoded in the compressed packet format. Negative or zero values will disable
 * compression, meaning the packet format should remain in the uncompressed
 * packet format. However, this packet is entirely optional, and if not sent,
 * compression will also not be enabled (the notchian server does not send the
 * packet when compression is disabled). <br>
 * <br>
 * Packet ID: 0x03<br>
 * State: Login<br>
 * Bound To: Client
 * 
 * @author Martin
 *
 */
public class PacketLoginOutSetCompressionEvent extends PacketLoginOutEvent {

	public static final String PROTOCOL_URL = "https://wiki.vg/Protocol#Set_Compression";
	public static final int PACKET_ID = 3;

	/**
	 * Maximum size of a packet before it is compressed.
	 */
	private int threshold;

	public PacketLoginOutSetCompressionEvent(Player injectedPlayer, int threshold) {
		super(injectedPlayer, PACKET_ID, PROTOCOL_URL);
		this.threshold = threshold;
	}

	public PacketLoginOutSetCompressionEvent(Player injectedPlayer, PacketLoginOutSetCompression packet) {
		super(injectedPlayer, PACKET_ID, PROTOCOL_URL);
		threshold = (int) new Field(PacketLoginOutSetCompression.class, "a").get(packet);
	}

	public int getThreshold() {
		return threshold;
	}

	@Override
	public Packet<PacketLoginOutListener> getNMS() {
		return new PacketLoginOutSetCompression(threshold);
	}

}
