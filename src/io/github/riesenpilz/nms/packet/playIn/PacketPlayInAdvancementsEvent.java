package io.github.riesenpilz.nms.packet.playIn;

import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;

import io.github.riesenpilz.nms.packet.PacketUtils;
import io.github.riesenpilz.nms.reflections.Field;
import net.minecraft.server.v1_16_R3.Packet;
import net.minecraft.server.v1_16_R3.PacketListenerPlayIn;
import net.minecraft.server.v1_16_R3.PacketPlayInAdvancements;

/**
 * https://wiki.vg/Protocol#Advancement_Tab
 * <p>
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
	private NamespacedKey tabId;

	public PacketPlayInAdvancementsEvent(Player injectedPlayer, PacketPlayInAdvancements packet) {
		super(injectedPlayer);
		tabId = PacketUtils.toNamespacedKey(packet.d());
		status = Status.getStatus(packet.c());
	}

	public PacketPlayInAdvancementsEvent(Player injectedPlayer, Status status, NamespacedKey tabId) {
		super(injectedPlayer);
		this.tabId = tabId;
		this.status = status;
	}

	public NamespacedKey getTabId() {
		return tabId;
	}

	public Status getStatus() {
		return status;
	}

	@Override
	public Packet<PacketListenerPlayIn> getNMS() {
		final PacketPlayInAdvancements packet = new PacketPlayInAdvancements();
		Field.set(packet, "a", status.getNMS());
		Field.set(packet, "b", PacketUtils.toMinecraftKey(tabId));
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

	public enum Status {
		OPENED_TAB(PacketPlayInAdvancements.Status.OPENED_TAB),
		CLOSED_SCREEN(PacketPlayInAdvancements.Status.CLOSED_SCREEN);

		private PacketPlayInAdvancements.Status nms;

		Status(PacketPlayInAdvancements.Status nms) {
			this.nms = nms;
		}

		public PacketPlayInAdvancements.Status getNMS() {
			return nms;
		}

		static Status getStatus(PacketPlayInAdvancements.Status nms) {
			for (Status status : values())
				if (status.getNMS().equals(nms))
					return status;
			return null;
		}

	}
}
