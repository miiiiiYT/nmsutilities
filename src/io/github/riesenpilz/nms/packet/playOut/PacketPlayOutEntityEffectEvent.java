package io.github.riesenpilz.nms.packet.playOut;

import org.bukkit.entity.Player;

import io.github.riesenpilz.nms.reflections.Field;
import net.minecraft.server.v1_16_R3.Packet;
import net.minecraft.server.v1_16_R3.PacketListenerPlayOut;
import net.minecraft.server.v1_16_R3.PacketPlayOutEntityEffect;

/**
 * https://wiki.vg/Protocol#Entity_Effect
 * <p>
 * Packet ID: 0x64<br>
 * State: Play<br>
 * Bound To: Client
 * 
 * @author Martin
 *
 */
public class PacketPlayOutEntityEffectEvent extends PacketPlayOutEntityEvent {

	private byte effectId;
	private byte amplifier;
	private int duartion;

	/**
	 * <ul>
	 * <li>0x01: Is ambient - was the effect spawned from a beacon? All
	 * beacon-generated effects are ambient. Ambient effects use a different icon in
	 * the HUD (blue border rather than gray). If all effects on an entity are
	 * ambient, the "Is potion effect ambient" living metadata field should be set
	 * to true. Usually should not be enabled.</li>
	 * <li>0x02: Show particles - should all particles from this effect be hidden?
	 * Effects with particles hidden are not included in the calculation of the
	 * effect color, and are not rendered on the HUD (but are still rendered within
	 * the inventory). Usually should be enabled.</li>
	 * <li>0x04: Show icon - should the icon be displayed on the client? Usually
	 * should be enabled.</li>
	 * </ul>
	 */
	private byte bitMask;

	public PacketPlayOutEntityEffectEvent(Player injectedPlayer, PacketPlayOutEntityEffect packet) {
		super(injectedPlayer, packet);
		effectId = Field.get(packet, "b", byte.class);
		amplifier = Field.get(packet, "c", byte.class);
		duartion = Field.get(packet, "d", int.class);
		bitMask = Field.get(packet, "e", byte.class);
	}

	public PacketPlayOutEntityEffectEvent(Player injectedPlayer, int entityId, byte effectId, byte amplifier,
			int duartion, byte bitMask) {
		super(injectedPlayer, entityId);
		this.effectId = effectId;
		this.amplifier = amplifier;
		this.duartion = duartion;
		this.bitMask = bitMask;
	}

	public byte getEffectId() {
		return effectId;
	}

	public byte getAmplifier() {
		return amplifier;
	}

	public int getDuartion() {
		return duartion;
	}

	public byte getBitMask() {
		return bitMask;
	}

	@Override
	public Packet<PacketListenerPlayOut> getNMS() {
		final PacketPlayOutEntityEffect packet = new PacketPlayOutEntityEffect();
		Field.set(packet, "a", getEntityId());
		Field.set(packet, "b", effectId);
		Field.set(packet, "c", amplifier);
		Field.set(packet, "d", duartion);
		Field.set(packet, "e", bitMask);
		return packet;
	}

	@Override
	public int getPacketID() {
		return 0x64;
	}

	@Override
	public String getProtocolURLString() {
		return "https://wiki.vg/Protocol#Entity_Effect";
	}
}
