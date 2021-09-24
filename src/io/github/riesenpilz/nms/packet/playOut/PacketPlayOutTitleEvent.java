package io.github.riesenpilz.nms.packet.playOut;

import org.bukkit.entity.Player;

import io.github.riesenpilz.nms.reflections.Field;
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
public class PacketPlayOutTitleEvent extends PacketPlayOutEvent {

	private Mode mode;
	private IChatBaseComponent text;
	private int fadeIn;
	private int stay;
	private int fadeOut;

	public PacketPlayOutTitleEvent(Player injectedPlayer, PacketPlayOutTitle packet) {
		super(injectedPlayer);

		mode = Mode.getMode(Field.get(packet, "a", EnumTitleAction.class));
		text = Field.get(packet, "b", IChatBaseComponent.class);
		fadeIn = Field.get(packet, "c", int.class);
		stay = Field.get(packet, "d", int.class);
		fadeOut = Field.get(packet, "e", int.class);
	}

	public PacketPlayOutTitleEvent(Player injectedPlayer, Mode mode, IChatBaseComponent text, int fadeIn, int stay,
			int fadeOut) {
		super(injectedPlayer);
		this.mode = mode;
		this.text = text;
		this.fadeIn = fadeIn;
		this.stay = stay;
		this.fadeOut = fadeOut;
	}

	public Mode getMode() {
		return mode;
	}

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

	public enum Mode {
		TITLE(EnumTitleAction.TITLE), SUBTITLE(EnumTitleAction.SUBTITLE), ACTIONBAR(EnumTitleAction.ACTIONBAR),
		TIMES(EnumTitleAction.TIMES), CLEAR(EnumTitleAction.CLEAR), RESET(EnumTitleAction.TIMES);

		private EnumTitleAction nms;

		private Mode(EnumTitleAction nms) {
			this.nms = nms;
		}

		public EnumTitleAction getNms() {
			return nms;
		}

		public static Mode getMode(EnumTitleAction nms) {
			for (Mode mode : values())
				if (mode.getNms() == nms)
					return mode;
			return null;
		}
	}
}
