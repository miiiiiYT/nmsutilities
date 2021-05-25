package io.github.riesenpilz.nms.packet.playIn;

import org.bukkit.entity.Player;

import net.minecraft.server.v1_16_R3.Packet;
import net.minecraft.server.v1_16_R3.PacketListenerPlayIn;
import net.minecraft.server.v1_16_R3.PacketPlayInResourcePackStatus;
import net.minecraft.server.v1_16_R3.PacketPlayInResourcePackStatus.EnumResourcePackStatus;

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
		status = ResourcePackStatus.getResourcePackStatus(packet.status);
	}

	public PacketPlayInResourcePackStatusEvent(Player injectedPlayer, ResourcePackStatus resourcePackStatus) {
		super(injectedPlayer);
		this.status = resourcePackStatus;
	}

	public ResourcePackStatus getStatus() {
		return status;
	}

	@Override
	public Packet<PacketListenerPlayIn> getNMS() {
		return new PacketPlayInResourcePackStatus(status.getNMS());
	}

	public static enum ResourcePackStatus {

		SUCCESSFULLY_LOADED(EnumResourcePackStatus.SUCCESSFULLY_LOADED), DECLINED(EnumResourcePackStatus.DECLINED),
		FAILED_DOWNLOAD(EnumResourcePackStatus.FAILED_DOWNLOAD), ACCEPTED(EnumResourcePackStatus.ACCEPTED);

		private EnumResourcePackStatus nms;

		private ResourcePackStatus(EnumResourcePackStatus nms) {
			this.nms = nms;
		}

		public EnumResourcePackStatus getNMS() {
			return nms;
		}

		public static ResourcePackStatus getResourcePackStatus(EnumResourcePackStatus nms) {
			for (ResourcePackStatus status : values())
				if (status.getNMS().equals(nms))
					return status;
			return null;
		}
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
