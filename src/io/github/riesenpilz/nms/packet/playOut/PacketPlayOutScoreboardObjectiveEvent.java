package io.github.riesenpilz.nms.packet.playOut;

import org.bukkit.entity.Player;
import org.bukkit.scoreboard.RenderType;

import io.github.riesenpilz.nms.reflections.Field;
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
public class PacketPlayOutScoreboardObjectiveEvent extends PacketPlayOutEvent {

	/**
	 * A unique name for the objective.
	 */
	private String name;
	private IChatBaseComponent displayName;
	private RenderType type;
	private Mode mode;

	public PacketPlayOutScoreboardObjectiveEvent(Player injectedPlayer, PacketPlayOutScoreboardObjective packet) {
		super(injectedPlayer);
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
		mode = Mode.getById(Field.get(packet, "d", int.class));
	}

	public PacketPlayOutScoreboardObjectiveEvent(Player injectedPlayer, String name, IChatBaseComponent displayName,
			RenderType type, Mode mode) {
		super(injectedPlayer);
		this.name = name;
		this.displayName = displayName;
		this.type = type;
		this.mode = mode;
	}

	public String getName() {
		return name;
	}

	public IChatBaseComponent getDisplayName() {
		return displayName;
	}

	public RenderType getType() {
		return type;
	}

	public Mode getMode() {
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

	public enum Mode {
		CREATE((byte) 0), REMOVE((byte) 1), UPDATE_DISPLAY((byte) 2);

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

}
