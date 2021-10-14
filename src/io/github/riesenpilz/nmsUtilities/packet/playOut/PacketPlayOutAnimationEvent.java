package io.github.riesenpilz.nmsUtilities.packet.playOut;

import org.apache.commons.lang.Validate;
import org.bukkit.entity.Player;

import io.github.riesenpilz.nmsUtilities.entity.livingEntity.player.PlayerAnimation;
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
 */
public class PacketPlayOutAnimationEvent extends PacketPlayOutEntityEvent {

	private PlayerAnimation animation;

	public PacketPlayOutAnimationEvent(Player injectedPlayer, PacketPlayOutAnimation packet) {
		super(injectedPlayer, packet);
		Validate.notNull(packet);
		animation = PlayerAnimation.getByID(Field.get(packet, "b", int.class));
	}

	public PacketPlayOutAnimationEvent(Player injectedPlayer, PlayerAnimation animation, int entityId) {
		super(injectedPlayer, entityId);
		Validate.notNull(animation);
		this.animation = animation;
	}

	public PlayerAnimation getAnimation() {
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

}
