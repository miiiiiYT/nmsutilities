package io.github.riesenpilz.nmsUtilities.entity.player;

import org.apache.commons.lang.Validate;

import io.github.riesenpilz.nmsUtilities.packet.playIn.PacketPlayInClientStatusEvent;
import net.minecraft.server.v1_16_R3.PacketPlayInClientCommand.EnumClientCommand;

/**
 * Represents {@link EnumClientCommand}. Only used by packets.
 * 
 * @see PacketPlayInClientStatusEvent
 */
public enum ClientStatus {
	/**
	 * Sent when the client is ready to complete login and when the client is ready
	 * to respawn after death.
	 */
	PERFORM_RESPAWN(EnumClientCommand.PERFORM_RESPAWN),
	/**
	 * Sent when the client opens the Statistics menu.
	 */
	REQUEST_STATS(EnumClientCommand.REQUEST_STATS);

	private final EnumClientCommand command;

	private ClientStatus(EnumClientCommand command) {
		this.command = command;
	}

	public EnumClientCommand getNMS() {
		return command;
	}

	public static ClientStatus getClientCommand(EnumClientCommand command) {
		Validate.notNull(command);
		for (ClientStatus status : ClientStatus.values())
			if (status.getNMS().equals(command))
				return status;
		throw new IllegalArgumentException();
	}
}
