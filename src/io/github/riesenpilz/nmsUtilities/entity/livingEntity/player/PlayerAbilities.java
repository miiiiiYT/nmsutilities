package io.github.riesenpilz.nmsUtilities.entity.livingEntity.player;

import org.apache.commons.lang.Validate;

import io.github.riesenpilz.nmsUtilities.packet.playIn.PacketPlayInAbilitiesEvent;
import io.github.riesenpilz.nmsUtilities.packet.playOut.PacketPlayOutPlayerAbilitiesEvent;

/**
 * Represents {@link net.minecraft.server.v1_16_R3.PlayerAbilities}. Only used
 * by packets.
 * 
 * @see PacketPlayInAbilitiesEvent
 * @see PacketPlayOutPlayerAbilitiesEvent
 */
public class PlayerAbilities {

	private boolean isInvulnerable;
	private boolean isFlying;
	private boolean canFly;
	private boolean canInstantlyBuild;
	private boolean mayBuild = true;

	private float flySpeed = 0.05F;
	private float walkSpeed = 0.1F;

	public PlayerAbilities(net.minecraft.server.v1_16_R3.PlayerAbilities playerAbilities) {
		Validate.notNull(playerAbilities);
		isInvulnerable = playerAbilities.isInvulnerable;
		isFlying = playerAbilities.isFlying;
		canFly = playerAbilities.canFly;
		canInstantlyBuild = playerAbilities.canInstantlyBuild;
		mayBuild = playerAbilities.mayBuild;
		flySpeed = playerAbilities.flySpeed;
		walkSpeed = playerAbilities.walkSpeed;
	}

	public PlayerAbilities(boolean isInvulnerable, boolean isFlying, boolean canFly, boolean canInstantlyBuild) {
		this.isInvulnerable = isInvulnerable;
		this.isFlying = isFlying;
		this.canFly = canFly;
		this.canInstantlyBuild = canInstantlyBuild;
	}

	public boolean isInvulnerable() {
		return isInvulnerable;
	}

	public void setInvulnerable(boolean isInvulnerable) {
		this.isInvulnerable = isInvulnerable;
	}

	public boolean isFlying() {
		return isFlying;
	}

	public void setFlying(boolean isFlying) {
		this.isFlying = isFlying;
	}

	public boolean isCanFly() {
		return canFly;
	}

	public void setCanFly(boolean canFly) {
		this.canFly = canFly;
	}

	public boolean isCanInstantlyBuild() {
		return canInstantlyBuild;
	}

	public void setCanInstantlyBuild(boolean canInstantlyBuild) {
		this.canInstantlyBuild = canInstantlyBuild;
	}

	public boolean isMayBuild() {
		return mayBuild;
	}

	public void setMayBuild(boolean mayBuild) {
		this.mayBuild = mayBuild;
	}

	public float getFlySpeed() {
		return flySpeed;
	}

	public void setFlySpeed(float flySpeed) {
		this.flySpeed = flySpeed;
	}

	public float getWalkSpeed() {
		return walkSpeed;
	}

	public void setWalkSpeed(float walkSpeed) {
		this.walkSpeed = walkSpeed;
	}

	@Override
	protected PlayerAbilities clone() {
		PlayerAbilities playerAbilities = new PlayerAbilities(isInvulnerable, isFlying, canFly, canInstantlyBuild);
		playerAbilities.setMayBuild(mayBuild);
		playerAbilities.setFlySpeed(flySpeed);
		playerAbilities.setWalkSpeed(walkSpeed);
		return playerAbilities;
	}

	public net.minecraft.server.v1_16_R3.PlayerAbilities getNMS() {
		final net.minecraft.server.v1_16_R3.PlayerAbilities playerAbilities = new net.minecraft.server.v1_16_R3.PlayerAbilities();
		playerAbilities.isInvulnerable = isInvulnerable;
		playerAbilities.isFlying = isFlying;
		playerAbilities.canFly = canFly;
		playerAbilities.canInstantlyBuild = canInstantlyBuild;
		playerAbilities.mayBuild = mayBuild;
		playerAbilities.flySpeed = flySpeed;
		playerAbilities.walkSpeed = walkSpeed;
		return playerAbilities;
	}
}
