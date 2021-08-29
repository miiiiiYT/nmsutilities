package io.github.riesenpilz.nms.packet.playOut;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;

import com.mojang.authlib.GameProfile;

import io.github.riesenpilz.nms.reflections.Field;
import net.minecraft.server.v1_16_R3.EnumGamemode;
import net.minecraft.server.v1_16_R3.IChatBaseComponent;
import net.minecraft.server.v1_16_R3.Packet;
import net.minecraft.server.v1_16_R3.PacketListenerPlayOut;
import net.minecraft.server.v1_16_R3.PacketPlayOutPlayerInfo;

/**
 * https://wiki.vg/Protocol#Player_Info
 * <p>
 * Sent by the server to update the user list ("tab" in the client).
 * <p>
 * The Property field looks as in the response of Mojang API#UUID -> Profile +
 * Skin/Cape (https://wiki.vg/Mojang_API#UUID_-.3E_Profile_.2B_Skin.2FCape),
 * except of course using the protocol format instead of JSON. That is, each
 * player will usually have one property with Name “textures” and Value being a
 * base64-encoded JSON string as documented at Mojang API#UUID -> Profile +
 * Skin/Cape. An empty properties array is also acceptable, and will cause
 * clients to display the player with one of the two default skins depending on
 * UUID.
 * <p>
 * Ping values correspond with icons in the following way:
 * <ul>
 * <li>A ping that negative (i.e. not known to the server yet) will result in
 * the no connection icon.</li>
 * <li>A ping under 150 milliseconds will result in 5 bars</li>
 * <li>A ping under 300 milliseconds will result in 4 bars</li>
 * <li>A ping under 600 milliseconds will result in 3 bars</li>
 * <li>A ping under 1000 milliseconds (1 second) will result in 2 bars</li>
 * <li>A ping greater than or equal to 1 second will result in 1 bar.</li>
 * </ul>
 * Packet ID: 0x36<br>
 * State: Play<br>
 * Bound To: Client
 * 
 * @author Martin
 *
 */
public class PacketPlayOutPlayerInfoEvent extends PacketPlayOutEvent {

	private Action action;
	private List<PlayerInfoData> playerInfoDatas = new ArrayList<>();

	@SuppressWarnings("unchecked")
	public PacketPlayOutPlayerInfoEvent(Player injectedPlayer, PacketPlayOutPlayerInfo packet) {
		super(injectedPlayer);
		action = Action.getAction(Field.get(packet, "a", PacketPlayOutPlayerInfo.EnumPlayerInfoAction.class));
		final List<PacketPlayOutPlayerInfo.PlayerInfoData> nms = Field.get(packet, "b", List.class);
		for (PacketPlayOutPlayerInfo.PlayerInfoData playerInfoData : nms)
			playerInfoDatas.add(new PlayerInfoData(playerInfoData));
	}

	public PacketPlayOutPlayerInfoEvent(Player injectedPlayer, Action action, List<PlayerInfoData> playerInfoDatas) {
		super(injectedPlayer);
		this.action = action;
		this.playerInfoDatas = playerInfoDatas;
	}

	public Action getAction() {
		return action;
	}

	public List<PlayerInfoData> getPlayerInfoDatas() {
		return playerInfoDatas;
	}

	@Override
	public Packet<PacketListenerPlayOut> getNMS() {
		final PacketPlayOutPlayerInfo packet = new PacketPlayOutPlayerInfo(action.getNMS());
		@SuppressWarnings("unchecked")
		final List<PacketPlayOutPlayerInfo.PlayerInfoData> nms = Field.get(packet, "b", List.class);
		nms.clear();
		for (PlayerInfoData playerInfoData : playerInfoDatas)
			nms.add(playerInfoData.getNMS(packet));
		return packet;
	}

	@Override
	public int getPacketID() {
		return 0x36;
	}

	@Override
	public String getProtocolURLString() {
		return "https://wiki.vg/Protocol#Player_Info";
	}

	public static class PlayerInfoData {

		private int ping;
		private GameMode gameMode;
		private GameProfile profile;
		private IChatBaseComponent displayName;

		public PlayerInfoData(GameProfile profile, int ping, GameMode gameMode, IChatBaseComponent displayName) {
			this.profile = profile;
			this.ping = ping;
			this.gameMode = gameMode;
			this.displayName = displayName;
		}

		@SuppressWarnings("deprecation")
		public PlayerInfoData(PacketPlayOutPlayerInfo.PlayerInfoData nms) {
			ping = nms.b();
			gameMode = GameMode.getByValue(nms.c().getId());
			profile = nms.a();
			displayName = nms.d();
		}

		public int getPing() {
			return ping;
		}

		public void setPing(int ping) {
			this.ping = ping;
		}

		public GameMode getGameMode() {
			return gameMode;
		}

		public void setGameMode(GameMode gameMode) {
			this.gameMode = gameMode;
		}

		public GameProfile getProfile() {
			return profile;
		}

		public void setProfile(GameProfile profile) {
			this.profile = profile;
		}

		public IChatBaseComponent getDisplayName() {
			return displayName;
		}

		public void setDisplayName(IChatBaseComponent displayName) {
			this.displayName = displayName;
		}

		@SuppressWarnings("deprecation")
		public PacketPlayOutPlayerInfo.PlayerInfoData getNMS(PacketPlayOutPlayerInfo packet) {
			return packet.new PlayerInfoData(profile, ping, EnumGamemode.getById(gameMode.getValue()), displayName);
		}

	}

	public enum Action {
		ADD_PLAYER(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER),
		UPDATE_GAME_MODE(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.UPDATE_GAME_MODE),
		UPDATE_LATENCY(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.UPDATE_LATENCY),
		UPDATE_DISPLAY_NAME(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.UPDATE_DISPLAY_NAME),
		REMOVE_PLAYER(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.REMOVE_PLAYER);

		private PacketPlayOutPlayerInfo.EnumPlayerInfoAction nms;

		private Action(PacketPlayOutPlayerInfo.EnumPlayerInfoAction nms) {
			this.nms = nms;
		}

		public PacketPlayOutPlayerInfo.EnumPlayerInfoAction getNMS() {
			return nms;
		}

		public static Action getAction(PacketPlayOutPlayerInfo.EnumPlayerInfoAction nms) {
			for (Action action : values())
				if (action.getNMS().equals(nms))
					return action;
			return null;
		}
	}
}
