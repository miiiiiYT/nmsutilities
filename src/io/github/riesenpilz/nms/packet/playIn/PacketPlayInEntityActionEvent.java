package io.github.riesenpilz.nms.packet.playIn;

import org.bukkit.entity.Player;

import io.github.riesenpilz.nms.entity.Entity;
import io.github.riesenpilz.nms.reflections.Field;
import net.minecraft.server.v1_16_R3.Packet;
import net.minecraft.server.v1_16_R3.PacketListenerPlayIn;
import net.minecraft.server.v1_16_R3.PacketPlayInEntityAction;
import net.minecraft.server.v1_16_R3.PacketPlayInEntityAction.EnumPlayerAction;

/**
 * https://wiki.vg/Protocol#Entity_Action<br>
 * <br>
 * Sent by the client to indicate that it has performed certain actions:
 * sneaking (crouching), sprinting, exiting a bed, jumping with a horse, and
 * opening a horse's inventory while riding it.<br>
 * <br>
 * Packet ID: 0x1C<br>
 * State: Play<br>
 * Bound To: Server
 * 
 * @author Martin
 *
 */
public class PacketPlayInEntityActionEvent extends PacketPlayInEvent {

	private PlayerAction action;

	/**
	 * Only used by the “start jump with horse” action, in which case it ranges from
	 * 0 to 100. In all other cases it is 0.
	 * 
	 */
	private int jumpBoost;

	public PacketPlayInEntityActionEvent(Player injectedPlayer, PacketPlayInEntityAction packet) {
		super(injectedPlayer);
		action = PlayerAction
				.getPlayerAction((EnumPlayerAction) new Field(PacketPlayInEntityAction.class, "animation").get(packet));
		jumpBoost = packet.d();
	}

	public PacketPlayInEntityActionEvent(Player injectedPlayer, PlayerAction action, int jumpBoost) {
		super(injectedPlayer);
		this.action = action;
		this.jumpBoost = jumpBoost;
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
		new Field(PacketPlayInEntityAction.class, "a").set(packet, new Entity(getInjectedPlayer()).getID());
		new Field(PacketPlayInEntityAction.class, "animation").set(packet, action.getNMS());
		new Field(PacketPlayInEntityAction.class, "c").set(packet, jumpBoost);
		return packet;
	}

	public static enum PlayerAction {

		START_SNEAKING(EnumPlayerAction.PRESS_SHIFT_KEY), STOP_SNEAKING(EnumPlayerAction.RELEASE_SHIFT_KEY),
		/**
		 * This is only sent when the “Leave Bed” button is clicked on the sleep GUI,
		 * not when waking up due today time.
		 */
		STOP_SLEEPING(EnumPlayerAction.STOP_SLEEPING), START_SPRINTING(EnumPlayerAction.START_SPRINTING),
		STOP_SPRINTING(EnumPlayerAction.STOP_SPRINTING), START_JUMP_WITH_HORSE(EnumPlayerAction.START_RIDING_JUMP),
		STOP_JUMP_WITH_HORSE(EnumPlayerAction.STOP_RIDING_JUMP),
		/**
		 * This is only sent when pressing the inventory key (default: E) while riding —
		 * all other methods of opening a horse's inventory (involving right-clicking or
		 * shift-right-clicking it) do not use this packet.
		 */
		OPEN_INVENTORY(EnumPlayerAction.OPEN_INVENTORY), START_FLYING_WITH_ELYTRA(EnumPlayerAction.START_FALL_FLYING);

		private final EnumPlayerAction animation;

		private PlayerAction(EnumPlayerAction animation) {
			this.animation = animation;
		}

		public EnumPlayerAction getNMS() {
			return animation;
		}

		public static PlayerAction getPlayerAction(EnumPlayerAction animation) {
			for (PlayerAction animation2 : PlayerAction.values())
				if (animation2.getNMS().equals(animation))
					return animation2;
			return null;
		}
	}

	@Override
	public int getPacketID() {
		return 28;
	}

	@Override
	public String getProtocolURLString() {
		return "https://wiki.vg/Protocol#Entity_Action";
	}
}
