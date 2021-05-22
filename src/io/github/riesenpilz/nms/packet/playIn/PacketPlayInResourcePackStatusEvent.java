package io.github.riesenpilz.nms.packet.playIn;

import java.net.MalformedURLException;
import java.net.URL;

import org.bukkit.entity.Player;

import net.minecraft.server.v1_16_R3.Packet;
import net.minecraft.server.v1_16_R3.PacketListenerPlayIn;
import net.minecraft.server.v1_16_R3.PacketPlayInResourcePackStatus;
import net.minecraft.server.v1_16_R3.PacketPlayInResourcePackStatus.EnumResourcePackStatus;

/**
 * https://wiki.vg/Protocol#Resource_Pack_Status<br>
 * <br>
 * Packet ID: 0x21<br>
 * State: Play<br>
 * Bound To: Server
 * 
 * @author Martin
 *
 */
public class PacketPlayInResourcePackStatusEvent extends PacketPlayInEvent {

	public static final URL PROTOCOL_URL = getURL();
	public static final int PACKET_ID = 33;

	private static URL getURL() {
		try {
			return new URL("https://wiki.vg/Protocol#Resource_Pack_Status");
		} catch (MalformedURLException ignored) {
		}
		return null;
	}

	private ResourcePackStatus resourcePackStatus;

	public PacketPlayInResourcePackStatusEvent(Player injectedPlayer, PacketPlayInResourcePackStatus packet) {
		super(injectedPlayer);
		resourcePackStatus = ResourcePackStatus.getResourcePackStatus(packet.status);
	}

	public PacketPlayInResourcePackStatusEvent(Player injectedPlayer, ResourcePackStatus resourcePackStatus) {
		super(injectedPlayer);
		this.resourcePackStatus = resourcePackStatus;
	}

	public ResourcePackStatus getResourcePackStatus() {
		return resourcePackStatus;
	}

	@Override
	public Packet<PacketListenerPlayIn> getNMS() {
		return new PacketPlayInResourcePackStatus(resourcePackStatus.getNMS());
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
			for (ResourcePackStatus resourcePackStatus : ResourcePackStatus.values())
				if (resourcePackStatus.getNMS().equals(nms))
					return resourcePackStatus;
			return null;
		}
	}

	@Override
	public int getPacketID() {
		return 33;
	}

	@Override
	public String getProtocolURLString() {
		return "https://wiki.vg/Protocol#Resource_Pack_Status";
	}
}
