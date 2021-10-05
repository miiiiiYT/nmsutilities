package io.github.riesenpilz.nmsUtilities.packet.loginOut;

import org.bukkit.entity.Player;

import io.github.riesenpilz.nmsUtilities.packet.PacketOutEvent;
import net.minecraft.server.v1_16_R3.Packet;
import net.minecraft.server.v1_16_R3.PacketLoginOutListener;

public abstract class PacketLoginOutEvent extends PacketOutEvent {
	public PacketLoginOutEvent(Player injectedPlayer) {
		super(injectedPlayer, PacketType.LOGIN_OUT);
	}

	@Override
	public abstract Packet<PacketLoginOutListener> getNMS();
}
