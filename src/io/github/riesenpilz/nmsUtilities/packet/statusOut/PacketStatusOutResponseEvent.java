package io.github.riesenpilz.nmsUtilities.packet.statusOut;

import org.bukkit.craftbukkit.v1_16_R3.util.CraftChatMessage;
import org.bukkit.entity.Player;

import com.mojang.authlib.GameProfile;

import io.github.riesenpilz.nmsUtilities.reflections.Field;
import net.minecraft.server.v1_16_R3.Packet;
import net.minecraft.server.v1_16_R3.PacketStatusOutListener;
import net.minecraft.server.v1_16_R3.PacketStatusOutServerInfo;
import net.minecraft.server.v1_16_R3.ServerPing.ServerData;
import net.minecraft.server.v1_16_R3.ServerPing.ServerPingPlayerSample;

/**
 * https://wiki.vg/Protocol#Response
 * <p>
 * Packet ID: 0x00<br>
 * State: Status<br>
 * Bound To: Client
 * 
 * @author Martin
 *
 */
public class PacketStatusOutResponseEvent extends PacketStatusOutEvent {

	private ServerPing ping;

	public PacketStatusOutResponseEvent(Player injectedPlayer, PacketStatusOutServerInfo packet) {
		super(injectedPlayer);
		ping = new ServerPing(Field.get(packet, "a", net.minecraft.server.v1_16_R3.ServerPing.class));
	}

	public PacketStatusOutResponseEvent(Player injectedPlayer, ServerPing ping) {
		super(injectedPlayer);
		this.ping = ping;
	}

	public ServerPing getPing() {
		return ping;
	}

	@Override
	public Packet<PacketStatusOutListener> getNMS() {
		return new PacketStatusOutServerInfo(ping.getNMS());
	}

	@Override
	public int getPacketID() {
		return 0x00;
	}

	@Override
	public String getProtocolURLString() {
		return "https://wiki.vg/Protocol#Response";
	}

	public static class ServerPing {
		private final net.minecraft.server.v1_16_R3.ServerPing ping;

		public ServerPing(net.minecraft.server.v1_16_R3.ServerPing ping) {
			this.ping = ping;
		}

		public ServerPing() {
			ping = new net.minecraft.server.v1_16_R3.ServerPing();
			ping.setPlayerSample(new ServerPingPlayerSample(0, 0));
			ping.setServerInfo(new ServerData(null, 0));
		}

		public String getMOTD() {
			return CraftChatMessage.fromComponent(ping.a());
		}

		public void setMOTD(String motd) {
			ping.setMOTD(CraftChatMessage.fromStringOrNull(motd));
		}

		public void setFavicon(String icon) {
			ping.setFavicon(icon);
		}

		public String getFavicon() {
			return ping.d();
		}

		public void setProtocolVersion(int version) {
			ping.setServerInfo(new ServerData(getServerDataA(), version));
		}

		public int getProtocolVersion() {
			return ping.getServerData().getProtocolVersion();
		}

		public void setServerDataA(String a) {
			ping.setServerInfo(new ServerData(a, getProtocolVersion()));
		}

		public String getServerDataA() {
			return ping.getServerData().a();
		}

		public void setPlayerSampleA(int a) {
			final ServerPingPlayerSample playerSample = new ServerPingPlayerSample(a, getPlayerSampleB());
			playerSample.a(getGameProfiles());
			ping.setPlayerSample(playerSample);
		}

		public int getPlayerSampleA() {
			return ping.b().a();
		}

		public void setPlayerSampleB(int b) {
			final ServerPingPlayerSample playerSample = new ServerPingPlayerSample(getPlayerSampleA(), b);
			playerSample.a(getGameProfiles());
			ping.setPlayerSample(playerSample);
		}

		public int getPlayerSampleB() {
			return ping.b().b();
		}

		public void setGameProfiles(GameProfile[] profiles) {
			ping.b().a(profiles);
		}

		public GameProfile[] getGameProfiles() {
			return ping.b().c();
		}

		public net.minecraft.server.v1_16_R3.ServerPing getNMS() {
			return ping;
		}

	}
}
