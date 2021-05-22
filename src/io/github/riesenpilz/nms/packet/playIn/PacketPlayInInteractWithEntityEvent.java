package io.github.riesenpilz.nms.packet.playIn;

import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import io.github.riesenpilz.nms.entity.Entity;
import io.github.riesenpilz.nms.entity.player.Hand;
import io.github.riesenpilz.nms.reflections.Field;
import net.minecraft.server.v1_16_R3.Packet;
import net.minecraft.server.v1_16_R3.PacketListenerPlayIn;
import net.minecraft.server.v1_16_R3.PacketPlayInSetCommandMinecart;
import net.minecraft.server.v1_16_R3.PacketPlayInUseEntity;
import net.minecraft.server.v1_16_R3.PacketPlayInUseEntity.EnumEntityUseAction;
import net.minecraft.server.v1_16_R3.Vec3D;

/**
 * https://wiki.vg/Protocol#Interact_Entity<br>
 * <br>
 * This packet is sent from the client to the server when the client attacks or
 * right-clicks another entity (a player, minecart, etc). A Notchian server only
 * accepts this packet if the entity being attacked/used is visible without
 * obstruction and within a 4-unit radius of the player's position. The target
 * X, Y, and Z fields represent the difference between the vector location of
 * the cursor at the time of the packet and the entity's position. Note that
 * middle-click in creative mode is interpreted by the client and sent as a
 * Creative Inventory Action packet instead. <br>
 * <br>
 * Packet ID: 0x0E<br>
 * State: Play<br>
 * Bound To: Server
 * 
 * @author Martin
 *
 */
public class PacketPlayInInteractWithEntityEvent extends PacketPlayInEvent {

	private Entity clickedEntity;
	private EntityUseAction action;

	/**
	 * Only if Type is interact at.
	 */
	private Vector velocity;

	/**
	 * Only if Type is interact or interact at.
	 */
	private Hand hand;

	/**
	 * If the client is sneaking.
	 */
	private boolean sneaking;

	public PacketPlayInInteractWithEntityEvent(Player injectedPlayer, PacketPlayInUseEntity packet) {
		super(injectedPlayer);
		final int entityID = (int) new Field(PacketPlayInSetCommandMinecart.class, "a").get(packet);
		clickedEntity = new Entity(entityID, injectedPlayer.getWorld());
		action = EntityUseAction.getEntityUseAction(packet.b());
		velocity = new Vector(packet.d().getX(), packet.d().getY(), packet.d().getZ());
		hand = Hand.getHand(packet.c());
		sneaking = packet.e();
	}

	public PacketPlayInInteractWithEntityEvent(Player injectedPlayer, org.bukkit.entity.Entity entity,
			EntityUseAction action, Vector velocity, Hand hand, boolean sneaking) {
		super(injectedPlayer);
		this.clickedEntity = new Entity(entity);
		this.action = action;
		this.velocity = velocity;
		this.hand = hand;
		this.sneaking = sneaking;
	}

	public Entity getClickedEntity() {
		return clickedEntity;
	}

	public EntityUseAction getAction() {
		return action;
	}

	public Vector getVelocity() {
		return velocity;
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
		new Field(PacketPlayInUseEntity.class, "a").set(packet, clickedEntity.getID());
		new Field(PacketPlayInUseEntity.class, "action").set(packet, action.getNMS());
		new Field(PacketPlayInUseEntity.class, "c").set(packet,
				new Vec3D(velocity.getX(), velocity.getY(), velocity.getZ()));
		new Field(PacketPlayInUseEntity.class, "d").set(packet, hand.getNMS());
		new Field(PacketPlayInUseEntity.class, "e").set(packet, sneaking);
		return packet;
	}

	public static enum EntityUseAction {

		INTERACT(EnumEntityUseAction.INTERACT), ATTACK(EnumEntityUseAction.ATTACK),
		INTERACT_AT(EnumEntityUseAction.INTERACT_AT);

		private EnumEntityUseAction nms;

		private EntityUseAction(EnumEntityUseAction nms) {
			this.nms = nms;
		}

		public EnumEntityUseAction getNMS() {
			return nms;
		}

		public static EntityUseAction getEntityUseAction(EnumEntityUseAction nms) {
			for (EntityUseAction action : EntityUseAction.values())
				if (action.getNMS().equals(nms))
					return action;
			return null;
		}
	}

	@Override
	public int getPacketID() {
		return 14;
	}

	@Override
	public String getProtocolURLString() {
		return "https://wiki.vg/Protocol#Interact_Entity";
	}
}
