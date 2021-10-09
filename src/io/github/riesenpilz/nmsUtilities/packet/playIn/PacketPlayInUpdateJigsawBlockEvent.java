package io.github.riesenpilz.nmsUtilities.packet.playIn;

import org.apache.commons.lang.Validate;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;

import io.github.riesenpilz.nmsUtilities.block.JigsawJointType;
import io.github.riesenpilz.nmsUtilities.packet.HasBlockPosition;
import io.github.riesenpilz.nmsUtilities.packet.PacketUtils;
import io.github.riesenpilz.nmsUtilities.reflections.Field;
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
public class PacketPlayInUpdateJigsawBlockEvent extends PacketPlayInEvent implements HasBlockPosition {

	private Location blockLocation;
	private NamespacedKey name;
	private NamespacedKey target;
	private NamespacedKey pool;

	/**
	 * "Turns into" on the GUI, final_state in NBT.
	 */
	private String finalState;

	private JigsawJointType jointType;

	public PacketPlayInUpdateJigsawBlockEvent(Player injectedPlayer, PacketPlayInSetJigsaw packet) {
		super(injectedPlayer);

		Validate.notNull(packet);

		blockLocation = PacketUtils.toLocation(packet.b(), injectedPlayer.getWorld());
		name = PacketUtils.toNamespacedKey(packet.c());
		target = PacketUtils.toNamespacedKey(packet.d());
		pool = PacketUtils.toNamespacedKey(packet.e());
		finalState = packet.f();
		jointType = JigsawJointType.getJointType(packet.g());
	}

	public PacketPlayInUpdateJigsawBlockEvent(Player injectedPlayer, Location blockLocation, NamespacedKey name,
			NamespacedKey target, NamespacedKey pool, String finalState, JigsawJointType type) {
		super(injectedPlayer);

		Validate.notNull(blockLocation);
		Validate.notNull(name);
		Validate.notNull(target);
		Validate.notNull(pool);
		Validate.notNull(finalState);
		Validate.notNull(type);

		this.blockLocation = blockLocation;
		this.name = name;
		this.target = target;
		this.pool = pool;
		this.finalState = finalState;
		jointType = type;
	}

	@Override
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

	public JigsawJointType getJointType() {
		return jointType;
	}

	@Override
	public Packet<PacketListenerPlayIn> getNMS() {
		final PacketPlayInSetJigsaw packet = new PacketPlayInSetJigsaw();
		Field.set(packet, "a", PacketUtils.toBlockPosition(blockLocation));
		Field.set(packet, "b", PacketUtils.toMinecraftKey(name));
		Field.set(packet, "c", PacketUtils.toMinecraftKey(target));
		Field.set(packet, "d", PacketUtils.toMinecraftKey(pool));
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

	
}
