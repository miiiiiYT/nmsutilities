package io.github.riesenpilz.nmsUtilities.block;

import org.apache.commons.lang.Validate;

import net.minecraft.server.v1_16_R3.TileEntityJigsaw.JointType;

/**
 * Represents {@link JointType}. Only used by packets.
 * 
 * @see PacketPlayInUpdateJigsawBlockEvent
 *
 */
public enum JigsawJointType {

	ROTATABLE(JointType.ROLLABLE), ALIGNED(JointType.ALIGNED);

	private final JointType nms;

	JigsawJointType(JointType nms) {
		this.nms = nms;
	}

	public JointType getNMS() {
		return nms;
	}

	public static JigsawJointType getJointType(JointType nms) {
		Validate.notNull(nms);
		for (JigsawJointType type : values())
			if (type.getNMS().equals(nms))
				return type;
		throw new IllegalArgumentException();
	}

}
