package io.github.riesenpilz.nms.packet.playOut;

import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;

import io.github.riesenpilz.nms.reflections.Field;
import net.minecraft.server.v1_16_R3.MinecraftKey;
import net.minecraft.server.v1_16_R3.Packet;
import net.minecraft.server.v1_16_R3.PacketListenerPlayOut;
import net.minecraft.server.v1_16_R3.PacketPlayOutAutoRecipe;

/**
 * https://wiki.vg/Protocol#Craft_Recipe_Response
 * <p>
 * Response to the serverbound packet (Craft Recipe Request), with the same
 * recipe ID. Appears to be used to notify the UI.
 * <p>
 * Packet ID: 0x31<br>
 * State: Play<br>
 * Bound To: Client
 * 
 * @author Martin
 *
 */
public class PacketPlayOutCraftRecipeResponseEvent extends PacketPlayOutEvent {

	private int windowID;
    private NamespacedKey recipeKey;
	
	@SuppressWarnings("deprecation")
	public PacketPlayOutCraftRecipeResponseEvent(Player injectedPlayer, PacketPlayOutAutoRecipe packet) {
		super(injectedPlayer);
		windowID = Field.get(packet, "a", int.class);
		final MinecraftKey nms = Field.get(packet, "b", MinecraftKey.class);
		recipeKey = new NamespacedKey(nms.getNamespace(), nms.getKey());
	}

	public PacketPlayOutCraftRecipeResponseEvent(Player injectedPlayer, int windowID, NamespacedKey recipeKey) {
		super(injectedPlayer);
		this.windowID = windowID;
		this.recipeKey = recipeKey;
	}

	public int getWindowID() {
		return windowID;
	}

	public NamespacedKey getRecipeKey() {
		return recipeKey;
	}

	@Override
	public Packet<PacketListenerPlayOut> getNMS() {
		final PacketPlayOutAutoRecipe packet = new PacketPlayOutAutoRecipe();
		Field.set(packet, "a", windowID);
		Field.set(packet, "b", new MinecraftKey(recipeKey.getNamespace(), recipeKey.getKey()));
		return packet;
	}

	@Override
	public int getPacketID() {
		return 0x31;
	}

	@Override
	public String getProtocolURLString() {
		return "https://wiki.vg/Protocol#Craft_Recipe_Response";
	}
}
