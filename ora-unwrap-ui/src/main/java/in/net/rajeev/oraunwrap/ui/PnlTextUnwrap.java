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
package in.net.rajeev.oraunwrap.ui;

import java.awt.FlowLayout;
/**
 * @author Rajeev Sreedharan
 *
 */
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.ExecutionException;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import in.net.rajeev.oraunwrap.core.Unwrapper;
import in.net.rajeev.oraunwrap.ui.commands.OpenActionObservable;
import in.net.rajeev.oraunwrap.ui.commands.Savable;
import in.net.rajeev.oraunwrap.ui.commands.SaveAsAction;

public class PnlTextUnwrap extends JPanel implements Savable {

	private static final long serialVersionUID = 1L;
	JTextArea txtInput;
	JTextArea txtOutput;
	JButton btnUnwrap;
	JButton btnClear;

	SwingWorker<String, Void> workerFileRead;
	/**
	 * Create the panel.
	 */
	public PnlTextUnwrap() {
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 10, 10, 10, 10, 10 };
		gridBagLayout.rowHeights = new int[] { 0, 10, 10, 10, 0 };
		gridBagLayout.columnWeights = new double[] { 0.0, 1.0, 0.0, 1.0, 0.0 };
		gridBagLayout.rowWeights = new double[] { 0.0, 0.0, 1.0, 0.0, 0.0 };
		setLayout(gridBagLayout);

		//****************** Input/Output ******************/
		txtInput = new JTextArea();
		GridBagConstraints gbc_txtInput = new GridBagConstraints();
		gbc_txtInput.fill = GridBagConstraints.BOTH;
		gbc_txtInput.insets = new Insets(5, 5, 5, 5);
		gbc_txtInput.gridx = 1;
		gbc_txtInput.gridy = 2;

		txtOutput = new JTextArea();
		GridBagConstraints gbc_txtOutput = new GridBagConstraints();
		gbc_txtOutput.fill = GridBagConstraints.BOTH;
		gbc_txtOutput.insets = new Insets(5, 5, 5, 5);
		gbc_txtOutput.gridx = 3;
		gbc_txtOutput.gridy = 2;

		JLabel lblWrappedCode = new JLabel(MessagesUI.getString("FrmUnwrapUI.wrapped"));
		GridBagConstraints gbc_lblWrappedCode = new GridBagConstraints();
		gbc_lblWrappedCode.anchor = GridBagConstraints.WEST;
		gbc_lblWrappedCode.insets = new Insets(10, 5, 5, 5);
		gbc_lblWrappedCode.gridx = 1;
		gbc_lblWrappedCode.gridy = 1;
		add(lblWrappedCode, gbc_lblWrappedCode);

		JLabel lblUnwrappedOutput = new JLabel(MessagesUI.getString("FrmUnwrapUI.unwrapped"));
		GridBagConstraints gbc_lblUnwrappedOutput = new GridBagConstraints();
		gbc_lblUnwrappedOutput.anchor = GridBagConstraints.WEST;
		gbc_lblUnwrappedOutput.insets = new Insets(10, 5, 5, 5);
		gbc_lblUnwrappedOutput.gridx = 3;
		gbc_lblUnwrappedOutput.gridy = 1;
		add(lblUnwrappedOutput, gbc_lblUnwrappedOutput);

		JScrollPane scrollIn = new JScrollPane(txtInput);
		scrollIn.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		add(scrollIn, gbc_txtInput);

		JScrollPane scrollOut = new JScrollPane(txtOutput);
		scrollOut.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		add(scrollOut, gbc_txtOutput);

		txtInput.setMaximumSize(txtInput.getPreferredSize());
		txtOutput.setMaximumSize(txtOutput.getPreferredSize());

		//****************** Buttons panel ******************/
		JPanel panel = new JPanel();
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.anchor = GridBagConstraints.EAST;
		gbc_panel.gridwidth = 3;
		gbc_panel.insets = new Insets(0, 0, 5, 5);
		gbc_panel.fill = GridBagConstraints.VERTICAL;
		gbc_panel.gridx = 1;
		gbc_panel.gridy = 3;
		add(panel, gbc_panel);
		panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		btnUnwrap = new JButton(MessagesUI.getString("FrmUnwrapUI.unwrap"));
		btnUnwrap.setPreferredSize(btnUnwrap.getPreferredSize());
		panel.add(btnUnwrap);

		btnClear = new JButton(MessagesUI.getString("FrmUnwrapUI.clear"));
		btnClear.setPreferredSize(btnUnwrap.getPreferredSize());
		panel.add(btnClear);
		
		//****************** Actions and listeners ******************/
		
		SwingUtilities.invokeLater(new Runnable() {
		    public void run() {
		    	addListenersAndActions();
		    }
		});
	}

	/**
	 * Add actions and listeners
	 */
	private void addListenersAndActions() {

		btnUnwrap.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				unwrap();
			}
		});
		
		btnClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				txtInput.setText("");
				txtOutput.setText("");
			}
		});

		txtInput.getDocument().addDocumentListener(new DocumentListener() {
			public void removeUpdate(DocumentEvent de) {unwrap();}
			public void insertUpdate(DocumentEvent de) {unwrap();}
			public void changedUpdate(DocumentEvent de) {unwrap();}
		});
		
		OpenActionObservable.getInstance().addObserver(new Observer() {
			public void update(Observable o, Object arg) {
				OpenActionObservable openActionObservable = (OpenActionObservable) o;
				File selectedFile = openActionObservable.getSelectedFile();
				readfile(selectedFile);
			}
		});
		
		SaveAsAction.getInstance().addSavable(this);
	}
	
	private void readfile(final File selectedFile){
		workerFileRead = new SwingWorker<String, Void>() {
		@Override
		public String doInBackground() {
			String data = null;
			try {
				data = new String(Files.readAllBytes(Paths.get(selectedFile.getAbsolutePath())));
			} catch (IOException e) {
				e.printStackTrace();
			}
			return data;
		}
		
		@Override
		public void done() {
			try {
				txtInput.setText((String) get());
			} catch (InterruptedException e) {
				txtInput.setText("");
				e.printStackTrace();
			} catch (ExecutionException e) {
				txtInput.setText("");
				e.printStackTrace();
			}
			workerFileRead = null;
		}
	};
	
	workerFileRead.execute();
	}


	/**
	 * Unwrap from input textfield to output textfield
	 */
	private void unwrap() {
		Unwrapper unwrapper = new Unwrapper();
		String unwrapped = unwrapper.unwrap(txtInput.getText());
		txtOutput.setText(unwrapped);
		txtOutput.selectAll();
		txtOutput.requestFocus();
	}

	public String getSavableText() {
		return txtOutput.getText();
	}

}
