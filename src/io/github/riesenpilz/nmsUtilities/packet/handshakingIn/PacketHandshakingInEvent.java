package io.github.riesenpilz.nmsUtilities.packet.handshakingIn;

import org.bukkit.entity.Player;

import io.github.riesenpilz.nmsUtilities.packet.PacketInEvent;
import io.github.riesenpilz.nmsUtilities.packet.PacketType;
import net.minecraft.server.v1_16_R3.Packet;
import net.minecraft.server.v1_16_R3.PacketHandshakingInListener;

public abstract class PacketHandshakingInEvent extends PacketInEvent {

	public PacketHandshakingInEvent(Player injectedPlayer) {
		super(injectedPlayer, PacketType.HANDSHAKING_IN);
	}

	@Override
	public abstract Packet<PacketHandshakingInListener> getNMS();
}
