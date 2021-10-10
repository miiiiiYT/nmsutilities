package io.github.riesenpilz.nmsUtilities.packet.playOut;

import org.apache.commons.lang.Validate;
import org.bukkit.entity.Player;

import io.github.riesenpilz.nmsUtilities.packet.HasText;
import io.github.riesenpilz.nmsUtilities.reflections.Field;
import net.minecraft.server.v1_16_R3.IChatBaseComponent;
import net.minecraft.server.v1_16_R3.Packet;
import net.minecraft.server.v1_16_R3.PacketListenerPlayOut;
import net.minecraft.server.v1_16_R3.PacketPlayOutTitle;
import net.minecraft.server.v1_16_R3.PacketPlayOutTitle.EnumTitleAction;

/**
 * https://wiki.vg/Protocol#Set_Title_SubTitle
 * <p>
 * Packet ID: 0x57/0x59/0x5A<br>
 * State: Play<br>
 * Bound To: Client
 * 
 * @author Martin
 *
 */
public class PacketPlayOutTitleEvent extends PacketPlayOutEvent implements HasText {

	private TitleMode mode;
	private IChatBaseComponent text;
	private int fadeIn;
	private int stay;
	private int fadeOut;

	public PacketPlayOutTitleEvent(Player injectedPlayer, PacketPlayOutTitle packet) {
		super(injectedPlayer);

		Validate.notNull(packet);

		mode = TitleMode.getMode(Field.get(packet, "a", EnumTitleAction.class));
		text = Field.get(packet, "b", IChatBaseComponent.class);
		fadeIn = Field.get(packet, "c", int.class);
		stay = Field.get(packet, "d", int.class);
		fadeOut = Field.get(packet, "e", int.class);
	}

	public PacketPlayOutTitleEvent(Player injectedPlayer, TitleMode mode, IChatBaseComponent text, int fadeIn, int stay,
			int fadeOut) {
		super(injectedPlayer);

		Validate.notNull(mode);
		Validate.notNull(text);

		this.mode = mode;
		this.text = text;
		this.fadeIn = fadeIn;
		this.stay = stay;
		this.fadeOut = fadeOut;
	}

	public TitleMode getMode() {
		return mode;
	}

	@Override
	public IChatBaseComponent getText() {
		return text;
	}

	public int getFadeIn() {
		return fadeIn;
	}

	public int getStay() {
		return stay;
	}

	public int getFadeOut() {
		return fadeOut;
	}

	@Override
	public Packet<PacketListenerPlayOut> getNMS() {
		return new PacketPlayOutTitle(mode.getNms(), text, stay, fadeOut, fadeIn);
	}

	@Override
	public int getPacketID() {
		return 0x57;
	}

	@Override
	public String getProtocolURLString() {
		return "https://wiki.vg/Protocol#Set_Title_SubTitle";
	}

	public enum TitleMode {
		TITLE(EnumTitleAction.TITLE), SUBTITLE(EnumTitleAction.SUBTITLE), ACTIONBAR(EnumTitleAction.ACTIONBAR),
		TIMES(EnumTitleAction.TIMES), CLEAR(EnumTitleAction.CLEAR), RESET(EnumTitleAction.TIMES);

		private final EnumTitleAction nms;

		private TitleMode(EnumTitleAction nms) {
			this.nms = nms;
		}

		public EnumTitleAction getNms() {
			return nms;
		}

		public static TitleMode getMode(EnumTitleAction nms) {
			Validate.notNull(nms);
			for (TitleMode mode : values())
				if (mode.getNms() == nms)
					return mode;
			throw new IllegalArgumentException();
		}
	}
}
