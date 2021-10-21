package io.github.riesenpilz.nmsUtilities.entity.livingEntity;

import java.util.Optional;

import javax.annotation.Nullable;

import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftLivingEntity;

import io.github.riesenpilz.nmsUtilities.entity.DataWatcherItem;
import io.github.riesenpilz.nmsUtilities.entity.WorldEntity;
import io.github.riesenpilz.nmsUtilities.entity.livingEntity.player.Hand;
import io.github.riesenpilz.nmsUtilities.packet.PacketUtils;
import net.minecraft.server.v1_16_R3.BlockPosition;
import net.minecraft.server.v1_16_R3.EntityLiving;

/**
 * Represents a {@link org.bukkit.entity.LivingEntity}.
 *
 */
public class LivingEntity extends WorldEntity {

	protected LivingEntity(org.bukkit.entity.LivingEntity bukkit) {
		super(bukkit);
	}

	public static LivingEntity getLivingEntityOf(org.bukkit.entity.LivingEntity bukkit) {
		return new LivingEntity(bukkit);
	}

	@Override
	public org.bukkit.entity.LivingEntity getBukkit() {
		return (org.bukkit.entity.LivingEntity) super.getBukkit();
	}

	public EntityLiving getNMS() {
		return ((CraftLivingEntity) getBukkit()).getHandle();
	}

	// DataWatcher (DW)
	/**
	 * Used to trigger blocking/eating/drinking animation.
	 * 
	 * @param handStates
	 * @return
	 */
	public static DataWatcherItem<Byte> getDWHandStates(boolean handActive, Hand activeHand,
			boolean inRiptideSpinAttack) {
		return new DataWatcherItem<>(7,
				PacketUtils.toBitSet(handActive, activeHand == Hand.MAIN_HAND ? false : true, inRiptideSpinAttack));
	}

	public static DataWatcherItem<Float> getDWHealth(float health) {
		return new DataWatcherItem<>(8, health);
	}

	/**
	 * 0 if there is no effect
	 * 
	 * @param potionEffectColor
	 * @return
	 */
	public static DataWatcherItem<Integer> getDWPotionEffectColor(int potionEffectColor) {
		return new DataWatcherItem<>(9, potionEffectColor);
	}

	/**
	 * Is potion effect ambient: reduces the number of particles generated by
	 * potions to 1/5 the normal amount
	 * 
	 * @param isPotionEffectAmbient
	 * @return
	 */
	public static DataWatcherItem<Boolean> getDWIsPotionEffectAmbient(boolean isPotionEffectAmbient) {
		return new DataWatcherItem<>(10, isPotionEffectAmbient);
	}

	public static DataWatcherItem<Integer> getDWArrowsInEntity(int arrowsInEntity) {
		return new DataWatcherItem<>(11, arrowsInEntity);
	}

	/**
	 * based on the AbsorptionAmount tag
	 * 
	 * @param healthAddedByAbsorption
	 * @return
	 */
	public static DataWatcherItem<Integer> getDWHealthAddedByAbsorption(int healthAddedByAbsorption) {
		return new DataWatcherItem<>(12, healthAddedByAbsorption);
	}

	/**
	 * Location of the bed that the entity is currently sleeping in (Empty if it
	 * isn't sleeping)
	 * 
	 * @param currentBed
	 * @return
	 */
	public static DataWatcherItem<Optional<BlockPosition>> getDWCurrentBed(@Nullable Location currentBed) {
		return new DataWatcherItem<>(13,
				currentBed == null ? Optional.empty() : Optional.of(PacketUtils.toBlockPosition(currentBed)));
	}
}
