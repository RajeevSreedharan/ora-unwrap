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
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.AbstractAction;
import javax.swing.JFileChooser;
import javax.swing.text.JTextComponent;

import in.net.rajeev.oraunwrap.ui.Main;

/**
 * @author Rajeev Sreedharan
 *
 */
public class SaveAsAction extends AbstractAction {

	private static final long serialVersionUID = 1L;
	private static SaveAsAction instance;
	private Component parent;

	static {
		instance = new SaveAsAction();
	}

	private SaveAsAction() {

	}

	public static SaveAsAction getInstance() {
		return instance;
	}
	
	public void setParent(Component parent) {
		this.parent = parent;
	}

	public void actionPerformed(ActionEvent e) {
		JTextComponent focused = FocusedComponentAction.getInstance().getFocusedComponent2();
		String savableText = focused != null ? focused.getText() : "";

		String lastSavedFolder = (String) Main.getProperties().get("last.saved.folder");

		JFileChooser chooser = new JFileChooser();
		chooser.setCurrentDirectory(new File(lastSavedFolder));

		int retrival = chooser.showSaveDialog(parent);

		if (retrival == JFileChooser.APPROVE_OPTION) {
			File selectedFile = chooser.getSelectedFile();
			FileWriter fw = null;
			try {
				fw = new FileWriter((selectedFile.getName().lastIndexOf(".") > 0 ? selectedFile.getAbsolutePath()
						: selectedFile + ".sql"));
				fw.write(savableText);
				if (!selectedFile.getParent().equals(lastSavedFolder)) {
					Main.updateProperty("last.saved.folder", selectedFile.getParent());
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			} finally {
				if (fw != null)
					try {
						fw.close();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
			}
		}
	}

}
