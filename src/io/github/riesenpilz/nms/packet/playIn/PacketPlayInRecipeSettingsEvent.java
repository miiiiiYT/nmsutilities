package io.github.riesenpilz.nms.packet.playIn;

import org.bukkit.entity.Player;

import io.github.riesenpilz.nms.inventory.RecipeBookType;
import io.github.riesenpilz.nms.reflections.Field;
import net.minecraft.server.v1_16_R3.Packet;
import net.minecraft.server.v1_16_R3.PacketListenerPlayIn;
import net.minecraft.server.v1_16_R3.PacketPlayInRecipeSettings;

/**
 * https://wiki.vg/Protocol#Set_Recipe_Book_State<br>
 * <br>
 * Replaces Recipe Book Data, type 1.<br>
 * <br>
 * Packet ID: 0x1E<br>
 * State: Play<br>
 * Bound To: Server
 * 
 * @author Martin
 *
 */
public class PacketPlayInRecipeSettingsEvent extends PacketPlayInEvent {

	private RecipeBookType recipeBookType;
	private boolean bookOpen;
	private boolean filterActive;

	public PacketPlayInRecipeSettingsEvent(Player injectedPlayer, PacketPlayInRecipeSettings packet) {
		super(injectedPlayer);
		recipeBookType = RecipeBookType.getRecipeBookType(packet.b());
	}

	public PacketPlayInRecipeSettingsEvent(Player injectedPlayer, RecipeBookType recipeBookType, boolean bookOpen,
			boolean filterActive) {
		super(injectedPlayer);
		this.recipeBookType = recipeBookType;
		this.bookOpen = bookOpen;
		this.filterActive = filterActive;
	}

	public RecipeBookType getRecipeBookType() {
		return recipeBookType;
	}

	public boolean isBookOpen() {
		return bookOpen;
	}

	public boolean isFilterActive() {
		return filterActive;
	}

	@Override
	public Packet<PacketListenerPlayIn> getNMS() {
		final PacketPlayInRecipeSettings packet = new PacketPlayInRecipeSettings();
		new Field(PacketPlayInRecipeSettings.class, "a").set(packet, recipeBookType.getNMS());
		new Field(PacketPlayInRecipeSettings.class, "b").set(packet, bookOpen);
		new Field(PacketPlayInRecipeSettings.class, "c").set(packet, filterActive);
		return packet;
	}

	@Override
	public int getPacketID() {
		return 30;
	}

	@Override
	public String getProtocolURLString() {
		return "https://wiki.vg/Protocol#Set_Recipe_Book_State";
	}

}
