package io.github.riesenpilz.nmsUtilities.packet.playIn;

import java.util.UUID;

import org.apache.commons.lang.Validate;
import org.bukkit.entity.Player;

import io.github.riesenpilz.nmsUtilities.reflections.Field;
import net.minecraft.server.v1_16_R3.Packet;
import net.minecraft.server.v1_16_R3.PacketListenerPlayIn;
import net.minecraft.server.v1_16_R3.PacketPlayInSpectate;

/**
 * https://wiki.vg/Protocol#Spectate
 * <p>
 * Teleports the player to the given entity. The player must be in spectator
 * mode.
 * <p>
 * The Notchian client only uses this to teleport to players, but it appears to
 * accept any type of entity. The entity does not need to be in the same
 * dimension as the player; if necessary, the player will be respawned in the
 * right world. If the given entity cannot be found (or isn't loaded), this
 * packet will be ignored. It will also be ignored if the player attempts to
 * teleport to themselves.
 * <p>
 * Packet ID: 0x2D<br>
 * State: Play<br>
 * Bound To: Server
 *
 * @author Martin
 *
 */
public class PacketPlayInSpectateEvent extends PacketPlayInEvent {

	/**
	 * UUID of the player to teleport to (can also be an entity UUID).
	 */
	private UUID targetUUID;

	public PacketPlayInSpectateEvent(Player injectedPlayer, PacketPlayInSpectate packet) {
		super(injectedPlayer);
		Validate.notNull(packet);
		targetUUID = Field.get(packet, "a", UUID.class);
	}

	public PacketPlayInSpectateEvent(Player injectedPlayer, UUID targetUUID) {
		super(injectedPlayer);
		Validate.notNull(targetUUID);
		this.targetUUID = targetUUID;
	}

	public UUID getTargetUUID() {
		return targetUUID;
	}

	@Override
	public Packet<PacketListenerPlayIn> getNMS() {
		return new PacketPlayInSpectate(targetUUID);
	}

	@Override
	public int getPacketID() {
		return 0x2D;
	}

	@Override
	public String getProtocolURLString() {
		return "https://wiki.vg/Protocol#Spectate";
	}

}
