package io.github.riesenpilz.nmsUtilities.packet;

import org.bukkit.entity.Player;

public abstract class PacketInEvent extends PacketEvent {

	public PacketInEvent(Player injectedPlayer, PacketType packetType) {
		super(injectedPlayer, packetType);
	}

	public void sendToServer() {
		new PacketConnection(io.github.riesenpilz.nmsUtilities.entity.livingEntity.player.Player.getPlayerOf(getBukkit()))
				.sendPacketToServer(this);
	}
}
