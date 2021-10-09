package io.github.riesenpilz.nmsUtilities.packet.playIn;

import org.apache.commons.lang.Validate;
import org.bukkit.Location;
import org.bukkit.Rotation;
import org.bukkit.block.structure.Mirror;
import org.bukkit.block.structure.UsageMode;
import org.bukkit.entity.Player;

import io.github.riesenpilz.nmsUtilities.block.StructureUpdateType;
import io.github.riesenpilz.nmsUtilities.packet.HasBlockPosition;
import io.github.riesenpilz.nmsUtilities.packet.PacketUtils;
import io.github.riesenpilz.nmsUtilities.reflections.Field;
import net.minecraft.server.v1_16_R3.Packet;
import net.minecraft.server.v1_16_R3.PacketListenerPlayIn;
import net.minecraft.server.v1_16_R3.PacketPlayInStruct;

/**
 * https://wiki.vg/Protocol#Update_Structure_Block
 * <p>
 * Used when Shift+F3+I is pressed while looking at a block
 * <p>
 * Packet ID: 0x2A<br>
 * State: Play<br>
 * Bound To: Server
 *
 * @author Martin
 *
 */
public class PacketPlayInUpdateStructureBlockEvent extends PacketPlayInEvent implements HasBlockPosition {

	private Location blockLocation;
	private StructureUpdateType updateType;
	private UsageMode mode;
	private String name;

	/**
	 * Between -32 and 32 each
	 */
	private Location offset;

	/**
	 * Between -32 and 32 each
	 */
	private Location size;

	private Mirror mirror;
	private Rotation rotation;

	private String metadata;
	private boolean ignoreEntities;
	private boolean showAir;
	private boolean showBoundingBox;

	/**
	 * Between 0 and 1.
	 */
	private float integrity;
	private long seed;

	public PacketPlayInUpdateStructureBlockEvent(Player injectedPlayer, PacketPlayInStruct packet) {
		super(injectedPlayer);

		Validate.notNull(packet);

		blockLocation = PacketUtils.toLocation(packet.b(), injectedPlayer.getWorld());
		updateType = StructureUpdateType.getUpdateType(packet.c());
		mode = PacketUtils.toUsageMode(packet.d());
		name = packet.e();
		offset = PacketUtils.toLocation(packet.f(), injectedPlayer.getWorld());
		size = PacketUtils.toLocation(packet.g(), injectedPlayer.getWorld());
		mirror = PacketUtils.toMirror(packet.h());
		rotation = PacketUtils.toRotation(packet.i());
		metadata = packet.j();
		ignoreEntities = packet.k();
		showAir = packet.l();
		showBoundingBox = packet.m();
		integrity = packet.n();
		seed = packet.o();
	}

	public PacketPlayInUpdateStructureBlockEvent(Player injectedPlayer, Location blockLocation, StructureUpdateType updateType,
			UsageMode mode, String name, Location offset, Location size, Mirror mirror, Rotation rotation, String metadata,
			boolean ignoreEntities, boolean showAir, boolean showBoundingBox, float integrity, long seed) {
		super(injectedPlayer);

		Validate.notNull(blockLocation);
		Validate.notNull(updateType);
		Validate.notNull(mode);
		Validate.notNull(name);
		Validate.notNull(offset);
		Validate.notNull(size);
		Validate.notNull(mirror);
		Validate.notNull(rotation);
		Validate.notNull(metadata);

		this.blockLocation = blockLocation;
		this.updateType = updateType;
		this.mode = mode;
		this.name = name;
		this.offset = offset;
		this.size = size;
		this.mirror = mirror;
		this.rotation = rotation;
		this.metadata = metadata;
		this.ignoreEntities = ignoreEntities;
		this.showAir = showAir;
		this.showBoundingBox = showBoundingBox;
		this.integrity = integrity;
		this.seed = seed;
	}

	@Override
	public Location getBlockLocation() {
		return blockLocation;
	}

	public StructureUpdateType getUpdateType() {
		return updateType;
	}

	public UsageMode getMode() {
		return mode;
	}

	public String getName() {
		return name;
	}

	public Location getOffset() {
		return offset;
	}

	public Location getSize() {
		return size;
	}

	public Mirror getMirror() {
		return mirror;
	}

	public Rotation getRotation() {
		return rotation;
	}

	public String getMetadata() {
		return metadata;
	}

	public boolean isIgnoreEntities() {
		return ignoreEntities;
	}

	public boolean isShowAir() {
		return showAir;
	}

	public boolean isShowBoundingBox() {
		return showBoundingBox;
	}

	public float getIntegrity() {
		return integrity;
	}

	public long getSeed() {
		return seed;
	}

	@Override
	public Packet<PacketListenerPlayIn> getNMS() {
		final PacketPlayInStruct packet = new PacketPlayInStruct();
		Field.set(packet, "a", PacketUtils.toBlockPosition(blockLocation));
		Field.set(packet, "b", updateType.getNMS());
		Field.set(packet, "c", PacketUtils.toBlockPropertyStructureMode(mode));
		Field.set(packet, "d", name);
		Field.set(packet, "e", PacketUtils.toBlockPosition(offset));
		Field.set(packet, "f", PacketUtils.toBlockPosition(size));
		Field.set(packet, "g", PacketUtils.toEnumBlockMirror(mirror));
		Field.set(packet, "h", PacketUtils.toEnumBlockRotation(rotation));
		Field.set(packet, "i", metadata);
		Field.set(packet, "j", ignoreEntities);
		Field.set(packet, "k", showAir);
		Field.set(packet, "l", showBoundingBox);
		Field.set(packet, "m", integrity);
		Field.set(packet, "n", seed);
		return packet;
	}

	@Override
	public int getPacketID() {
		return 0x2A;
	}

	@Override
	public String getProtocolURLString() {
		return "https://wiki.vg/Protocol#Update_Structure_Block";
	}

}
