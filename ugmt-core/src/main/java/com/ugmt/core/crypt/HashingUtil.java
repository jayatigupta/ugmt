package com.ugmt.core.crypt;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.codec.binary.Base64;

public final class HashingUtil {

	public static String generateSHA512Hash(String plainText)
			throws NoSuchAlgorithmException {
		MessageDigest md = MessageDigest.getInstance("SHA-512");
		byte[] encrypted = md.digest(plainText.getBytes());
		return Base64.encodeBase64String(encrypted);
	}

	private HashingUtil() {
	}
}
