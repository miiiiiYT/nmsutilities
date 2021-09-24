package io.github.riesenpilz.nms.packet.playOut;

import org.bukkit.SoundCategory;
import org.bukkit.entity.Player;

import io.github.riesenpilz.nms.reflections.Field;
import net.minecraft.server.v1_16_R3.Packet;
import net.minecraft.server.v1_16_R3.PacketListenerPlayOut;
import net.minecraft.server.v1_16_R3.PacketPlayOutEntitySound;
import net.minecraft.server.v1_16_R3.SoundEffect;

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
public class PacketPlayOutEntitySoundEvent extends PacketPlayOutEvent {

	private SoundEffect effect;
	private SoundCategory category;
	private int entityId;

	/**
	 * 1.0 is 100%, capped between 0.0 and 1.0 by Notchian clients.
	 */
	private float volume;

	/**
	 * Float between 0.5 and 2.0 by Notchian clients.
	 */
	private float pitch;

	public PacketPlayOutEntitySoundEvent(Player injectedPlayer, PacketPlayOutEntitySound packet) {
		super(injectedPlayer);
		effect = Field.get(packet, "a", SoundEffect.class);
		category = SoundCategory
				.valueOf(Field.get(packet, "b", net.minecraft.server.v1_16_R3.SoundCategory.class).name());
		entityId = Field.get(packet, "c", int.class);
		volume = Field.get(packet, "d", float.class);
		pitch = Field.get(packet, "e", float.class);
	}

	public PacketPlayOutEntitySoundEvent(Player injectedPlayer, SoundEffect effect, SoundCategory category,
			int entityId, float volume, float pitch) {
		super(injectedPlayer);
		this.effect = effect;
		this.category = category;
		this.entityId = entityId;
		this.volume = volume;
		this.pitch = pitch;
	}

	public SoundEffect getEffect() {
		return effect;
	}

	public SoundCategory getCategory() {
		return category;
	}

	public int getEntityId() {
		return entityId;
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
		Field.set(packet, "a", effect);
		Field.set(packet, "b", net.minecraft.server.v1_16_R3.SoundCategory.valueOf(category.name()));
		Field.set(packet, "c", entityId);
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
