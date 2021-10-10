package io.github.riesenpilz.nmsUtilities.packet.playOut;

import org.apache.commons.lang.Validate;
import org.bukkit.entity.Player;

import io.github.riesenpilz.nmsUtilities.reflections.Field;
import net.minecraft.server.v1_16_R3.ITagRegistry;
import net.minecraft.server.v1_16_R3.Packet;
import net.minecraft.server.v1_16_R3.PacketListenerPlayOut;
import net.minecraft.server.v1_16_R3.PacketPlayOutTags;

/**
 * https://wiki.vg/Protocol#Tags
 * <p>
 * Packet ID: 0x66<br>
 * State: Play<br>
 * Bound To: Client
 * 
 * @author Martin
 *
 */
public class PacketPlayOutTagsEvent extends PacketPlayOutEvent {

	private ITagRegistry tags;

	public PacketPlayOutTagsEvent(Player injectedPlayer, PacketPlayOutTags packet) {
		super(injectedPlayer);

		Validate.notNull(packet);

		tags = Field.get(packet, "a", ITagRegistry.class);
	}

	public PacketPlayOutTagsEvent(Player injectedPlayer, ITagRegistry tags) {
		super(injectedPlayer);

		Validate.notNull(tags);

		this.tags = tags;
	}

	public ITagRegistry getTags() {
		return tags;
	}

	@Override
	public Packet<PacketListenerPlayOut> getNMS() {
		return new PacketPlayOutTags(tags);
	}

	@Override
	public int getPacketID() {
		return 0x66;
	}

	@Override
	public String getProtocolURLString() {
		return "https://wiki.vg/Protocol#Tags";
	}
}
