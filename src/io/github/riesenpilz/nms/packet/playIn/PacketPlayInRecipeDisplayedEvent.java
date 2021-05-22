package io.github.riesenpilz.nms.packet.playIn;

import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;

import io.github.riesenpilz.nms.reflections.Field;
import net.minecraft.server.v1_16_R3.MinecraftKey;
import net.minecraft.server.v1_16_R3.Packet;
import net.minecraft.server.v1_16_R3.PacketListenerPlayIn;
import net.minecraft.server.v1_16_R3.PacketPlayInRecipeDisplayed;

/**
 * https://wiki.vg/Protocol#Set_Displayed_Recipe<br>
 * <br>
 * Replaces Recipe Book Data, type 0.<br>
 * <br>
 * Packet ID: 0x1F<br>
 * State: Play<br>
 * Bound To: Server
 * 
 * @author Martin
 *
 */
public class PacketPlayInRecipeDisplayedEvent extends PacketPlayInEvent {

	private NamespacedKey recipeID;

	@SuppressWarnings("deprecation")
	public PacketPlayInRecipeDisplayedEvent(Player injectedPlayer, PacketPlayInRecipeDisplayed packet) {
		super(injectedPlayer);
		recipeID = new NamespacedKey(packet.b().getNamespace(), packet.b().getKey());
	}

	public PacketPlayInRecipeDisplayedEvent(Player injectedPlayer, NamespacedKey recipeID) {
		super(injectedPlayer);
		this.recipeID = recipeID;
	}

	public NamespacedKey getRecipeID() {
		return recipeID;
	}

	@Override
	public Packet<PacketListenerPlayIn> getNMS() {
		final PacketPlayInRecipeDisplayed packet = new PacketPlayInRecipeDisplayed();
		new Field(PacketPlayInRecipeDisplayed.class, "a").set(packet,
				new MinecraftKey(recipeID.getNamespace(), recipeID.getKey()));
		return packet;
	}

	@Override
	public int getPacketID() {
		return 31;
	}

	@Override
	public String getProtocolURLString() {
		return "https://wiki.vg/Protocol#Set_Displayed_Recipe";
	}
}
