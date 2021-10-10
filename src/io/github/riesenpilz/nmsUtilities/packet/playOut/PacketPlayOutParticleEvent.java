package io.github.riesenpilz.nmsUtilities.packet.playOut;

import org.apache.commons.lang.Validate;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import io.github.riesenpilz.nmsUtilities.reflections.Field;
import net.minecraft.server.v1_16_R3.Packet;
import net.minecraft.server.v1_16_R3.PacketListenerPlayOut;
import net.minecraft.server.v1_16_R3.PacketPlayOutWorldParticles;
import net.minecraft.server.v1_16_R3.ParticleParam;

/**
 * https://wiki.vg/Protocol#Particle_2
 * <p>
 * Displays the named particle.
 * <p>
 * Packet ID: 0x24<br>
 * State: Play<br>
 * Bound To: Client
 * 
 * @author Martin
 *
 */
public class PacketPlayOutParticleEvent extends PacketPlayOutEvent {

	/**
	 * If true, particle distance increases from 256 to 65536.
	 */
	private boolean longDistance;

	/**
	 * will be multiplied by random.nextGaussian().
	 */
	private Location particleLocation;

	/**
	 * This is added to the X position after being multiplied by
	 * random.nextGaussian().
	 */
	private float offsetX;

	/**
	 * This is added to the Y position after being multiplied by
	 * random.nextGaussian().
	 */
	private float offsetY;

	/**
	 * This is added to the Z position after being multiplied by
	 * random.nextGaussian().
	 */
	private float offsetZ;

	/**
	 * https://wiki.vg/Protocol#Particle
	 */
	private float data;
	private int count;

	private ParticleParam particleParam;

	public PacketPlayOutParticleEvent(Player injectedPlayer, PacketPlayOutWorldParticles packet) {
		super(injectedPlayer);

		Validate.notNull(packet);

		particleLocation = new Location(injectedPlayer.getWorld(), Field.get(packet, "a", double.class),
				Field.get(packet, "b", double.class), Field.get(packet, "c", double.class));
		offsetX = Field.get(packet, "d", float.class);
		offsetY = Field.get(packet, "e", float.class);
		offsetZ = Field.get(packet, "f", float.class);
		data = Field.get(packet, "g", float.class);
		count = Field.get(packet, "h", int.class);
		longDistance = Field.get(packet, "i", boolean.class);
		particleParam = Field.get(packet, "j", ParticleParam.class);
	}

	public PacketPlayOutParticleEvent(Player injectedPlayer, Location particleLocation, float offsetX, float offsetY,
			float offsetZ, float data, int count, boolean longDistance, ParticleParam particleParam) {
		super(injectedPlayer);

		Validate.notNull(particleLocation);
		Validate.notNull(particleParam);

		this.particleLocation = particleLocation;
		this.offsetX = offsetX;
		this.offsetY = offsetY;
		this.offsetZ = offsetZ;
		this.data = data;
		this.count = count;
		this.longDistance = longDistance;
		this.particleParam = particleParam;
	}

	public Location getParticleLocation() {
		return particleLocation;
	}

	public float getOffsetX() {
		return offsetX;
	}

	public float getOffsetY() {
		return offsetY;
	}

	public float getOffsetZ() {
		return offsetZ;
	}

	public float getData() {
		return data;
	}

	public int getCount() {
		return count;
	}

	public boolean isLongDistance() {
		return longDistance;
	}

	public ParticleParam getParticleParam() {
		return particleParam;
	}

	@Override
	public Packet<PacketListenerPlayOut> getNMS() {
		return new PacketPlayOutWorldParticles(particleParam, longDistance, particleLocation.getX(),
				particleLocation.getY(), particleLocation.getZ(), offsetZ, offsetY, offsetX, data, count);
	}

	@Override
	public int getPacketID() {
		return 0x24;
	}

	@Override
	public String getProtocolURLString() {
		return "https://wiki.vg/Protocol#Particle_2";
	}
}
