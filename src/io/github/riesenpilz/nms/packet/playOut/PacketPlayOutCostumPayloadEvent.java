package io.github.riesenpilz.nms.packet.playOut;

import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;

import io.github.riesenpilz.nms.packet.PacketUtils;
import io.github.riesenpilz.nms.reflections.Field;
import net.minecraft.server.v1_16_R3.MinecraftKey;
import net.minecraft.server.v1_16_R3.Packet;
import net.minecraft.server.v1_16_R3.PacketDataSerializer;
import net.minecraft.server.v1_16_R3.PacketListenerPlayOut;
import net.minecraft.server.v1_16_R3.PacketPlayOutCustomPayload;

/**
 * https://wiki.vg/Protocol#Plugin_Message_.28clientbound.29
 * <p>
 * Mods and plugins can use this to send their data. Minecraft itself uses
 * several plugin channels. These internal channels are in the minecraft
 * namespace.
 * <p>
 * More documentation on this:
 * http://dinnerbone.com/blog/2012/01/13/minecraft-plugin-channels-messaging/
 * <p>
 * Packet ID: 0x17<br>
 * State: Play<br>
 * Bound To: Client
 * 
 * @author Martin
 *
 */
public class PacketPlayOutCostumPayloadEvent extends PacketPlayOutEvent {

	/**
	 * Name of the plugin channel used to send the data.
	 */
	private NamespacedKey key;

	/**
	 * Any data, depending on the channel. minecraft: channels are documented here.
	 */
	private PacketDataSerializer data;

	public PacketPlayOutCostumPayloadEvent(Player injectedPlayer, PacketPlayOutCustomPayload packet) {
		super(injectedPlayer);
		key = PacketUtils.toNamespacedKey(Field.get(packet, "r", MinecraftKey.class));
	}

	public NamespacedKey getKey() {
		return key;
	}

	public PacketDataSerializer getData() {
		return data;
	}

	@Override
	public Packet<PacketListenerPlayOut> getNMS() {
		return new PacketPlayOutCustomPayload(PacketUtils.toMinecraftKey(key), data);
	}

	@Override
	public int getPacketID() {
		return 0x17;
	}

	@Override
	public String getProtocolURLString() {
		return "https://wiki.vg/Protocol#Plugin_Message_.28clientbound.29";
	}
}
