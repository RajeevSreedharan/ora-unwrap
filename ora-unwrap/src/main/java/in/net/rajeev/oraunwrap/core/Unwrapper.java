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
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.zip.InflaterInputStream;


public class Unwrapper {
	private final static String CREATE_STATEMENT = "CREATE OR REPLACE ";

	/**
	 * This method takes a base64 encoded wrapped unit in Oracle database and
	 * returns a decoded or 'unwrapped' source of the unit.
	 * 
	 * @param source
	 * @return
	 */
	public String unwrap(String source) {
		String unwrrapedOutput = null;
		String[] splitSource = source.split("\n");

		try {
			StringBuilder unwrapped = new StringBuilder();
			StringBuilder others = new StringBuilder();
			
			verifyWrappedCode(splitSource);
			Base64Helper.decodeBase64(splitSource, unwrapped, others);
			String transformed = Base64Helper.transform(unwrapped.toString().substring(40));
			byte[] compressedBytes = DatatypeUtil.parseHexBinary(transformed);
			String txtRet = inflate(compressedBytes);
			
			unwrrapedOutput = CREATE_STATEMENT + txtRet + "\n" + others.toString();
		} catch (Exception e) {
			unwrrapedOutput = e.getMessage();
			e.printStackTrace();
		}
		return unwrrapedOutput;
	}

	/**
	 * Verify if input string is in a wrapped source format 
	 * 
	 * @param splitSource
	 */
	protected void verifyWrappedCode(String[] splitSource) {
		String firstLine = splitSource[0];
		if (!firstLine.trim().endsWith("wrapped")) {
			throw new RuntimeException(Messages.getString("UnWrapper.msg.notwrapped"));
		}
	}

	/**
	 * Uncompress data in byte series compressed format
	 * 
	 * @param src
	 * @return
	 */
	protected String inflate(byte[] src) {
			InflaterInputStream iis = new InflaterInputStream(new ByteArrayInputStream(src));
			StringBuilder sb = new StringBuilder();
			int c;
			try {
				while((c = iis.read()) != -1)
					sb.append((char) c);
			} catch (IOException e) {
				e.printStackTrace();
			}
			return sb.toString();
	}
}