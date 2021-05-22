package io.github.riesenpilz.nms.packet.playIn;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import io.github.riesenpilz.nms.reflections.Field;
import net.minecraft.server.v1_16_R3.BlockPosition;
import net.minecraft.server.v1_16_R3.BlockPropertyStructureMode;
import net.minecraft.server.v1_16_R3.EnumBlockMirror;
import net.minecraft.server.v1_16_R3.EnumBlockRotation;
import net.minecraft.server.v1_16_R3.Packet;
import net.minecraft.server.v1_16_R3.PacketListenerPlayIn;
import net.minecraft.server.v1_16_R3.PacketPlayInStruct;

/**
 * https://wiki.vg/Protocol#Update_Structure_Block<br>
 * <br>
 * Used when Shift+F3+I is pressed while looking at a block.<br>
 * <br>
 * Packet ID: 0x2A<br>
 * State: Play<br>
 * Bound To: Server
 * 
 * @author Martin
 *
 */
public class PacketPlayInUpdateStructureBlockEvent extends PacketPlayInEvent {

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
		blockLocation = new Location(injectedPlayer.getWorld(), packet.b().getX(), packet.b().getY(),
				packet.b().getZ());
		updateType = UpdateType.getUpdateType(packet.c());
		mode = Mode.getMode(packet.d());
		name = packet.e();
		offset = new Location(injectedPlayer.getWorld(), packet.f().getX(), packet.f().getY(), packet.f().getZ());
		size = new Location(injectedPlayer.getWorld(), packet.g().getX(), packet.g().getY(), packet.g().getZ());
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
		new Field(PacketPlayInStruct.class, "a").set(packet,
				new BlockPosition(blockLocation.getX(), blockLocation.getY(), blockLocation.getZ()));
		new Field(PacketPlayInStruct.class, "b").set(packet, updateType.getNMS());
		new Field(PacketPlayInStruct.class, "c").set(packet, mode.getNMS());
		new Field(PacketPlayInStruct.class, "d").set(packet, name);
		new Field(PacketPlayInStruct.class, "e").set(packet,
				new BlockPosition(offset.getX(), offset.getY(), offset.getZ()));
		new Field(PacketPlayInStruct.class, "f").set(packet, new BlockPosition(size.getX(), size.getY(), size.getZ()));
		new Field(PacketPlayInStruct.class, "g").set(packet, mirror.getNMS());
		new Field(PacketPlayInStruct.class, "h").set(packet, rotation.getNMS());
		new Field(PacketPlayInStruct.class, "i").set(packet, metadata);
		new Field(PacketPlayInStruct.class, "j").set(packet, ignoreEntities);
		new Field(PacketPlayInStruct.class, "k").set(packet, showAir);
		new Field(PacketPlayInStruct.class, "l").set(packet, showBoundingBox);
		new Field(PacketPlayInStruct.class, "m").set(packet, integrity);
		new Field(PacketPlayInStruct.class, "n").set(packet, seed);
		return packet;
	}

	public static enum UpdateType {

		UPDATE_DATA(net.minecraft.server.v1_16_R3.TileEntityStructure.UpdateType.UPDATE_DATA),
		SAVE_AREA(net.minecraft.server.v1_16_R3.TileEntityStructure.UpdateType.SAVE_AREA),
		LOAD_AREA(net.minecraft.server.v1_16_R3.TileEntityStructure.UpdateType.LOAD_AREA),
		SCAN_AREA(net.minecraft.server.v1_16_R3.TileEntityStructure.UpdateType.SCAN_AREA);

		private net.minecraft.server.v1_16_R3.TileEntityStructure.UpdateType nms;

		private UpdateType(net.minecraft.server.v1_16_R3.TileEntityStructure.UpdateType nms) {
			this.nms = nms;
		}

		public net.minecraft.server.v1_16_R3.TileEntityStructure.UpdateType getNMS() {
			return nms;
		}

		public static UpdateType getUpdateType(net.minecraft.server.v1_16_R3.TileEntityStructure.UpdateType nms) {
			for (UpdateType updateType : UpdateType.values())
				if (updateType.getNMS().equals(nms))
					return updateType;
			return null;
		}
	}

	public static enum Mode {
		SAVE(BlockPropertyStructureMode.SAVE), LOAD(BlockPropertyStructureMode.LOAD),
		CORNER(BlockPropertyStructureMode.CORNER), DATA(BlockPropertyStructureMode.DATA);

		private BlockPropertyStructureMode nms;

		private Mode(BlockPropertyStructureMode nms) {
			this.nms = nms;
		}

		public BlockPropertyStructureMode getNMS() {
			return nms;
		}

		public static Mode getMode(BlockPropertyStructureMode nms) {
			for (Mode mode : Mode.values())
				if (mode.getNMS().equals(nms))
					return mode;
			return null;
		}
	}

	public static enum Mirror {
		NONE(EnumBlockMirror.NONE), LEFT_RIGHT(EnumBlockMirror.LEFT_RIGHT), FRONT_BACK(EnumBlockMirror.FRONT_BACK);

		private EnumBlockMirror nms;

		private Mirror(EnumBlockMirror nms) {
			this.nms = nms;
		}

		public EnumBlockMirror getNMS() {
			return nms;
		}

		public static Mirror getMirror(EnumBlockMirror nms) {
			for (Mirror mirror : Mirror.values())
				if (mirror.getNMS().equals(nms))
					return mirror;
			return null;
		}
	}

	public static enum Rotation {
		NONE(EnumBlockRotation.NONE), CLOCKWISE_90(EnumBlockRotation.CLOCKWISE_90),
		CLOCKWISE_180(EnumBlockRotation.CLOCKWISE_180), COUNTERCLOCKWISE_90(EnumBlockRotation.COUNTERCLOCKWISE_90);

		private EnumBlockRotation nms;

		private Rotation(EnumBlockRotation nms) {
			this.nms = nms;
		}

		public EnumBlockRotation getNMS() {
			return nms;
		}

		public static Rotation getRotation(EnumBlockRotation nms) {
			for (Rotation rotation : Rotation.values())
				if (rotation.getNMS().equals(nms))
					return rotation;
			return null;
		}
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
