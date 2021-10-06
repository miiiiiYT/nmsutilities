package io.github.riesenpilz.nmsUtilities.packet.playOut;

import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;

import io.github.riesenpilz.nmsUtilities.packet.PacketUtils;
import io.github.riesenpilz.nmsUtilities.reflections.Field;
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
public class PacketPlayOutCraftRecipeResponseEvent extends PacketPlayOutInventoryEvent {

	private NamespacedKey recipeId;

	public PacketPlayOutCraftRecipeResponseEvent(Player injectedPlayer, PacketPlayOutAutoRecipe packet) {
		super(injectedPlayer, packet);
		recipeId = PacketUtils.toNamespacedKey(Field.get(packet, "b", MinecraftKey.class));
	}

	public PacketPlayOutCraftRecipeResponseEvent(Player injectedPlayer, int inventoryId, NamespacedKey recipeId) {
		super(injectedPlayer, inventoryId);
		this.recipeId = recipeId;
	}

	public NamespacedKey getRecipeId() {
		return recipeId;
	}

	@Override
	public Packet<PacketListenerPlayOut> getNMS() {
		final PacketPlayOutAutoRecipe packet = new PacketPlayOutAutoRecipe();
		Field.set(packet, "a", getInventoryId());
		Field.set(packet, "b", PacketUtils.toMinecraftKey(recipeId));
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
