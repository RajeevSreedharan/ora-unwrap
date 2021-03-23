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

import java.io.IOException;

/**
 * @author Rajeev Sreedharan
 *
 */
public class Base64Helper {

	/**
	 * Transformation map
	 */
	private static final String[] transformMap = new String[] { "3D", "65", "85", "B3", "18", "DB", "E2", "87", "F1",
			"52", "AB", "63", "4B", "B5", "A0", "5F", "7D", "68", "7B", "9B", "24", "C2", "28", "67", "8A", "DE", "A4",
			"26", "1E", "03", "EB", "17", "6F", "34", "3E", "7A", "3F", "D2", "A9", "6A", "0F", "E9", "35", "56", "1F",
			"B1", "4D", "10", "78", "D9", "75", "F6", "BC", "41", "04", "81", "61", "06", "F9", "AD", "D6", "D5", "29",
			"7E", "86", "9E", "79", "E5", "05", "BA", "84", "CC", "6E", "27", "8E", "B0", "5D", "A8", "F3", "9F", "D0",
			"A2", "71", "B8", "58", "DD", "2C", "38", "99", "4C", "48", "07", "55", "E4", "53", "8C", "46", "B6", "2D",
			"A5", "AF", "32", "22", "40", "DC", "50", "C3", "A1", "25", "8B", "9C", "16", "60", "5C", "CF", "FD", "0C",
			"98", "1C", "D4", "37", "6D", "3C", "3A", "30", "E8", "6C", "31", "47", "F5", "33", "DA", "43", "C8", "E3",
			"5E", "19", "94", "EC", "E6", "A3", "95", "14", "E0", "9D", "64", "FA", "59", "15", "C5", "2F", "CA", "BB",
			"0B", "DF", "F2", "97", "BF", "0A", "76", "B4", "49", "44", "5A", "1D", "F0", "00", "96", "21", "80", "7F",
			"1A", "82", "39", "4F", "C1", "A7", "D7", "0D", "D1", "D8", "FF", "13", "93", "70", "EE", "5B", "EF", "BE",
			"09", "B9", "77", "72", "E7", "B2", "54", "B7", "2A", "C7", "73", "90", "66", "20", "0E", "51", "ED", "F8",
			"7C", "8F", "2E", "F4", "12", "C6", "2B", "83", "CD", "AC", "CB", "3B", "C4", "4E", "C0", "69", "36", "62",
			"02", "AE", "88", "FC", "AA", "42", "08", "A6", "45", "57", "D3", "9A", "BD", "E1", "23", "8D", "92", "4A",
			"11", "89", "74", "6B", "91", "FB", "FE", "C9", "01", "EA", "1B", "F7", "CE" };

	/**
	 * Get value based on tansformation map.
	 * 
	 * @param hexLocation
	 * @return
	 */
	public static String getTransformedValue(String hexLocation) {
		int location = Integer.parseInt(hexLocation, 16);
		return transformMap[location];
	}

	/**
	 * Decode Base64 with a string array as input. Unwrapped output is appended to
	 * buffer <code>unwrapped<code> provided as input. If any element is a
	 * non-base64 string, the element is appended as-is without any decoding
	 * 
	 * @param splitSource
	 * @param unwrapped
	 * @param others
	 * @throws IOException
	 */
	public static void decodeBase64(String[] splitSource, StringBuilder unwrapped, StringBuilder others)
			throws IOException {
		for (int i = 20; i < splitSource.length; i++) {
			if (splitSource[i].length() < 4) {
				others.append(splitSource[i]);
				continue;
			}
			byte[] bs = Base64.decode(splitSource[i].getBytes(), 0, splitSource[i].getBytes().length, 0);

			String b = DatatypeUtil.bytesToHex(bs);
			unwrapped.append(b);
		}
	}

	/**
	 * Input is series of location value in hex format. The mapped characters are
	 * returned based on the transformation codes for the location.
	 * 
	 * @param input
	 * @return
	 */
	public static String transform(String input) {
		StringBuilder sb = new StringBuilder();
		String transformedValue = null;

		// Split to 2 characters
		String[] array = input.split("(?<=\\G.{2})");
		for (String location : array) {

			transformedValue = getTransformedValue(location);
			if (transformedValue != null)
				sb.append(transformedValue);
			else {
				throw new RuntimeException(Messages.getString("Base64Helper.msg.noxformcode") + location);
			}
		}

		return sb.toString();
	}
}
