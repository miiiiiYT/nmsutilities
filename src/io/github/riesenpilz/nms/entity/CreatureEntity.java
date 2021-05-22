package io.github.riesenpilz.nms.entity;

import io.github.riesenpilz.nms.entity.livingEntity.LivingEntity;
import io.github.riesenpilz.nms.entity.pathfinder.PathfinderGoalSelector;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftCreature;
import org.bukkit.event.entity.EntityTargetEvent.TargetReason;

import net.minecraft.server.v1_16_R3.EntityCreature;

public class CreatureEntity extends LivingEntity {
    private final org.bukkit.entity.Creature creature;

    public CreatureEntity(org.bukkit.entity.Creature creature) {
        super(creature);
        this.creature = creature;
    }

    public org.bukkit.entity.Creature getCreature() {
        return creature;
    }

    public EntityCreature getNMS() {
        return ((CraftCreature) creature).getHandle();
    }

    public void setTarget(LivingEntity livingEntity) {
        getNMS().setGoalTarget(livingEntity.getNMS());
    }

    public void setTarget(LivingEntity livingEntity, TargetReason reason, boolean fireEvent) {
        getNMS().setGoalTarget(livingEntity.getNMS(), reason, fireEvent);
    }

    public PathfinderGoalSelector getGoalSelector() {
        return new PathfinderGoalSelector(getNMS().goalSelector);
    }
}
