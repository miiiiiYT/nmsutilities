package io.github.riesenpilz.nms.packet.playOut;

import org.bukkit.entity.Player;

import io.github.riesenpilz.nms.reflections.Field;
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
public class PacketPlayOutAnimationEvent extends PacketPlayOutEvent {

	public enum Animation {
		CRITICAL_EFFECT(4), LEAVE_BED(2), MAGIC_CRITICAL_EFFECT(5), SWING_MAIN_ARM(0), SWING_OFFHAND(3), TAKE_DAMAGE(1);

		public static Animation getByID(int id) {
			for (Animation animation : values())
				if (animation.getId() == id)
					return animation;
			return null;
		}

		private int id;

		Animation(int id) {
			this.id = id;
		}

		public int getId() {
			return id;
		}
	}

	private Animation animation;

	private int playerID;

	public PacketPlayOutAnimationEvent(Player injectedPlayer, Animation animation, int playerID) {
		super(injectedPlayer);
		this.animation = animation;
		this.playerID = playerID;
	}

	public PacketPlayOutAnimationEvent(Player injectedPlayer, PacketPlayOutAnimation packet) {
		super(injectedPlayer);
		playerID = (int) new Field(packet.getClass(), "a").get(packet);
		animation = Animation.getByID((int) new Field(packet.getClass(), "a").get(packet));
	}

	public Animation getAnimation() {
		return animation;
	}

	@Override
	public Packet<PacketListenerPlayOut> getNMS() {
		final PacketPlayOutAnimation packet = new PacketPlayOutAnimation();
		new Field(packet.getClass(), "a").set(packet, playerID);
		new Field(packet.getClass(), "b").set(packet, animation.getId());
		return packet;
	}

	@Override
	public int getPacketID() {
		return 0x05;
	}

	public int getPlayerID() {
		return playerID;
	}

	@Override
	public String getProtocolURLString() {
		return "https://wiki.vg/Protocol#Entity_Animation_.28clientbound.29";
	}
}
