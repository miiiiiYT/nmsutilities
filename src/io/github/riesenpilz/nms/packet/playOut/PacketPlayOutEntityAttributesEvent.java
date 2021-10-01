package io.github.riesenpilz.nms.packet.playOut;

import java.util.List;

import org.bukkit.entity.Player;

import io.github.riesenpilz.nms.reflections.Field;
import net.minecraft.server.v1_16_R3.Packet;
import net.minecraft.server.v1_16_R3.PacketListenerPlayOut;
import net.minecraft.server.v1_16_R3.PacketPlayOutUpdateAttributes;
import net.minecraft.server.v1_16_R3.PacketPlayOutUpdateAttributes.AttributeSnapshot;

/**
 * https://wiki.vg/Protocol#Entity_Properties
 * <p>
 * Sets attributes on the given entity.
 * <p>
 * Packet ID: 0x63<br>
 * State: Play<br>
 * Bound To: Client
 * 
 * @author Martin
 *
 */
public class PacketPlayOutEntityAttributesEvent extends PacketPlayOutEntityEvent {

    private List<AttributeSnapshot> attributes;

	
	@SuppressWarnings("unchecked")
	public PacketPlayOutEntityAttributesEvent(Player injectedPlayer, PacketPlayOutUpdateAttributes packet) {
		super(injectedPlayer, packet);
		
		attributes = Field.get(packet, "b", List.class);
	}

	public PacketPlayOutEntityAttributesEvent(Player injectedPlayer, int entityId, List<AttributeSnapshot> attributes) {
		super(injectedPlayer, entityId);
		this.attributes = attributes;
	}

	public List<AttributeSnapshot> getAttributes() {
		return attributes;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Packet<PacketListenerPlayOut> getNMS() {
		final PacketPlayOutUpdateAttributes packet = new PacketPlayOutUpdateAttributes();
		Field.set(packet, "a", getEntityId());
		Field.get(packet, "b", List.class).addAll(attributes);
		return packet;
	}

	@Override
	public int getPacketID() {
		return 0x63;
	}

	@Override
	public String getProtocolURLString() {
		return "https://wiki.vg/Protocol#Entity_Properties";
	}
}
