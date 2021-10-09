package io.github.riesenpilz.nmsUtilities.packet.playOut;

import org.bukkit.entity.Player;

import io.github.riesenpilz.nmsUtilities.packet.PacketOutEvent;
import io.github.riesenpilz.nmsUtilities.packet.PacketType;
import net.minecraft.server.v1_16_R3.Packet;
import net.minecraft.server.v1_16_R3.PacketListenerPlayOut;

public abstract class PacketPlayOutEvent extends PacketOutEvent {

	public PacketPlayOutEvent(Player injectedPlayer) {
		super(injectedPlayer, PacketType.PLAY_OUT);
	}

	@Override
	public abstract Packet<PacketListenerPlayOut> getNMS();
}
