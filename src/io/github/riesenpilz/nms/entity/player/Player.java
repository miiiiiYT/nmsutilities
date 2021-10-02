package io.github.riesenpilz.nms.entity.player;

import org.bukkit.craftbukkit.v1_16_R3.entity.CraftPlayer;

import io.github.riesenpilz.nms.entity.livingEntity.LivingEntity;
import io.netty.channel.ChannelPipeline;
import net.minecraft.server.v1_16_R3.EntityPlayer;
import net.minecraft.server.v1_16_R3.PlayerConnection;

public class Player extends LivingEntity {

	protected Player(org.bukkit.entity.Player bukkit) {
		super(bukkit);
	}

	public static Player getPlayerOf(org.bukkit.entity.Player bukkit) {
		return new Player(bukkit);
	}

	@Override
	public org.bukkit.entity.Player getBukkit() {
		return (org.bukkit.entity.Player) super.getBukkit();
	}

	@Override
	public EntityPlayer getNMS() {
		return ((CraftPlayer) getBukkit()).getHandle();
	}

	public ChannelPipeline getChannelPipeline() {
		return getPlayerConnection().networkManager.channel.pipeline();
	}

	public PlayerConnection getPlayerConnection() {
		return getNMS().playerConnection;
	}

	public String getName() {
		return getBukkit().getName();
	}

}
