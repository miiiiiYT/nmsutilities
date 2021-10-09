package io.github.riesenpilz.nmsUtilities.packet.playIn;

import org.apache.commons.lang.Validate;
import org.bukkit.entity.Player;

import io.github.riesenpilz.nmsUtilities.inventory.RecipeBookType;
import io.github.riesenpilz.nmsUtilities.reflections.Field;
import net.minecraft.server.v1_16_R3.Packet;
import net.minecraft.server.v1_16_R3.PacketListenerPlayIn;
import net.minecraft.server.v1_16_R3.PacketPlayInRecipeSettings;

/**
 * https://wiki.vg/Protocol#Set_Recipe_Book_State
 * <p>
 * Replaces Recipe Book Data, type 1.
 * <p>
 * Packet ID: 0x1E<br>
 * State: Play<br>
 * Bound To: Server
 *
 * @author Martin
 *
 */
public class PacketPlayInRecipeSettingsEvent extends PacketPlayInEvent {

	private RecipeBookType type;
	private boolean bookOpen;
	private boolean filterActive;

	public PacketPlayInRecipeSettingsEvent(Player injectedPlayer, PacketPlayInRecipeSettings packet) {
		super(injectedPlayer);
		Validate.notNull(packet);
		type = RecipeBookType.getRecipeBookType(packet.b());
	}

	public PacketPlayInRecipeSettingsEvent(Player injectedPlayer, RecipeBookType type, boolean bookOpen,
			boolean filterActive) {
		super(injectedPlayer);
		Validate.notNull(type);
		this.type = type;
		this.bookOpen = bookOpen;
		this.filterActive = filterActive;
	}

	public RecipeBookType getType() {
		return type;
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
		Field.set(packet, "a", type.getNMS());
		Field.set(packet, "b", bookOpen);
		Field.set(packet, "c", filterActive);
		return packet;
	}

	@Override
	public int getPacketID() {
		return 0x1E;
	}

	@Override
	public String getProtocolURLString() {
		return "https://wiki.vg/Protocol#Set_Recipe_Book_State";
	}

}
