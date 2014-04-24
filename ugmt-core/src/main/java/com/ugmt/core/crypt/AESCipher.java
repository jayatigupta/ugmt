package com.ugmt.core.crypt;

import java.nio.charset.Charset;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

import com.ugmt.core.util.PropertyLoader;

public final class AESCipher {

	private static final byte[] KEY = Base64.decodeBase64(PropertyLoader.props.getProperty(
			"aescipherkey"));
	private static final SecretKeySpec keySpec = new SecretKeySpec(KEY, "AES");

	public static String encrypt(String input) {
		try {
			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
			cipher.init(Cipher.ENCRYPT_MODE, keySpec);
			return Base64.encodeBase64String(cipher.doFinal(input
					.getBytes(Charset.forName("UTF-8"))));
		} catch (Exception ex) {
			throw new RuntimeException(ex.getMessage());
		}
	}

	public static String decrypt(String input) {
		try {
			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
			cipher.init(Cipher.DECRYPT_MODE, keySpec);
			return new String(cipher.doFinal(Base64.decodeBase64(input)));
		} catch (Exception ex) {
			throw new RuntimeException(ex.getMessage());
		}
	}

	public static void main(String p[]) {
		String test = "puneet";
		String enc = AESCipher.encrypt(test);
		String dec = AESCipher.decrypt(enc);
		System.out.println(enc);
		System.out.println(dec);
	}
}
