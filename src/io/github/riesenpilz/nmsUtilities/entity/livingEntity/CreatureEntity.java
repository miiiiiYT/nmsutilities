package io.github.riesenpilz.nmsUtilities.entity.livingEntity;

import org.apache.commons.lang.Validate;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftCreature;
import org.bukkit.entity.Creature;
import org.bukkit.event.entity.EntityTargetEvent.TargetReason;

import io.github.riesenpilz.nmsUtilities.entity.DataWatcherItem;
import io.github.riesenpilz.nmsUtilities.entity.pathfinder.PathfinderGoalSelector;
import io.github.riesenpilz.nmsUtilities.packet.ByteBuilder;
import net.minecraft.server.v1_16_R3.EntityCreature;

/**
 * Represents a {@link Creature}
 *
 */
public class CreatureEntity extends LivingEntity {
	private final Creature creature;

	public CreatureEntity(Creature creature) {
		super(creature);
		this.creature = creature;
	}

	public Creature getCreature() {
		return creature;
	}

	public EntityCreature getNMS() {
		return ((CraftCreature) creature).getHandle();
	}

	public void setTarget(LivingEntity livingEntity) {
		Validate.notNull(livingEntity);
		getNMS().setGoalTarget(livingEntity.getNMS());
	}

	public void setTarget(LivingEntity livingEntity, TargetReason reason, boolean fireEvent) {
		Validate.notNull(livingEntity);
		Validate.notNull(reason);
		getNMS().setGoalTarget(livingEntity.getNMS(), reason, fireEvent);
	}

	public PathfinderGoalSelector getGoalSelector() {
		return new PathfinderGoalSelector(getNMS().goalSelector);
	}
	// DataWatcher (DW)

	public static DataWatcherItem<Byte> getDWMobStates(byte mobStates) {
		return new DataWatcherItem<>(14, mobStates);
	}

	public static class DWHandStatesBuilder extends ByteBuilder {
		private boolean NoAI;
		private boolean leftHanded;
		private boolean agressive;

		public DWHandStatesBuilder setNoAI(boolean noAI) {
			NoAI = noAI;

			return this;
		}

		public DWHandStatesBuilder setLeftHanded(boolean leftHanded) {
			this.leftHanded = leftHanded;
			return this;
		}

		public DWHandStatesBuilder setAgressive(boolean agressive) {
			this.agressive = agressive;
			return this;
		}

		public byte build() {
			return super.build(NoAI, leftHanded, agressive);
		}
	}
}
