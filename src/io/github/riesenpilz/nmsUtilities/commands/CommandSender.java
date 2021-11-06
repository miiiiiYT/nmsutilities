package io.github.riesenpilz.nmsUtilities.commands;

import javax.annotation.Nullable;

import org.apache.commons.lang.Validate;
import org.bukkit.Location;

import io.github.riesenpilz.nmsUtilities.entity.WorldEntity;
import io.github.riesenpilz.nmsUtilities.world.ServerWorld;
import net.minecraft.server.v1_16_R3.CommandListenerWrapper;
import net.minecraft.server.v1_16_R3.Entity;
import net.minecraft.server.v1_16_R3.IChatBaseComponent;
import net.minecraft.server.v1_16_R3.Vec2F;
import net.minecraft.server.v1_16_R3.Vec3D;

public class CommandSender {
	private CommandListenerWrapper nms;

	protected CommandSender(CommandListenerWrapper nms) {
		Validate.notNull(nms);
		this.nms = nms;
//		commandListenerWrapper.base, commandListenerWrapper.getPosition(), commandListenerWrapper.i(),
//		commandListenerWrapper.getWorld(), Field.get(commandListenerWrapper, "f", int.class),
//		commandListenerWrapper.getName(), commandListenerWrapper.getScoreboardDisplayName(),
//		commandListenerWrapper.getServer(), commandListenerWrapper.getEntity(),
//		Field.get(commandListenerWrapper, "j", boolean.class),
//		(ResultConsumer<CommandListenerWrapper>) Field.get(commandListenerWrapper, "l", ResultConsumer.class),
//		commandListenerWrapper.k();
	}

	public IChatBaseComponent getScoreboardDisplayName() {
		return nms.getScoreboardDisplayName();
	}

	public String getName() {
		return nms.getName();
	}

	public boolean hasPermission(int i) {
		return nms.hasPermission(i);
	}

	public Location getLocation() {
		final Vec3D pos = nms.getPosition();
		final Vec2F bi = nms.i();
		return new Location(getWorld().getBukkit(), pos.x, pos.y, pos.z, bi.i, bi.j);
	}

	public ServerWorld getWorld() {
		return ServerWorld.getWorldOf(nms.getWorld());
	}

	@Nullable
	public WorldEntity getSender() {
		final @Nullable Entity entity = nms.getEntity();
		return entity == null ? null : WorldEntity.getWorldEntity(entity);
	}

	public ArgumentAnchor getAnchor() {
		return ArgumentAnchor.getArgumentAnchor(nms.k());
	}

	public CommandListenerWrapper getNMS() {
		return nms;
	}

	public static CommandSender getCommandSender(CommandListenerWrapper clw) {
		return new CommandSender(clw);
	}

}
