package io.github.riesenpilz.nms.packet.playOut;

import org.bukkit.EntityEffect;
import org.bukkit.entity.Player;

import io.github.riesenpilz.nms.reflections.Field;
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
public class PacketPlayOutEntityEffectRemoveEvent extends PacketPlayOutEvent {

	private int entityId;
	private EntityEffect effect;

	@SuppressWarnings("deprecation")
	public PacketPlayOutEntityEffectRemoveEvent(Player injectedPlayer, PacketPlayOutRemoveEntityEffect packet) {
		super(injectedPlayer);

		entityId = Field.get(packet, "a", int.class);
		for (EntityEffect effect : EntityEffect.values())
			if (effect.getData() == MobEffectList.getId(Field.get(packet, "b", MobEffectList.class)))
				this.effect = effect;
	}

	public PacketPlayOutEntityEffectRemoveEvent(Player injectedPlayer, int entityId, EntityEffect effect) {
		super(injectedPlayer);
		this.entityId = entityId;
		this.effect = effect;
	}

	public int getEntityId() {
		return entityId;
	}

	public EntityEffect getEffect() {
		return effect;
	}

	@SuppressWarnings("deprecation")
	@Override
	public Packet<PacketListenerPlayOut> getNMS() {
		return new PacketPlayOutRemoveEntityEffect(entityId, MobEffectList.fromId(effect.getData()));
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
