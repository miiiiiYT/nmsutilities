package io.github.riesenpilz.nms.packet.playOut;

import org.bukkit.entity.Player;

import com.mojang.brigadier.tree.RootCommandNode;

import io.github.riesenpilz.nms.reflections.Field;
import net.minecraft.server.v1_16_R3.ICompletionProvider;
import net.minecraft.server.v1_16_R3.Packet;
import net.minecraft.server.v1_16_R3.PacketListenerPlayOut;
import net.minecraft.server.v1_16_R3.PacketPlayOutCommands;

/**
 * https://wiki.vg/Protocol#Declare_Commands
 * <p>
 * Lists all of the commands on the server, and how they are parsed.
 * <p>
 * This is a directed graph, with one root node. Each redirect or child node
 * must refer only to nodes that have already been declared.
 * <p>
 * For more information on this packet, see the Command Data article.
 * <p>
 * Packet ID: 0x10<br>
 * State: Play<br>
 * Bound To: Client
 * 
 * @author Martin
 *
 */
public class PacketPlayOutListCommandsEvent extends PacketPlayOutEvent {

	private RootCommandNode<ICompletionProvider> commands;

	@SuppressWarnings("unchecked")
	public PacketPlayOutListCommandsEvent(Player injectedPlayer, PacketPlayOutCommands packet) {
		super(injectedPlayer);
		commands = Field.get(packet, "a", RootCommandNode.class);
	}

	public PacketPlayOutListCommandsEvent(Player injectedPlayer, RootCommandNode<ICompletionProvider> commands) {
		super(injectedPlayer);
		this.commands = commands;
	}

	public RootCommandNode<ICompletionProvider> getCommands() {
		return commands;
	}

	@Override
	public Packet<PacketListenerPlayOut> getNMS() {
		return new PacketPlayOutCommands(commands);
	}


	@Override
	public int getPacketID() {
		return 0x10;
	}

	@Override
	public String getProtocolURLString() {
		return "https://wiki.vg/Protocol#Declare_Commands";
	}
}
