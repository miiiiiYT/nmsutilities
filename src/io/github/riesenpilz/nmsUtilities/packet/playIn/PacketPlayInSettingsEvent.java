package io.github.riesenpilz.nmsUtilities.packet.playIn;

import org.apache.commons.lang.Validate;
import org.bukkit.entity.Player;

import io.github.riesenpilz.nmsUtilities.entity.livingEntity.player.ChatVisibilitySetting;
import io.github.riesenpilz.nmsUtilities.entity.livingEntity.player.MainHandSetting;
import io.github.riesenpilz.nmsUtilities.reflections.Field;
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
	 * “Colors” multiplayer setting.
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
		Validate.notNull(packet);

		locale = packet.locale;
		viewDistance = packet.viewDistance;
		chatVisibility = ChatVisibilitySetting.getChatVisibility(packet.d());
		chatColors = packet.e();

		final int skinSettings = packet.f();
		capeEnabled = (skinSettings & 1) == 1;
		jacketEnabled = (skinSettings & 2) == 2;
		leftSleeveEnabled = (skinSettings & 4) == 4;
		rightSleeveEnabled = (skinSettings & 8) == 8;
		leftPantsLegEnabled = (skinSettings & 16) == 16;
		rightPantsLegEnabled = (skinSettings & 32) == 32;
		capeEnabled = (skinSettings & 64) == 64;

		mainHand = MainHandSetting.getMainHand(packet.getMainHand());
	}

	public PacketPlayInSettingsEvent(Player injectedPlayer, String locale, int viewDistance,
			ChatVisibilitySetting chatVisibility, boolean chatColors, boolean capeEnabled, boolean jacketEnabled,
			boolean leftSleeveEnabled, boolean rightSleeveEnabled, boolean leftPantsLegEnabled,
			boolean rightPantsLegEnabled, boolean hatEnabled, MainHandSetting mainHand) {
		super(injectedPlayer);

		Validate.notNull(locale);
		Validate.notNull(chatVisibility);
		Validate.notNull(mainHand);

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
		int skinSettings = capeEnabled ? 1 : 0;
		skinSettings += jacketEnabled ? 2 : 0;
		skinSettings += leftSleeveEnabled ? 4 : 0;
		skinSettings += rightSleeveEnabled ? 8 : 0;
		skinSettings += leftPantsLegEnabled ? 16 : 0;
		skinSettings += rightPantsLegEnabled ? 32 : 0;
		skinSettings += hatEnabled ? 64 : 0;
		Field.set(packet, "e", skinSettings);
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
