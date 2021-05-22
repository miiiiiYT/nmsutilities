package io.github.riesenpilz.nms.entity.player;

import org.bukkit.craftbukkit.v1_16_R3.entity.CraftPlayer;

import io.github.riesenpilz.nms.entity.livingEntity.LivingEntity;
import io.netty.channel.ChannelPipeline;
import net.minecraft.server.v1_16_R3.EntityPlayer;
import net.minecraft.server.v1_16_R3.PlayerConnection;

public class Player extends LivingEntity {
	private final org.bukkit.entity.Player player;
	public Player(org.bukkit.entity.Player player) {
		super(player);
		this.player = player;
	}
	@Override
	public EntityPlayer getNMS() {
		return  ((CraftPlayer) player).getHandle();
	}
	
	public ChannelPipeline getChannelPipeline() {
		 return getPlayerConnection().networkManager.channel.pipeline();
	}
	public PlayerConnection getPlayerConnection() {
		return getNMS().playerConnection;
	}
	public org.bukkit.entity.Player getPlayer() {
		return player;
	}
}
