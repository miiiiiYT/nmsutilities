package io.github.riesenpilz.nmsUtilities.packet.playIn;

import javax.annotation.Nullable;

import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;

import io.github.riesenpilz.nmsUtilities.packet.PacketUtils;
import io.github.riesenpilz.nmsUtilities.reflections.Field;
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
	@Nullable
	private NamespacedKey tabId;

	public PacketPlayInAdvancementsEvent(Player injectedPlayer, PacketPlayInAdvancements packet) {
		super(injectedPlayer);
		tabId = packet.d() == null ? null : PacketUtils.toNamespacedKey(packet.d());
		status = Status.getStatus(packet.c());
	}

	public PacketPlayInAdvancementsEvent(Player injectedPlayer, Status status, @Nullable NamespacedKey tabId) {
		super(injectedPlayer);
		this.tabId = tabId;
		this.status = status;
	}

	@Nullable
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
