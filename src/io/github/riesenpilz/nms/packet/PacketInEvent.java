package io.github.riesenpilz.nms.packet;

import org.bukkit.entity.Player;

public abstract class PacketInEvent extends PacketEvent {

	public PacketInEvent(Player injectedPlayer, PacketType packetType) {
		super(injectedPlayer, packetType);
	}

	public void sendToServer() {
		new PacketConnection(getInjectedPlayer()).sendPacketToServer(this);
	}
}
