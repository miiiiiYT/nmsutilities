package io.github.riesenpilz.nmsUtilities.packet;

import org.bukkit.Difficulty;
import org.bukkit.EntityEffect;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.World;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.EntityType;
import org.bukkit.util.Vector;

import io.github.riesenpilz.nmsUtilities.reflections.Field;
import net.minecraft.server.v1_16_R3.BlockPosition;
import net.minecraft.server.v1_16_R3.EntityTypes;
import net.minecraft.server.v1_16_R3.EnumDifficulty;
import net.minecraft.server.v1_16_R3.EnumDirection;
import net.minecraft.server.v1_16_R3.EnumGamemode;
import net.minecraft.server.v1_16_R3.IRegistry;
import net.minecraft.server.v1_16_R3.MinecraftKey;
import net.minecraft.server.v1_16_R3.MobEffectList;
import net.minecraft.server.v1_16_R3.ResourceKey;
import net.minecraft.server.v1_16_R3.SectionPosition;
import net.minecraft.server.v1_16_R3.SoundEffect;
import net.minecraft.server.v1_16_R3.Vec3D;

public class PacketUtils {

	public static Location toLocation(BlockPosition pos, World world) {
		return new Location(world, pos.getX(), pos.getY(), pos.getZ());
	}

	public static BlockPosition toBlockPosition(Location loc) {
		return new BlockPosition(loc.getX(), loc.getY(), loc.getZ());
	}

	public static Location toLocation(SectionPosition pos, World world) {
		return new Location(world, pos.getX(), pos.getY(), pos.getZ());
	}

	public static SectionPosition toSectionPosition(Location loc) {
		return SectionPosition.a(loc.getBlockX(), loc.getBlockX(), loc.getBlockZ());
	}

	public static Vector toVetor(Vec3D vec3d) {
		return new Vector(vec3d.getX(), vec3d.getY(), vec3d.getZ());
	}

	public static Vec3D toVec3D(Vector vector) {
		return new Vec3D(vector.getX(), vector.getY(), vector.getZ());
	}

	@SuppressWarnings("deprecation")
	public static NamespacedKey toNamespacedKey(MinecraftKey minecraftKey) {
		return new NamespacedKey(minecraftKey.getNamespace(), minecraftKey.getKey());
	}

	public static MinecraftKey toMinecraftKey(NamespacedKey namespacedKey) {
		return new MinecraftKey(namespacedKey.getNamespace(), namespacedKey.getKey());
	}

	@SuppressWarnings("deprecation")
	public static GameMode toGameMode(EnumGamemode enumGamemode) {
		return GameMode.getByValue(enumGamemode.getId());
	}

	@SuppressWarnings("deprecation")
	public static EnumGamemode toEnumGamemode(GameMode gameMode) {
		return EnumGamemode.getById(gameMode.getValue());
	}

	@SuppressWarnings("deprecation")
	public static Difficulty toDifficulty(EnumDifficulty enumDifficulty) {
		return Difficulty.getByValue(enumDifficulty.a());
	}

	@SuppressWarnings("deprecation")
	public static EnumDifficulty toEnumDifficulty(Difficulty difficulty) {
		return EnumDifficulty.getById(difficulty.getValue());
	}

	public static SoundCategory toSoundCategory(net.minecraft.server.v1_16_R3.SoundCategory nms) {
		return SoundCategory.valueOf(nms.name());
	}

	public static net.minecraft.server.v1_16_R3.SoundCategory toNMSSoundCategory(SoundCategory soundCategory) {
		return net.minecraft.server.v1_16_R3.SoundCategory.valueOf(soundCategory.name());
	}

	@SuppressWarnings("deprecation")
	public static EntityType toEntityType(EntityTypes<?> entityTypes) {
		return EntityType.fromName(entityTypes.f());
	}

	@SuppressWarnings("deprecation")
	public static EntityTypes<?> toEntityTypes(EntityType entityType) {
		return EntityTypes.a(entityType.getName()).orElseThrow();
	}

	public static BlockFace toBlockFace(EnumDirection enumDirection) {
		return BlockFace.valueOf(enumDirection.name());
	}

	public static EnumDirection toEnumDirection(BlockFace facing) {
		return EnumDirection.valueOf(facing.name());
	}

	public static NamespacedKey toNamespacedKey(ResourceKey<?> resourceKey) {
		return toNamespacedKey(resourceKey.a());
	}

	public static <T> ResourceKey<T> toResourceKey(NamespacedKey worldName, ResourceKey<IRegistry<T>> type) {
		return ResourceKey.a(type, PacketUtils.toMinecraftKey(worldName));
	}

	public static Sound toSound(SoundEffect soundEffect) {
		NamespacedKey key = toNamespacedKey(Field.get(soundEffect, "b", MinecraftKey.class));
		for (Sound sound : Sound.values())
			if (sound.getKey().equals(key))
				return sound;
		throw new IllegalArgumentException();
	}

	public static SoundEffect toSoundEffect(Sound sound) {
		MinecraftKey key = toMinecraftKey(sound.getKey());
		for (SoundEffect soundEffect : IRegistry.SOUND_EVENT)
			if (Field.get(soundEffect, "b", MinecraftKey.class).equals(key))
				return soundEffect;
		return null;
	}

	public static Sound toSound(MinecraftKey minecraftKey) {
		NamespacedKey key = toNamespacedKey(minecraftKey);
		for (Sound sound : Sound.values())
			if (sound.getKey().equals(key))
				return sound;
		throw new IllegalArgumentException();
	}

	@SuppressWarnings("deprecation")
	public static EntityEffect toEntityEffect(MobEffectList mobEffectList) {
		for (EntityEffect effect : EntityEffect.values())
			if (effect.getData() == MobEffectList.getId(mobEffectList))
				return effect;
		return null;
	}

	@SuppressWarnings("deprecation")
	public static MobEffectList toMobEffectList(EntityEffect effect) {
		return MobEffectList.fromId(effect.getData());
	}
}
