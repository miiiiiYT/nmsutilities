package io.github.riesenpilz.nms.packet.playIn;

import javax.annotation.Nullable;

import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import io.github.riesenpilz.nms.entity.player.Hand;
import io.github.riesenpilz.nms.packet.PacketUtils;
import io.github.riesenpilz.nms.reflections.Field;
import net.minecraft.server.v1_16_R3.Packet;
import net.minecraft.server.v1_16_R3.PacketListenerPlayIn;
import net.minecraft.server.v1_16_R3.PacketPlayInUseEntity;
import net.minecraft.server.v1_16_R3.PacketPlayInUseEntity.EnumEntityUseAction;

/**
 * https://wiki.vg/Protocol#Interact_Entity
 * <p>
 * This packet is sent from the client to the server when the client attacks or
 * right-clicks another entity (a player, minecart, etc). A Notchian server only
 * accepts this packet if the entity being attacked/used is visible without
 * obstruction and within a 4-unit radius of the player's position. The vector
 * field represent the difference between the vector location of the cursor at
 * the time of the packet and the entity's position. Note that middle-click in
 * creative mode is interpreted by the client and sent as a Creative Inventory
 * Action packet instead.
 * <p>
 * Packet ID: 0x0E<br>
 * State: Play<br>
 * Bound To: Server
 *
 * @author Martin
 *
 */
public class PacketPlayInEntityInteractEvent extends PacketPlayInEvent {

	private int entityID;
	private EntityUseAction action;
	private Vector vector;

	/**
	 * Only if Type is interact or interact at.
	 */
	@Nullable
	private Hand hand;
	private boolean sneaking;

	public PacketPlayInEntityInteractEvent(Player injectedPlayer, PacketPlayInUseEntity packet) {
		super(injectedPlayer);
		entityID = Field.get(packet, "a", int.class);
		vector = EntityUseAction.INTERACT_AT.equals(action) ? PacketUtils.toVetor(packet.d()) : new Vector();
		hand = !EntityUseAction.ATTACK.equals(action) ? Hand.getHand(packet.c()) : null;
		sneaking = packet.e();
	}

	public PacketPlayInEntityInteractEvent(Player injectedPlayer, int entityID, EntityUseAction action, Vector vector,
			Hand hand, boolean sneaking) {
		super(injectedPlayer);
		this.entityID = entityID;
		this.action = action;
		this.vector = vector;
		this.hand = hand;
		this.sneaking = sneaking;
	}

	public int getEntityID() {
		return entityID;
	}

	public EntityUseAction getAction() {
		return action;
	}

	public Vector getVelocity() {
		return vector;
	}

	public Hand getHand() {
		return hand;
	}

	public boolean isSneaking() {
		return sneaking;
	}

	@Override
	public Packet<PacketListenerPlayIn> getNMS() {
		final PacketPlayInUseEntity packet = new PacketPlayInUseEntity();
		Field.set(packet, "a", entityID);
		Field.set(packet, "b", action.getNMS());
		Field.set(packet, "c", PacketUtils.toVec3D(vector));
		Field.set(packet, "d", hand.getNMS());
		Field.set(packet, "e", sneaking);
		return packet;
	}

	@Override
	public int getPacketID() {
		return 0x0E;
	}

	@Override
	public String getProtocolURLString() {
		return "https://wiki.vg/Protocol#Interact_Entity";
	}

	public enum EntityUseAction {

		INTERACT(EnumEntityUseAction.INTERACT), ATTACK(EnumEntityUseAction.ATTACK),
		INTERACT_AT(EnumEntityUseAction.INTERACT_AT);

		private EnumEntityUseAction nms;

		EntityUseAction(EnumEntityUseAction nms) {
			this.nms = nms;
		}

		public EnumEntityUseAction getNMS() {
			return nms;
		}

		public static EntityUseAction getEntityUseAction(EnumEntityUseAction nms) {
			for (EntityUseAction action : values())
				if (action.getNMS().equals(nms))
					return action;
			return null;
		}
	}
}
