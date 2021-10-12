package io.github.riesenpilz.nmsUtilities.entity;

import org.apache.commons.lang.Validate;

import io.github.riesenpilz.nmsUtilities.packet.playIn.PacketPlayInEntityInteractEvent;
import net.minecraft.server.v1_16_R3.PacketPlayInUseEntity.EnumEntityUseAction;

/**
 * Represents {@link EnumEntityUseAction}. Only used by packets.
 * 
 * @see PacketPlayInEntityInteractEvent
 *
 */
public enum EntityUseAction {

	INTERACT(EnumEntityUseAction.INTERACT), ATTACK(EnumEntityUseAction.ATTACK),
	INTERACT_AT(EnumEntityUseAction.INTERACT_AT);

	private final EnumEntityUseAction nms;

	EntityUseAction(EnumEntityUseAction nms) {
		this.nms = nms;
	}

	public EnumEntityUseAction getNMS() {
		return nms;
	}

	public static EntityUseAction getEntityUseAction(EnumEntityUseAction nms) {
		Validate.notNull(nms);
		for (EntityUseAction action : values())
			if (action.getNMS().equals(nms))
				return action;
		throw new IllegalArgumentException();
	}
}
