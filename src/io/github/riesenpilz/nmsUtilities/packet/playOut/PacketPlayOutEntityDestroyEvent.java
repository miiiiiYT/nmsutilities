package io.github.riesenpilz.nmsUtilities.packet.playOut;

import org.apache.commons.lang.Validate;
import org.bukkit.entity.Player;

import io.github.riesenpilz.nmsUtilities.reflections.Field;
import net.minecraft.server.v1_16_R3.Packet;
import net.minecraft.server.v1_16_R3.PacketListenerPlayOut;
import net.minecraft.server.v1_16_R3.PacketPlayOutEntityDestroy;

/**
 * https://wiki.vg/Protocol#Destroy_Entities
 * <p>
 * Sent by the server when an entity is to be destroyed on the client.
 * <p>
 * Packet ID: 0x3A<br>
 * State: Play<br>
 * Bound To: Client
 * 
 * @author Martin
 *
 */
public class PacketPlayOutEntityDestroyEvent extends PacketPlayOutEvent {

	/**
	 * The list of entities to destroy.
	 */
	private int[] entityIds;

	public PacketPlayOutEntityDestroyEvent(Player injectedPlayer, PacketPlayOutEntityDestroy packet) {
		super(injectedPlayer);

		Validate.notNull(packet);

		entityIds = Field.get(packet, "a", int[].class);
	}

	public PacketPlayOutEntityDestroyEvent(Player injectedPlayer, int[] entityIds) {
		super(injectedPlayer);

		Validate.notNull(entityIds);

		this.entityIds = entityIds;
	}

	public int[] getEntityIds() {
		return entityIds;
	}

	@Override
	public Packet<PacketListenerPlayOut> getNMS() {
		return new PacketPlayOutEntityDestroy(entityIds);
	}

	@Override
	public int getPacketID() {
		return 0x3A;
	}

	@Override
	public String getProtocolURLString() {
		return "https://wiki.vg/Protocol#Destroy_Entities";
	}
}
