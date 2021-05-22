package io.github.riesenpilz.nms.packet.handshakingIn;

import org.bukkit.entity.Player;

import io.github.riesenpilz.nms.packet.PacketInEvent;
import net.minecraft.server.v1_16_R3.Packet;
import net.minecraft.server.v1_16_R3.PacketHandshakingInListener;

public abstract class PacketHandshakingInEvent extends PacketInEvent {

	public PacketHandshakingInEvent(Player injectedPlayer) {
		super(injectedPlayer, PacketType.HANDSHAKING_IN);
	}

	@Override
	public abstract Packet<PacketHandshakingInListener> getNMS();
}
