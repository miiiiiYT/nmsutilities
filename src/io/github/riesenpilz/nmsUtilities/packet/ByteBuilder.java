package io.github.riesenpilz.nmsUtilities.packet;

import org.apache.commons.lang.Validate;

public class ByteBuilder {

	public byte build(boolean... booleans) {
		Validate.isTrue(booleans.length <= 8);
		byte obyte = 0;
		for (int i = 0; i < booleans.length; i++)
			obyte += booleans[i] ? 1 << i : 0;
		return obyte;
	}
}
