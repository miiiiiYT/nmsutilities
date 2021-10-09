package io.github.riesenpilz.nmsUtilities.entity;

import org.bukkit.craftbukkit.v1_16_R3.entity.CraftLivingEntity;

import net.minecraft.server.v1_16_R3.EntityLiving;

/**
 * Represents a {@link org.bukkit.entity.LivingEntity bukkit}
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
}
