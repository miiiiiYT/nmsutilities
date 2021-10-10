package io.github.riesenpilz.nmsUtilities.packet.playOut;

import org.apache.commons.lang.Validate;
import org.bukkit.entity.Player;

import io.github.riesenpilz.nmsUtilities.entity.player.PlayerAbilities;
import io.github.riesenpilz.nmsUtilities.reflections.Field;
import net.minecraft.server.v1_16_R3.Packet;
import net.minecraft.server.v1_16_R3.PacketListenerPlayOut;
import net.minecraft.server.v1_16_R3.PacketPlayOutAbilities;

/**
 * https://wiki.vg/Protocol#Player_Abilities_.28clientbound.29
 * <p>
 * Packet ID: 0x32<br>
 * State: Play<br>
 * Bound To: Client
 * 
 * @author Martin
 *
 */
public class PacketPlayOutPlayerAbilitiesEvent extends PacketPlayOutEvent {

	private boolean isInvulnerable;
	private boolean isFlying;
	private boolean canFly;
	private boolean canInstantlyBreak;

	/**
	 * 0.05 by default.
	 */
	private float flyingSpeed;

	/**
	 * Modifies the field of view, like a speed potion. A Notchian server will use
	 * the same value as the movement speed sent in the Entity Properties packet,
	 * which defaults to 0.1 for players.
	 */
	private float fovModifier;

	public PacketPlayOutPlayerAbilitiesEvent(Player injectedPlayer, PacketPlayOutAbilities packet) {
		super(injectedPlayer);

		Validate.notNull(packet);

		isInvulnerable = Field.get(packet, "a", boolean.class);
		isFlying = Field.get(packet, "b", boolean.class);
		canFly = Field.get(packet, "c", boolean.class);
		canInstantlyBreak = Field.get(packet, "d", boolean.class);
		flyingSpeed = Field.get(packet, "e", float.class);
		fovModifier = Field.get(packet, "f", float.class);
	}

	public PacketPlayOutPlayerAbilitiesEvent(Player injectedPlayer, boolean isInvulnerable, boolean isFlying,
			boolean canFly, boolean canInstantlyBreak, float flyingSpeed, float fovModifier) {
		super(injectedPlayer);

		this.isInvulnerable = isInvulnerable;
		this.isFlying = isFlying;
		this.canFly = canFly;
		this.canInstantlyBreak = canInstantlyBreak;
		this.flyingSpeed = flyingSpeed;
		this.fovModifier = fovModifier;
	}

	public boolean isInvulnerable() {
		return isInvulnerable;
	}

	public boolean isFlying() {
		return isFlying;
	}

	public boolean isCanFly() {
		return canFly;
	}

	public boolean isCanInstantlyBreak() {
		return canInstantlyBreak;
	}

	public float getFlyingSpeed() {
		return flyingSpeed;
	}

	public float getFovModifier() {
		return fovModifier;
	}

	@Override
	public Packet<PacketListenerPlayOut> getNMS() {
		final PlayerAbilities playerAbilities = new PlayerAbilities(isInvulnerable, isFlying, canFly,
				canInstantlyBreak);
		playerAbilities.setFlySpeed(flyingSpeed);
		playerAbilities.setWalkSpeed(fovModifier);
		return new PacketPlayOutAbilities(playerAbilities.getNMS());
	}

	@Override
	public int getPacketID() {
		return 0x32;
	}

	@Override
	public String getProtocolURLString() {
		return "https://wiki.vg/Protocol#Player_Abilities_.28clientbound.29";
	}
}
