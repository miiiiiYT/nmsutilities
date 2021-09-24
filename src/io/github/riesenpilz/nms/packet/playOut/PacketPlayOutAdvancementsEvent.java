package io.github.riesenpilz.nms.packet.playOut;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;

import io.github.riesenpilz.nms.packet.PacketUtils;
import io.github.riesenpilz.nms.reflections.Field;
import net.minecraft.server.v1_16_R3.Advancement.SerializedAdvancement;
import net.minecraft.server.v1_16_R3.AdvancementProgress;
import net.minecraft.server.v1_16_R3.MinecraftKey;
import net.minecraft.server.v1_16_R3.Packet;
import net.minecraft.server.v1_16_R3.PacketListenerPlayOut;
import net.minecraft.server.v1_16_R3.PacketPlayOutAdvancements;

/**
 * https://wiki.vg/Protocol#Advancements
 * <p>
 * Packet ID: 0x62<br>
 * State: Play<br>
 * Bound To: Client
 * 
 * @author Martin
 *
 */
public class PacketPlayOutAdvancementsEvent extends PacketPlayOutEvent {

	/**
	 * Whether to reset/clear the current advancements.
	 */
	private boolean reset;
	private Map<NamespacedKey, SerializedAdvancement> advancements;

	/**
	 * The identifiers of the advancements that should be removed.
	 */
	private Set<NamespacedKey> identifiers;
	private Map<NamespacedKey, AdvancementProgress> progresses;

	@SuppressWarnings("unchecked")
	public PacketPlayOutAdvancementsEvent(Player injectedPlayer, PacketPlayOutAdvancements packet) {
		super(injectedPlayer);

		reset = Field.get(packet, "a", boolean.class);

		advancements = new HashMap<>();
		identifiers = new HashSet<>();

		final Map<MinecraftKey, SerializedAdvancement> nmsAdvancements = Field.get(packet, "b", Map.class);
		for (Entry<MinecraftKey, SerializedAdvancement> entry : nmsAdvancements.entrySet())
			advancements.put(PacketUtils.toNamespacedKey(entry.getKey()), entry.getValue());

		final Set<MinecraftKey> nmsIdentifiers = Field.get(packet, "c", Set.class);
		for (MinecraftKey minecraftKey : nmsIdentifiers)
			identifiers.add(PacketUtils.toNamespacedKey(minecraftKey));

		final Map<MinecraftKey, AdvancementProgress> nmsProgresses = Field.get(packet, "d", Map.class);
		for (Entry<MinecraftKey, AdvancementProgress> entry : nmsProgresses.entrySet())
			progresses.put(PacketUtils.toNamespacedKey(entry.getKey()), entry.getValue());
	}

	public PacketPlayOutAdvancementsEvent(Player injectedPlayer, boolean reset,
			Map<NamespacedKey, SerializedAdvancement> advancements, Set<NamespacedKey> identifiers,
			Map<NamespacedKey, AdvancementProgress> progresses) {
		super(injectedPlayer);
		this.reset = reset;
		this.advancements = advancements;
		this.identifiers = identifiers;
		this.progresses = progresses;
	}

	public boolean isReset() {
		return reset;
	}

	public Map<NamespacedKey, SerializedAdvancement> getAdvancements() {
		return advancements;
	}

	public Set<NamespacedKey> getIdentifiers() {
		return identifiers;
	}

	public Map<NamespacedKey, AdvancementProgress> getProgresses() {
		return progresses;
	}

	@Override
	public Packet<PacketListenerPlayOut> getNMS() {

		final Map<MinecraftKey, SerializedAdvancement> nmsAdvancements = new HashMap<>();
		final Set<MinecraftKey> nmsIdentifiers = new HashSet<>();
		final Map<MinecraftKey, AdvancementProgress> nmsProgresses = new HashMap<>();

		for (Entry<NamespacedKey, SerializedAdvancement> entry : advancements.entrySet())
			nmsAdvancements.put(PacketUtils.toMinecraftKey(entry.getKey()), entry.getValue());

		for (NamespacedKey namespacedKey : identifiers)
			nmsIdentifiers.add(PacketUtils.toMinecraftKey(namespacedKey));

		for (Entry<NamespacedKey, AdvancementProgress> entry : progresses.entrySet())
			nmsProgresses.put(PacketUtils.toMinecraftKey(entry.getKey()), entry.getValue());

		final PacketPlayOutAdvancements packet = new PacketPlayOutAdvancements();
		return packet;
	}

	@Override
	public int getPacketID() {
		return 0x62;
	}

	@Override
	public String getProtocolURLString() {
		return "https://wiki.vg/Protocol#Advancements";
	}
}
