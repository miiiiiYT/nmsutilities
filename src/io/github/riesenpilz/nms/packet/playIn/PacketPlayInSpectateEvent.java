package io.github.riesenpilz.nms.packet.playIn;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import io.github.riesenpilz.nms.reflections.Field;
import net.minecraft.server.v1_16_R3.Packet;
import net.minecraft.server.v1_16_R3.PacketListenerPlayIn;
import net.minecraft.server.v1_16_R3.PacketPlayInSpectate;

/**
 * https://wiki.vg/Protocol#Spectate<br>
 * <br>
 * Teleports the player to the given entity. The player must be in spectator
 * mode.<br>
 * <br>
 * The Notchian client only uses this to teleport to players, but it appears to
 * accept any type of entity. The entity does not need to be in the same
 * dimension as the player; if necessary, the player will be respawned in the
 * right world. If the given entity cannot be found (or isn't loaded), this
 * packet will be ignored. It will also be ignored if the player attempts to
 * teleport to themselves.<br>
 * <br>
 * Packet ID: 0x2D<br>
 * State: Play<br>
 * Bound To: Server
 * 
 * @author Martin
 *
 */
public class PacketPlayInSpectateEvent extends PacketPlayInEvent {

	/**
	 * The target the player to teleport to
	 */
	private Entity target;

	public PacketPlayInSpectateEvent(Player injectedPlayer, PacketPlayInSpectate packet) {
		super(injectedPlayer);
		target = Bukkit.getEntity((UUID) new Field(PacketPlayInSpectate.class, "a").get(packet));
	}

	public PacketPlayInSpectateEvent(Player injectedPlayer, Entity target) {
		super(injectedPlayer);
		this.target = target;
	}

	public Entity getTarget() {
		return target;
	}

	@Override
	public Packet<PacketListenerPlayIn> getNMS() {
		return new PacketPlayInSpectate(target.getUniqueId());
	}

	@Override
	public int getPacketID() {
		return 45;
	}

	@Override
	public String getProtocolURLString() {
		return "https://wiki.vg/Protocol#Spectate";
	}

}
