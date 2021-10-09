package io.github.riesenpilz.nmsUtilities.packet.playIn;

import org.apache.commons.lang.Validate;
import org.bukkit.entity.Player;

import io.github.riesenpilz.nmsUtilities.resourcePack.ResourcePackStatus;
import net.minecraft.server.v1_16_R3.Packet;
import net.minecraft.server.v1_16_R3.PacketListenerPlayIn;
import net.minecraft.server.v1_16_R3.PacketPlayInResourcePackStatus;

/**
 * https://wiki.vg/Protocol#Resource_Pack_Status
 * <p>
 * Packet ID: 0x21<br>
 * State: Play<br>
 * Bound To: Server
 * 
 * @author Martin
 *
 */
public class PacketPlayInResourcePackStatusEvent extends PacketPlayInEvent {

	private ResourcePackStatus status;

	public PacketPlayInResourcePackStatusEvent(Player injectedPlayer, PacketPlayInResourcePackStatus packet) {
		super(injectedPlayer);
		Validate.notNull(packet);
		status = ResourcePackStatus.getResourcePackStatus(packet.status);
	}

	public PacketPlayInResourcePackStatusEvent(Player injectedPlayer, ResourcePackStatus satus) {
		super(injectedPlayer);
		Validate.notNull(satus);
		this.status = satus;
	}

	public ResourcePackStatus getStatus() {
		return status;
	}

	@Override
	public Packet<PacketListenerPlayIn> getNMS() {
		return new PacketPlayInResourcePackStatus(status.getNMS());
	}

	

	@Override
	public int getPacketID() {
		return 0x21;
	}

	@Override
	public String getProtocolURLString() {
		return "https://wiki.vg/Protocol#Resource_Pack_Status";
	}
}
