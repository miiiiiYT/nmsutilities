package io.github.riesenpilz.nms.packet.playIn;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import io.github.riesenpilz.nms.packet.HasBlockPosition;
import io.github.riesenpilz.nms.packet.PacketUtils;
import io.github.riesenpilz.nms.reflections.Field;
import net.minecraft.server.v1_16_R3.BlockPropertyStructureMode;
import net.minecraft.server.v1_16_R3.EnumBlockMirror;
import net.minecraft.server.v1_16_R3.EnumBlockRotation;
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
	private UpdateType updateType;
	private Mode mode;
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
		blockLocation = PacketUtils.toLocation(packet.b(), injectedPlayer.getWorld());
		updateType = UpdateType.getUpdateType(packet.c());
		mode = Mode.getMode(packet.d());
		name = packet.e();
		offset = PacketUtils.toLocation(packet.f(), injectedPlayer.getWorld());
		size = PacketUtils.toLocation(packet.g(), injectedPlayer.getWorld());
		mirror = Mirror.getMirror(packet.h());
		rotation = Rotation.getRotation(packet.i());
		metadata = packet.j();
		ignoreEntities = packet.k();
		showAir = packet.l();
		showBoundingBox = packet.m();
		integrity = packet.n();
		seed = packet.o();
	}

	public PacketPlayInUpdateStructureBlockEvent(Player injectedPlayer, Location blockLocation, UpdateType updateType,
			Mode mode, String name, Location offset, Location size, Mirror mirror, Rotation rotation, String metadata,
			boolean ignoreEntities, boolean showAir, boolean showBoundingBox, float integrity, long seed) {
		super(injectedPlayer);
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

	public UpdateType getUpdateType() {
		return updateType;
	}

	public Mode getMode() {
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
		Field.set(packet, "c", mode.getNMS());
		Field.set(packet, "d", name);
		Field.set(packet, "e", PacketUtils.toBlockPosition(offset));
		Field.set(packet, "f", PacketUtils.toBlockPosition(size));
		Field.set(packet, "g", mirror.getNMS());
		Field.set(packet, "h", rotation.getNMS());
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

	public enum UpdateType {

		UPDATE_DATA(net.minecraft.server.v1_16_R3.TileEntityStructure.UpdateType.UPDATE_DATA),
		SAVE_AREA(net.minecraft.server.v1_16_R3.TileEntityStructure.UpdateType.SAVE_AREA),
		LOAD_AREA(net.minecraft.server.v1_16_R3.TileEntityStructure.UpdateType.LOAD_AREA),
		SCAN_AREA(net.minecraft.server.v1_16_R3.TileEntityStructure.UpdateType.SCAN_AREA);

		private net.minecraft.server.v1_16_R3.TileEntityStructure.UpdateType nms;

		UpdateType(net.minecraft.server.v1_16_R3.TileEntityStructure.UpdateType nms) {
			this.nms = nms;
		}

		public net.minecraft.server.v1_16_R3.TileEntityStructure.UpdateType getNMS() {
			return nms;
		}

		public static UpdateType getUpdateType(net.minecraft.server.v1_16_R3.TileEntityStructure.UpdateType nms) {
			for (UpdateType type : values())
				if (type.getNMS().equals(nms))
					return type;
			return null;
		}
	}

	public enum Mode {
		SAVE(BlockPropertyStructureMode.SAVE), LOAD(BlockPropertyStructureMode.LOAD),
		CORNER(BlockPropertyStructureMode.CORNER), DATA(BlockPropertyStructureMode.DATA);

		private BlockPropertyStructureMode nms;

		Mode(BlockPropertyStructureMode nms) {
			this.nms = nms;
		}

		public BlockPropertyStructureMode getNMS() {
			return nms;
		}

		public static Mode getMode(BlockPropertyStructureMode nms) {
			for (Mode mode : values())
				if (mode.getNMS().equals(nms))
					return mode;
			return null;
		}
	}

	public enum Mirror {
		NONE(EnumBlockMirror.NONE), LEFT_RIGHT(EnumBlockMirror.LEFT_RIGHT), FRONT_BACK(EnumBlockMirror.FRONT_BACK);

		private EnumBlockMirror nms;

		Mirror(EnumBlockMirror nms) {
			this.nms = nms;
		}

		public EnumBlockMirror getNMS() {
			return nms;
		}

		public static Mirror getMirror(EnumBlockMirror nms) {
			for (Mirror mirror : values())
				if (mirror.getNMS().equals(nms))
					return mirror;
			return null;
		}
	}

	public enum Rotation {
		NONE(EnumBlockRotation.NONE), CLOCKWISE_90(EnumBlockRotation.CLOCKWISE_90),
		CLOCKWISE_180(EnumBlockRotation.CLOCKWISE_180), COUNTERCLOCKWISE_90(EnumBlockRotation.COUNTERCLOCKWISE_90);

		private EnumBlockRotation nms;

		Rotation(EnumBlockRotation nms) {
			this.nms = nms;
		}

		public EnumBlockRotation getNMS() {
			return nms;
		}

		public static Rotation getRotation(EnumBlockRotation nms) {
			for (Rotation rotation : values())
				if (rotation.getNMS().equals(nms))
					return rotation;
			return null;
		}
	}

}
