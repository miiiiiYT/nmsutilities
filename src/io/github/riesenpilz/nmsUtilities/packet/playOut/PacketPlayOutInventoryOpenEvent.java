package io.github.riesenpilz.nmsUtilities.packet.playOut;

import org.bukkit.entity.Player;

import io.github.riesenpilz.nmsUtilities.reflections.Field;
import net.minecraft.server.v1_16_R3.IChatBaseComponent;
import net.minecraft.server.v1_16_R3.Packet;
import net.minecraft.server.v1_16_R3.PacketListenerPlayOut;
import net.minecraft.server.v1_16_R3.PacketPlayOutOpenWindow;

/**
 * https://wiki.vg/Protocol#Open_Window
 * <p>
 * This is sent to the client when it should open an inventory, such as a chest,
 * workbench, or furnace. This message is not sent anywhere for clients opening
 * their own inventory. For horses, use Open Horse Window.
 * <p>
 * Packet ID: 0x2E<br>
 * State: Play<br>
 * Bound To: Client
 * 
 * @author Martin
 *
 */
public class PacketPlayOutInventoryOpenEvent extends PacketPlayOutInventoryEvent {

	/**
	 * The window type to use for display. Contained in the minecraft:menu registry;
	 * see Inventory for the different values. (https://wiki.vg/Inventory)
	 */
	private int windowType;
	private IChatBaseComponent title;

	public PacketPlayOutInventoryOpenEvent(Player injectedPlayer, PacketPlayOutOpenWindow packet) {
		super(injectedPlayer, packet);
		windowType = Field.get(packet, "b", int.class);
		title = Field.get(packet, "c", IChatBaseComponent.class);
	}

	public PacketPlayOutInventoryOpenEvent(Player injectedPlayer, int inventoryId, int windowType,
			IChatBaseComponent title) {
		super(injectedPlayer, inventoryId);
		this.windowType = windowType;
		this.title = title;
	}

	public int getWindowType() {
		return windowType;
	}

	public IChatBaseComponent getTitle() {
		return title;
	}

	@Override
	public Packet<PacketListenerPlayOut> getNMS() {
		final PacketPlayOutOpenWindow packet = new PacketPlayOutOpenWindow();
		Field.set(packet, "a", getInventoryId());
		Field.set(packet, "b", windowType);
		Field.set(packet, "c", title);
		return packet;
	}

	@Override
	public int getPacketID() {
		return 0x2E;
	}

	@Override
	public String getProtocolURLString() {
		return "https://wiki.vg/Protocol#Open_Window";
	}
}
