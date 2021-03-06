package io.github.riesenpilz.nmsUtilities.packet.playOut;

import org.apache.commons.lang.Validate;
import org.bukkit.entity.Player;

import io.github.riesenpilz.nmsUtilities.reflections.Field;
import io.github.riesenpilz.nmsUtilities.scoreboard.UpdateScoreMode;
import net.minecraft.server.v1_16_R3.Packet;
import net.minecraft.server.v1_16_R3.PacketListenerPlayOut;
import net.minecraft.server.v1_16_R3.PacketPlayOutScoreboardScore;
import net.minecraft.server.v1_16_R3.ScoreboardServer.Action;

/**
 * https://wiki.vg/Protocol#Update_Score
 * <p>
 * This is sent to the client when it should update a scoreboard item.
 * <p>
 * Packet ID: 0x56<br>
 * State: Play<br>
 * Bound To: Client
 * 
 * @author Martin
 *
 */
public class PacketPlayOutScoreboardUpdateScoreEvent extends PacketPlayOutEvent {

	/**
	 * The entity whose score this is. For players, this is their username; for
	 * other entities, it is their UUID. (40 chars max.)
	 */
	private String entityName;

	/**
	 * The name of the objective the score belongs to. (16 chars max.)
	 */
	private String objectiveName;

	/**
	 * The score to be displayed next to the entry.
	 */
	private int value;
	private UpdateScoreMode mode;

	public PacketPlayOutScoreboardUpdateScoreEvent(Player injectedPlayer, PacketPlayOutScoreboardScore packet) {
		super(injectedPlayer);

		Validate.notNull(packet);

		entityName = Field.get(packet, "a", String.class);
		entityName = Field.get(packet, "b", String.class);
		value = Field.get(packet, "c", int.class);
		mode = UpdateScoreMode.getMode(Field.get(packet, "d", Action.class));
	}

	public PacketPlayOutScoreboardUpdateScoreEvent(Player injectedPlayer, String entityName, String objectiveName,
			int value, UpdateScoreMode mode) {
		super(injectedPlayer);

		Validate.notNull(entityName);
		Validate.notNull(objectiveName);
		Validate.notNull(mode);

		this.entityName = entityName;
		this.objectiveName = objectiveName;
		this.value = value;
		this.mode = mode;
	}

	public String getEntityName() {
		return entityName;
	}

	public String getObjectiveName() {
		return objectiveName;
	}

	public int getValue() {
		return value;
	}

	public UpdateScoreMode getMode() {
		return mode;
	}

	@Override
	public Packet<PacketListenerPlayOut> getNMS() {
		return new PacketPlayOutScoreboardScore(mode.getNMS(), entityName, objectiveName, value);
	}

	@Override
	public int getPacketID() {
		return 0x56;
	}

	@Override
	public String getProtocolURLString() {
		return "https://wiki.vg/Protocol#Update_Score";
	}

}
