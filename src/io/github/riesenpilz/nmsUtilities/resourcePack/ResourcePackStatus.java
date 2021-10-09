package io.github.riesenpilz.nmsUtilities.resourcePack;

import org.apache.commons.lang.Validate;

import net.minecraft.server.v1_16_R3.PacketPlayInResourcePackStatus.EnumResourcePackStatus;

/**
 * Represents {@link EnumResourcePackStatus}. Only used by Packets.
 * 
 * @see PacketPlayInResourcePackStatusEvent
 *
 */
public enum ResourcePackStatus {

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
		Validate.notNull(nms);
		for (ResourcePackStatus status : values())
			if (status.getNMS().equals(nms))
				return status;
		throw new IllegalArgumentException();
	}
}
