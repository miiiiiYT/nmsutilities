package io.github.riesenpilz.nmsUtilities.commands;

import org.apache.commons.lang.Validate;

import net.minecraft.server.v1_16_R3.ArgumentAnchor.Anchor;

public enum ArgumentAnchor {

	FEET(Anchor.FEET), EYES(Anchor.EYES);

	private final Anchor nms;

	private ArgumentAnchor(Anchor nms) {
		this.nms = nms;
	}

	public Anchor getNMS() {
		return nms;
	}
	
	public static ArgumentAnchor getArgumentAnchor(Anchor nms) {
		Validate.notNull(nms);
		for (ArgumentAnchor anchor : values())
			if (anchor.getNMS().equals(nms))
				return anchor;
		throw new IllegalArgumentException();
	}
}
