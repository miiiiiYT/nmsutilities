package io.github.riesenpilz.nmsUtilities.packet.playOut;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import io.github.riesenpilz.nmsUtilities.packet.HasText;
import io.github.riesenpilz.nmsUtilities.reflections.Field;
import net.minecraft.server.v1_16_R3.EnumChatFormat;
import net.minecraft.server.v1_16_R3.IChatBaseComponent;
import net.minecraft.server.v1_16_R3.Packet;
import net.minecraft.server.v1_16_R3.PacketListenerPlayOut;
import net.minecraft.server.v1_16_R3.PacketPlayOutScoreboardTeam;

/**
 * https://wiki.vg/Protocol#Teams
 * <p>
 * Creates and updates teams.
 * <p>
 * Packet ID: 0x55<br>
 * State: Play<br>
 * Bound To: Client
 * 
 * @author Martin
 *
 */
public class PacketPlayOutScoreboardTeamEvent extends PacketPlayOutEvent implements HasText {

	/**
	 * A unique name for the team. (Shared with scoreboard).(16 chars max.)
	 */
	private String name;
	private IChatBaseComponent displayName;

	/**
	 * Displayed before the names of players that are part of this team.
	 */
	private IChatBaseComponent prefix;

	/**
	 * Displayed after the names of players that are part of this team.
	 */
	private IChatBaseComponent suffix;
	private NameTagVisibility visibility;
	private CollisionRule rule;
	private ChatColor teamColor;
	private Collection<String> entities;
	private Mode mode;
	private Set<FriendlyTag> tags;

	@SuppressWarnings("unchecked")
	public PacketPlayOutScoreboardTeamEvent(Player injectedPlayer, PacketPlayOutScoreboardTeam packet) {
		super(injectedPlayer);
		name = Field.get(packet, "a", String.class);
		mode = Mode.getById(Field.get(packet, "b", int.class));
		tags = new HashSet<>();
		entities = new ArrayList<>();
		if (mode.equals(Mode.CREATE) || mode.equals(Mode.UPDATE_INFO)) {
			displayName = Field.get(packet, "b", IChatBaseComponent.class);

			int nmsTags = Field.get(packet, "j", int.class);
			for (int position = 0; position < Byte.BYTES; position++)
				if ((nmsTags >> position & 1) == 1)
					tags.add(FriendlyTag.getByBitposition(position));

			visibility = NameTagVisibility.getByName(Field.get(packet, "e", String.class));
			rule = CollisionRule.getByName(Field.get(packet, "f", String.class));

			teamColor = ChatColor
					.getByChar(Field.get(Field.get(packet, "g", EnumChatFormat.class), "character", char.class));
			prefix = Field.get(packet, "b", IChatBaseComponent.class);
			suffix = Field.get(packet, "b", IChatBaseComponent.class);
		}

		if (mode.equals(Mode.CREATE) || mode.equals(Mode.ADD_ENTITIES) || mode.equals(Mode.REMOVE_ENTITIES)) {
			entities = Field.get(packet, "h", Collection.class);
		}

	}

	public PacketPlayOutScoreboardTeamEvent(Player injectedPlayer, String name, IChatBaseComponent displayName,
			IChatBaseComponent prefix, IChatBaseComponent suffix, NameTagVisibility visibility, CollisionRule rule,
			ChatColor teamColor, Collection<String> entities, Mode mode, Set<FriendlyTag> tags) {
		super(injectedPlayer);
		this.name = name;
		this.displayName = displayName;
		this.prefix = prefix;
		this.suffix = suffix;
		this.visibility = visibility;
		this.rule = rule;
		this.teamColor = teamColor;
		this.entities = entities;
		this.mode = mode;
		this.tags = tags;
	}

	public String getName() {
		return name;
	}

	/**
	 * Display name
	 */
	public IChatBaseComponent getText() {
		return displayName;
	}

	public IChatBaseComponent getPrefix() {
		return prefix;
	}

	public IChatBaseComponent getSuffix() {
		return suffix;
	}

	public NameTagVisibility getVisibility() {
		return visibility;
	}

	public CollisionRule getRule() {
		return rule;
	}

	public ChatColor getTeamColor() {
		return teamColor;
	}

	public Collection<String> getEntities() {
		return entities;
	}

	public Mode getMode() {
		return mode;
	}

	public Set<FriendlyTag> getTags() {
		return tags;
	}

	@Override
	public Packet<PacketListenerPlayOut> getNMS() {
		final PacketPlayOutScoreboardTeam packet = new PacketPlayOutScoreboardTeam();
		Field.set(packet, "a", name);
		Field.set(packet, "b", displayName);
		Field.set(packet, "c", prefix);
		Field.set(packet, "d", suffix);
		Field.set(packet, "e", visibility.getName());
		Field.set(packet, "f", rule.getName());
		Field.set(packet, "g", EnumChatFormat.a(Field.get(teamColor, "intCode", int.class)));
		Field.set(packet, "h", entities);
		Field.set(packet, "i", (int) mode.getId());
		int nmsTags = 0;
		for (FriendlyTag tag : tags)
			nmsTags += tag.getBit();
		Field.set(packet, "j", nmsTags);
		return packet;
	}

	@Override
	public int getPacketID() {
		return 0x55;
	}

	@Override
	public String getProtocolURLString() {
		return "https://wiki.vg/Protocol#Teams";
	}

	public enum Mode {
		CREATE((byte) 0), REMOVE((byte) 1), UPDATE_INFO((byte) 2), ADD_ENTITIES((byte) 3), REMOVE_ENTITIES((byte) 4);

		private byte id;

		private Mode(byte id) {
			this.id = id;
		}

		public byte getId() {
			return id;
		}

		public static Mode getById(int id) {
			for (Mode mode : values())
				if (mode.getId() == id)
					return mode;
			return null;
		}
	}

	public enum FriendlyTag {
		FRIENDLY_FIRE(0x01), SEE_INVISIBLE_PLAYERS(0x02);

		private int bit;

		private FriendlyTag(int bit) {
			this.bit = bit;
		}

		public int getBit() {
			return bit;
		}

		public static FriendlyTag getByBitposition(int position) {
			final double pow = Math.pow(2, position);
			for (FriendlyTag tag : values())
				if (tag.getBit() == pow)
					return tag;
			return null;
		}
	}

	public enum NameTagVisibility {
		ALWAYS("always"), HIDE_FOR_OTHER_TEAMS("hideForOtherTeams"), HIDE_FOR_OWN_TEAMS("hideForOwnTeam"),
		NEVER("never");

		private String name;

		private NameTagVisibility(String name) {
			this.name = name;
		}

		public String getName() {
			return name;
		}

		public static NameTagVisibility getByName(String name) {
			for (NameTagVisibility rule : values())
				if (rule.getName().equals(name))
					return rule;
			return null;
		}
	}

	public enum CollisionRule {
		ALWAYS("always"), PUSH_OTHER_TEAMS("pushOtherTeams"), PUSH_OWN_TEAMS("pushOwnTeam"), NEVER("never");

		private String name;

		private CollisionRule(String name) {
			this.name = name;
		}

		public String getName() {
			return name;
		}

		public static CollisionRule getByName(String name) {
			for (CollisionRule rule : values())
				if (rule.getName().equals(name))
					return rule;
			return null;
		}
	}
}
