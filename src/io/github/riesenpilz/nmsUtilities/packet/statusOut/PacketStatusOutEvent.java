package io.github.riesenpilz.nmsUtilities.packet.statusOut;

import org.bukkit.entity.Player;

import io.github.riesenpilz.nmsUtilities.packet.PacketOutEvent;
import io.github.riesenpilz.nmsUtilities.packet.PacketType;
import net.minecraft.server.v1_16_R3.Packet;
import net.minecraft.server.v1_16_R3.PacketStatusOutListener;

public abstract class PacketStatusOutEvent extends PacketOutEvent {

	public PacketStatusOutEvent(Player injectedPlayer) {
		super(injectedPlayer, PacketType.STATUS_OUT);
	}

	@Override
	public abstract Packet<PacketStatusOutListener> getNMS();
}
