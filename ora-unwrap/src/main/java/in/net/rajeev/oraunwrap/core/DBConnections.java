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

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import oracle.jdbc.pool.OracleDataSource;

/**
 * @author Rajeev Sreedharan
 *
 */
public class DBConnections {
	static Connection conn;

	public static Connection getConnection() {
		return conn;
	}

	public static Connection connect(String host, String service, String port, String user, String password)
			throws SQLException {
		Properties prop = new Properties();
		prop.setProperty("user", user);
		prop.setProperty("password", password);

		conn = connect(host, service, port, prop);

		return conn;
	}

	public static Connection connect(String host, String service, String port, Properties prop) throws SQLException {
		DriverManager.registerDriver(new oracle.jdbc.OracleDriver());
		OracleDataSource ods = new OracleDataSource();

		String url = "jdbc:oracle:thin:@//" + host + ":" + port + "/" + service;

		prop.setProperty("v$session.program	", "Unwrapper");

		ods.setConnectionProperties(prop);
		ods.setURL(url);

		conn = ods.getConnection();

		ConnectionObservable.getInstance().checkConnection();
		
		return conn;
	}

	public static boolean disconnect() {
		boolean disconnected = false;
		try {
			conn.close();
			conn = null;
			disconnected = true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		ConnectionObservable.getInstance().checkConnection();
		
		return disconnected;
	}

}
