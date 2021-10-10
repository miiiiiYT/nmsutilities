package io.github.riesenpilz.nmsUtilities.packet.playOut;

import org.apache.commons.lang.Validate;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import io.github.riesenpilz.nmsUtilities.packet.HasBlockPosition;
import io.github.riesenpilz.nmsUtilities.packet.PacketUtils;
import io.github.riesenpilz.nmsUtilities.reflections.Field;
import net.minecraft.server.v1_16_R3.BlockPosition;
import net.minecraft.server.v1_16_R3.Packet;
import net.minecraft.server.v1_16_R3.PacketListenerPlayOut;

/**
 * https://wiki.vg/Protocol#Effect
 * <p>
 * Sent when a client is to play a sound or particle effect.
 * <p>
 * By default, the Minecraft client adjusts the volume of sound effects based on
 * distance. The final boolean field is used to disable this, and instead the
 * effect is played from 2 blocks away in the correct direction. Currently this
 * is only used for effect 1023 (wither spawn), effect 1028 (enderdragon death),
 * and effect 1038 (end portal opening); it is ignored on other effects.
 * <p>
 * Play record: This is actually a special case within this packet. You can
 * start/stop a record at a specific location. Use a valid Record ID to start a
 * record (or overwrite a currently playing one), any other value will stop the
 * record. See Data Generators for information on item IDs.
 * (https://wiki.vg/Data_Generators )
 * <p>
 * Packet ID: 0x23<br>
 * State: Play<br>
 * Bound To: Client
 */
public class PacketPlayOutEffectEvent extends PacketPlayOutEvent implements HasBlockPosition {

	private int effectId;
	private Location blockLocation;
	private int data;
	private boolean relativeVolumeDisabled;

	public PacketPlayOutEffectEvent(Player injectedPlayer,
			net.minecraft.server.v1_16_R3.PacketPlayOutWorldEvent packet) {
		super(injectedPlayer);

		Validate.notNull(packet);

		effectId = Field.get(packet, "a", int.class);
		blockLocation = PacketUtils.toLocation(Field.get(packet, "b", BlockPosition.class), injectedPlayer.getWorld());
		data = Field.get(packet, "c", int.class);
		relativeVolumeDisabled = Field.get(packet, "d", boolean.class);
	}

	public PacketPlayOutEffectEvent(Player injectedPlayer, int effectId, Location blockLocation, int data,
			boolean relativeVolumeDisabled) {
		super(injectedPlayer);

		Validate.notNull(blockLocation);

		this.effectId = effectId;
		this.blockLocation = blockLocation;
		this.data = data;
		this.relativeVolumeDisabled = relativeVolumeDisabled;
	}

	public int getEffectId() {
		return effectId;
	}

	public int getData() {
		return data;
	}

	public boolean isRelativeVolumeDisabled() {
		return relativeVolumeDisabled;
	}

	@Override
	public Location getBlockLocation() {
		return blockLocation;
	}

	@Override
	public Packet<PacketListenerPlayOut> getNMS() {
		return new net.minecraft.server.v1_16_R3.PacketPlayOutWorldEvent(effectId,
				PacketUtils.toBlockPosition(blockLocation), data, relativeVolumeDisabled);
	}

	@Override
	public int getPacketID() {
		return 0x23;
	}

	@Override
	public String getProtocolURLString() {
		return "https://wiki.vg/Protocol#Effect";
	}

}
