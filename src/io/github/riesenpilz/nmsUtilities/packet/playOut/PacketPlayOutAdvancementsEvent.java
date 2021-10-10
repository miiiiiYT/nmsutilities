package io.github.riesenpilz.nmsUtilities.packet.playOut;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.lang.Validate;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;

import io.github.riesenpilz.nmsUtilities.advancemts.Advancement;
import io.github.riesenpilz.nmsUtilities.advancemts.AdvancementProgress;
import io.github.riesenpilz.nmsUtilities.packet.PacketUtils;
import io.github.riesenpilz.nmsUtilities.reflections.Field;
import net.minecraft.server.v1_16_R3.Advancement.SerializedAdvancement;
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
 */
public class PacketPlayOutAdvancementsEvent extends PacketPlayOutEvent {

	/**
	 * Whether to reset/clear the current advancements.
	 */
	private boolean reset;
	private Map<NamespacedKey, Advancement> advancements;

	/**
	 * The identifiers of the advancements that should be removed.
	 */
	private Set<NamespacedKey> identifiers;
	private Map<NamespacedKey, AdvancementProgress> progresses;

	@SuppressWarnings("unchecked")
	public PacketPlayOutAdvancementsEvent(Player injectedPlayer, PacketPlayOutAdvancements packet) {
		super(injectedPlayer);

		Validate.notNull(packet);

		reset = Field.get(packet, "a", boolean.class);

		advancements = new HashMap<>();
		identifiers = new HashSet<>();
		progresses = new HashMap<>();

		final Map<MinecraftKey, SerializedAdvancement> nmsAdvancements = Field.get(packet, "b", Map.class);
		for (Entry<MinecraftKey, SerializedAdvancement> entry : nmsAdvancements.entrySet())
			advancements.put(PacketUtils.toNamespacedKey(entry.getKey()),
					Advancement.getAdvancementOf(entry.getValue()));

		final Set<MinecraftKey> nmsIdentifiers = Field.get(packet, "c", Set.class);
		for (MinecraftKey minecraftKey : nmsIdentifiers)
			identifiers.add(PacketUtils.toNamespacedKey(minecraftKey));

		final Map<MinecraftKey, net.minecraft.server.v1_16_R3.AdvancementProgress> nmsProgresses = Field.get(packet,
				"d", Map.class);
		for (Entry<MinecraftKey, net.minecraft.server.v1_16_R3.AdvancementProgress> entry : nmsProgresses.entrySet())
			progresses.put(PacketUtils.toNamespacedKey(entry.getKey()),
					AdvancementProgress.getAdvancementProgress(entry.getValue()));
	}

	public PacketPlayOutAdvancementsEvent(Player injectedPlayer, boolean reset,
			Map<NamespacedKey, Advancement> advancements, Set<NamespacedKey> identifiers,
			Map<NamespacedKey, AdvancementProgress> progresses) {
		super(injectedPlayer);

		Validate.notNull(advancements);
		Validate.notNull(identifiers);
		Validate.notNull(progresses);

		this.reset = reset;
		this.advancements = advancements;
		this.identifiers = identifiers;
		this.progresses = progresses;
	}

	public boolean isReset() {
		return reset;
	}

	public Map<NamespacedKey, Advancement> getAdvancements() {
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
		final Map<MinecraftKey, net.minecraft.server.v1_16_R3.AdvancementProgress> nmsProgresses = new HashMap<>();

		for (Entry<NamespacedKey, Advancement> entry : advancements.entrySet())
			nmsAdvancements.put(PacketUtils.toMinecraftKey(entry.getKey()), entry.getValue().getNMS());

		for (NamespacedKey namespacedKey : identifiers)
			nmsIdentifiers.add(PacketUtils.toMinecraftKey(namespacedKey));

		for (Entry<NamespacedKey, AdvancementProgress> entry : progresses.entrySet())
			nmsProgresses.put(PacketUtils.toMinecraftKey(entry.getKey()), entry.getValue().getNMS());

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
