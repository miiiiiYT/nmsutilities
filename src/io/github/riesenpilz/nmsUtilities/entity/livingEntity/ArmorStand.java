package io.github.riesenpilz.nmsUtilities.entity.livingEntity;

import org.bukkit.craftbukkit.v1_16_R3.entity.CraftArmorStand;
import org.bukkit.util.Vector;

import io.github.riesenpilz.nmsUtilities.entity.DataWatcherItem;
import io.github.riesenpilz.nmsUtilities.packet.PacketUtils;
import net.minecraft.server.v1_16_R3.EntityArmorStand;
import net.minecraft.server.v1_16_R3.Vector3f;

/**
 * Represents a {@link org.bukkit.entity.ArmorStand}.
 * <p>
 * <i> Note that armor stands with the invisible flag from the base entity class
 * set also cannot be attacked or damaged, except for by the void.</i>
 */
public class ArmorStand extends LivingEntity {

	protected ArmorStand(org.bukkit.entity.ArmorStand bukkit) {
		super(bukkit);
	}

	public static ArmorStand getArmorStandOf(org.bukkit.entity.ArmorStand bukkit) {
		return new ArmorStand(bukkit);
	}

	@Override
	public org.bukkit.entity.ArmorStand getBukkit() {
		return (org.bukkit.entity.ArmorStand) super.getBukkit();
	}

	@Override
	public EntityArmorStand getNMS() {
		return ((CraftArmorStand) getBukkit()).getHandle();
	}

	// DataWatcher (DW)
	public static DataWatcherItem<Byte> getDWArmorStandStates(boolean small, boolean arms, boolean noBasePlate,
			boolean marker) {
		return new DataWatcherItem<>(14, PacketUtils.toBitSet(small, arms, noBasePlate, marker));
	}

	public static DataWatcherItem<Vector3f> getDWHeadRotation(Vector headRotation) {
		return new DataWatcherItem<>(15, PacketUtils.toVector3f(headRotation));
	}

	public static DataWatcherItem<Vector3f> getDWBodyRotation(Vector bodyRotation) {
		return new DataWatcherItem<>(16, PacketUtils.toVector3f(bodyRotation));
	}

	public static DataWatcherItem<Vector3f> getDWLeftArmRotation(Vector leftArmRotation) {
		return new DataWatcherItem<>(17, PacketUtils.toVector3f(leftArmRotation));
	}

	public static DataWatcherItem<Vector3f> getDWRightArmRotation(Vector rightArmRotation) {
		return new DataWatcherItem<>(18, PacketUtils.toVector3f(rightArmRotation));
	}

	public static DataWatcherItem<Vector3f> getDWLeftLegRotation(Vector leftLegRotation) {
		return new DataWatcherItem<>(19, PacketUtils.toVector3f(leftLegRotation));
	}

	public static DataWatcherItem<Vector3f> getDWRightLegRotation(Vector rightLegRotation) {
		return new DataWatcherItem<>(20, PacketUtils.toVector3f(rightLegRotation));
	}
}
