package io.github.riesenpilz.nmsUtilities.packet.playOut;

import org.apache.commons.lang.Validate;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.RenderType;

import io.github.riesenpilz.nmsUtilities.packet.HasText;
import io.github.riesenpilz.nmsUtilities.reflections.Field;
import io.github.riesenpilz.nmsUtilities.scoreboard.ScoreboardObjectiveMode;
import net.minecraft.server.v1_16_R3.IChatBaseComponent;
import net.minecraft.server.v1_16_R3.IScoreboardCriteria.EnumScoreboardHealthDisplay;
import net.minecraft.server.v1_16_R3.Packet;
import net.minecraft.server.v1_16_R3.PacketListenerPlayOut;
import net.minecraft.server.v1_16_R3.PacketPlayOutScoreboardObjective;

/**
 * https://wiki.vg/Protocol#Scoreboard_Objective
 * <p>
 * This is sent to the client when it should create a new scoreboard objective
 * or remove one.
 * <p>
 * Packet ID: 0x53<br>
 * State: Play<br>
 * Bound To: Client
 * 
 * @author Martin
 *
 */
public class PacketPlayOutScoreboardObjectiveEvent extends PacketPlayOutEvent implements HasText {

	/**
	 * A unique name for the objective.
	 */
	private String name;
	private IChatBaseComponent displayName;
	private RenderType type;
	private ScoreboardObjectiveMode mode;

	public PacketPlayOutScoreboardObjectiveEvent(Player injectedPlayer, PacketPlayOutScoreboardObjective packet) {
		super(injectedPlayer);

		Validate.notNull(packet);

		name = Field.get(packet, "a", String.class);
		displayName = Field.get(packet, "b", IChatBaseComponent.class);
		switch (Field.get(packet, "c", EnumScoreboardHealthDisplay.class)) {
		case HEARTS:
			type = RenderType.HEARTS;
			break;
		case INTEGER:
			type = RenderType.INTEGER;
			break;
		default:
			type = null;
		}
		mode = ScoreboardObjectiveMode.getById(Field.get(packet, "d", int.class));
	}

	public PacketPlayOutScoreboardObjectiveEvent(Player injectedPlayer, String name, IChatBaseComponent displayName,
			RenderType type, ScoreboardObjectiveMode mode) {
		super(injectedPlayer);

		Validate.notNull(name);
		Validate.notNull(displayName);
		Validate.notNull(type);
		Validate.notNull(mode);

		this.name = name;
		this.displayName = displayName;
		this.type = type;
		this.mode = mode;
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

	public RenderType getType() {
		return type;
	}

	public ScoreboardObjectiveMode getMode() {
		return mode;
	}

	@Override
	public Packet<PacketListenerPlayOut> getNMS() {
		final PacketPlayOutScoreboardObjective packet = new PacketPlayOutScoreboardObjective();
		Field.set(packet, "a", name);
		Field.set(packet, "b", displayName);
		EnumScoreboardHealthDisplay nms;
		switch (type) {
		case HEARTS:
			nms = EnumScoreboardHealthDisplay.HEARTS;
			break;
		case INTEGER:
			nms = EnumScoreboardHealthDisplay.INTEGER;
			break;
		default:
			nms = null;
		}
		Field.set(packet, "c", nms);
		Field.set(packet, "d", (int) mode.getId());
		return packet;
	}

	@Override
	public int getPacketID() {
		return 0x53;
	}

	@Override
	public String getProtocolURLString() {
		return "https://wiki.vg/Protocol#Scoreboard_Objective";
	}

}
