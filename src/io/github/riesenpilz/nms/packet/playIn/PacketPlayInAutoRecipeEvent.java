package io.github.riesenpilz.nms.packet.playIn;

import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;

import io.github.riesenpilz.nms.reflections.Field;
import net.minecraft.server.v1_16_R3.MinecraftKey;
import net.minecraft.server.v1_16_R3.Packet;
import net.minecraft.server.v1_16_R3.PacketListenerPlayIn;
import net.minecraft.server.v1_16_R3.PacketPlayInAutoRecipe;

/**
 * https://wiki.vg/Protocol#Craft_Recipe_Request<br>
 * <br>
 * This packet is sent when a player clicks a recipe in the crafting book that
 * is craftable (white border).<br>
 * <br>
 * Packet ID: 0x19<br>
 * State: Play<br>
 * Bound To: Server
 * 
 * @author Martin
 *
 */
public class PacketPlayInAutoRecipeEvent extends PacketPlayInEvent {

	private int windowID;
	private NamespacedKey recipeID;

	/**
	 * Affects the amount of items processed; true if shift is down when clicked.
	 */
	private boolean makeAll;

	public PacketPlayInAutoRecipeEvent(Player injectedPlayer, int windowID, NamespacedKey recipeID, boolean makeAll) {
		super(injectedPlayer);
		this.windowID = windowID;
		this.recipeID = recipeID;
		this.makeAll = makeAll;
	}

	@SuppressWarnings("deprecation")
	public PacketPlayInAutoRecipeEvent(Player injectedPlayer, PacketPlayInAutoRecipe packet) {
		super(injectedPlayer);
		windowID = packet.b();
		recipeID = new NamespacedKey(packet.c().getNamespace(), packet.c().getKey());
		makeAll = packet.d();
	}

	public int getWindowID() {
		return windowID;
	}

	public NamespacedKey getRecipeID() {
		return recipeID;
	}

	public boolean isMakeAll() {
		return makeAll;
	}

	@Override
	public Packet<PacketListenerPlayIn> getNMS() {
		final PacketPlayInAutoRecipe packet = new PacketPlayInAutoRecipe();
		new Field(PacketPlayInAutoRecipe.class, "a").set(packet, windowID);
		new Field(PacketPlayInAutoRecipe.class, "b").set(packet,
				new MinecraftKey(recipeID.getNamespace(), recipeID.getKey()));
		new Field(PacketPlayInAutoRecipe.class, "c").set(packet, makeAll);
		return packet;
	}

	@Override
	public int getPacketID() {
		return 0x19;
	}

	@Override
	public String getProtocolURLString() {
		return "https://wiki.vg/Protocol#Craft_Recipe_Request";
	}
}
