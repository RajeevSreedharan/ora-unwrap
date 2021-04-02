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

import java.sql.SQLException;
import java.util.Observable;

/**
 * @author Rajeev Sreedharan
 *
 */
public class ConnectionObservable extends Observable {
	protected static ConnectionObservable connectionObservable = null;
	protected static boolean connected = false;

	private ConnectionObservable() {
		checkConnection();
	}

	public static ConnectionObservable getInstance() {
		if (connectionObservable == null)
			connectionObservable = new ConnectionObservable();
		return connectionObservable;
	}

	public boolean checkConnection() {
		try {
			if (!(DBConnections.getConnection() == null) && !DBConnections.getConnection().isClosed()) {
				if (connected == false) {
					connected = true;
					connectionObservable.setChanged();
					connectionObservable.notifyObservers();
				}
			} else {
				if (connected == true) {
					connected = false;
					connectionObservable.setChanged();
					connectionObservable.notifyObservers();
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return connected;
	}
}
