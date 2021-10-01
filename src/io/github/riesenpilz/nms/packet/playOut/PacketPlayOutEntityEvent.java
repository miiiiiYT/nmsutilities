package io.github.riesenpilz.nms.packet.playOut;

import org.bukkit.entity.Player;

import io.github.riesenpilz.nms.entity.WorldEntity;
import io.github.riesenpilz.nms.reflections.Field;
import net.minecraft.server.v1_16_R3.Packet;
import net.minecraft.server.v1_16_R3.PacketListenerPlayOut;

public abstract class PacketPlayOutEntityEvent extends PacketPlayOutEvent {

	private int entityId;

	public PacketPlayOutEntityEvent(Player injectedPlayer, int entityId) {
		super(injectedPlayer);
		this.entityId = entityId;
	}

	protected PacketPlayOutEntityEvent(Player injectedPlayer, Packet<PacketListenerPlayOut> packet) {
		this(injectedPlayer, packet, "a");
	}

	protected PacketPlayOutEntityEvent(Player injectedPlayer, Packet<PacketListenerPlayOut> packet, String fieldName) {
		super(injectedPlayer);
		this.entityId = Field.get(packet, fieldName, int.class);
	}

	public int getEntityId() {
		return entityId;
	}

	public WorldEntity getEntity() {
		return WorldEntity.getWorldEntity(entityId, getInjectedPlayer().getWorld());
	}

}
