package io.github.riesenpilz.nms.packet.playOut;

import org.bukkit.entity.Player;

import io.github.riesenpilz.nms.reflections.Field;
import net.minecraft.server.v1_16_R3.Packet;
import net.minecraft.server.v1_16_R3.PacketListenerPlayOut;
import net.minecraft.server.v1_16_R3.PacketPlayOutAttachEntity;

/**
 * https://wiki.vg/Protocol#Attach_Entity
 * <p>
 * This packet is sent when an entity has been leashed to another entity.
 * <p>
 * Packet ID: 0x4E<br>
 * State: Play<br>
 * Bound To: Client
 * 
 * @author Martin
 *
 */
public class PacketPlayOutEntityAttachEvent extends PacketPlayOutEvent {

	private int attachedEntity;

	/**
	 * ID of the entity holding the lead. Set to -1 to detach.
	 */
	private int holdingEntity;

	public PacketPlayOutEntityAttachEvent(Player injectedPlayer, PacketPlayOutAttachEntity packet) {
		super(injectedPlayer);

		attachedEntity = Field.get(packet, "a", int.class);
		holdingEntity = Field.get(packet, "b", int.class);
	}

	public PacketPlayOutEntityAttachEvent(Player injectedPlayer, int attachedEntity, int holdingEntity) {
		super(injectedPlayer);
		this.attachedEntity = attachedEntity;
		this.holdingEntity = holdingEntity;
	}

	public int getAttachedEntity() {
		return attachedEntity;
	}

	public int getHoldingEntity() {
		return holdingEntity;
	}

	@Override
	public Packet<PacketListenerPlayOut> getNMS() {
		final PacketPlayOutAttachEntity packet = new PacketPlayOutAttachEntity();
		Field.set(packet, "a", attachedEntity);
		Field.set(packet, "b", holdingEntity);
		return packet;
	}

	@Override
	public int getPacketID() {
		return 0x4E;
	}

	@Override
	public String getProtocolURLString() {
		return "https://wiki.vg/Protocol#Attach_Entity";
	}
}
