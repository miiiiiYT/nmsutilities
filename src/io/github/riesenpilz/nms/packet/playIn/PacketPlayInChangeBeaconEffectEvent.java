package io.github.riesenpilz.nms.packet.playIn;

import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;

import io.github.riesenpilz.nms.reflections.Field;
import net.minecraft.server.v1_16_R3.Packet;
import net.minecraft.server.v1_16_R3.PacketListenerPlayIn;
import net.minecraft.server.v1_16_R3.PacketPlayInBeacon;

/**
 * https://wiki.vg/Protocol#Set_Beacon_Effect
 * <p>
 * Changes the effect of the current beacon.
 * <p>
 * Packet ID: 0x24<br>
 * State: Play<br>
 * Bound To: Server
 * 
 * @author Martin
 *
 */
public class PacketPlayInChangeBeaconEffectEvent extends PacketPlayInEvent {

	private PotionEffectType primaryEffect;
	private PotionEffectType secondaryEffect;

	public PacketPlayInChangeBeaconEffectEvent(Player injectedPlayer, PotionEffectType primaryEffect,
			PotionEffectType secondaryEffect) {
		super(injectedPlayer);
		this.primaryEffect = primaryEffect;
		this.secondaryEffect = secondaryEffect;
	}

	@SuppressWarnings("deprecation")
	public PacketPlayInChangeBeaconEffectEvent(Player injectedPlayer, PacketPlayInBeacon packet) {
		super(injectedPlayer);
		primaryEffect = PotionEffectType.getById(packet.b());
		secondaryEffect = PotionEffectType.getById(packet.c());
	}

	public PotionEffectType getPrimaryEffect() {
		return primaryEffect;
	}

	public PotionEffectType getSecondaryEffect() {
		return secondaryEffect;
	}

	@SuppressWarnings("deprecation")
	@Override
	public Packet<PacketListenerPlayIn> getNMS() {
		final PacketPlayInBeacon packet = new PacketPlayInBeacon();
		Field.set(packet, "a", primaryEffect.getId());
		Field.set(packet, "b", secondaryEffect.getId());
		return packet;
	}

	@Override
	public int getPacketID() {
		return 0x24;
	}

	@Override
	public String getProtocolURLString() {
		return "https://wiki.vg/Protocol#Set_Beacon_Effect";
	}
}
