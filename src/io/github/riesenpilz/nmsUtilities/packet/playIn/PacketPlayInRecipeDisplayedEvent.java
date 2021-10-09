package io.github.riesenpilz.nmsUtilities.packet.playIn;

import org.apache.commons.lang.Validate;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;

import io.github.riesenpilz.nmsUtilities.packet.PacketUtils;
import io.github.riesenpilz.nmsUtilities.reflections.Field;
import net.minecraft.server.v1_16_R3.Packet;
import net.minecraft.server.v1_16_R3.PacketListenerPlayIn;
import net.minecraft.server.v1_16_R3.PacketPlayInRecipeDisplayed;

/**
 * https://wiki.vg/Protocol#Set_Displayed_Recipe
 * <p>
 * Replaces Recipe Book Data, type 0.
 * <p>
 * Packet ID: 0x1F<br>
 * State: Play<br>
 * Bound To: Server
 * 
 * @author Martin
 *
 */
public class PacketPlayInRecipeDisplayedEvent extends PacketPlayInEvent {

	private NamespacedKey recipeId;

	public PacketPlayInRecipeDisplayedEvent(Player injectedPlayer, PacketPlayInRecipeDisplayed packet) {
		super(injectedPlayer);
		Validate.notNull(packet);
		recipeId = PacketUtils.toNamespacedKey(packet.b());
	}

	public PacketPlayInRecipeDisplayedEvent(Player injectedPlayer, NamespacedKey recipeId) {
		super(injectedPlayer);
		Validate.notNull(recipeId);
		this.recipeId = recipeId;
	}

	public NamespacedKey getRecipeId() {
		return recipeId;
	}

	@Override
	public Packet<PacketListenerPlayIn> getNMS() {
		final PacketPlayInRecipeDisplayed packet = new PacketPlayInRecipeDisplayed();
		Field.set(packet, "a", PacketUtils.toMinecraftKey(recipeId));
		return packet;
	}

	@Override
	public int getPacketID() {
		return 0x1F;
	}

	@Override
	public String getProtocolURLString() {
		return "https://wiki.vg/Protocol#Set_Displayed_Recipe";
	}
}
