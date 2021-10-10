package io.github.riesenpilz.nmsUtilities.block;

/**
 * Only used by packets
 * 
 * @see PacketPlayOutBlockEntityDataEvent
 *
 */
public enum BlockAction {
	/**
	 * Set data of a mob spawner (everything except for SpawnPotentials: current
	 * delay, min/max delay, mob to be spawned, spawn count, spawn range, etc.)
	 */
	MOB_SPAWNER(1),

	/**
	 * Set command block text (command and last execution status)
	 */
	COMMAND_BLOCK(2),

	/**
	 * Set the level, primary, and secondary powers of a beacon
	 */
	BEACON(3),

	/**
	 * Set rotation and skin of mob head
	 */
	MOB_HEAD(4),

	/**
	 * Declare a conduit
	 */
	COUNDIT(5),

	/**
	 * Set base color and patterns on a banner
	 */
	BANNER(6),

	/**
	 * Set the data for a Structure tile entity
	 */
	STRUCTURE_BLOCK(7),

	/**
	 * Set the destination for a end gateway
	 */
	END_GATEWAY(8),

	/**
	 * Set the text on a sign
	 */
	SIGN(9),

	/**
	 * Unused
	 */
	UNUSED(10),

	/**
	 * Declare a bed
	 */
	BED(11),

	/**
	 * Set data of a jigsaw block
	 */
	JIGSAW_BLOCK(12),

	/**
	 * Set items in a campfire
	 */
	CAMPFIRE(13),

	/**
	 * Beehive information
	 */
	BEEHIVE(14);

	private final int id;

	private BlockAction(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public static BlockAction getById(int id) {
		for (BlockAction action : values())
			if (action.getId() == id)
				return action;
		throw new IllegalArgumentException();
	}
}
