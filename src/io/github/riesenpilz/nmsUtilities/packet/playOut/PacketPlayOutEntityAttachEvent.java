package io.github.riesenpilz.nmsUtilities.packet.playOut;

import org.bukkit.entity.Player;

import io.github.riesenpilz.nmsUtilities.reflections.Field;
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
public class PacketPlayOutEntityAttachEvent extends PacketPlayOutEntityEvent {

	/**
	 * ID of the entity holding the lead. Set to -1 to detach.
	 */
	private int holdingEntityId;

	public PacketPlayOutEntityAttachEvent(Player injectedPlayer, PacketPlayOutAttachEntity packet) {
		super(injectedPlayer, packet);

		holdingEntityId = Field.get(packet, "b", int.class);
	}

	public PacketPlayOutEntityAttachEvent(Player injectedPlayer, int attachedEntityId, int holdingEntityId) {
		super(injectedPlayer, attachedEntityId);
		this.holdingEntityId = holdingEntityId;
	}

	/**
	 * Attached entity id
	 */
	public int getEntityId() {
		return super.getEntityId();
	}

	public int getHoldingEntityId() {
		return holdingEntityId;
	}

	@Override
	public Packet<PacketListenerPlayOut> getNMS() {
		final PacketPlayOutAttachEntity packet = new PacketPlayOutAttachEntity();
		Field.set(packet, "a", getEntityId());
		Field.set(packet, "b", holdingEntityId);
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
