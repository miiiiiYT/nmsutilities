package io.github.riesenpilz.nms.packet.playOut;

import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;

import io.github.riesenpilz.nms.packet.PacketUtils;
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
public class PacketPlayOutCraftRecipeResponseEvent extends PacketPlayOutInventoryEvent {

    private NamespacedKey recipeKey;
	
	public PacketPlayOutCraftRecipeResponseEvent(Player injectedPlayer, PacketPlayOutAutoRecipe packet) {
		super(injectedPlayer, packet);
		recipeKey = PacketUtils.toNamespacedKey(Field.get(packet, "b", MinecraftKey.class));
	}

	public PacketPlayOutCraftRecipeResponseEvent(Player injectedPlayer, int inventoryId, NamespacedKey recipeKey) {
		super(injectedPlayer, inventoryId);
		this.recipeKey = recipeKey;
	}

	public NamespacedKey getRecipeKey() {
		return recipeKey;
	}

	@Override
	public Packet<PacketListenerPlayOut> getNMS() {
		final PacketPlayOutAutoRecipe packet = new PacketPlayOutAutoRecipe();
		Field.set(packet, "a", getInventoryId());
		Field.set(packet, "b", PacketUtils.toMinecraftKey(recipeKey));
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
