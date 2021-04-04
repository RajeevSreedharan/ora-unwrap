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
 package in.net.rajeev.oraunwrap.ui.commands;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import in.net.rajeev.oraunwrap.core.ConnectionObservable;
import in.net.rajeev.oraunwrap.core.DBConnections;
import in.net.rajeev.oraunwrap.ui.DialogLogon;

/**
 * @author Rajeev Sreedharan
 *
 */
public class ConnectAction extends AbstractAction {
	public static final String CONNECT = "CONNECT", DISCONNECT = "DISCONNECT";

	private static final long serialVersionUID = 1L;

	public void actionPerformed(ActionEvent e) {
		if (DBConnections.getConnection() == null) {

			DialogLogon dialog = DialogLogon.getInstance();
			dialog.setVisible(true);
		} else {
			DBConnections.disconnect();
		}

		ConnectionObservable.getInstance().checkConnection();
	}

}
