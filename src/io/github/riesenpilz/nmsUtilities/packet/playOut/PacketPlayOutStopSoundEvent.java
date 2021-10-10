package io.github.riesenpilz.nmsUtilities.packet.playOut;

import org.apache.commons.lang.Validate;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.entity.Player;

import io.github.riesenpilz.nmsUtilities.packet.PacketUtils;
import io.github.riesenpilz.nmsUtilities.reflections.Field;
import net.minecraft.server.v1_16_R3.MinecraftKey;
import net.minecraft.server.v1_16_R3.Packet;
import net.minecraft.server.v1_16_R3.PacketListenerPlayOut;
import net.minecraft.server.v1_16_R3.PacketPlayOutStopSound;

/**
 * https://wiki.vg/Protocol#Stop_Sound
 * <p>
 * Packet ID: 0x5D<br>
 * State: Play<br>
 * Bound To: Client
 * 
 * @author Martin
 *
 */
public class PacketPlayOutStopSoundEvent extends PacketPlayOutEvent {

	/**
	 * If not present, then all sounds are cleared.
	 */
	private Sound sound;

	/**
	 * If not present, then sounds from all sources are cleared.
	 */
	private SoundCategory category;

	public PacketPlayOutStopSoundEvent(Player injectedPlayer, PacketPlayOutStopSound packet) {
		super(injectedPlayer);

		Validate.notNull(packet);

		sound = PacketUtils.toSound(Field.get(packet, "a", MinecraftKey.class));
		category = PacketUtils
				.toSoundCategory(Field.get(packet, "b", net.minecraft.server.v1_16_R3.SoundCategory.class));
	}

	public PacketPlayOutStopSoundEvent(Player injectedPlayer, Sound sound, SoundCategory category) {
		super(injectedPlayer);

		Validate.notNull(sound);
		Validate.notNull(category);

		this.sound = sound;
		this.category = category;
	}

	public Sound getSound() {
		return sound;
	}

	public SoundCategory getCategory() {
		return category;
	}

	@Override
	public Packet<PacketListenerPlayOut> getNMS() {
		return new PacketPlayOutStopSound(PacketUtils.toMinecraftKey(sound.getKey()),
				PacketUtils.toNMSSoundCategory(category));
	}

	@Override
	public int getPacketID() {
		return 0x5D;
	}

	@Override
	public String getProtocolURLString() {
		return "https://wiki.vg/Protocol#Stop_Sound";
	}
}
