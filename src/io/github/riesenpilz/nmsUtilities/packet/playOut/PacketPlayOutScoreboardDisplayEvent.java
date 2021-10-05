package io.github.riesenpilz.nmsUtilities.packet.playOut;

import org.bukkit.entity.Player;

import io.github.riesenpilz.nmsUtilities.reflections.Field;
import net.minecraft.server.v1_16_R3.Packet;
import net.minecraft.server.v1_16_R3.PacketListenerPlayOut;
import net.minecraft.server.v1_16_R3.PacketPlayOutScoreboardDisplayObjective;

/**
 * https://wiki.vg/Protocol#Entity_Animation_.28clientbound.29
 * <p>
 * This is sent to the client when it should display a scoreboard
 * <p>
 * Packet ID: 0x4C<br>
 * State: Play<br>
 * Bound To: Client
 * 
 * @author Martin
 *
 */
public class PacketPlayOutScoreboardDisplayEvent extends PacketPlayOutEvent {

	private ScoreboardPosition position;

	/**
	 * The unique name for the scoreboard to be displayed (16 chars max.).
	 */
	private String name;

	public PacketPlayOutScoreboardDisplayEvent(Player injectedPlayer, PacketPlayOutScoreboardDisplayObjective packet) {
		super(injectedPlayer);
		
		position = ScoreboardPosition.getScoreboardPosition(Field.get(packet, "a", int.class));
		name = Field.get(packet, "b", String.class);
	}

	@Override
	public Packet<PacketListenerPlayOut> getNMS() {
		final PacketPlayOutScoreboardDisplayObjective packet = new PacketPlayOutScoreboardDisplayObjective();
		Field.set(packet, "a", position.getPositionValue());
		Field.set(packet, "b", name);
		return packet;
	}

	@Override
	public int getPacketID() {
		return 0x4C;
	}

	@Override
	public String getProtocolURLString() {
		return "https://wiki.vg/Protocol#Entity_Animation_.28clientbound.29";
	}

	public enum ScoreboardPosition {

		LIST(0), SIDEBAR(1), BELOW_NAME(2), TEAM_WHITE(3), TEAM_ORANGE(4), TEAM_MAGENTA(5), TEAM_LIGHT_BLUE(6),
		TEAM_YELLOW(7), TEAM_LIME(8), TEAM_PINK(9), TEAM_GRAY(10), TEAM_LIGHT_GRAY(11), TEAM_CYAN(12), TEAM_PURPLE(13),
		TEAM_BLUE(14), TEAM_BROWN(15), TEAM_GREEN(16), TEAM_RED(17), TEAM_BLACK(18);

		private int positionValue;

		private ScoreboardPosition(int position) {
			this.positionValue = position;
		}

		public int getPositionValue() {
			return positionValue;
		}

		public static ScoreboardPosition getScoreboardPosition(int positionValue) {
			for (ScoreboardPosition position : values())
				if (position.getPositionValue() == positionValue)
					return position;
			return null;
		}
	}
}
