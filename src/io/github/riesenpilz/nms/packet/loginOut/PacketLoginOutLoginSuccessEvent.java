package io.github.riesenpilz.nms.packet.loginOut;

import org.bukkit.entity.Player;

import com.mojang.authlib.GameProfile;

import io.github.riesenpilz.nms.reflections.Field;
import net.minecraft.server.v1_16_R3.Packet;
import net.minecraft.server.v1_16_R3.PacketLoginOutListener;
import net.minecraft.server.v1_16_R3.PacketLoginOutSuccess;

/**
 * https://wiki.vg/Protocol#Login_Success<br>
 * <br>
 * This packet switches the connection state to play.<br>
 * <br>
 * <i>Note: The (notchian) server might take a bit to fully transition to the
 * Play state, so it's recommended to wait before sending Play packets, either
 * by setting a timeout, or waiting for Play packets from the server (usually
 * Player Info).<br>
 * The notchian client doesn't send any (non-keep alive) packets until the next
 * tick/time update packet.</i> <br>
 * <br>
 * Packet ID: 0x02<br>
 * State: Login<br>
 * Bound To: Client
 * 
 * @author Martin
 *
 */
public class PacketLoginOutLoginSuccessEvent extends PacketLoginOutEvent {

	private GameProfile profile;

	public PacketLoginOutLoginSuccessEvent(Player injectedPlayer, GameProfile profile) {
		super(injectedPlayer);
		this.profile = profile;
	}

	public PacketLoginOutLoginSuccessEvent(Player injectedPlayer, PacketLoginOutSuccess packet) {
		super(injectedPlayer);
		profile = (GameProfile) new Field(PacketLoginOutSuccess.class, "a").get(packet);
	}

	public GameProfile getProfile() {
		return profile;
	}

	@Override
	public Packet<PacketLoginOutListener> getNMS() {
		return new PacketLoginOutSuccess(profile);
	}

	@Override
	public int getPacketID() {
		return 2;
	}

	@Override
	public String getProtocolURLString() {
		return "https://wiki.vg/Protocol#Login_Success";
	}

}
