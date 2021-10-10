package io.github.riesenpilz.nmsUtilities.packet.playOut;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang.Validate;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import io.github.riesenpilz.nmsUtilities.packet.HasText;
import io.github.riesenpilz.nmsUtilities.packet.PacketUtils;
import io.github.riesenpilz.nmsUtilities.reflections.Field;
import io.github.riesenpilz.nmsUtilities.scoreboard.CollisionRule;
import io.github.riesenpilz.nmsUtilities.scoreboard.FriendlyTag;
import io.github.riesenpilz.nmsUtilities.scoreboard.ScoreboardTeamMode;
import io.github.riesenpilz.nmsUtilities.scoreboard.NameTagVisibility;
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
	private ScoreboardTeamMode mode;
	private Set<FriendlyTag> tags;

	@SuppressWarnings("unchecked")
	public PacketPlayOutScoreboardTeamEvent(Player injectedPlayer, PacketPlayOutScoreboardTeam packet) {
		super(injectedPlayer);

		Validate.notNull(packet);

		name = Field.get(packet, "a", String.class);
		mode = ScoreboardTeamMode.getById(Field.get(packet, "b", int.class));
		tags = new HashSet<>();
		entities = new ArrayList<>();
		if (mode.equals(ScoreboardTeamMode.CREATE) || mode.equals(ScoreboardTeamMode.UPDATE_INFO)) {
			displayName = Field.get(packet, "b", IChatBaseComponent.class);

			int nmsTags = Field.get(packet, "j", int.class);
			for (int position = 0; position < Byte.BYTES; position++)
				if ((nmsTags >> position & 1) == 1)
					tags.add(FriendlyTag.getByBitposition(position));

			visibility = NameTagVisibility.getByName(Field.get(packet, "e", String.class));
			rule = CollisionRule.getByName(Field.get(packet, "f", String.class));

			teamColor = PacketUtils.toColor(Field.get(packet, "g", EnumChatFormat.class));
			prefix = Field.get(packet, "b", IChatBaseComponent.class);
			suffix = Field.get(packet, "b", IChatBaseComponent.class);
		}

		if (mode.equals(ScoreboardTeamMode.CREATE) || mode.equals(ScoreboardTeamMode.ADD_ENTITIES) || mode.equals(ScoreboardTeamMode.REMOVE_ENTITIES)) {
			entities = Field.get(packet, "h", Collection.class);
		}

	}

	public PacketPlayOutScoreboardTeamEvent(Player injectedPlayer, String name, IChatBaseComponent displayName,
			IChatBaseComponent prefix, IChatBaseComponent suffix, NameTagVisibility visibility, CollisionRule rule,
			ChatColor teamColor, Collection<String> entities, ScoreboardTeamMode mode, Set<FriendlyTag> tags) {
		super(injectedPlayer);

		Validate.notNull(name);
		Validate.notNull(displayName);
		Validate.notNull(prefix);
		Validate.notNull(suffix);
		Validate.notNull(visibility);
		Validate.notNull(rule);
		Validate.notNull(teamColor);
		Validate.notNull(entities);
		Validate.notNull(mode);
		Validate.notNull(tags);

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

	public ScoreboardTeamMode getMode() {
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
		Field.set(packet, "g", PacketUtils.toEnumChatFormat(teamColor));
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

}
