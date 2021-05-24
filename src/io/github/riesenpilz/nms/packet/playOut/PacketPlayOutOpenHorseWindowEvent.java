package io.github.riesenpilz.nms.packet.playOut;

import org.bukkit.entity.Player;

import io.github.riesenpilz.nms.reflections.Field;
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
public class PacketPlayOutOpenHorseWindowEvent extends PacketPlayOutEvent {

	private int windowID;
	private int slots;
	private int entityID;

	public PacketPlayOutOpenHorseWindowEvent(Player injectedPlayer, PacketPlayOutOpenWindowHorse packet) {
		super(injectedPlayer);
		windowID = Field.get(packet, "a", int.class);
		slots = Field.get(packet, "b", int.class);
		entityID = Field.get(packet, "c", int.class);
	}

	public PacketPlayOutOpenHorseWindowEvent(Player injectedPlayer, int windowID, int slots, int entityID) {
		super(injectedPlayer);
		this.windowID = windowID;
		this.slots = slots;
		this.entityID = entityID;
	}

	public int getWindowID() {
		return windowID;
	}

	public int getSlots() {
		return slots;
	}

	public int getEntityID() {
		return entityID;
	}

	@Override
	public Packet<PacketListenerPlayOut> getNMS() {
		return new PacketPlayOutOpenWindowHorse(windowID, slots, entityID);
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
