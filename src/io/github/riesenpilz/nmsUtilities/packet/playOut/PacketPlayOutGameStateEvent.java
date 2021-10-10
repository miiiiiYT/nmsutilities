package io.github.riesenpilz.nmsUtilities.packet.playOut;

import org.apache.commons.lang.Validate;
import org.bukkit.entity.Player;

import io.github.riesenpilz.nmsUtilities.game.GameState;
import io.github.riesenpilz.nmsUtilities.reflections.Field;
import net.minecraft.server.v1_16_R3.Packet;
import net.minecraft.server.v1_16_R3.PacketListenerPlayOut;
import net.minecraft.server.v1_16_R3.PacketPlayOutGameStateChange;

/**
 * https://wiki.vg/Protocol#Change_Game_State
 * <p>
 * Used for a wide variety of game state things, from weather to bed use to
 * gamemode to demo messages.
 * <p>
 * Packet ID: 0x1D<br>
 * State: Play<br>
 * Bound To: Client
 * 
 * @author Martin
 *
 */
public class PacketPlayOutGameStateEvent extends PacketPlayOutEvent {

	private GameState gameState;

	/**
	 * Depends on Reason.
	 */
	private float value;

	public PacketPlayOutGameStateEvent(Player injectedPlayer, PacketPlayOutGameStateChange packet) {
		super(injectedPlayer);

		Validate.notNull(packet);

		gameState = GameState.getGameState(Field.get(packet, "m", PacketPlayOutGameStateChange.a.class));
		value = Field.get(packet, "n", float.class);
	}

	public PacketPlayOutGameStateEvent(Player injectedPlayer, GameState gameState, float value) {
		super(injectedPlayer);

		Validate.notNull(gameState);

		this.gameState = gameState;
		this.value = value;
	}

	public GameState getGameState() {
		return gameState;
	}

	public float getValue() {
		return value;
	}

	@Override
	public Packet<PacketListenerPlayOut> getNMS() {
		return new PacketPlayOutGameStateChange(gameState.getNms(), value);
	}

	@Override
	public int getPacketID() {
		return 0x1D;
	}

	@Override
	public String getProtocolURLString() {
		return "https://wiki.vg/Protocol#Change_Game_State";
	}
	
}
