package io.github.riesenpilz.nmsUtilities.packet.playOut;

import org.apache.commons.lang.Validate;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import io.github.riesenpilz.nmsUtilities.block.BlockAction;
import io.github.riesenpilz.nmsUtilities.nbt.NBTTag;
import io.github.riesenpilz.nmsUtilities.packet.HasBlockPosition;
import io.github.riesenpilz.nmsUtilities.packet.PacketUtils;
import io.github.riesenpilz.nmsUtilities.reflections.Field;
import net.minecraft.server.v1_16_R3.BlockPosition;
import net.minecraft.server.v1_16_R3.NBTTagCompound;
import net.minecraft.server.v1_16_R3.Packet;
import net.minecraft.server.v1_16_R3.PacketListenerPlayOut;
import net.minecraft.server.v1_16_R3.PacketPlayOutTileEntityData;

/**
 * https://wiki.vg/Protocol#Block_Entity_Data
 * <p>
 * Sets the block entity associated with the block at the given location.
 * <p>
 * Packet ID: 0x09<br>
 * State: Play<br>
 * Bound To: Client
 * 
 * @author Martin
 *
 */
public class PacketPlayOutBlockEntityDataEvent extends PacketPlayOutEvent implements HasBlockPosition {

	private Location blockLocation;
	private BlockAction action;
	private NBTTag nbtTag;

	public PacketPlayOutBlockEntityDataEvent(Player injectedPlayer, PacketPlayOutTileEntityData packet) {
		super(injectedPlayer);
		
		Validate.notNull(packet);
		
		blockLocation = PacketUtils.toLocation(Field.get(packet, "a", BlockPosition.class), injectedPlayer.getWorld());
		action = BlockAction.getById(Field.get(packet.getClass(), "b", int.class));
		nbtTag = NBTTag.getNBTTagOf(Field.get(packet.getClass(), "c", NBTTagCompound.class));
	}

	public PacketPlayOutBlockEntityDataEvent(Player injectedPlayer, Location blockLocation, BlockAction action,
			NBTTag nbtTag) {
		super(injectedPlayer);
		
		Validate.notNull(blockLocation);
		Validate.notNull(action);
		
		this.blockLocation = blockLocation;
		this.action = action;
		this.nbtTag = nbtTag;
	}

	@Override
	public Location getBlockLocation() {
		return blockLocation;
	}

	public BlockAction getAction() {
		return action;
	}

	public NBTTag getNbtTag() {
		return nbtTag;
	}

	@Override
	public Packet<PacketListenerPlayOut> getNMS() {
		return new PacketPlayOutTileEntityData(PacketUtils.toBlockPosition(blockLocation), getPacketID(),
				nbtTag.getNMS());
	}

	@Override
	public int getPacketID() {
		return 0x09;
	}

	@Override
	public String getProtocolURLString() {
		return "https://wiki.vg/Protocol#Block_Entity_Data";
	}

	
}
