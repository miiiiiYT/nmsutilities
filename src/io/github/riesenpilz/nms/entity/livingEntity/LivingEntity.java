package io.github.riesenpilz.nms.entity.livingEntity;

import org.bukkit.craftbukkit.v1_16_R3.entity.CraftLivingEntity;

import io.github.riesenpilz.nms.entity.WorldEntity;
import net.minecraft.server.v1_16_R3.EntityLiving;

public class LivingEntity extends WorldEntity {
    private final org.bukkit.entity.LivingEntity livingEntity;

    public LivingEntity(org.bukkit.entity.LivingEntity livingEntity) {
        super(livingEntity);
        this.livingEntity = livingEntity;
    }

    public org.bukkit.entity.LivingEntity getLivingEntity() {
        return livingEntity;
    }

    public EntityLiving getNMS() {
        return ((CraftLivingEntity) livingEntity).getHandle();
    }
}
