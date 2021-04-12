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

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.io.File;

import javax.swing.AbstractAction;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import in.net.rajeev.oraunwrap.ui.Main;

/**
 * @author Rajeev Sreedharan
 *
 */
public class OpenAction  extends AbstractAction {
	Component parent;
	
	public OpenAction(Component parent) {
		super();
		this.parent = parent;
	}

	private static final long serialVersionUID = 1L;

	public void actionPerformed(ActionEvent e) {
		String lastSavedFolder = (String) Main.getProperties().get("last.saved.folder");

		FileNameExtensionFilter fileNameExtensionFilter = new FileNameExtensionFilter("PL/SQL Binary (*.plb)", "plb");
		JFileChooser jf = new JFileChooser();
		jf.setCurrentDirectory(new File(lastSavedFolder));
		jf.setMultiSelectionEnabled(false);
		jf.setFileSelectionMode(JFileChooser.FILES_ONLY);
		jf.addChoosableFileFilter(fileNameExtensionFilter);
		jf.setFileFilter(fileNameExtensionFilter);

		int action = jf.showOpenDialog(parent);
		File selectedFile = jf.getSelectedFile();
		if(action == JFileChooser.APPROVE_OPTION && selectedFile != null) {
			OpenActionObservable.getInstance().setSelectedFile(selectedFile);
		}
	
	}

}
