package io.github.riesenpilz.nmsUtilities.packet.statusIn;

import org.bukkit.entity.Player;

import io.github.riesenpilz.nmsUtilities.packet.PacketInEvent;
import io.github.riesenpilz.nmsUtilities.packet.PacketType;
import net.minecraft.server.v1_16_R3.Packet;
import net.minecraft.server.v1_16_R3.PacketStatusInListener;

public abstract class PacketStatusInEvent extends PacketInEvent {
	public PacketStatusInEvent(Player injectedPlayer) {
		super(injectedPlayer, PacketType.STATUS_IN);
	}

	@Override
	public abstract Packet<PacketStatusInListener> getNMS();
}
