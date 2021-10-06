package io.github.riesenpilz.nmsUtilities.packet.playOut;

import org.bukkit.entity.Player;

import io.github.riesenpilz.nmsUtilities.reflections.Field;
import net.minecraft.server.v1_16_R3.Packet;
import net.minecraft.server.v1_16_R3.PacketListenerPlayOut;
import net.minecraft.server.v1_16_R3.PacketPlayOutAnimation;

/**
 * https://wiki.vg/Protocol#Entity_Animation_.28clientbound.29<br>
 * <br>
 * Sent whenever an entity should change animation.<br>
 * <br>
 * Packet ID: 0x05<br>
 * State: Play<br>
 * Bound To: Client
 *
 * @author Martin
 *
 */
public class PacketPlayOutAnimationEvent extends PacketPlayOutEntityEvent {

	private Animation animation;

	public PacketPlayOutAnimationEvent(Player injectedPlayer, PacketPlayOutAnimation packet) {
		super(injectedPlayer, packet);
		animation = Animation.getByID(Field.get(packet, "b", int.class));
	}

	public PacketPlayOutAnimationEvent(Player injectedPlayer, Animation animation, int entityId) {
		super(injectedPlayer, entityId);
		this.animation = animation;
	}

	public Animation getAnimation() {
		return animation;
	}

	@Override
	public Packet<PacketListenerPlayOut> getNMS() {
		final PacketPlayOutAnimation packet = new PacketPlayOutAnimation();
		Field.set(packet, "a", getEntityId());
		Field.set(packet, "b", animation.getId());
		return packet;
	}

	@Override
	public int getPacketID() {
		return 0x05;
	}

	@Override
	public String getProtocolURLString() {
		return "https://wiki.vg/Protocol#Entity_Animation_.28clientbound.29";
	}

	public enum Animation {
		CRITICAL_EFFECT(4), LEAVE_BED(2), MAGIC_CRITICAL_EFFECT(5), SWING_MAIN_ARM(0), SWING_OFFHAND(3), TAKE_DAMAGE(1);

		private int id;

		Animation(int id) {
			this.id = id;
		}

		public int getId() {
			return id;
		}

		public static Animation getByID(int id) {
			for (Animation animation : values())
				if (animation.getId() == id)
					return animation;
			return null;
		}

	}
}
