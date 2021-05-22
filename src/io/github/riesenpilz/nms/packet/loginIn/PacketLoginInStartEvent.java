package io.github.riesenpilz.nms.packet.loginIn;

import org.bukkit.entity.Player;

import com.mojang.authlib.GameProfile;

import net.minecraft.server.v1_16_R3.Packet;
import net.minecraft.server.v1_16_R3.PacketLoginInListener;
import net.minecraft.server.v1_16_R3.PacketLoginInStart;

/**
 * https://wiki.vg/Protocol#Login_Start<br>
 * <br>
 * Packet ID: 0x00<br>
 * State: Login<br>
 * Bound To: Server
 * 
 * @author Martin
 *
 */
public class PacketLoginInStartEvent extends PacketLoginInEvent {

	public static final String PROTOCOL_URL = "https://wiki.vg/Protocol#Login_Start";
	public static final int PACKET_ID = 0;

	private GameProfile profile;

	public PacketLoginInStartEvent(Player injectedPlayer, GameProfile profile) {
		super(injectedPlayer, PACKET_ID, PROTOCOL_URL);
		this.profile = profile;
	}

	public PacketLoginInStartEvent(Player injectedPlayer, PacketLoginInStart packet) {
		super(injectedPlayer, PACKET_ID, PROTOCOL_URL);
		profile = packet.b();
	}

	public GameProfile getProfile() {
		return profile;
	}

	@Override
	public Packet<PacketLoginInListener> getNMS() {
		return new PacketLoginInStart(profile);
	}

}
