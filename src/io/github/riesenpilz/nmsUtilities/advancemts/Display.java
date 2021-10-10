package io.github.riesenpilz.nmsUtilities.advancemts;

import javax.annotation.Nullable;

import org.apache.commons.lang.Validate;
import org.bukkit.NamespacedKey;

import io.github.riesenpilz.nmsUtilities.inventory.ItemStack;
import io.github.riesenpilz.nmsUtilities.packet.PacketUtils;
import io.github.riesenpilz.nmsUtilities.reflections.Field;
import net.minecraft.server.v1_16_R3.AdvancementDisplay;
import net.minecraft.server.v1_16_R3.IChatBaseComponent;
import net.minecraft.server.v1_16_R3.MinecraftKey;

/**
 * Represents a {@link AdvancementDisplay}. Only used by packets.
 * 
 * @see PacketPlayOutAdvancementsEvent
 *
 */
public class Display {
	private IChatBaseComponent title;
	private IChatBaseComponent description;
	private ItemStack icon;

	@Nullable
	private NamespacedKey background;
	private AdvancementFrameType frame;
	private boolean showToast;
	private boolean announceToChat;
	private boolean hidden;
	private float x;
	private float y;

	public Display(IChatBaseComponent title, IChatBaseComponent description, ItemStack icon,
			@Nullable NamespacedKey background, AdvancementFrameType frame, boolean showToast, boolean announceToChat,
			boolean hidden, float x, float y) {

		Validate.notNull(title);
		Validate.notNull(description);
		Validate.notNull(icon);
		Validate.notNull(frame);

		this.title = title;
		this.description = description;
		this.icon = icon;
		this.background = background;
		this.frame = frame;
		this.showToast = showToast;
		this.announceToChat = announceToChat;
		this.hidden = hidden;
		this.x = x;
		this.y = y;
	}

	protected Display(AdvancementDisplay nms) {
		Validate.notNull(nms);

		this.title = nms.a();
		this.description = nms.b();
		this.icon = ItemStack.getItemStackOf(Field.get(nms, "c", net.minecraft.server.v1_16_R3.ItemStack.class));
		final MinecraftKey nmsBackGground = Field.get(nms, "d", MinecraftKey.class);
		this.background = nmsBackGground == null ? null : PacketUtils.toNamespacedKey(nmsBackGground);
		this.frame = AdvancementFrameType.getAdvancementFrameTypeOf(nms.e());
		this.showToast = Field.get(nms, "f", boolean.class);
		this.announceToChat = nms.i();
		this.hidden = nms.j();
		this.x = Field.get(nms, "i", float.class);
		this.y = Field.get(nms, "j", float.class);
	}

	public IChatBaseComponent getTitle() {
		return title;
	}

	public void setTitle(IChatBaseComponent title) {
		Validate.notNull(title);
		this.title = title;
	}

	public IChatBaseComponent getDescription() {
		return description;
	}

	public void setDescription(IChatBaseComponent description) {
		Validate.notNull(description);
		this.description = description;
	}

	public ItemStack getIcon() {
		return icon;
	}

	public void setIcon(ItemStack icon) {
		Validate.notNull(icon);
		this.icon = icon;
	}

	@Nullable
	public NamespacedKey getBackground() {
		return background;
	}

	public void setBackground(@Nullable NamespacedKey background) {
		this.background = background;
	}

	public AdvancementFrameType getFrame() {
		return frame;
	}

	public void setFrame(AdvancementFrameType frame) {
		Validate.notNull(frame);
		this.frame = frame;
	}

	public boolean isShowToast() {
		return showToast;
	}

	public void setShowToast(boolean showToast) {
		this.showToast = showToast;
	}

	public boolean isAnnounceToChat() {
		return announceToChat;
	}

	public void setAnnounceToChat(boolean announceToChat) {
		this.announceToChat = announceToChat;
	}

	public boolean isHidden() {
		return hidden;
	}

	public void setHidden(boolean hidden) {
		this.hidden = hidden;
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public static Display getDisplayOf(AdvancementDisplay nms) {
		return new Display(nms);
	}

	public AdvancementDisplay getNMS() {
		final AdvancementDisplay nms = new AdvancementDisplay(icon.getNMS(), title, description,
				background == null ? null : PacketUtils.toMinecraftKey(background), frame.getNMS(), showToast,
				announceToChat, hidden);
		nms.a(x, y);
		return nms;
	}

}