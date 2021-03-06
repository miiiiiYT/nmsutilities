package io.github.riesenpilz.nmsUtilities.packet.playIn;

import org.bukkit.entity.Player;

import io.github.riesenpilz.nmsUtilities.entity.WorldEntity;
import io.github.riesenpilz.nmsUtilities.reflections.Field;
import net.minecraft.server.v1_16_R3.Packet;
import net.minecraft.server.v1_16_R3.PacketListenerPlayIn;

public abstract class PacketPlayInEntityEvent extends PacketPlayInEvent {

	private int entityId;

	public PacketPlayInEntityEvent(Player injectedPlayer, int entityId) {
		super(injectedPlayer);
		this.entityId = entityId;
	}

	protected PacketPlayInEntityEvent(Player injectedPlayer, Packet<PacketListenerPlayIn> packet) {
		this(injectedPlayer, packet, "a");
	}

	protected PacketPlayInEntityEvent(Player injectedPlayer, Packet<PacketListenerPlayIn> packet, String fieldName) {
		super(injectedPlayer);
		this.entityId = Field.get(packet, fieldName, int.class);
	}

	public int getEntityId() {
		return entityId;
	}

	public WorldEntity getEntity() {
		return WorldEntity.getWorldEntity(entityId, getBukkit().getWorld());
	}

}
