package io.github.riesenpilz.nmsUtilities.packet;

import java.net.MalformedURLException;
import java.net.URL;

import org.apache.commons.lang.Validate;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import net.minecraft.server.v1_16_R3.Packet;

public abstract class PacketEvent extends Event {

	private static HandlerList handlers = new HandlerList();

	public static HandlerList getHandlerList() {
		return handlers;
	}

	private boolean canceled = false;

	private final Player injectedPlayer;

	private final PacketType packetType;

	public PacketEvent(Player injectedPlayer, PacketType packetType) {
		super(true);

		Validate.notNull(injectedPlayer);
		Validate.notNull(packetType);
		this.injectedPlayer = injectedPlayer;
		this.packetType = packetType;
	}

	public String getBoundTo() {
		return packetType.getBoundTo();
	}

	@Override
	public HandlerList getHandlers() {
		return handlers;
	}

	public Player getBukkit() {
		return injectedPlayer;
	}

	public io.github.riesenpilz.nmsUtilities.entity.livingEntity.player.Player getPlayer() {
		return io.github.riesenpilz.nmsUtilities.entity.livingEntity.player.Player.getPlayerOf(injectedPlayer);
	}

	public abstract Packet<?> getNMS();

	public abstract int getPacketID();

	public String getPacketIDBinary() {
		return Integer.toHexString(getPacketID());
	}

	public PacketType getPacketType() {
		return packetType;
	}

	public URL getProtocolURL() {
		try {
			return new URL(getProtocolURLString());
		} catch (final MalformedURLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public abstract String getProtocolURLString();

	public String getState() {
		return packetType.getState();
	}

	public boolean isCanceled() {
		return canceled;
	}

	public void setCanceled(boolean canceled) {
		this.canceled = canceled;
	}

}
