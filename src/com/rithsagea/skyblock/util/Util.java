package com.rithsagea.skyblock.util;

import java.util.UUID;

public class Util {
	public static UUID stringToUUID(String value) {
		return UUID.fromString(
				value.substring(0, 8) + '-'
				+ value.substring(8, 12) + '-'
				+ value.substring(12, 16) + '-'
				+ value.substring(16, 20) + '-'
				+ value.substring(20, 32)
				);
	}
}
