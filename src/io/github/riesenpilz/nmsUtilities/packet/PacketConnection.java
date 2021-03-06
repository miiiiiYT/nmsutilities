package io.github.riesenpilz.nmsUtilities.packet;

import io.github.riesenpilz.nmsUtilities.entity.livingEntity.player.Player;

public class PacketConnection {
	private final Player player;

	public PacketConnection(Player player) {
		this.player = player;
	}

	public void sendPacketToClient(PacketOutEvent packet) {
		player.getPlayerConnection().sendPacket(packet.getNMS());
	}

	public void sendPacketToServer(PacketInEvent packet) {
		player.getChannelPipeline().fireChannelRead(packet.getNMS());
	}
}
