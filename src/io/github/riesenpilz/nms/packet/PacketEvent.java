package io.github.riesenpilz.nms.packet;

import java.net.MalformedURLException;
import java.net.URL;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import net.minecraft.server.v1_16_R3.Packet;

public abstract class PacketEvent extends Event {
	
	private boolean canceled = false;
	private final Player injectedPlayer;
	private final PacketType packetType;

	public PacketEvent(Player injectedPlayer, PacketType packetType) {
		super(true);
		this.injectedPlayer = injectedPlayer;
		this.packetType = packetType;
	}

	private static HandlerList handlers = new HandlerList();

	@Override
	public HandlerList getHandlers() {
		return handlers;
	}

	public static HandlerList getHandlerList() {
		return handlers;
	}

	public Player getInjectedPlayer() {
		return injectedPlayer;
	}

	public PacketType getPacketType() {
		return packetType;
	}

	public abstract int getPacketID();

	
	public abstract String getProtocolURLString();
	
	public URL getProtocolURL() {
		try {
			return new URL(getProtocolURLString());
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public String getPacketIDBinary() {
		String s = "0x" + getPacketID() / 16;
		if (getPacketID() % 16 < 10)
			return s + getPacketID() % 16;
		switch (getPacketID() % 16) {
		case 10:
			return s + "A";
		case 11:
			return s + "B";
		case 12:
			return s + "C";
		case 13:
			return s + "D";
		case 14:
			return s + "E";
		case 15:
			return s + "F";
		default:
			return null;
		}
	}

	public String getState() {
		return packetType.getState();
	}

	public String getBoundTo() {
		return packetType.getBoundTo();
	}

	public abstract Packet<?> getNMS();

	public boolean isCanceled() {
		return canceled;
	}

	public void setCanceled(boolean canceled) {
		this.canceled = canceled;
	}

	public static enum PacketType {
		PLAY_IN("play", "server"), PLAY_OUT("play", "client"), HANDSHAKING_IN("handshaking", "server"),
		STATUS_IN("status", "server"), STATUS_OUT("status", "client"), LOGIN_IN("login", "server"), LOGIN_OUT("login", "client");

		private final String state;
		private final String boundTo;

		PacketType(String state, String boundTo) {
			this.state = state;
			this.boundTo = boundTo;
		}

		public String getState() {
			return state;
		}

		public String getBoundTo() {
			return boundTo;
		}
	}
}
