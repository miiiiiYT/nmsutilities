package io.github.riesenpilz.nmsUtilities.packet.playOut;

import org.apache.commons.lang.Validate;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import io.github.riesenpilz.nmsUtilities.packet.PacketUtils;
import io.github.riesenpilz.nmsUtilities.reflections.Field;
import net.minecraft.server.v1_16_R3.BlockPosition;
import net.minecraft.server.v1_16_R3.Packet;
import net.minecraft.server.v1_16_R3.PacketListenerPlayOut;
import net.minecraft.server.v1_16_R3.PacketPlayOutSpawnPosition;

/**
 * https://wiki.vg/Protocol#Spawn_Position
 * <p>
 * Sent by the server after login to specify the coordinates of the spawn point
 * (the point at which players spawn at, and which the compass points to). It
 * can be sent at any time to update the point compasses point at.
 * <p>
 * Packet ID: 0x4B<br>
 * State: Play<br>
 * Bound To: Client
 * 
 * @author Martin
 *
 */
public class PacketPlayOutSpawnPositionEvent extends PacketPlayOutEvent {

	private Location spawnLocation;

	/**
	 * The angle at which to respawn at.
	 */
	private float angle;

	public PacketPlayOutSpawnPositionEvent(Player injectedPlayer, PacketPlayOutSpawnPosition packet) {
		super(injectedPlayer);

		Validate.notNull(packet);

		spawnLocation = PacketUtils.toLocation(Field.get(packet, "position", BlockPosition.class),
				injectedPlayer.getWorld());
		angle = Field.get(packet, "b", float.class);
	}

	public PacketPlayOutSpawnPositionEvent(Player injectedPlayer, Location spawnLocation, float angle) {
		super(injectedPlayer);

		Validate.notNull(spawnLocation);

		this.spawnLocation = spawnLocation;
		this.angle = angle;
	}

	public Location getSpawnLocation() {
		return spawnLocation;
	}

	public float getAngle() {
		return angle;
	}

	@Override
	public Packet<PacketListenerPlayOut> getNMS() {
		return new PacketPlayOutSpawnPosition(PacketUtils.toBlockPosition(spawnLocation), angle);
	}

	@Override
	public int getPacketID() {
		return 0x4B;
	}

	@Override
	public String getProtocolURLString() {
		return "https://wiki.vg/Protocol#Spawn_Position";
	}
}
