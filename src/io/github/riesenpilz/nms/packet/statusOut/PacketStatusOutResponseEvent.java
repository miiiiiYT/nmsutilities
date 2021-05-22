package io.github.riesenpilz.nms.packet.statusOut;

import org.bukkit.entity.Player;

import com.google.gson.Gson;

import io.github.riesenpilz.nms.reflections.Field;
import net.minecraft.server.v1_16_R3.Packet;
import net.minecraft.server.v1_16_R3.PacketStatusOutListener;
import net.minecraft.server.v1_16_R3.PacketStatusOutServerInfo;

/**
 * https://wiki.vg/Protocol#Response<br>
 * <br>
 * Packet ID: 0x00<br>
 * State: Status<br>
 * Bound To: Client
 * 
 * @author Martin
 *
 */
public class PacketStatusOutResponseEvent extends PacketStatusOutEvent {

	/**
	 * See Server List Ping#Response; as with all strings this is prefixed by its
	 * length as a VarInt. ( 2767 chars)
	 * 
	 */
	private static final Gson response = (Gson) new Field(PacketStatusOutServerInfo.class, "a").get(null);

	public PacketStatusOutResponseEvent(Player injectedPlayer) {
		super(injectedPlayer);
	}

	public PacketStatusOutResponseEvent(Player injectedPlayer, PacketStatusOutServerInfo packet) {
		super(injectedPlayer);
	}

	public static Gson getResponse() {
		return response;
	}

	@Override
	public Packet<PacketStatusOutListener> getNMS() {
		return new PacketStatusOutServerInfo();
	}

	@Override
	public int getPacketID() {
		return 0;
	}

	@Override
	public String getProtocolURLString() {
		return "https://wiki.vg/Protocol#Response";
	}

}
