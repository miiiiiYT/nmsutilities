package io.github.riesenpilz.nmsUtilities.packet.handshakingIn;

import org.bukkit.entity.Player;

import io.github.riesenpilz.nmsUtilities.reflections.Field;
import net.minecraft.server.v1_16_R3.EnumProtocol;
import net.minecraft.server.v1_16_R3.Packet;
import net.minecraft.server.v1_16_R3.PacketHandshakingInListener;
import net.minecraft.server.v1_16_R3.PacketHandshakingInSetProtocol;

/**
 * https://wiki.vg/Protocol#Handshake
 * <p>
 * This causes the server to switch into the target state.
 * <p>
 * Packet ID: 0x00<br>
 * State: Handshaking<br>
 * Bound To: Server
 * 
 * @author Martin
 *
 */
public class PacketHandshakingInHandshakeEvent extends PacketHandshakingInEvent {

	/**
	 * See protocol version numbers (currently 754 in Minecraft 1.16.5).
	 */
	private int protocolVersion;

	/**
	 * Hostname or IP, e.g. localhost or 127.0.0.1, that was used to connect. The
	 * Notchian server does not use this information. Note that SRV records are a
	 * complete redirect, e.g. if _minecraft._tcp.example.com points to
	 * mc.example.org, users connecting to example.com will provide mc.example.org
	 * as server address in addition to connecting to it. (255 chars)
	 */
	public String serverAddress;

	/**
	 * Default is 25565. The Notchian server does not use this information.
	 */
	public int serverPort;
	private State nextState;

	public PacketHandshakingInHandshakeEvent(Player injectedPlayer, PacketHandshakingInSetProtocol packet) {
		super(injectedPlayer);
		protocolVersion = packet.c();
		serverAddress = packet.hostname;
		serverPort = packet.port;
		nextState = State.getState(packet.b());
	}

	public PacketHandshakingInHandshakeEvent(Player injectedPlayer, int protocolVersion, String serverAddress,
			int serverPort, State nextState) {
		super(injectedPlayer);
		this.protocolVersion = protocolVersion;
		this.serverAddress = serverAddress;
		this.serverPort = serverPort;
		this.nextState = nextState;
	}

	public int getProtocolVersion() {
		return protocolVersion;
	}

	public String getServerAddress() {
		return serverAddress;
	}

	public int getServerPort() {
		return serverPort;
	}

	public State getNextState() {
		return nextState;
	}

	@Override
	public Packet<PacketHandshakingInListener> getNMS() {
		final PacketHandshakingInSetProtocol packet = new PacketHandshakingInSetProtocol();
		Field.set(packet, "a", protocolVersion);
		packet.hostname = serverAddress;
		packet.port = serverPort;
		Field.set(packet, "d", nextState.getNMS());
		return packet;
	}

	public static enum State {
		HANDSHAKING(EnumProtocol.HANDSHAKING), PLAY(EnumProtocol.PLAY), STATUS(EnumProtocol.STATUS),
		LOGIN(EnumProtocol.LOGIN);

		private EnumProtocol nms;

		private State(EnumProtocol nms) {
			this.nms = nms;
		}

		public EnumProtocol getNMS() {
			return nms;
		}

		public static State getState(EnumProtocol nms) {
			for (State state : values())
				if (state.getNMS().equals(nms))
					return state;
			return null;
		}
	}

	@Override
	public int getPacketID() {
		return 0x00;
	}

	@Override
	public String getProtocolURLString() {
		return "https://wiki.vg/Protocol#Handshake";
	}
}
