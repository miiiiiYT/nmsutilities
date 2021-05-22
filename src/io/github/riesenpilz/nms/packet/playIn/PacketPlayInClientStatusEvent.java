package io.github.riesenpilz.nms.packet.playIn;

import org.bukkit.entity.Player;

import net.minecraft.server.v1_16_R3.Packet;
import net.minecraft.server.v1_16_R3.PacketListenerPlayIn;
import net.minecraft.server.v1_16_R3.PacketPlayInClientCommand;
import net.minecraft.server.v1_16_R3.PacketPlayInClientCommand.EnumClientCommand;

/**
 * https://wiki.vg/Protocol#Client_Status<br>
 * <br>
 * Packet ID: 0x04<br>
 * State: Play<br>
 * Bound To: Server
 * 
 * @author Martin
 *
 */
public class PacketPlayInClientStatusEvent extends PacketPlayInEvent {

	private ClientStatus status;

	public PacketPlayInClientStatusEvent(Player injectedPlayer, PacketPlayInClientCommand packet) {
		super(injectedPlayer);
		status = ClientStatus.getClientCommand(packet.b());
	}

	public PacketPlayInClientStatusEvent(Player injectedPlayer, ClientStatus status) {
		super(injectedPlayer);
		this.status = status;
	}

	public ClientStatus getStatus() {
		return status;
	}

	@Override
	public Packet<PacketListenerPlayIn> getNMS() {
		return new PacketPlayInClientCommand(status.getNMS());
	}

	public static enum ClientStatus {
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
			for (ClientStatus command2 : ClientStatus.values())
				if (command2.getNMS().equals(command))
					return command2;
			return null;
		}
	}

	@Override
	public int getPacketID() {
		return 0x04;
	}

	@Override
	public String getProtocolURLString() {
		return "https://wiki.vg/Protocol#Client_Status";
	}
}
