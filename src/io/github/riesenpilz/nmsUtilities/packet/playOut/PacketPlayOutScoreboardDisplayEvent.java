package io.github.riesenpilz.nmsUtilities.packet.playOut;

import org.apache.commons.lang.Validate;
import org.bukkit.entity.Player;

import io.github.riesenpilz.nmsUtilities.reflections.Field;
import io.github.riesenpilz.nmsUtilities.scoreboard.ScoreboardPosition;
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

		Validate.notNull(packet);

		position = ScoreboardPosition.getScoreboardPosition(Field.get(packet, "a", int.class));
		name = Field.get(packet, "b", String.class);
	}

	public PacketPlayOutScoreboardDisplayEvent(Player injectedPlayer, ScoreboardPosition position, String name) {
		super(injectedPlayer);

		Validate.notNull(position);
		Validate.notNull(name);

		this.position = position;
		this.name = name;
	}

	public ScoreboardPosition getPosition() {
		return position;
	}

	public String getName() {
		return name;
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

	
}
