package io.github.riesenpilz.nms.packet.playOut;

import java.util.List;

import org.bukkit.entity.Player;

import io.github.riesenpilz.nms.reflections.Field;
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
 * 
 * @author Martin
 *
 */
public class PacketPlayOutRecipeUpdateEvent extends PacketPlayOutEvent {

	private List<IRecipe<?>> recipes;
	
	@SuppressWarnings("unchecked")
	public PacketPlayOutRecipeUpdateEvent(Player injectedPlayer, PacketPlayOutRecipeUpdate packet) {
		super(injectedPlayer);
		
		recipes = Field.get(packet, "a", List.class);
	}

	public PacketPlayOutRecipeUpdateEvent(Player injectedPlayer, List<IRecipe<?>> recipes) {
		super(injectedPlayer);
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
