package io.github.riesenpilz.nms.packet;

import org.bukkit.entity.Player;

public class PacketConnection {
	private final Player player;

	public PacketConnection(Player player) {
		this.player = player;
	}

	public void sendPacketToServer(PacketEvent packet) {
		new io.github.riesenpilz.nms.entity.player.Player(player).getChannelPipeline()
				.fireChannelRead(packet.getNMS());
	}

	public void sendPacketToClient(PacketEvent packet) {
		new io.github.riesenpilz.nms.entity.player.Player(player).getPlayerConnection()
				.sendPacket(packet.getNMS());
	}
}
