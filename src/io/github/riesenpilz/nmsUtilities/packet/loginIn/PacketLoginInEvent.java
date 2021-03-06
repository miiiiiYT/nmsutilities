package io.github.riesenpilz.nmsUtilities.packet.loginIn;

import org.bukkit.entity.Player;

import io.github.riesenpilz.nmsUtilities.packet.PacketInEvent;
import io.github.riesenpilz.nmsUtilities.packet.PacketType;
import net.minecraft.server.v1_16_R3.Packet;
import net.minecraft.server.v1_16_R3.PacketLoginInListener;

public abstract class PacketLoginInEvent extends PacketInEvent {

	public PacketLoginInEvent(Player injectedPlayer) {
		super(injectedPlayer, PacketType.LOGIN_IN);
	}

	@Override
	public abstract Packet<PacketLoginInListener> getNMS();
}
