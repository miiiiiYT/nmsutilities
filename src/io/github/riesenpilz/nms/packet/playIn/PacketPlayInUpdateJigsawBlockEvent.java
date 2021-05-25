package io.github.riesenpilz.nms.packet.playIn;

import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;

import io.github.riesenpilz.nms.packet.PacketUtils;
import io.github.riesenpilz.nms.reflections.Field;
import net.minecraft.server.v1_16_R3.MinecraftKey;
import net.minecraft.server.v1_16_R3.Packet;
import net.minecraft.server.v1_16_R3.PacketListenerPlayIn;
import net.minecraft.server.v1_16_R3.PacketPlayInSetJigsaw;

/**
 * https://wiki.vg/Protocol#Update_Jigsaw_Block
 * <p>
 * Sent when Done is pressed on the Jigsaw Block interface.
 * <p>
 * Packet ID: 0x29<br>
 * State: Play<br>
 * Bound To: Server
 *
 * @author Martin
 *
 */
public class PacketPlayInUpdateJigsawBlockEvent extends PacketPlayInEvent {

	private Location blockLocation;
	private NamespacedKey name;
	private NamespacedKey target;
	private NamespacedKey pool;

	/**
	 * "Turns into" on the GUI, final_state in NBT.
	 */
	private String finalState;

	private JointType jointType;

	@SuppressWarnings("deprecation")
	public PacketPlayInUpdateJigsawBlockEvent(Player injectedPlayer, PacketPlayInSetJigsaw packet) {
		super(injectedPlayer);
		blockLocation = PacketUtils.toLocation(packet.b(), injectedPlayer.getWorld());
		name = new NamespacedKey(packet.c().getNamespace(), packet.c().getKey());
		target = new NamespacedKey(packet.d().getNamespace(), packet.d().getKey());
		pool = new NamespacedKey(packet.e().getNamespace(), packet.e().getKey());
		finalState = packet.f();
		jointType = JointType.getJointType(packet.g());
	}

	public PacketPlayInUpdateJigsawBlockEvent(Player injectedPlayer, Location blockLocation, NamespacedKey name,
			NamespacedKey target, NamespacedKey pool, String finalState, JointType type) {
		super(injectedPlayer);
		this.blockLocation = blockLocation;
		this.name = name;
		this.target = target;
		this.pool = pool;
		this.finalState = finalState;
		jointType = type;
	}

	public Location getBlockLocation() {
		return blockLocation;
	}

	public NamespacedKey getName() {
		return name;
	}

	public NamespacedKey getTarget() {
		return target;
	}

	public NamespacedKey getPool() {
		return pool;
	}

	public String getFinalState() {
		return finalState;
	}

	public JointType getJointType() {
		return jointType;
	}

	@Override
	public Packet<PacketListenerPlayIn> getNMS() {
		final PacketPlayInSetJigsaw packet = new PacketPlayInSetJigsaw();
		Field.set(packet, "a", PacketUtils.toBlockPosition(blockLocation));
		Field.set(packet, "b", new MinecraftKey(name.getNamespace(), name.getKey()));
		Field.set(packet, "c", new MinecraftKey(target.getNamespace(), target.getKey()));
		Field.set(packet, "d", new MinecraftKey(pool.getNamespace(), pool.getKey()));
		Field.set(packet, "e", finalState);
		Field.set(packet, "f", jointType.getNMS());
		return packet;
	}

	@Override
	public int getPacketID() {
		return 0x29;
	}

	@Override
	public String getProtocolURLString() {
		return "https://wiki.vg/Protocol#Update_Jigsaw_Block";
	}

	public enum JointType {

		ROTATABLE(net.minecraft.server.v1_16_R3.TileEntityJigsaw.JointType.ROLLABLE),
		ALIGNED(net.minecraft.server.v1_16_R3.TileEntityJigsaw.JointType.ALIGNED);

		private net.minecraft.server.v1_16_R3.TileEntityJigsaw.JointType nms;

		JointType(net.minecraft.server.v1_16_R3.TileEntityJigsaw.JointType nms) {
			this.nms = nms;
		}

		public net.minecraft.server.v1_16_R3.TileEntityJigsaw.JointType getNMS() {
			return nms;
		}

		public static JointType getJointType(net.minecraft.server.v1_16_R3.TileEntityJigsaw.JointType nms) {
			for (JointType type : values())
				if (type.getNMS().equals(nms))
					return type;
			return null;
		}

	}
}
