package io.github.riesenpilz.nmsUtilities.entity.livingEntity.player;

import org.apache.commons.lang.Validate;
import org.bukkit.GameMode;

import com.mojang.authlib.GameProfile;

import io.github.riesenpilz.nmsUtilities.packet.PacketUtils;
import io.github.riesenpilz.nmsUtilities.packet.playOut.PacketPlayOutPlayerInfoEvent;
import net.minecraft.server.v1_16_R3.IChatBaseComponent;
import net.minecraft.server.v1_16_R3.PacketPlayOutPlayerInfo;

/**
 * Represents {@link PacketPlayOutPlayerInfo.PlayerInfoData}. Only used by
 * packets.
 * 
 * @see PacketPlayOutPlayerInfoEvent
 *
 */
public class PlayerInfoData {

	private int ping;
	private GameMode gameMode;
	private GameProfile profile;
	private IChatBaseComponent displayName;

	public PlayerInfoData(GameProfile profile, int ping, GameMode gameMode, IChatBaseComponent displayName) {

		Validate.notNull(profile);
		Validate.notNull(gameMode);
		Validate.notNull(displayName);

		this.profile = profile;
		this.ping = ping;
		this.gameMode = gameMode;
		this.displayName = displayName;
	}

	protected PlayerInfoData(PacketPlayOutPlayerInfo.PlayerInfoData nms) {

		Validate.notNull(nms);

		ping = nms.b();
		gameMode = PacketUtils.toGameMode(nms.c());
		profile = nms.a();
		displayName = nms.d();
	}

	public static PlayerInfoData getPlayerInfoData(PacketPlayOutPlayerInfo.PlayerInfoData nms) {
		return new PlayerInfoData(nms);
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
		Validate.notNull(gameMode);
		this.gameMode = gameMode;
	}

	public GameProfile getProfile() {
		return profile;
	}

	public void setProfile(GameProfile profile) {
		Validate.notNull(profile);
		this.profile = profile;
	}

	public IChatBaseComponent getDisplayName() {
		return displayName;
	}

	public void setDisplayName(IChatBaseComponent displayName) {
		Validate.notNull(displayName);
		this.displayName = displayName;
	}

	public PacketPlayOutPlayerInfo.PlayerInfoData getNMS() {
		return new PacketPlayOutPlayerInfo().new PlayerInfoData(profile, ping, PacketUtils.toEnumGamemode(gameMode), displayName);
	}

}