package com.ugmt.core.util;

import java.util.UUID;

public final class UniqueIDGenerator {

	public static String generateUniqueID() {
		return UUID.randomUUID().toString().replace("-", "");
	}

	private UniqueIDGenerator() {
	}
}
