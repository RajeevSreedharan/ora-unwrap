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
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.util.Observable;
import java.util.Observer;

import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.JTabbedPane;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.text.DefaultEditorKit;

import com.formdev.flatlaf.FlatClientProperties;

import in.net.rajeev.oraunwrap.core.ConnectionObservable;
import in.net.rajeev.oraunwrap.ui.commands.ConnectAction;
import in.net.rajeev.oraunwrap.ui.commands.OpenAction;
import in.net.rajeev.oraunwrap.ui.commands.OpenActionObservable;
import in.net.rajeev.oraunwrap.ui.commands.SaveAsAction;
import in.net.rajeev.oraunwrap.ui.helpers.UnwrapperIconSet;

/**
 * @author Rajeev Sreedharan
 *
 */
public class FrmUnwrapUI extends JFrame {

	private static final long serialVersionUID = 1L;

	private JPanel contentPane;
	private JMenuItem mntmFileConnect;
	JTabbedPane tabbedPane;
	JPanel pnlTextUnwrap;
	JPanel pnlDBUnitUnwrap;
	JPanel pnlSchemaUnwrap;
	
	JMenuItem mntmHelpAbout;
	JMenuItem mntmFileExit;
	JMenuItem mntmFileOpen;
	JMenuItem mntmFileSaveas;
	
	JToggleButton toolbarConnection;

	private JToggleButton toolbarConsole;
	
	private static final ImageIcon iconDisconnected = UnwrapperIconSet.getIcon(UnwrapperIconSet.ICON_NETWORK_DISCONNECTED);
	private static final ImageIcon iconConnected = UnwrapperIconSet.getIcon(UnwrapperIconSet.ICON_NETWORK_CONNECTED);

