package io.github.riesenpilz.nmsUtilities.packet;

import org.bukkit.entity.Player;

public abstract class PacketOutEvent extends PacketEvent {

	public PacketOutEvent(Player injectedPlayer, PacketType packetType) {
		super(injectedPlayer, packetType);
	}
	public void sendToClient() {
		new PacketConnection(io.github.riesenpilz.nmsUtilities.entity.player.Player.getPlayerOf(getBukkit())).sendPacketToClient(this);
	}
}
