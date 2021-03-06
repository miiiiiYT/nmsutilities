package io.github.riesenpilz.nmsUtilities.packet.playOut;

import org.apache.commons.lang.Validate;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.entity.Player;

import io.github.riesenpilz.nmsUtilities.packet.PacketUtils;
import io.github.riesenpilz.nmsUtilities.reflections.Field;
import net.minecraft.server.v1_16_R3.Packet;
import net.minecraft.server.v1_16_R3.PacketListenerPlayOut;
import net.minecraft.server.v1_16_R3.PacketPlayOutEntitySound;
import net.minecraft.server.v1_16_R3.SoundEffect;
import net.minecraft.server.v1_16_R3.SoundEffects;

/**
 * https://wiki.vg/Protocol#Entity_Sound_Effect
 * <p>
 * Plays a sound effect from an entity.
 * <p>
 * <i>The pitch and volume fields in this packet are ignored. See MC-138832 for
 * more information.(https://bugs.mojang.com/browse/MC-138832)</i>
 * <p>
 * Packet ID: 0x5B<br>
 * State: Play<br>
 * Bound To: Client
 * 
 * @author Martin
 *
 */
public class PacketPlayOutEntitySoundEvent extends PacketPlayOutEntityEvent {

	/**
	 * @see SoundEffects
	 */
	private Sound sound;
	private SoundCategory category;

	/**
	 * 1.0 is 100%, capped between 0.0 and 1.0 by Notchian clients.
	 */
	private float volume;

	/**
	 * Float between 0.5 and 2.0 by Notchian clients.
	 */
	private float pitch;

	public PacketPlayOutEntitySoundEvent(Player injectedPlayer, PacketPlayOutEntitySound packet) {
		super(injectedPlayer, packet, "c");

		sound = PacketUtils.toSound(Field.get(packet, "a", SoundEffect.class));
		category = PacketUtils
				.toSoundCategory(Field.get(packet, "b", net.minecraft.server.v1_16_R3.SoundCategory.class));
		volume = Field.get(packet, "d", float.class);
		pitch = Field.get(packet, "e", float.class);
	}

	public PacketPlayOutEntitySoundEvent(Player injectedPlayer, Sound sound, SoundCategory category, int entityId,
			float volume, float pitch) {
		super(injectedPlayer, entityId);

		Validate.notNull(sound);
		Validate.notNull(category);

		this.sound = sound;
		this.category = category;
		this.volume = volume;
		this.pitch = pitch;
	}

	public Sound getEffect() {
		return sound;
	}

	public SoundCategory getCategory() {
		return category;
	}

	public float getVolume() {
		return volume;
	}

	public float getPitch() {
		return pitch;
	}

	@Override
	public Packet<PacketListenerPlayOut> getNMS() {
		final PacketPlayOutEntitySound packet = new PacketPlayOutEntitySound();
		Field.set(packet, "a", PacketUtils.toSoundEffect(sound));
		Field.set(packet, "b", PacketUtils.toNMSSoundCategory(category));
		Field.set(packet, "c", getEntityId());
		Field.set(packet, "d", volume);
		Field.set(packet, "e", pitch);
		return packet;
	}

	@Override
	public int getPacketID() {
		return 0x5B;
	}

	@Override
	public String getProtocolURLString() {
		return "https://wiki.vg/Protocol#Entity_Sound_Effect";
	}
}
