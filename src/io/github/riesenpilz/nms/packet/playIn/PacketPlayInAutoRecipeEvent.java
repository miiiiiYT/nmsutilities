package io.github.riesenpilz.nms.packet.playIn;

import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;

import io.github.riesenpilz.nms.packet.PacketUtils;
import io.github.riesenpilz.nms.reflections.Field;
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
public class PacketPlayInAutoRecipeEvent extends PacketPlayInInventoryEvent {

	private NamespacedKey recipeId;

	/**
	 * Affects the amount of items processed; true if shift is down when clicked.
	 */
	private boolean makeAll;

	public PacketPlayInAutoRecipeEvent(Player injectedPlayer, PacketPlayInAutoRecipe packet) {
		super(injectedPlayer, packet.b());
		recipeId = PacketUtils.toNamespacedKey(packet.c());
		makeAll = packet.d();
	}

	public PacketPlayInAutoRecipeEvent(Player injectedPlayer, int windowID, NamespacedKey recipeId, boolean makeAll) {
		super(injectedPlayer, windowID);
		this.recipeId = recipeId;
		this.makeAll = makeAll;
	}

	public NamespacedKey getRecipeID() {
		return recipeId;
	}

	public boolean isMakeAll() {
		return makeAll;
	}

	@Override
	public Packet<PacketListenerPlayIn> getNMS() {
		final PacketPlayInAutoRecipe packet = new PacketPlayInAutoRecipe();
		Field.set(packet, "a", getInventoryId());
		Field.set(packet, "b", PacketUtils.toMinecraftKey(recipeId));
		Field.set(packet, "c", makeAll);
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
