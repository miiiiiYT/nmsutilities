package io.github.riesenpilz.nmsUtilities.packet.playOut;

import org.apache.commons.lang.Validate;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.entity.Player;

import io.github.riesenpilz.nmsUtilities.packet.PacketUtils;
import io.github.riesenpilz.nmsUtilities.reflections.Field;
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

		Validate.notNull(packet);

		sound = PacketUtils.toSound(Field.get(packet, "a", SoundEffect.class));
		category = PacketUtils
				.toSoundCategory(Field.get(packet, "b", net.minecraft.server.v1_16_R3.SoundCategory.class));
		location = new Location(injectedPlayer.getWorld(), Field.get(packet, "c", int.class) / 8D,
				Field.get(packet, "d", int.class) / 8D, Field.get(packet, "e", int.class) / 8D);
		volume = Field.get(packet, "f", float.class);
		pitch = Field.get(packet, "g", float.class);
	}

	public PacketPlayOutSoundEvent(Player injectedPlayer, Sound sound, SoundCategory category, Location location,
			float volume, float pitch) {
		super(injectedPlayer);

		Validate.notNull(sound);
		Validate.notNull(category);
		Validate.notNull(location);

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
		return new PacketPlayOutNamedSoundEffect(PacketUtils.toSoundEffect(sound),
				PacketUtils.toNMSSoundCategory(category), location.getX(), location.getY(), location.getZ(), volume,
				pitch);
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
