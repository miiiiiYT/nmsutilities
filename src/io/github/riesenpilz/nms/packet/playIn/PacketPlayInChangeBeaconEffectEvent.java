package io.github.riesenpilz.nms.packet.playIn;

import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;

import io.github.riesenpilz.nms.reflections.Field;
import net.minecraft.server.v1_16_R3.Packet;
import net.minecraft.server.v1_16_R3.PacketListenerPlayIn;
import net.minecraft.server.v1_16_R3.PacketPlayInBeacon;

/**
 * https://wiki.vg/Protocol#Set_Beacon_Effect<br>
 * <br>
 * Changes the effect of the current beacon.<br>
 * <br>
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
		new Field(PacketPlayInBeacon.class, "a").set(packet, primaryEffect.getId());
		new Field(PacketPlayInBeacon.class, "b").set(packet, secondaryEffect.getId());
		return packet;
	}

	@Override
	public int getPacketID() {
		return 36;
	}

	@Override
	public String getProtocolURLString() {
		return "https://wiki.vg/Protocol#Set_Beacon_Effect";
	}
}
