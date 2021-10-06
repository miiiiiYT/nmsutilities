package io.github.riesenpilz.nmsUtilities.packet.playOut;

import org.bukkit.entity.Player;

import io.github.riesenpilz.nmsUtilities.reflections.Field;
import net.minecraft.server.v1_16_R3.Packet;
import net.minecraft.server.v1_16_R3.PacketListenerPlayOut;
import net.minecraft.server.v1_16_R3.PacketPlayOutResourcePackSend;

/**
 * https://wiki.vg/Protocol#Resource_Pack_Send
 * <p>
 * Packet ID: 0x3C<br>
 * State: Play<br>
 * Bound To: Client
 * 
 * @author Martin
 *
 */
public class PacketPlayOutResourcePackEvent extends PacketPlayOutEvent {

	/**
	 * The URL to the resource pack.
	 */
	private String url;

	/**
	 * A 40 character hexadecimal and lowercase SHA-1 hash of the resource pack
	 * file. (must be lower case in order to work)<br>
	 * If it's not a 40 character hexadecimal string, the client will not use it for
	 * hash verification and likely waste bandwidth — but it will still treat it as
	 * a unique id
	 */
	private String hash;

	public PacketPlayOutResourcePackEvent(Player injectedPlayer, PacketPlayOutResourcePackSend packet) {
		super(injectedPlayer);

		url = Field.get(packet, "a", String.class);
		hash = Field.get(packet, "b", String.class);
	}

	public PacketPlayOutResourcePackEvent(Player injectedPlayer, String url, String hash) {
		super(injectedPlayer);
		this.url = url;
		this.hash = hash;
	}

	public String getUrl() {
		return url;
	}

	public String getHash() {
		return hash;
	}

	@Override
	public Packet<PacketListenerPlayOut> getNMS() {
		return new PacketPlayOutResourcePackSend(url, hash);
	}

	@Override
	public int getPacketID() {
		return 0x3C;
	}

	@Override
	public String getProtocolURLString() {
		return "https://wiki.vg/Protocol#Resource_Pack_Send";
	}
}
