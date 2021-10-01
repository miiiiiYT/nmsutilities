package io.github.riesenpilz.nms.packet.playOut;

import org.bukkit.entity.Player;

import io.github.riesenpilz.nms.reflections.Field;
import net.minecraft.server.v1_16_R3.Packet;
import net.minecraft.server.v1_16_R3.PacketListenerPlayOut;
import net.minecraft.server.v1_16_R3.PacketPlayOutCollect;

/**
 * https://wiki.vg/Protocol#Collect_Item
 * <p>
 * Sent by the server when someone picks up an item lying on the ground — its
 * sole purpose appears to be the animation of the item flying towards you. It
 * doesn't destroy the entity in the client memory, and it doesn't add it to
 * your inventory. The server only checks for items to be picked up after each
 * Player Position (and Player Position And Look) packet sent by the client. The
 * collector entity can be any entity; it does not have to be a player. The
 * collected entity also can be any entity, but the Notchian server only uses
 * this for items, experience orbs, and the different varieties of arrows.
 * <p>
 * Packet ID: 0x60<br>
 * State: Play<br>
 * Bound To: Client
 * 
 * @author Martin
 *
 */
public class PacketPlayOutCollectItemEvent extends PacketPlayOutEntityEvent {

	private int collectedEntityId;

	/**
	 * Seems to be 1 for XP orbs, otherwise the number of items in the stack.
	 */
	private int count;

	public PacketPlayOutCollectItemEvent(Player injectedPlayer, PacketPlayOutCollect packet) {
		super(injectedPlayer, packet, "b");

		collectedEntityId = Field.get(packet, "a", int.class);
		count = Field.get(packet, "c", int.class);
	}

	public PacketPlayOutCollectItemEvent(Player injectedPlayer, int collectedEntityId, int collectorEntityId,
			int count) {
		super(injectedPlayer, collectorEntityId);
		this.collectedEntityId = collectedEntityId;
		this.count = count;
	}

	public int getCollectedEntityId() {
		return collectedEntityId;
	}
	/**
	 * collecotor entity ID
	 */
	public int getEntityId() {
		return super.getEntityId();
	}

	public int getCount() {
		return count;
	}

	@Override
	public Packet<PacketListenerPlayOut> getNMS() {
		return new PacketPlayOutCollect(collectedEntityId, getEntityId(), count);
	}

	@Override
	public int getPacketID() {
		return 0x60;
	}

	@Override
	public String getProtocolURLString() {
		return "https://wiki.vg/Protocol#Collect_Item";
	}
}
