package com.odcgroup.t24.server.external.util;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

/**
 * Scrambler and Unscrambler
 */
public class ServerPropertiesPasswordScrambler {



	public static final String DESEDE_ENCRYPTION_SCHEME = "DESede";
	
	private static final String ENCRYPT_MODE = "DESede/ECB/PKCS5Padding";
	
	private static final String UNICODE = "UTF-8";
	
	private static final String PREFIX = "{encoded}";
	
	/**
	 * 3DES key generated once with: KeyGenerator desEdeGen =
	 * KeyGenerator.getInstance("DESede"); desEdeGen.init(168, new
	 * SecureRandom());
	 */
	static final byte[] TDESKEYDATA = { (byte) 0x4C, (byte) 0xB0, (byte) 0xC8,
			(byte) 0x19, (byte) 0x58, (byte) 0x9E, (byte) 0xB6, (byte) 0xE5,
			(byte) 0x68, (byte) 0xBA, (byte) 0x51, (byte) 0x13, (byte) 0x15,
			(byte) 0xD0, (byte) 0xC8, (byte) 0x01, (byte) 0xBC, (byte) 0x6D,
			(byte) 0x38, (byte) 0x32, (byte) 0x75, (byte) 0x04, (byte) 0x7A,
			(byte) 0xD6 };

	private synchronized Cipher getCipher(boolean encrypt) throws Exception {
		SecretKey secretKey = new SecretKeySpec(TDESKEYDATA, 0, TDESKEYDATA.length, DESEDE_ENCRYPTION_SCHEME);
		Cipher cipher = Cipher.getInstance(ENCRYPT_MODE);
		if (encrypt) {
			cipher.init(Cipher.ENCRYPT_MODE, secretKey);
		} else {
			cipher.init(Cipher.DECRYPT_MODE, secretKey);
		}
		return cipher;
	}

	private String encrypt(String string) throws Exception {
		byte[] bytes = string.getBytes(UNICODE);
		byte[] encryptedBytes = getCipher(true).doFinal(bytes);
		byte[] encodedBytes = Base64.encodeBase64(encryptedBytes);
		String encryptedString = new String(encodedBytes, UNICODE);
		return encryptedString;
	}

	private String decrypt(String string) throws Exception {
		byte[] encryptedBytes = Base64.decodeBase64(string.getBytes(UNICODE));
		byte[] decryptedBytes = getCipher(false).doFinal(encryptedBytes);
		String decryptedString = new String(decryptedBytes, UNICODE);
		return decryptedString;
	}
	
	// return null if the string cannot be encoded
	public String encode(String string) {
		String result = "";
		if (string.startsWith(PREFIX)) {
			// the string is already encrypted, do nothing
			result = string;
		} else if(!string.isEmpty()) {
			try {
				result = PREFIX+encrypt(string);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	
	public String decode(String string) {
		String result = null;
		if (string.startsWith(PREFIX)) {
			// this string is encrypted, so we decrypt it
			try {
				result = decrypt(string.replace(PREFIX, ""));
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			// return simply this string, was a priori not encrypted
			result = string;
		}
		return result;
	}
	
	/**
	 * Constructor for ServerPropertiesPasswordScrambler
	 */
	public ServerPropertiesPasswordScrambler() {
		super();
	}

}