	/**
	 * Create the frame.
	 */
	public FrmUnwrapUI() {
		setTitle(MessagesUI.getString("FrmUnwrapUI.unwrapper"));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		setIconImage(UnwrapperIconSet.getMainIcon());

		getRootPane().setWindowDecorationStyle(JRootPane.FRAME);
		getRootPane().putClientProperty(FlatClientProperties.MENU_BAR_EMBEDDED, null);
		setUndecorated(true);
		JFrame.setDefaultLookAndFeelDecorated(true);
		JDialog.setDefaultLookAndFeelDecorated(true);

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		setExtendedState(java.awt.Frame.MAXIMIZED_BOTH);

		//****************** Menu ******************/
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		JMenu mnFileMenu = new JMenu(MessagesUI.getString("FrmUnwrapUI.filemenu"));
		menuBar.add(mnFileMenu);

		JMenu mnEditMenu = new JMenu(MessagesUI.getString("FrmUnwrapUI.editmenu"));
		menuBar.add(mnEditMenu);

		JMenu mnHelpMenu = new JMenu(MessagesUI.getString("FrmUnwrapUI.helpmenu"));
		menuBar.add(mnHelpMenu);

		mntmFileOpen = new JMenuItem(MessagesUI.getString("FrmUnwrapUI.openmenu"));
		mntmFileOpen.setMnemonic(KeyEvent.VK_O);
		mntmFileOpen.setAccelerator(KeyStroke.getKeyStroke('O', Toolkit.getDefaultToolkit ().getMenuShortcutKeyMask()));
		mnFileMenu.add(mntmFileOpen);

		mntmFileSaveas = new JMenuItem(MessagesUI.getString("FrmUnwrapUI.saveasmenu"));
		mntmFileSaveas.setMnemonic(KeyEvent.VK_S);
		mntmFileSaveas.setAccelerator(KeyStroke.getKeyStroke('S', Toolkit.getDefaultToolkit ().getMenuShortcutKeyMask()));
		mnFileMenu.add(mntmFileSaveas);

		mntmFileConnect = new JMenuItem(MessagesUI.getString("FrmUnwrapUI.connectaction"));
		mntmFileConnect.setActionCommand(ConnectAction.CONNECT);
		mntmFileConnect.setMnemonic(KeyEvent.VK_D);
		mntmFileConnect.setAccelerator(KeyStroke.getKeyStroke('D', Toolkit.getDefaultToolkit ().getMenuShortcutKeyMask()));
		mnFileMenu.add(mntmFileConnect);

		mntmFileExit = new JMenuItem(MessagesUI.getString("FrmUnwrapUI.exitmenu"));
		mntmFileExit.setMnemonic(KeyEvent.VK_E);
		mntmFileExit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F4, Toolkit.getDefaultToolkit ().getMenuShortcutKeyMask()));
		mnFileMenu.add(mntmFileExit);

		JMenuItem mntmEditCopy = new JMenuItem();
		mnEditMenu.add(mntmEditCopy);
		mntmEditCopy.setAction(new DefaultEditorKit.CopyAction());
		mntmEditCopy.setText(MessagesUI.getString("FrmUnwrapUI.copymenu"));
		mntmEditCopy.setMnemonic(KeyEvent.VK_C);
		mntmEditCopy.setAccelerator(KeyStroke.getKeyStroke('C', Toolkit.getDefaultToolkit ().getMenuShortcutKeyMask()));

		JMenuItem mntmEditPaste = new JMenuItem();
		mnEditMenu.add(mntmEditPaste);
		mntmEditPaste.setAction(new DefaultEditorKit.PasteAction());
		mntmEditPaste.setText(MessagesUI.getString("FrmUnwrapUI.pastemenu"));
		mntmEditPaste.setMnemonic(KeyEvent.VK_V);
		mntmEditPaste.setAccelerator(KeyStroke.getKeyStroke('V', Toolkit.getDefaultToolkit ().getMenuShortcutKeyMask()));
		
		JMenu mntmHelpLanguage = new JMenu(MessagesUI.getString("FrmUnwrapUI.languagemenu"));
		mnHelpMenu.add(mntmHelpLanguage);

		JMenuItem mntmHelpLanguageEnglish = new JMenuItem(MessagesUI.getString("FrmUnwrapUI.language.english"));
		mntmHelpLanguage.add(mntmHelpLanguageEnglish);

		mntmHelpAbout = new JMenuItem(MessagesUI.getString("FrmUnwrapUI.aboutmenu"));
		mnHelpMenu.add(mntmHelpAbout);

		//****************** Tabbed panes ******************/
		tabbedPane = new JTabbedPane();
		pnlTextUnwrap = new PnlTextUnwrap();
		tabbedPane.addTab(MessagesUI.getString("FrmUnwrapUI.tabText.title"), null, pnlTextUnwrap, MessagesUI.getString("FrmUnwrapUI.tabText.desc"));
		pnlDBUnitUnwrap = new PnlDBUnitUnwrap();
		tabbedPane.addTab(MessagesUI.getString("FrmUnwrapUI.tabDBUnit.title"), null, pnlDBUnitUnwrap, MessagesUI.getString("FrmUnwrapUI.tabDBUnit.title"));
		pnlSchemaUnwrap = new JPanel();
		tabbedPane.addTab(MessagesUI.getString("FrmUnwrapUI.tabSchema.title"), null, pnlSchemaUnwrap, MessagesUI.getString("FrmUnwrapUI.tabSchema.title"));
		
		JToolBar trailing = new JToolBar();
		trailing.setFloatable(false);
		trailing.setBorder((Border) null);
		trailing.add(Box.createHorizontalGlue());
		toolbarConnection = new JToggleButton(iconDisconnected);
		toolbarConnection.setSelectedIcon(iconConnected);
		toolbarConnection.setToolTipText(MessagesUI.getString("FrmUnwrapUI.connectaction"));
		trailing.add(toolbarConnection);
		toolbarConsole = new JToggleButton(UnwrapperIconSet.getIcon(UnwrapperIconSet.ICON_CONSOLE));
		trailing.add(toolbarConsole);
		toolbarConsole.setToolTipText(MessagesUI.getString("FrmUnwrapUI.console"));

		contentPane.add(tabbedPane);	
		tabbedPane.putClientProperty("JTabbedPane.trailingComponent", trailing);
		
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

		mntmHelpAbout.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				DialogAboutBox.getInstance().setLocationRelativeTo(null);
				DialogAboutBox.getInstance().setVisible(true);

			}
		});
		
		ConnectAction connectAction = new ConnectAction();
		mntmFileConnect.addActionListener(connectAction);
		toolbarConnection.addActionListener(connectAction);
		toolbarConnection.getModel().addChangeListener(new ChangeListener() {
	        public void stateChanged(ChangeEvent evt) {
	           if (ConnectionObservable.getInstance().checkConnection()) {
					toolbarConnection.setSelected(true);
	           } else {
					toolbarConnection.setSelected(false);
	           }
	        }
	     });
		
		mntmFileExit.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				FrmUnwrapUI.this.dispatchEvent(new WindowEvent(FrmUnwrapUI.this, WindowEvent.WINDOW_CLOSING));
			}
		});
		
		mntmFileOpen.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				
			}
		});
		
		mntmFileOpen.addActionListener(new OpenAction(this));
		
		ConnectionObservable.getInstance().addObserver(new Observer() {
			public void update(Observable o, Object arg) {
				if (ConnectionObservable.getInstance().checkConnection()) {
					mntmFileConnect.setText(MessagesUI.getString("FrmUnwrapUI.disconnectaction"));
					mntmFileConnect.setActionCommand(ConnectAction.DISCONNECT);
					if(tabbedPane.getComponentAt(tabbedPane.getSelectedIndex()).equals(pnlTextUnwrap))
						tabbedPane.setSelectedComponent(pnlDBUnitUnwrap);
					toolbarConnection.setSelected(true);
					toolbarConnection.setToolTipText(MessagesUI.getString("FrmUnwrapUI.disconnectaction"));
				} else {
					mntmFileConnect.setText(MessagesUI.getString("FrmUnwrapUI.connectaction"));
					mntmFileConnect.setActionCommand(ConnectAction.CONNECT);
					toolbarConnection.setSelected(false);
					toolbarConnection.setToolTipText(MessagesUI.getString("FrmUnwrapUI.connectaction"));
				}
			}
		});
		
		OpenActionObservable.getInstance().addObserver(new Observer() {
			public void update(Observable o, Object arg) {
				tabbedPane.setSelectedComponent(pnlTextUnwrap);
			}
		});

		mntmFileSaveas.addActionListener(SaveAsAction.getInstance());
		SaveAsAction.getInstance().setParent(FrmUnwrapUI.this);
	}

}
