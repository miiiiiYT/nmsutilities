package io.github.riesenpilz.nmsUtilities.commands;

import java.util.function.Predicate;

import org.apache.commons.lang.Validate;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_16_R3.CraftServer;

import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;

import net.minecraft.server.v1_16_R3.CommandDispatcher;
import net.minecraft.server.v1_16_R3.CommandListenerWrapper;
import net.minecraft.server.v1_16_R3.DedicatedServer;

/**
 * 
 * This class can be used to register a command with Mojang Brigadier without
 * registering it in the plugin.yml. nevertheless it still has to be registered
 * with {@code #register()}.
 *
 */
public class Command {

	private final LiteralArgumentBuilder<CommandListenerWrapper> cmd;

	public Command(String name) {
		cmd = CommandDispatcher.a(name);
	}

	public Command requires(final Predicate<CommandSender> requirement) {
		Validate.notNull(requirement);
		cmd.requires(clw -> requirement.test(CommandSender.getCommandSender(clw)));
		return this;
	}

	public Command register() {
		final DedicatedServer server = ((CraftServer) Bukkit.getServer()).getServer();
		server.vanillaCommandDispatcher.a().register(cmd);
		return this;
	}

	public Command then(String literal) {
		cmd.then(CommandDispatcher.a(literal));
		return this;
	}

	public Command then(String argName, ArgumentType<?> type) {
		cmd.then(CommandDispatcher.a(argName, type));
		return this;
	}

	public Command executes(final com.mojang.brigadier.Command<CommandListenerWrapper> command) {
		cmd.executes(command);
		return this;
	}

	public LiteralArgumentBuilder<CommandListenerWrapper> getLiteralArgumentBuilder() {
		return cmd;
	}
}
