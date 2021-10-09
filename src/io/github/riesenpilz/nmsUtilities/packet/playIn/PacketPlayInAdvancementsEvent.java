package io.github.riesenpilz.nmsUtilities.packet.playIn;

import javax.annotation.Nullable;

import org.apache.commons.lang.Validate;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;

import io.github.riesenpilz.nmsUtilities.advancemts.AdvancementStatus;
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

	private AdvancementStatus status;

	/**
	 * Only present if status is Opened tab.
	 */
	@Nullable
	private NamespacedKey tabId;

	public PacketPlayInAdvancementsEvent(Player injectedPlayer, PacketPlayInAdvancements packet) {
		super(injectedPlayer);
		Validate.notNull(packet);
		tabId = packet.d() == null ? null : PacketUtils.toNamespacedKey(packet.d());
		status = AdvancementStatus.getAdvancementStatus(packet.c());
	}

	public PacketPlayInAdvancementsEvent(Player injectedPlayer, AdvancementStatus status,
			@Nullable NamespacedKey tabId) {
		super(injectedPlayer);
		Validate.notNull(status);
		this.tabId = tabId;
		this.status = status;
	}

	@Nullable
	public NamespacedKey getTabId() {
		return tabId;
	}

	public AdvancementStatus getStatus() {
		return status;
	}

	@Override
	public Packet<PacketListenerPlayIn> getNMS() {
		final PacketPlayInAdvancements packet = new PacketPlayInAdvancements();
		Field.set(packet, "a", status.getNMS());
		Field.set(packet, "b", tabId == null ? null : PacketUtils.toMinecraftKey(tabId));
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
