package io.github.riesenpilz.nms.packet.playIn;

import org.bukkit.entity.Player;

import io.github.riesenpilz.nms.entity.player.ChatVisibilitySetting;
import io.github.riesenpilz.nms.entity.player.MainHandSetting;
import io.github.riesenpilz.nms.reflections.Field;
import net.minecraft.server.v1_16_R3.Packet;
import net.minecraft.server.v1_16_R3.PacketListenerPlayIn;
import net.minecraft.server.v1_16_R3.PacketPlayInSettings;

/**
 * https://wiki.vg/Protocol#Client_Settings
 * <p>
 * Sent when the player connects, or when settings are changed.
 * <p>
 * Packet ID: 0x05<br>
 * State: Play<br>
 * Bound To: Server
 *
 * @author Martin
 *
 */
public class PacketPlayInSettingsEvent extends PacketPlayInEvent {

	/**
	 * e.g. en_GB. (16 chars max)
	 */
	public String locale;

	/**
	 * Client-side render distance, in chunks.
	 */
	private int viewDistance;

	/**
	 * See processing chat for more information.
	 */
	private ChatVisibilitySetting chatVisibility;

	/**
	 * �Colors� multiplayer setting.
	 */
	private boolean chatColors;

	// Displayed Skin Parts
	private boolean capeEnabled;
	private boolean jacketEnabled;
	private boolean leftSleeveEnabled;
	private boolean rightSleeveEnabled;
	private boolean leftPantsLegEnabled;
	private boolean rightPantsLegEnabled;
	private boolean hatEnabled;

	private MainHandSetting mainHand;

	public PacketPlayInSettingsEvent(Player injectedPlayer, PacketPlayInSettings packet) {
		super(injectedPlayer);

		locale = packet.locale;
		viewDistance = packet.viewDistance;
		chatVisibility = ChatVisibilitySetting.getChatVisibility(packet.d());
		chatColors = packet.e();

		final int i = packet.f();
		capeEnabled = (i & 1) == 1;
		jacketEnabled = (i & 2) == 2;
		leftSleeveEnabled = (i & 4) == 4;
		rightSleeveEnabled = (i & 8) == 8;
		leftPantsLegEnabled = (i & 16) == 16;
		rightPantsLegEnabled = (i & 32) == 32;
		capeEnabled = (i & 64) == 64;

		mainHand = MainHandSetting.getMainHand(packet.getMainHand());
	}

	public PacketPlayInSettingsEvent(Player injectedPlayer, String locale, int viewDistance,
			ChatVisibilitySetting chatVisibility, boolean chatColors, boolean capeEnabled, boolean jacketEnabled,
			boolean leftSleeveEnabled, boolean rightSleeveEnabled, boolean leftPantsLegEnabled,
			boolean rightPantsLegEnabled, boolean hatEnabled, MainHandSetting mainHand) {
		super(injectedPlayer);

		this.locale = locale;
		this.viewDistance = viewDistance;
		this.chatVisibility = chatVisibility;
		this.chatColors = chatColors;
		this.capeEnabled = capeEnabled;
		this.jacketEnabled = jacketEnabled;
		this.leftSleeveEnabled = leftSleeveEnabled;
		this.rightSleeveEnabled = rightSleeveEnabled;
		this.leftPantsLegEnabled = leftPantsLegEnabled;
		this.rightPantsLegEnabled = rightPantsLegEnabled;
		this.hatEnabled = hatEnabled;
	}

	public MainHandSetting getMainHand() {
		return mainHand;
	}

	public int getViewDistance() {
		return viewDistance;
	}

	public String getLocale() {
		return locale;
	}

	public ChatVisibilitySetting getChatVisibility() {
		return chatVisibility;
	}

	public boolean isChatColors() {
		return chatColors;
	}

	public boolean isCapeEnabled() {
		return capeEnabled;
	}

	public boolean isJacketEnabled() {
		return jacketEnabled;
	}

	public boolean isLeftSleeveEnabled() {
		return leftSleeveEnabled;
	}

	public boolean isRightSleeveEnabled() {
		return rightSleeveEnabled;
	}

	public boolean isLeftPantsLegEnabled() {
		return leftPantsLegEnabled;
	}

	public boolean isRightPantsLegEnabled() {
		return rightPantsLegEnabled;
	}

	public boolean isHatEnabled() {
		return hatEnabled;
	}

	@Override
	public Packet<PacketListenerPlayIn> getNMS() {
		final PacketPlayInSettings packet = new PacketPlayInSettings();
		packet.viewDistance = viewDistance;
		packet.locale = locale;
		Field.set(packet, "c", chatVisibility.getNMS());
		Field.set(packet, "d", chatColors);
		int e = capeEnabled ? 1 : 0;
		e += jacketEnabled ? 2 : 0;
		e += leftSleeveEnabled ? 4 : 0;
		e += rightSleeveEnabled ? 8 : 0;
		e += leftPantsLegEnabled ? 16 : 0;
		e += rightPantsLegEnabled ? 32 : 0;
		e += hatEnabled ? 64 : 0;
		Field.set(packet, "e", e);
		Field.set(packet, "f", mainHand.getNMS());
		return packet;
	}

	@Override
	public int getPacketID() {
		return 0x05;
	}

	@Override
	public String getProtocolURLString() {
		return "https://wiki.vg/Protocol#Client_Settings";
	}

}
