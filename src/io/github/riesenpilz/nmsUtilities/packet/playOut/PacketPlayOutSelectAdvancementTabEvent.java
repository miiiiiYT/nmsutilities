package io.github.riesenpilz.nmsUtilities.packet.playOut;

import javax.annotation.Nullable;

import org.bukkit.entity.Player;

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
		tab = AdvancementTab.getAdvancementTab(Field.get(packet, "a", MinecraftKey.class).getKey());
	}

	public PacketPlayOutSelectAdvancementTabEvent(Player injectedPlayer, AdvancementTab tab) {
		super(injectedPlayer);
		this.tab = tab;
	}

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

	public enum AdvancementTab {
		STORY("story/root"), NETHER("nether/root"), END("end/root"), ADVENTURE("adventure/root"),
		HUSBANDRY("husbandry/root");

		private String identifier;

		private AdvancementTab(String identifier) {
			this.identifier = identifier;
		}

		public String getIdentifier() {
			return identifier;
		}

		public static AdvancementTab getAdvancementTab(String identifier) {
			for (AdvancementTab tab : AdvancementTab.values())
				if (tab.getIdentifier().equals(identifier))
					return tab;
			return null;
		}
	}
}
