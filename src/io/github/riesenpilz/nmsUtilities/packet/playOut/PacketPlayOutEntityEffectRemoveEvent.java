package io.github.riesenpilz.nmsUtilities.packet.playOut;

import org.bukkit.EntityEffect;
import org.bukkit.entity.Player;

import io.github.riesenpilz.nmsUtilities.packet.PacketUtils;
import io.github.riesenpilz.nmsUtilities.reflections.Field;
import net.minecraft.server.v1_16_R3.MobEffectList;
import net.minecraft.server.v1_16_R3.Packet;
import net.minecraft.server.v1_16_R3.PacketListenerPlayOut;
import net.minecraft.server.v1_16_R3.PacketPlayOutRemoveEntityEffect;

/**
 * https://wiki.vg/Protocol#Remove_Entity_Effect
 * <p>
 * Packet ID: 0x3B<br>
 * State: Play<br>
 * Bound To: Client
 * 
 * @author Martin
 *
 */
public class PacketPlayOutEntityEffectRemoveEvent extends PacketPlayOutEntityEvent {

	private EntityEffect effect;

	public PacketPlayOutEntityEffectRemoveEvent(Player injectedPlayer, PacketPlayOutRemoveEntityEffect packet) {
		super(injectedPlayer, packet);
		effect = PacketUtils.toEntityEffect(Field.get(packet, "b", MobEffectList.class));

	}

	public PacketPlayOutEntityEffectRemoveEvent(Player injectedPlayer, int entityId, EntityEffect effect) {
		super(injectedPlayer, entityId);
		this.effect = effect;
	}

	public EntityEffect getEffect() {
		return effect;
	}

	@Override
	public Packet<PacketListenerPlayOut> getNMS() {
		return new PacketPlayOutRemoveEntityEffect(getEntityId(), PacketUtils.toMobEffectList(effect));
	}

	@Override
	public int getPacketID() {
		return 0x3B;
	}

	@Override
	public String getProtocolURLString() {
		return "https://wiki.vg/Protocol#Remove_Entity_Effect";
	}
}
