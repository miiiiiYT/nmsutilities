package io.github.riesenpilz.nmsUtilities.packet.playIn;

import org.apache.commons.lang.Validate;
import org.bukkit.entity.Player;

import io.github.riesenpilz.nmsUtilities.entity.livingEntity.player.ClientStatus;
import net.minecraft.server.v1_16_R3.Packet;
import net.minecraft.server.v1_16_R3.PacketListenerPlayIn;
import net.minecraft.server.v1_16_R3.PacketPlayInClientCommand;

/**
 * https://wiki.vg/Protocol#Client_Status
 * <p>
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
		Validate.notNull(packet);
		status = ClientStatus.getClientCommand(packet.b());
	}

	public PacketPlayInClientStatusEvent(Player injectedPlayer, ClientStatus status) {
		super(injectedPlayer);
		Validate.notNull(status);
		this.status = status;
	}

	public ClientStatus getStatus() {
		return status;
	}

	@Override
	public Packet<PacketListenerPlayIn> getNMS() {
		return new PacketPlayInClientCommand(status.getNMS());
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
