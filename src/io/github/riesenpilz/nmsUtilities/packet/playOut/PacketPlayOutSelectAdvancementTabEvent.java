package io.github.riesenpilz.nmsUtilities.packet.playOut;

import javax.annotation.Nullable;

import org.apache.commons.lang.Validate;
import org.bukkit.entity.Player;

import io.github.riesenpilz.nmsUtilities.advancemts.AdvancementTab;
import io.github.riesenpilz.nmsUtilities.reflections.Field;
import net.minecraft.server.v1_16_R3.MinecraftKey;
import net.minecraft.server.v1_16_R3.Packet;
import net.minecraft.server.v1_16_R3.PacketListenerPlayOut;
import net.minecraft.server.v1_16_R3.PacketPlayOutSelectAdvancementTab;

/**
 * https://wiki.vg/Protocol#Select_Advancement_Tab
 * <p>
 * Sent by the server to indicate that the client should switch advancement tab.
 * Sent either when the client switches tab in the GUI or when an advancement in
 * another tab is made.
 * <p>
 * Packet ID: 0x40<br>
 * State: Play<br>
 * Bound To: Client
 * 
 * @author Martin
 *
 */
public class PacketPlayOutSelectAdvancementTabEvent extends PacketPlayOutEvent {

	/**
	 * If no or an invalid identifier is sent, the client will switch to the first
	 * tab in the GUI.
	 */
	@Nullable
	private AdvancementTab tab;

	public PacketPlayOutSelectAdvancementTabEvent(Player injectedPlayer, PacketPlayOutSelectAdvancementTab packet) {
		super(injectedPlayer);

		Validate.notNull(packet);

		tab = AdvancementTab.getAdvancementTab(Field.get(packet, "a", MinecraftKey.class).getKey());
	}

	public PacketPlayOutSelectAdvancementTabEvent(Player injectedPlayer, @Nullable AdvancementTab tab) {
		super(injectedPlayer);

		this.tab = tab;
	}

	@Nullable
	public AdvancementTab getTab() {
		return tab;
	}

	@Override
	public Packet<PacketListenerPlayOut> getNMS() {
		return new PacketPlayOutSelectAdvancementTab(new MinecraftKey(tab.getIdentifier()));
	}

	@Override
	public int getPacketID() {
		return 0x40;
	}

	@Override
	public String getProtocolURLString() {
		return "https://wiki.vg/Protocol#Select_Advancement_Tab";
	}

	
}
