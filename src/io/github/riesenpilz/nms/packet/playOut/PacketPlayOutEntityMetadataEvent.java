package io.github.riesenpilz.nms.packet.playOut;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;

import io.github.riesenpilz.nms.entity.DataWatcherItem;
import io.github.riesenpilz.nms.reflections.Field;
import net.minecraft.server.v1_16_R3.DataWatcher;
import net.minecraft.server.v1_16_R3.Packet;
import net.minecraft.server.v1_16_R3.PacketListenerPlayOut;
import net.minecraft.server.v1_16_R3.PacketPlayOutEntityMetadata;

/**
 * https://wiki.vg/Protocol#Entity_Metadata
 * <p>
 * Updates one or more metadata properties for an existing entity. Any
 * properties not included in the Metadata field are left
 * unchanged.(https://wiki.vg/Entity_metadata#Entity_Metadata_Format)
 * <p>
 * Packet ID: 0x4D<br>
 * State: Play<br>
 * Bound To: Client
 * 
 * @author Martin
 *
 */
public class PacketPlayOutEntityMetadataEvent extends PacketPlayOutEvent {

	private int entityId;
	private List<DataWatcherItem<?>> metadata;

	@SuppressWarnings("unchecked")
	public PacketPlayOutEntityMetadataEvent(Player injectedPlayer, PacketPlayOutEntityMetadata packet) {
		super(injectedPlayer);

		entityId = Field.get(packet, "a", int.class);
		final List<DataWatcher.Item<?>> nmsItems = Field.get(packet, "a", List.class);
		metadata = new ArrayList<>();
		for (DataWatcher.Item<?> nmsItem : nmsItems)
			metadata.add(DataWatcherItem.getDataWatcherItemFrom(nmsItem));
	}

	public PacketPlayOutEntityMetadataEvent(Player injectedPlayer, int entityId, List<DataWatcherItem<?>> metadata) {
		super(injectedPlayer);
		this.entityId = entityId;
		this.metadata = metadata;
	}

	public int getEntityId() {
		return entityId;
	}

	public List<DataWatcherItem<?>> getMetadata() {
		return metadata;
	}

	@Override
	public Packet<PacketListenerPlayOut> getNMS() {
		final PacketPlayOutEntityMetadata packet = new PacketPlayOutEntityMetadata();
		Field.set(packet, "a", entityId);
		Field.set(packet, "b", metadata);
		return packet;
	}

	@Override
	public int getPacketID() {
		return 0x4D;
	}

	@Override
	public String getProtocolURLString() {
		return "https://wiki.vg/Protocol#Entity_Metadata";
	}

	}
