package io.github.riesenpilz.nms.packet.playOut;

import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.entity.Player;

import io.github.riesenpilz.nms.reflections.Field;
import net.minecraft.server.v1_16_R3.IRegistry;
import net.minecraft.server.v1_16_R3.MinecraftKey;
import net.minecraft.server.v1_16_R3.Packet;
import net.minecraft.server.v1_16_R3.PacketListenerPlayOut;
import net.minecraft.server.v1_16_R3.PacketPlayOutNamedSoundEffect;
import net.minecraft.server.v1_16_R3.SoundEffect;

/**
 * https://wiki.vg/Protocol#Named_Sound_Effect
 * <p>
 * Used to play a sound effect on the client. Custom sounds may be added by
 * resource packs.
 * <p>
 * Packet ID: 0x18<br>
 * State: Play<br>
 * Bound To: Client
 *
 * @author Martin
 *
 */
public class PacketPlayOutSoundEvent extends PacketPlayOutEvent {

	private Sound sound;
	private SoundCategory category;

	private Location location;

	/**
	 * 1 is 100%, can be more.
	 */
	private float volume;

	/**
	 * Float between 0.5 and 2.0 by Notchian clients.
	 */
	private float pitch;

	public PacketPlayOutSoundEvent(Player injectedPlayer, PacketPlayOutNamedSoundEffect packet) {
		super(injectedPlayer);
		sound = getSound(Field.get(Field.get(packet, "a", SoundEffect.class), "b", MinecraftKey.class).getKey());
		category = SoundCategory
				.valueOf(Field.get(packet, "b", net.minecraft.server.v1_16_R3.SoundCategory.class).name());
		location = new Location(injectedPlayer.getWorld(), Field.get(packet, "c", int.class) / 8D,
				Field.get(packet, "d", int.class) / 8D, Field.get(packet, "e", int.class) / 8D);
		volume = Field.get(packet, "f", float.class);
		pitch = Field.get(packet, "g", float.class);
	}

	private Sound getSound(String key) {
		for (Sound sound : Sound.values())
			if (sound.getKey().getKey().equals(key))
				return sound;
		return null;
	}

	public PacketPlayOutSoundEvent(Player injectedPlayer, Sound sound, SoundCategory category, Location location,
			float volume, float pitch) {
		super(injectedPlayer);
		this.sound = sound;
		this.category = category;
		this.location = location;
		this.volume = volume;
		this.pitch = pitch;
	}

	public Sound getSound() {
		return sound;
	}

	public SoundCategory getCategory() {
		return category;
	}

	public Location getLocation() {
		return location;
	}

	public float getVolume() {
		return volume;
	}

	public float getPitch() {
		return pitch;
	}

	@Override
	public Packet<PacketListenerPlayOut> getNMS() {
		return new PacketPlayOutNamedSoundEffect(getEffect(),
				net.minecraft.server.v1_16_R3.SoundCategory.valueOf(category.name()), location.getX(), location.getY(),
				location.getZ(), volume, pitch);
	}

	private SoundEffect getEffect() {
		for (SoundEffect soundEffect : IRegistry.SOUND_EVENT)
			if (Field.get(soundEffect, "b", MinecraftKey.class).getKey().equals(sound.getKey().getKey()))
				return soundEffect;
		return null;
	}

	@Override
	public int getPacketID() {
		return 0x18;
	}

	@Override
	public String getProtocolURLString() {
		return "https://wiki.vg/Protocol#Named_Sound_Effect";
	}
}
