/*
 * Copyright 2017 Rajeev Sreedharan
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package in.net.rajeev.oraunwrap.core;

/**
 * @author Rajeev Sreedharan
 *
 */
public class DatatypeUtil {

	private static final char[] hexCode = "0123456789ABCDEF".toCharArray();

	/**
	 * Converts a series of byte array into a hexadecimal string.
	 * 
	 * @param bytes
	 * @return hexadecimal in string format
	 */
	public static String bytesToHex(byte[] bytes) {
		char[] hexChars = new char[bytes.length * 2];
		for (int j = 0; j < bytes.length; j++) {
			int v = bytes[j] & 0xFF;
			hexChars[j * 2] = hexCode[v >>> 4];
			hexChars[j * 2 + 1] = hexCode[v & 0x0F];
		}
		return new String(hexChars);
	}

	/**
	 * Converts a hexadecimal in string format to a byte array
	 * 
	 * @param hexString
	 * @return byte array
	 */
	public static byte[] parseHexBinary(String hexString) {
		final int len = hexString.length();

		// "111" is not a valid hex encoding.
		if (len % 2 != 0) {
			throw new IllegalArgumentException("hexBinary needs to be even-length: " + hexString);
		}

		byte[] out = new byte[len / 2];

		for (int i = 0; i < len; i += 2) {
			int h = hexToBin(hexString.charAt(i));
			int l = hexToBin(hexString.charAt(i + 1));
			if (h == -1 || l == -1) {
				throw new IllegalArgumentException("contains illegal character for hexBinary: " + hexString);
			}

			out[i / 2] = (byte) (h * 16 + l);
		}

		return out;
	}

	private static int hexToBin(char ch) {
		if ('0' <= ch && ch <= '9') {
			return ch - '0';
		}
		if ('A' <= ch && ch <= 'F') {
			return ch - 'A' + 10;
		}
		if ('a' <= ch && ch <= 'f') {
			return ch - 'a' + 10;
		}
		return -1;
	}
}
