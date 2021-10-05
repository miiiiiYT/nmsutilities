package io.github.riesenpilz.nmsUtilities.packet.playIn;

import org.bukkit.entity.Player;

import io.github.riesenpilz.nmsUtilities.entity.player.PlayerAbilities;
import net.minecraft.server.v1_16_R3.Packet;
import net.minecraft.server.v1_16_R3.PacketListenerPlayIn;
import net.minecraft.server.v1_16_R3.PacketPlayInAbilities;

/**
 * https://wiki.vg/Protocol#Player_Abilities_.28serverbound.29
 * <p>
 * The vanilla client sends this packet when the player starts/stops flying with
 * the Flags parameter changed accordingly.
 * <p>
 * Packet ID: 0x1A<br>
 * State: Play<br>
 * Bound To: Server
 *
 * @author Martin
 *
 */
public class PacketPlayInAbilitiesEvent extends PacketPlayInEvent {

	private boolean isFlying;

	public PacketPlayInAbilitiesEvent(Player injectedPlayer, PacketPlayInAbilities packet) {
		super(injectedPlayer);
		isFlying = packet.isFlying();
	}

	public PacketPlayInAbilitiesEvent(Player injectedPlayer, PlayerAbilities playerAbilities) {
		super(injectedPlayer);
		isFlying = playerAbilities.isFlying();
	}

	public boolean isFlying() {
		return isFlying;
	}

	@Override
	public Packet<PacketListenerPlayIn> getNMS() {
		PlayerAbilities playerAbilities = new PlayerAbilities(false, isFlying(), isFlying(), false);
		PacketPlayInAbilities packet = new PacketPlayInAbilities(playerAbilities.getNMS());
		return packet;
	}

	@Override
	public int getPacketID() {
		return 0x1A;
	}

	@Override
	public String getProtocolURLString() {
		return "https://wiki.vg/Protocol#Player_Abilities_.28serverbound.29";
	}

}
