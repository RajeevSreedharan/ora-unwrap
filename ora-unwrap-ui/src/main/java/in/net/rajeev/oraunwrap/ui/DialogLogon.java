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

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JProgressBar;
import javax.swing.JTextField;
import javax.swing.SwingWorker;
import javax.swing.border.EmptyBorder;

import in.net.rajeev.oraunwrap.core.ConnectionObservable;
import in.net.rajeev.oraunwrap.core.DBConnections;
import in.net.rajeev.oraunwrap.ui.helpers.UnwrapperIconSet;

/**
 * @author Rajeev Sreedharan
 *
 */
public class DialogLogon extends JDialog {
	
	private static final long serialVersionUID = 1L;

	private static DialogLogon instance = new DialogLogon();
	private SwingWorker<Object, Void> worker = null;
	
	public static DialogLogon getInstance() {
		if(instance == null)
			instance = new DialogLogon();
		return instance;
	}
	
	private final JPanel contentPanel = new JPanel();
	private JTextField tfUser;
	private JPasswordField tfPassword;
	private JTextField tfHost;
	private JTextField tfPort;
	private JTextField tfService;
	private JProgressBar progressBar;
	/**
	 * Create the dialog.
	 */
	private DialogLogon() {
		setTitle("Database Logon");
		setBounds(0, 0, 270, 250);
		setModal(true);
		setIconImage(UnwrapperIconSet.getMainIcon());
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
		
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		GridBagLayout gbl_contentPanel = new GridBagLayout();
		gbl_contentPanel.columnWidths = new int[]{0, 0, 0, 0, 0};
		gbl_contentPanel.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_contentPanel.columnWeights = new double[]{0.1, 0.4, 0.0, 1.0, 0.2};
		gbl_contentPanel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0};
		contentPanel.setLayout(gbl_contentPanel);
		{
			JLabel lblUser = new JLabel("User");
			GridBagConstraints gbc_lblUser = new GridBagConstraints();
			gbc_lblUser.insets = new Insets(0, 0, 5, 5);
			gbc_lblUser.anchor = GridBagConstraints.EAST;
			gbc_lblUser.gridx = 1;
			gbc_lblUser.gridy = 1;
			contentPanel.add(lblUser, gbc_lblUser);
		}
		{
			tfUser = new JTextField();
			GridBagConstraints gbc_tfUser = new GridBagConstraints();
			gbc_tfUser.insets = new Insets(0, 0, 5, 5);
			gbc_tfUser.fill = GridBagConstraints.HORIZONTAL;
			gbc_tfUser.gridx = 3;
			gbc_tfUser.gridy = 1;
			contentPanel.add(tfUser, gbc_tfUser);
			tfUser.setColumns(10);
		}
		{
			JLabel lblPassword = new JLabel("Password");
			GridBagConstraints gbc_lblPassword = new GridBagConstraints();
			gbc_lblPassword.anchor = GridBagConstraints.EAST;
			gbc_lblPassword.insets = new Insets(0, 0, 5, 5);
			gbc_lblPassword.gridx = 1;
			gbc_lblPassword.gridy = 2;
			contentPanel.add(lblPassword, gbc_lblPassword);
		}
		{
			tfPassword = new JPasswordField();
			GridBagConstraints gbc_tfPassword = new GridBagConstraints();
			gbc_tfPassword.insets = new Insets(0, 0, 5, 5);
			gbc_tfPassword.fill = GridBagConstraints.HORIZONTAL;
			gbc_tfPassword.gridx = 3;
			gbc_tfPassword.gridy = 2;
			contentPanel.add(tfPassword, gbc_tfPassword);
			tfPassword.setColumns(10);
		}
		{
			JLabel lblHost = new JLabel("Host");
			GridBagConstraints gbc_lblHost = new GridBagConstraints();
			gbc_lblHost.anchor = GridBagConstraints.EAST;
			gbc_lblHost.insets = new Insets(0, 0, 5, 5);
			gbc_lblHost.gridx = 1;
			gbc_lblHost.gridy = 3;
			contentPanel.add(lblHost, gbc_lblHost);
		}
		{
			tfHost = new JTextField();
			GridBagConstraints gbc_tfHost = new GridBagConstraints();
			gbc_tfHost.insets = new Insets(0, 0, 5, 5);
			gbc_tfHost.fill = GridBagConstraints.HORIZONTAL;
			gbc_tfHost.gridx = 3;
			gbc_tfHost.gridy = 3;
			contentPanel.add(tfHost, gbc_tfHost);
			tfHost.setColumns(10);
		}
		{
			JLabel lblPort = new JLabel("Port");
			GridBagConstraints gbc_lblPort = new GridBagConstraints();
			gbc_lblPort.anchor = GridBagConstraints.EAST;
			gbc_lblPort.insets = new Insets(0, 0, 5, 5);
			gbc_lblPort.gridx = 1;
			gbc_lblPort.gridy = 4;
			contentPanel.add(lblPort, gbc_lblPort);
		}
		{
			tfPort = new JTextField();
			tfPort.setText("1521");
			GridBagConstraints gbc_tfPort = new GridBagConstraints();
			gbc_tfPort.insets = new Insets(0, 0, 5, 5);
			gbc_tfPort.fill = GridBagConstraints.HORIZONTAL;
			gbc_tfPort.gridx = 3;
			gbc_tfPort.gridy = 4;
			contentPanel.add(tfPort, gbc_tfPort);
			tfPort.setColumns(10);
		}
		{
			JLabel lblService = new JLabel("Service");
			GridBagConstraints gbc_lblService = new GridBagConstraints();
			gbc_lblService.insets = new Insets(0, 0, 5, 5);
			gbc_lblService.anchor = GridBagConstraints.EAST;
			gbc_lblService.gridx = 1;
			gbc_lblService.gridy = 5;
			contentPanel.add(lblService, gbc_lblService);
		}
		{
			tfService = new JTextField();
			GridBagConstraints gbc_tfService = new GridBagConstraints();
			gbc_tfService.insets = new Insets(0, 0, 5, 5);
			gbc_tfService.fill = GridBagConstraints.HORIZONTAL;
			gbc_tfService.gridx = 3;
			gbc_tfService.gridy = 5;
			contentPanel.add(tfService, gbc_tfService);
			tfService.setColumns(10);
		}
		{
			progressBar = new JProgressBar();
			progressBar.setIndeterminate(false);
			GridBagConstraints gbc_progressBar = new GridBagConstraints();
			gbc_progressBar.insets = new Insets(0, 0, 5, 5);
			gbc_progressBar.gridx = 3;
			gbc_progressBar.gridy = 8;
			contentPanel.add(progressBar, gbc_progressBar);
			progressBar.setVisible(false);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
				okButton.addActionListener(new ActionListener() {
					
					public void actionPerformed(ActionEvent e) {
						progressBar.setVisible(true);
						progressBar.setIndeterminate(true);
						if(worker == null || !worker.getState().equals(SwingWorker.StateValue.STARTED))
							connect();
						
					}
				});
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
				cancelButton.addActionListener(new ActionListener() {
					
					public void actionPerformed(ActionEvent e) {
						DialogLogon.this.setVisible(false);
						if(worker !=null && worker.getState().equals(SwingWorker.StateValue.STARTED)) {
							worker.cancel(true);
							progressBar.setVisible(false);
							progressBar.setIndeterminate(false);
						}
					}
				});
			}
		}
		tfUser.setText("C51PRODHST");
		tfPassword.setText("C51PRODHST");
		tfHost.setText("192.168.56.103");
		tfPort.setText("1521");
		tfService.setText("GRCU");
		

		
	}
	private void connect(){
		worker = new SwingWorker<Object, Void>() {
		@Override
		public Object doInBackground() {
			boolean connected = false;
			try {
				DBConnections.connect(tfHost.getText(), tfService.getText(), tfPort.getText(), tfUser.getText(), tfPassword.getText());
			} catch (SQLException e1) {
				e1.printStackTrace();
				JOptionPane.showMessageDialog(DialogLogon.this, e1.getMessage(),
						"Could not connect", JOptionPane.ERROR_MESSAGE);
			}
			return connected;
		}
		
		@Override
		public void done() {
			progressBar.setVisible(false);
			progressBar.setIndeterminate(false);
			worker = null;
			ConnectionObservable.getInstance().checkConnection();
			DialogLogon.this.setVisible(false);
		}
	};
	
	worker.execute();
	}

}
