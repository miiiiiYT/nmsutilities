package io.github.riesenpilz.nmsUtilities.packet.playIn;

import javax.annotation.Nullable;

import org.apache.commons.lang.Validate;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import io.github.riesenpilz.nmsUtilities.entity.EntityUseAction;
import io.github.riesenpilz.nmsUtilities.entity.livingEntity.player.Hand;
import io.github.riesenpilz.nmsUtilities.packet.PacketUtils;
import io.github.riesenpilz.nmsUtilities.reflections.Field;
import net.minecraft.server.v1_16_R3.Packet;
import net.minecraft.server.v1_16_R3.PacketListenerPlayIn;
import net.minecraft.server.v1_16_R3.PacketPlayInUseEntity;

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
public class PacketPlayInEntityInteractEvent extends PacketPlayInEntityEvent {

	private EntityUseAction action;
	private Vector vector;

	/**
	 * Only if Type is interact or interact at.
	 */
	@Nullable
	private Hand hand;
	private boolean sneaking;

	public PacketPlayInEntityInteractEvent(Player injectedPlayer, PacketPlayInUseEntity packet) {
		super(injectedPlayer, packet);
		Validate.notNull(packet);
		vector = EntityUseAction.INTERACT_AT.equals(action) ? PacketUtils.toVetor(packet.d()) : new Vector();
		hand = !EntityUseAction.ATTACK.equals(action) ? Hand.getHand(packet.c()) : null;
		sneaking = packet.e();
	}

	public PacketPlayInEntityInteractEvent(Player injectedPlayer, int entityId, EntityUseAction action, Vector vector,
			Hand hand, boolean sneaking) {
		super(injectedPlayer, entityId);
		Validate.notNull(action);
		Validate.notNull(vector);
		Validate.isTrue((action != EntityUseAction.INTERACT && action != EntityUseAction.INTERACT_AT) || hand != null);
		this.action = action;
		this.vector = vector;
		this.hand = hand;
		this.sneaking = sneaking;
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
		Field.set(packet, "a", getEntityId());
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

	
}
