package io.github.riesenpilz.nmsUtilities.packet.playOut;

import java.util.UUID;

import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.entity.Player;

import io.github.riesenpilz.nmsUtilities.reflections.Field;
import net.minecraft.server.v1_16_R3.BossBattle;
import net.minecraft.server.v1_16_R3.IChatBaseComponent;
import net.minecraft.server.v1_16_R3.Packet;
import net.minecraft.server.v1_16_R3.PacketListenerPlayOut;
import net.minecraft.server.v1_16_R3.PacketPlayOutBoss;
import net.minecraft.server.v1_16_R3.PacketPlayOutBoss.Action;

/**
 * https://wiki.vg/Protocol#Boss_Bar<br>
 * <br>
 * Packet ID: 0x0C<br>
 * State: Play<br>
 * Bound To: Client
 *
 * @author Martin
 *
 */
public class PacketPlayOutBossBarEvent extends PacketPlayOutEvent {

	/**
	 * Unique ID for this bar.
	 */
	private UUID uuid;
	private BossBarAction action;

	/**
	 * Only used if {@link #action} is {@link BossBarAction#ADD} or
	 * {@link BossBarAction#UPDATE_TITLE}
	 */
	private IChatBaseComponent title;

	/**
	 * Only used if {@link #action} is {@link BossBarAction#ADD} or
	 * {@link BossBarAction#UPDATE_HEALTH}
	 * <p>
	 * From 0 to 1. Values greater than 1 do not crash a Notchian client, and start
	 * rendering part of a second health bar at around 1.5.
	 */
	private float health;

	/**
	 * Only used if {@link #action} is {@link BossBarAction#ADD} or
	 * {@link BossBarAction#UPDATE_STYLE}
	 */
	private BarColor color;

	/**
	 * Only used if {@link #action} is {@link BossBarAction#ADD} or
	 * {@link BossBarAction#UPDATE_STYLE}
	 */
	private BarStyle style;

	/**
	 * Only used if {@link #action} is {@link BossBarAction#ADD} or
	 * {@link BossBarAction#UPDATE_PROPERTIES}
	 * <p>
	 * Darkens the sky like during fighting a wither.
	 */
	private boolean darkenSky;

	/**
	 * Only used if {@link #action} is {@link BossBarAction#ADD} or
	 * {@link BossBarAction#UPDATE_PROPERTIES}
	 * <p>
	 * Tells the client to play the Ender Dragon boss music.
	 */
	private boolean playBossMusic;

	/**
	 * Only used if {@link #action} is {@link BossBarAction#ADD} or
	 * {@link BossBarAction#UPDATE_PROPERTIES}
	 * <p>
	 * Creates fog around the world.
	 */
	private boolean createFog;

	public PacketPlayOutBossBarEvent(Player injectedPlayer, PacketPlayOutBoss packet) {
		super(injectedPlayer);
		uuid = Field.get(packet, "a", UUID.class);
		action = BossBarAction.getBossBarAction(Field.get(packet, "b", Action.class));
		switch (action) {
		case ADD:
			title = Field.get(packet, "c", IChatBaseComponent.class);
			health = Field.get(packet, "d", float.class);
			color = BarColor.valueOf(Field.get(packet, "e", BossBattle.BarColor.class).name());
			style = BarStyle.valueOf(Field.get(packet, "f", BossBattle.BarStyle.class).name()
					.replace("NOTCHED", "SEGMENTED").replace("PROGRESS", "SOLID"));
			darkenSky = Field.get(packet, "g", boolean.class);
			playBossMusic = Field.get(packet, "h", boolean.class);
			createFog = Field.get(packet, "i", boolean.class);
		case REMOVE:
			break;
		case UPDATE_HEALTH:
			health = Field.get(packet, "d", float.class);
			break;
		case UPDATE_TITLE:
			title = Field.get(packet, "c", IChatBaseComponent.class);
			break;
		case UPDATE_STYLE:
			color = BarColor.valueOf(Field.get(packet, "e", BossBattle.BarColor.class).name());
			style = BarStyle.valueOf(Field.get(packet, "f", BossBattle.BarStyle.class).name());
			break;
		case UPDATE_PROPERTIES:
			darkenSky = Field.get(packet, "g", boolean.class);
			playBossMusic = Field.get(packet, "h", boolean.class);
			createFog = Field.get(packet, "i", boolean.class);
		}

	}

	public UUID getUuid() {
		return uuid;
	}

	public BossBarAction getAction() {
		return action;
	}

	public IChatBaseComponent getTitle() {
		return title;
	}

	public float getHealth() {
		return health;
	}

	public BarColor getColor() {
		return color;
	}

	public BarStyle getStyle() {
		return style;
	}

	public boolean isDarkenSky() {
		return darkenSky;
	}

	public boolean isPlayBossMusic() {
		return playBossMusic;
	}

	public boolean isCreateFog() {
		return createFog;
	}

	@Override
	public Packet<PacketListenerPlayOut> getNMS() {
		final PacketPlayOutBoss packet = new PacketPlayOutBoss();
		Field.set(packet, "a", uuid);
		Field.set(packet, "b", action.getNMS());
		switch (action) {
		case ADD:
			Field.set(packet, "c", title);
			Field.set(packet, "d", health);
			Field.set(packet, "e", BossBattle.BarColor.valueOf(color.name()));
			Field.set(packet, "f", BossBattle.BarStyle
					.valueOf(style.name().replace("SEGMENTED", "NOTCHED").replace("SOLID", "PROGRESS")));

			Field.set(packet, "g", darkenSky);
			Field.set(packet, "h", playBossMusic);
			Field.set(packet, "i", createFog);
		case REMOVE:
		default:
			break;
		case UPDATE_HEALTH:
			Field.set(packet, "d", health);
			break;
		case UPDATE_TITLE:
			Field.set(packet, "c", title);
			break;
		case UPDATE_STYLE:
			Field.set(packet, "e", BossBattle.BarColor.valueOf(color.name()));
			Field.set(packet, "f", BossBattle.BarStyle
					.valueOf(style.name().replace("SEGMENTED", "NOTCHED").replace("SOLID", "PROGRESS")));

			break;
		case UPDATE_PROPERTIES:
			Field.set(packet, "g", darkenSky);
			Field.set(packet, "h", playBossMusic);
			Field.set(packet, "i", createFog);
		}
		return packet;
	}

	@Override
	public int getPacketID() {
		return 0x0C;
	}

	@Override
	public String getProtocolURLString() {
		return "https://wiki.vg/Protocol#Boss_Bar";
	}

	public enum BossBarAction {
		ADD(Action.ADD), REMOVE(Action.REMOVE), UPDATE_HEALTH(Action.UPDATE_PCT), UPDATE_TITLE(Action.UPDATE_NAME),
		UPDATE_STYLE(Action.UPDATE_STYLE), UPDATE_PROPERTIES(Action.UPDATE_PROPERTIES);

		private Action nms;

		BossBarAction(Action action) {
			nms = action;
		}

		public Action getNMS() {
			return nms;
		}

		public static BossBarAction getBossBarAction(Action nms) {
			for (BossBarAction action : values())
				if (action.getNMS().equals(nms))
					return action;
			return null;
		}
	}
}
