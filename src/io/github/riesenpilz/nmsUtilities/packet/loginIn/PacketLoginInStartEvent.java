package io.github.riesenpilz.nmsUtilities.packet.loginIn;

import org.bukkit.entity.Player;

import com.mojang.authlib.GameProfile;

import net.minecraft.server.v1_16_R3.Packet;
import net.minecraft.server.v1_16_R3.PacketLoginInListener;
import net.minecraft.server.v1_16_R3.PacketLoginInStart;

/**
 * https://wiki.vg/Protocol#Login_Start
 * <p>
 * Packet ID: 0x00<br>
 * State: Login<br>
 * Bound To: Server
 * 
 * @author Martin
 *
 */
public class PacketLoginInStartEvent extends PacketLoginInEvent {

	private GameProfile profile;

	public PacketLoginInStartEvent(Player injectedPlayer, GameProfile profile) {
		super(injectedPlayer);
		this.profile = profile;
	}

	public PacketLoginInStartEvent(Player injectedPlayer, PacketLoginInStart packet) {
		super(injectedPlayer);
		profile = packet.b();
	}

	public GameProfile getProfile() {
		return profile;
	}

	@Override
	public Packet<PacketLoginInListener> getNMS() {
		return new PacketLoginInStart(profile);
	}

	@Override
	public int getPacketID() {
		return 0x00;
	}

	@Override
	public String getProtocolURLString() {
		return "https://wiki.vg/Protocol#Login_Start";
	}

}
