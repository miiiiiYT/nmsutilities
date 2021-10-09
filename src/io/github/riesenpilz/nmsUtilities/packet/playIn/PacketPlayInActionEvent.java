package io.github.riesenpilz.nmsUtilities.packet.playIn;

import org.apache.commons.lang.Validate;

import io.github.riesenpilz.nmsUtilities.entity.player.Player;
import io.github.riesenpilz.nmsUtilities.entity.player.PlayerAction;
import io.github.riesenpilz.nmsUtilities.reflections.Field;
import net.minecraft.server.v1_16_R3.Packet;
import net.minecraft.server.v1_16_R3.PacketListenerPlayIn;
import net.minecraft.server.v1_16_R3.PacketPlayInEntityAction;

/**
 * https://wiki.vg/Protocol#Entity_Action
 * <p>
 * Sent by the client to indicate that it has performed certain actions:
 * sneaking (crouching), sprinting, exiting a bed, jumping with a horse, and
 * opening a horse's inventory while riding it.
 * <p>
 * Packet ID: 0x1C<br>
 * State: Play<br>
 * Bound To: Server
 *
 * @author Martin
 *
 */
public class PacketPlayInActionEvent extends PacketPlayInEntityEvent {

	private PlayerAction action;

	/**
	 * Only used by the “start jump with horse” action, in which case it ranges from
	 * 0 to 100. In all other cases it is 0.
	 *
	 */
	private int jumpBoost;

	public PacketPlayInActionEvent(org.bukkit.entity.Player injectedPlayer, PacketPlayInEntityAction packet) {
		super(injectedPlayer, packet);
		Validate.notNull(packet);
		action = PlayerAction.getPlayerAction(packet.c());
		jumpBoost = packet.d();

	}

	public PacketPlayInActionEvent(org.bukkit.entity.Player injectedPlayer, int playerId, PlayerAction action,
			int jumpBoost) {
		super(injectedPlayer, playerId);
		Validate.notNull(action);
		this.action = action;
		this.jumpBoost = jumpBoost;
	}

	@Override
	public Player getEntity() {
		return (Player) super.getEntity();
	}

	public PlayerAction getAction() {
		return action;
	}

	public int getJumpBoost() {
		return jumpBoost;
	}

	@Override
	public Packet<PacketListenerPlayIn> getNMS() {
		final PacketPlayInEntityAction packet = new PacketPlayInEntityAction();
		Field.set(packet, "a", getEntityId());
		Field.set(packet, "animation", action.getNMS());
		Field.set(packet, "c", jumpBoost);
		return packet;
	}

	@Override
	public int getPacketID() {
		return 0x1C;
	}

	@Override
	public String getProtocolURLString() {
		return "https://wiki.vg/Protocol#Entity_Action";
	}
}
