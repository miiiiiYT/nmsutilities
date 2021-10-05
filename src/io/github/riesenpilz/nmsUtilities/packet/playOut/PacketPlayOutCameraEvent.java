package io.github.riesenpilz.nmsUtilities.packet.playOut;

import org.bukkit.entity.Player;

import io.github.riesenpilz.nmsUtilities.reflections.Field;
import net.minecraft.server.v1_16_R3.Packet;
import net.minecraft.server.v1_16_R3.PacketListenerPlayOut;
import net.minecraft.server.v1_16_R3.PacketPlayOutCamera;

/**
 * https://wiki.vg/Protocol#Camera
 * <p>
 * Sets the entity that the player renders from. This is normally used when the
 * player left-clicks an entity while in spectator mode.
 * <p>
 * The player's camera will move with the entity and look where it is looking.
 * The entity is often another player, but can be any type of entity. The player
 * is unable to move this entity (move packets will act as if they are coming
 * from the other entity).
 * <p>
 * If the given entity is not loaded by the player, this packet is ignored. To
 * return control to the player, send this packet with their entity ID.
 * <p>
 * The Notchian server resets this (sends it back to the default entity)
 * whenever the spectated entity is killed or the player sneaks, but only if
 * they were spectating an entity. It also sends this packet whenever the player
 * switches out of spectator mode (even if they weren't spectating an entity).
 * <p>
 * The notchian client also loads certain shaders for given entities:
 * <ul>
 * <li>Creeper -> shaders/post/creeper.json</li>
 * <li>Spider (and cave spider) -> shaders/post/spider.json</li>
 * <li>Enderman -> shaders/post/invert.json</li>
 * <li>Anything else -> the current shader is unloaded</li>
 * </ul>
 * Packet ID: 0x47<br>
 * State: Play<br>
 * Bound To: Client
 * 
 * @author Martin
 *
 */
public class PacketPlayOutCameraEvent extends PacketPlayOutEntityEvent {

	public PacketPlayOutCameraEvent(Player injectedPlayer, PacketPlayOutCamera packet) {
		super(injectedPlayer, packet);
		
	}

	public PacketPlayOutCameraEvent(Player injectedPlayer, int targetEntityId) {
		super(injectedPlayer, targetEntityId);
	}

	/**
	 * ID of the entity to set the client's camera to.
	 */
	public int getEntityId() {
		return super.getEntityId();
	}

	@Override
	public Packet<PacketListenerPlayOut> getNMS() {
		final PacketPlayOutCamera packet = new PacketPlayOutCamera();
		Field.set(packet, "a", getEntityId());
		return packet;
	}

	@Override
	public int getPacketID() {
		return 0x47;
	}

	@Override
	public String getProtocolURLString() {
		return "https://wiki.vg/Protocol#Camera";
	}
}
