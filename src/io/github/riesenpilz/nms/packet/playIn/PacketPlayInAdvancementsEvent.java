package io.github.riesenpilz.nms.packet.playIn;

import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;

import io.github.riesenpilz.nms.reflections.Field;
import net.minecraft.server.v1_16_R3.MinecraftKey;
import net.minecraft.server.v1_16_R3.Packet;
import net.minecraft.server.v1_16_R3.PacketListenerPlayIn;
import net.minecraft.server.v1_16_R3.PacketPlayInAdvancements;

/**
 * https://wiki.vg/Protocol#Advancement_Tab<br>
 * <br>
 * Packet ID: 0x22<br>
 * State: Play<br>
 * Bound To: Server
 * 
 * @author Martin
 *
 */
public class PacketPlayInAdvancementsEvent extends PacketPlayInEvent {

	private Status status;

	/**
	 * Only present if status is Opened tab.
	 */
	private NamespacedKey tabID;

	public PacketPlayInAdvancementsEvent(Player injectedPlayer, Status status, NamespacedKey tabID) {
		super(injectedPlayer);
		this.tabID = tabID;
		this.status = status;
	}

	@SuppressWarnings("deprecation")
	public PacketPlayInAdvancementsEvent(Player injectedPlayer, PacketPlayInAdvancements packet) {
		super(injectedPlayer);
		tabID = new NamespacedKey(packet.d().getNamespace(), packet.d().getKey());
		status = Status.getStatus(packet.c());
	}

	public NamespacedKey getTabID() {
		return tabID;
	}

	public Status getStatus() {
		return status;
	}

	public static enum Status {
		OPENED_TAB(PacketPlayInAdvancements.Status.OPENED_TAB),
		CLOSED_SCREEN(PacketPlayInAdvancements.Status.CLOSED_SCREEN);

		private PacketPlayInAdvancements.Status status;

		private Status(PacketPlayInAdvancements.Status status) {
			this.status = status;
		}

		public PacketPlayInAdvancements.Status getNMS() {
			return status;
		}

		static Status getStatus(PacketPlayInAdvancements.Status status) {
			for (Status status2 : Status.values())
				if (status2.getNMS().equals(status))
					return status2;
			return null;
		}

	}

	@Override
	public Packet<PacketListenerPlayIn> getNMS() {
		final PacketPlayInAdvancements packet = new PacketPlayInAdvancements();
		new Field(PacketPlayInAdvancements.class, "a").set(packet, status.getNMS());
		new Field(PacketPlayInAdvancements.class, "b").set(packet,
				new MinecraftKey(tabID.getNamespace(), tabID.getKey()));
		return packet;
	}

	@Override
	public int getPacketID() {
		return 0x22;
	}

	@Override
	public String getProtocolURLString() {
		return "https://wiki.vg/Protocol#Advancement_Tab";
	}
}
