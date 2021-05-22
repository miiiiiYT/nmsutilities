package io.github.riesenpilz.nms.packet.playIn;

import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;

import net.minecraft.server.v1_16_R3.MinecraftKey;
import net.minecraft.server.v1_16_R3.Packet;
import net.minecraft.server.v1_16_R3.PacketDataSerializer;
import net.minecraft.server.v1_16_R3.PacketListenerPlayIn;
import net.minecraft.server.v1_16_R3.PacketPlayInCustomPayload;

/**
 * https://wiki.vg/Protocol#Plugin_Message_.28serverbound.29<br>
 * <br>
 * Mods and plugins can use this to send their data. Minecraft itself uses some
 * plugin channels. These internal channels are in the minecraft namespace.<br>
 * More documentation on this:<br>
 * http://dinnerbone.com/blog/2012/01/13/minecraft-plugin-channels-messaging/<br>
 * Note that the length of Data is known only from the packet length, since the
 * packet has no length field of any kind.<br>
 * <br>
 * Packet ID: 0x0B<br>
 * State: Play<br>
 * Bound To: Server
 * 
 * @author Martin
 *
 */
public class PacketPlayInCustomPayloadEvent extends PacketPlayInEvent {

	/**
	 * Two-way<br>
	 * <br>
	 * For version 1.12.2(protocol version 340) and below, channel name is:
	 * MC|Brand<br>
	 * <br>
	 * Announces the server and client implementation name right after a player has
	 * logged in. For the Notchian client and server, this is "vanilla" (which is
	 * chosen using the Function<br>
	 * net.minecraft.client.ClientBrandRetriever.getClientModName()) (encoded as a
	 * UTF-8 string).<br>
	 * <br>
	 * These brands are used in crash reports and a few other locations. It's
	 * recommended that custom clients and servers use their own brand names for the
	 * purpose of identification (for the Notchian client, the class used to get the
	 * brand is one of the few non-obfuscated classes). The brand is not processed
	 * in any other way, and Notchian clients will connect to servers with different
	 * brands with no issue (the brand is not used to validate).<br>
	 * <br>
	 * The Notchian server sends a minecraft:brand packet right after it sends a
	 * Join Game packet, and the Notchian client sends it right after receiving a
	 * Join Game packet. However, some modified clients and servers will not send
	 * this packet (or will take longer to send it than normal), so it is important
	 * to not crash if the brand has not been sent. Additionally, the brand may
	 * change at any time (for instance, if connected through a BungeeCord instance,
	 * you may switch from a server with one brand to a server with another brand
	 * without receiving a Join Game packet).
	 */
	@SuppressWarnings("deprecation")
	public static final NamespacedKey BRAD = new NamespacedKey(PacketPlayInCustomPayload.a.getNamespace(),
			PacketPlayInCustomPayload.a.getKey());

	/**
	 * Name of the plugin channel used to send the data.
	 */
	private NamespacedKey channel;

	/**
	 * Any data, depending on the channel.
	 */
	private PacketDataSerializer data;

	@SuppressWarnings("deprecation")
	public PacketPlayInCustomPayloadEvent(Player injectedPlayer, PacketPlayInCustomPayload packet) {
		super(injectedPlayer);
		channel = new NamespacedKey(packet.tag.getNamespace(), packet.tag.getKey());
		data = packet.data;
	}

	public PacketPlayInCustomPayloadEvent(Player injectedPlayer, NamespacedKey channel, PacketDataSerializer data) {
		super(injectedPlayer);
		this.channel = channel;
		this.data = data;
	}

	public NamespacedKey getChannel() {
		return channel;
	}

	public PacketDataSerializer getData() {
		return data;
	}

	@Override
	public Packet<PacketListenerPlayIn> getNMS() {
		final PacketPlayInCustomPayload packet = new PacketPlayInCustomPayload();
		packet.tag = new MinecraftKey(channel.getNamespace(), channel.getKey());
		packet.data = data;
		return packet;
	}

	@Override
	public int getPacketID() {
		return 0x0B;
	}

	@Override
	public String getProtocolURLString() {
		return "https://wiki.vg/Protocol#Plugin_Message_.28serverbound.29";
	}
}
