package io.github.riesenpilz.nmsUtilities.packet.playOut;

import org.bukkit.entity.Player;

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
		gameState = GameState.getGameState(Field.get(packet, "m", PacketPlayOutGameStateChange.a.class));
		value = Field.get(packet, "n", float.class);
	}

	public PacketPlayOutGameStateEvent(Player injectedPlayer, GameState gameState, float value) {
		super(injectedPlayer);
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

	public static enum GameState {

		/**
		 * Note: Sends message 'block.minecraft.spawn.not_valid'(You have no home bed or
		 * charged respawn anchor, or it was obstructed) to the client.
		 */
		NO_RESPAWN_BLOCK(PacketPlayOutGameStateChange.a), END_RAINING(PacketPlayOutGameStateChange.b),
		BEGIN_RAINING(PacketPlayOutGameStateChange.c),

		/**
		 * 0: Survival, 1: Creative, 2: Adventure, 3: Spectator.
		 */
		CHANGE_GAMEMODE(PacketPlayOutGameStateChange.f),

		/**
		 * 0: Just respawn player.<br>
		 * 1: Roll the credits and respawn player.<br>
		 * Note that 1 is only sent by notchian server when player has not yet achieved
		 * advancement "The end?", else 0 is sent.<br>
		 */
		WIN_GAME(PacketPlayOutGameStateChange.e),

		/**
		 * 0: Show welcome to demo screen<br>
		 * 101: Tell movement controls<br>
		 * 102: Tell jump control<br>
		 * 103: Tell inventory control<br>
		 * 104: Tell that the demo is over and print a message about how to take a
		 * screenshot.
		 */
		DEMO_EVENT(PacketPlayOutGameStateChange.g),

		/**
		 * <i>Note: Sent when any player is struck by an arrow.</i>
		 */
		ARROW_HIT_PLAYER(PacketPlayOutGameStateChange.g),

		/**
		 * <i>Note: Seems to change both skycolor and lightning.</i><br>
		 * This can cause HUD color change in client, when level is higher than 20. It
		 * goes away only when game is restarted or client receives same packet (from
		 * any server) but with value of 0. Is this a bug?
		 * <p>
		 * Rain level starting from 0.
		 */
		RAIN_LEVEL_CHANGE(PacketPlayOutGameStateChange.h),

		/**
		 * <i>Note: Seems to change both skycolor and lightning (same as Rain level
		 * change, but doesn't start rain). It also requires rain to render by notchian
		 * client.</i><br>
		 * This can cause HUD color change in client, when level is higher than 20. It
		 * goes away only when game is restarted or client receives same packet (from
		 * any server) but with value of 0. Is this a bug?
		 * <p>
		 * Thunder level starting from 0.
		 */
		THUNDER_LEVEL_CHANGE(PacketPlayOutGameStateChange.i),
		PLAY_PUFFERFISH_STING_SOUND(PacketPlayOutGameStateChange.j),

		/**
		 * effect and sound
		 */
		PLAY_ELDER_GUARDIAN_APPEARENCE(PacketPlayOutGameStateChange.k),

		/**
		 * 0: Enable respawn screen, 1: Immediately respawn (sent when the
		 * doImmediateRespawn gamerule changes).
		 */
		ENABLE_RESPAWN_SCREEN(PacketPlayOutGameStateChange.l);

		private PacketPlayOutGameStateChange.a nms;

		private GameState(PacketPlayOutGameStateChange.a nms) {
			this.nms = nms;
		}

		public PacketPlayOutGameStateChange.a getNms() {
			return nms;
		}

		public static GameState getGameState(PacketPlayOutGameStateChange.a nms) {
			for (GameState state : values())
				if (state.getNms().equals(nms))
					return state;
			return null;
		}
	}
}
