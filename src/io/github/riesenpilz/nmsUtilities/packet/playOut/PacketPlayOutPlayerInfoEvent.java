package io.github.riesenpilz.nmsUtilities.packet.playOut;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.Validate;
import org.bukkit.entity.Player;

import io.github.riesenpilz.nmsUtilities.entity.livingEntity.player.PlayerInfoAction;
import io.github.riesenpilz.nmsUtilities.entity.livingEntity.player.PlayerInfoData;
import io.github.riesenpilz.nmsUtilities.reflections.Field;
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

	private PlayerInfoAction action;
	private List<PlayerInfoData> playerInfoDatas = new ArrayList<>();

	@SuppressWarnings("unchecked")
	public PacketPlayOutPlayerInfoEvent(Player injectedPlayer, PacketPlayOutPlayerInfo packet) {
		super(injectedPlayer);

		Validate.notNull(packet);

		action = PlayerInfoAction.getAction(Field.get(packet, "a", PacketPlayOutPlayerInfo.EnumPlayerInfoAction.class));
		final List<PacketPlayOutPlayerInfo.PlayerInfoData> nms = Field.get(packet, "b", List.class);
		for (PacketPlayOutPlayerInfo.PlayerInfoData playerInfoData : nms)
			playerInfoDatas.add(PlayerInfoData.getPlayerInfoData(playerInfoData));
	}

	public PacketPlayOutPlayerInfoEvent(Player injectedPlayer, PlayerInfoAction action,
			List<PlayerInfoData> playerInfoDatas) {
		super(injectedPlayer);

		Validate.notNull(action);
		Validate.notNull(playerInfoDatas);

		this.action = action;
		this.playerInfoDatas = playerInfoDatas;
	}

	public PlayerInfoAction getAction() {
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
			nms.add(playerInfoData.getNMS());
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

}
