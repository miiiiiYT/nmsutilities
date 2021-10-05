package io.github.riesenpilz.nmsUtilities.packet.playOut;

import org.bukkit.entity.Player;

import io.github.riesenpilz.nmsUtilities.entity.WorldEntity;
import io.github.riesenpilz.nmsUtilities.reflections.Field;
import net.minecraft.server.v1_16_R3.Packet;
import net.minecraft.server.v1_16_R3.PacketListenerPlayOut;
import net.minecraft.server.v1_16_R3.PacketPlayOutOpenWindowHorse;

/**
 * https://wiki.vg/Protocol#Open_Horse_Window
 * <p>
 * This packet is used exclusively for opening the horse GUI. Open Window is
 * used for all other GUIs.
 * <p>
 * Packet ID: 0x1E<br>
 * State: Play<br>
 * Bound To: Client
 * 
 * @author Martin
 *
 */
public class PacketPlayOutOpenHorseWindowEvent extends PacketPlayOutInventoryEvent {

	private int slots;
	private int entityId;

	public PacketPlayOutOpenHorseWindowEvent(Player injectedPlayer, PacketPlayOutOpenWindowHorse packet) {
		super(injectedPlayer, packet);
		slots = Field.get(packet, "b", int.class);
		entityId = Field.get(packet, "c", int.class);
	}

	public PacketPlayOutOpenHorseWindowEvent(Player injectedPlayer, int inventoryId, int slots, int entityId) {
		super(injectedPlayer, inventoryId);
		this.slots = slots;
		this.entityId = entityId;
	}

	public int getSlots() {
		return slots;
	}

	public int getEntityId() {
		return entityId;
	}
	public WorldEntity getEntity() {
		return WorldEntity.getWorldEntity(entityId, getBukkit().getWorld());
	}
	@Override
	public Packet<PacketListenerPlayOut> getNMS() {
		return new PacketPlayOutOpenWindowHorse(getInventoryId(), slots, entityId);
	}

	@Override
	public int getPacketID() {
		return 0x1E;
	}

	@Override
	public String getProtocolURLString() {
		return "https://wiki.vg/Protocol#Open_Horse_Window";
	}
}
