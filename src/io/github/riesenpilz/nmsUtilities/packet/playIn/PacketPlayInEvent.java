package io.github.riesenpilz.nmsUtilities.packet.playIn;

import org.bukkit.entity.Player;

import io.github.riesenpilz.nmsUtilities.packet.PacketInEvent;
import net.minecraft.server.v1_16_R3.Packet;
import net.minecraft.server.v1_16_R3.PacketListenerPlayIn;

public abstract class PacketPlayInEvent extends PacketInEvent {

	public PacketPlayInEvent(Player injectedPlayer) {
		super(injectedPlayer, PacketType.PLAY_IN);
	}

	@Override
	public abstract Packet<PacketListenerPlayIn> getNMS();
}
