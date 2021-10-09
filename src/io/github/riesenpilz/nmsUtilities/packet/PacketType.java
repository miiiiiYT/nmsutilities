package io.github.riesenpilz.nmsUtilities.packet;

public enum PacketType {
	HANDSHAKING_IN("handshaking", "server"), LOGIN_IN("login", "server"), LOGIN_OUT("login", "client"),
	PLAY_IN("play", "server"), PLAY_OUT("play", "client"), STATUS_IN("status", "server"),
	STATUS_OUT("status", "client");

	private final String boundTo;
	private final String state;

	PacketType(String state, String boundTo) {
		this.state = state;
		this.boundTo = boundTo;
	}

	public String getBoundTo() {
		return boundTo;
	}

	public String getState() {
		return state;
	}
}