package io.github.riesenpilz.nmsUtilities.packet.playOut;

import org.apache.commons.lang.Validate;
import org.bukkit.craftbukkit.libs.it.unimi.dsi.fastutil.objects.Object2IntMap;
import org.bukkit.entity.Player;

import io.github.riesenpilz.nmsUtilities.reflections.Field;
import net.minecraft.server.v1_16_R3.Packet;
import net.minecraft.server.v1_16_R3.PacketListenerPlayOut;
import net.minecraft.server.v1_16_R3.PacketPlayOutStatistic;
import net.minecraft.server.v1_16_R3.Statistic;

/**
 * https://wiki.vg/Protocol#Statistics<br>
 * <br>
 * Sent as a response to Client Status 0x04 (id 1). Will only send the changed
 * values if previously requested.<br>
 * <br>
 * Packet ID: 0x05<br>
 * State: Play<br>
 * Bound To: Client
 *
 * @author Martin
 *
 */
public class PacketPlayOutStatisticsEvent extends PacketPlayOutEvent {

	private Object2IntMap<Statistic<?>> statistics;

	@SuppressWarnings("unchecked")
	public PacketPlayOutStatisticsEvent(Player injectedPlayer, PacketPlayOutStatistic packet) {
		super(injectedPlayer);

		Validate.notNull(packet);

		statistics = (Object2IntMap<Statistic<?>>) new Field(packet.getClass(), "a").get(packet);
	}

	public PacketPlayOutStatisticsEvent(Player injectedPlayer, Object2IntMap<Statistic<?>> statistics) {
		super(injectedPlayer);

		Validate.notNull(statistics);

		this.statistics = statistics;
	}

	public Object2IntMap<Statistic<?>> getStatistics() {
		return statistics;
	}

	@Override
	public Packet<PacketListenerPlayOut> getNMS() {
		return new PacketPlayOutStatistic(statistics);
	}

	@Override
	public int getPacketID() {
		return 0x05;
	}

	@Override
	public String getProtocolURLString() {
		return "https://wiki.vg/Protocol#Statistics";
	}

}
