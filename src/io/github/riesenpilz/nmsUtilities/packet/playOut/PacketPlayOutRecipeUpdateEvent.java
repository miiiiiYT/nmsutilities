package io.github.riesenpilz.nmsUtilities.packet.playOut;

import java.util.List;

import org.apache.commons.lang.Validate;
import org.bukkit.entity.Player;

import io.github.riesenpilz.nmsUtilities.reflections.Field;
import net.minecraft.server.v1_16_R3.IRecipe;
import net.minecraft.server.v1_16_R3.Packet;
import net.minecraft.server.v1_16_R3.PacketListenerPlayOut;
import net.minecraft.server.v1_16_R3.PacketPlayOutRecipeUpdate;

/**
 * https://wiki.vg/Protocol#Declare_Recipes
 * <p>
 * Packet ID: 0x65<br>
 * State: Play<br>
 * Bound To: Client
 */
public class PacketPlayOutRecipeUpdateEvent extends PacketPlayOutEvent {

	private List<IRecipe<?>> recipes;

	@SuppressWarnings("unchecked")
	public PacketPlayOutRecipeUpdateEvent(Player injectedPlayer, PacketPlayOutRecipeUpdate packet) {
		super(injectedPlayer);

		Validate.notNull(packet);

		recipes = Field.get(packet, "a", List.class);
	}

	public PacketPlayOutRecipeUpdateEvent(Player injectedPlayer, List<IRecipe<?>> recipes) {
		super(injectedPlayer);

		Validate.notNull(recipes);

		this.recipes = recipes;
	}

	public List<IRecipe<?>> getRecipes() {
		return recipes;
	}

	@Override
	public Packet<PacketListenerPlayOut> getNMS() {
		return new PacketPlayOutRecipeUpdate(recipes);
	}

	@Override
	public int getPacketID() {
		return 0x65;
	}

	@Override
	public String getProtocolURLString() {
		return "https://wiki.vg/Protocol#Declare_Recipes";
	}
}
