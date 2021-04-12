package in.net.rajeev.oraunwrap.ui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

import in.net.rajeev.oraunwrap.core.ConnectionObservable;
import in.net.rajeev.oraunwrap.core.DBConnections;
import in.net.rajeev.oraunwrap.core.DBSchemaUtil;
import in.net.rajeev.oraunwrap.core.Unwrapper;
import in.net.rajeev.oraunwrap.ui.commands.ConnectAction;
import in.net.rajeev.oraunwrap.ui.commands.Savable;
import in.net.rajeev.oraunwrap.ui.commands.SaveAsAction;
import in.net.rajeev.oraunwrap.ui.helpers.AutoCompletion;

public class PnlDBUnitUnwrap extends JPanel implements Savable {

	private static final long serialVersionUID = 1L;

	JComboBox<String> comboBoxSearch;
	JTree treeUnits;
	JButton btnConnect;
	JTextArea taOutput;
	JButton btnUnwrap;
	
	public PnlDBUnitUnwrap() {
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 10, 10, 0, 0, 0, 0, 10 };
		gridBagLayout.rowHeights = new int[] { 10, 0, 10, 10 };
		gridBagLayout.columnWeights = new double[] { 0.0, 0.15, 0.0, 1.0, 0.0, 0.10, 0.0 };
		gridBagLayout.rowWeights = new double[] { 0.0, 0.0, 1.0, 0.0 };
		setLayout(gridBagLayout);
		
		//****************** Search box ******************/

		comboBoxSearch = new JComboBox<String>();
		comboBoxSearch.setEditable(true);
		GridBagConstraints gbc_comboBoxSearch = new GridBagConstraints();
		gbc_comboBoxSearch.insets = new Insets(5, 5, 5, 5);
		gbc_comboBoxSearch.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBoxSearch.gridx = 1;
		gbc_comboBoxSearch.gridy = 1;
		add(comboBoxSearch, gbc_comboBoxSearch);

		AutoCompletion.enable(comboBoxSearch);

		DefaultMutableTreeNode topNode = new DefaultMutableTreeNode("USER");

		//****************** JTree ******************/
		treeUnits = new JTree(topNode);
//		tree.setEnabled(false);
		JScrollPane scrollPaneTree = new JScrollPane(treeUnits);
		GridBagConstraints gbc_scrollPaneTree = new GridBagConstraints();
		gbc_scrollPaneTree.fill = GridBagConstraints.BOTH;
		gbc_scrollPaneTree.insets = new Insets(5, 5, 5, 5);
		gbc_scrollPaneTree.anchor = GridBagConstraints.NORTHWEST;
		gbc_scrollPaneTree.gridx = 1;
		gbc_scrollPaneTree.gridy = 2;
		add(scrollPaneTree, gbc_scrollPaneTree);

		taOutput = new JTextArea();
		JScrollPane scrollPaneOutput = new JScrollPane(taOutput);
		GridBagConstraints gbc_scrollPaneOutput = new GridBagConstraints();
		gbc_scrollPaneOutput.gridheight = 2;
		gbc_scrollPaneOutput.insets = new Insets(5, 5, 5, 5);
		gbc_scrollPaneOutput.fill = GridBagConstraints.BOTH;
		gbc_scrollPaneOutput.gridx = 3;
		gbc_scrollPaneOutput.gridy = 1;
		add(scrollPaneOutput, gbc_scrollPaneOutput);

		//****************** Buttons panel ******************/
		JPanel panel = new JPanel();
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.gridheight = 2;
		gbc_panel.insets = new Insets(5, 5, 5, 5);
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.gridx = 5;
		gbc_panel.gridy = 1;
		add(panel, gbc_panel);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[] { 124, 0 };
		gbl_panel.rowHeights = new int[] { 23, 23, 0, 0, 0, 0, 0, 0, 0, 0 };
		gbl_panel.columnWeights = new double[] { 0.0, Double.MIN_VALUE };
		gbl_panel.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		panel.setLayout(gbl_panel);

		btnConnect = new JButton(MessagesUI.getString("FrmUnwrapUI.connectaction"));
		GridBagConstraints gbc_btnConnect = new GridBagConstraints();
		gbc_btnConnect.fill = GridBagConstraints.BOTH;
		gbc_btnConnect.insets = new Insets(0, 0, 5, 0);
		gbc_btnConnect.gridx = 0;
		gbc_btnConnect.gridy = 0;
		panel.add(btnConnect, gbc_btnConnect);

		JButton btnReload = new JButton(MessagesUI.getString("FrmUnwrapUI.reload"));
		GridBagConstraints gbc_btnReload = new GridBagConstraints();
		gbc_btnReload.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnReload.insets = new Insets(0, 0, 5, 0);
		gbc_btnReload.gridx = 0;
		gbc_btnReload.gridy = 1;
		panel.add(btnReload, gbc_btnReload);

		btnUnwrap = new JButton(MessagesUI.getString("FrmUnwrapUI.unwrap"));
		GridBagConstraints gbc_btnUnwrap = new GridBagConstraints();
		gbc_btnUnwrap.insets = new Insets(0, 0, 5, 0);
		gbc_btnUnwrap.fill = GridBagConstraints.BOTH;
		gbc_btnUnwrap.gridx = 0;
		gbc_btnUnwrap.gridy = 2;
		panel.add(btnUnwrap, gbc_btnUnwrap);

		JButton btnSaveAs = new JButton(MessagesUI.getString("FrmUnwrapUI.saveasmenu"));
		GridBagConstraints gbc_btnSaveAs = new GridBagConstraints();
		gbc_btnSaveAs.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnSaveAs.insets = new Insets(0, 0, 5, 0);
		gbc_btnSaveAs.gridx = 0;
		gbc_btnSaveAs.gridy = 3;
		panel.add(btnSaveAs, gbc_btnSaveAs);

		JButton btnExecute = new JButton(MessagesUI.getString("FrmUnwrapUI.execute"));
		GridBagConstraints gbc_btnExecute = new GridBagConstraints();
		gbc_btnExecute.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnExecute.insets = new Insets(0, 0, 5, 0);
		gbc_btnExecute.gridx = 0;
		gbc_btnExecute.gridy = 4;
		panel.add(btnExecute, gbc_btnExecute);

		JCheckBox chckbxLoadWrapped = new JCheckBox(MessagesUI.getString("FrmUnwrapUI.loadwrapped"));
		chckbxLoadWrapped.setSelected(true);
		GridBagConstraints gbc_chckbxLoadWrapped = new GridBagConstraints();
		gbc_chckbxLoadWrapped.insets = new Insets(0, 0, 5, 0);
		gbc_chckbxLoadWrapped.gridx = 0;
		gbc_chckbxLoadWrapped.gridy = 7;
		panel.add(chckbxLoadWrapped, gbc_chckbxLoadWrapped);
		chckbxLoadWrapped.setEnabled(false);

		JCheckBox chckbxWrapAutomatically = new JCheckBox(MessagesUI.getString("FrmUnwrapUI.unwrapauto"));
		chckbxWrapAutomatically.setSelected(false);
		GridBagConstraints gbc_chckbxWrapAutomatically = new GridBagConstraints();
		gbc_chckbxWrapAutomatically.anchor = GridBagConstraints.WEST;
		gbc_chckbxWrapAutomatically.gridx = 0;
		gbc_chckbxWrapAutomatically.gridy = 8;
		panel.add(chckbxWrapAutomatically, gbc_chckbxWrapAutomatically);
		chckbxWrapAutomatically.setEnabled(false);
		
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

		comboBoxSearch.addItemListener(new ItemListener() {

			public void itemStateChanged(ItemEvent event) {
				if (event.getStateChange() == ItemEvent.SELECTED) {
					String item = (String) event.getItem();
					TreePath path = find((DefaultMutableTreeNode) treeUnits.getModel().getRoot(), item);
					treeUnits.setSelectionPath(path);
					treeUnits.scrollPathToVisible(path);
				}

			}
		});
		
		btnConnect.addActionListener(new ConnectAction());

		ConnectionObservable.getInstance().addObserver(new Observer() {
			public void update(Observable o, Object arg) {
				if (ConnectionObservable.getInstance().checkConnection()) {
					connected();
					btnConnect.setText(MessagesUI.getString("FrmUnwrapUI.disconnectaction"));
					btnConnect.setActionCommand(ConnectAction.DISCONNECT);
					DefaultMutableTreeNode root = (DefaultMutableTreeNode) treeUnits.getModel().getRoot();
					treeUnits.expandPath(new TreePath(root.getPath()));

				} else {
					disConnected();
					btnConnect.setText(MessagesUI.getString("FrmUnwrapUI.connectaction"));
					btnConnect.setActionCommand(ConnectAction.CONNECT);
				}
			}
		});
		
		btnUnwrap.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				Unwrapper unwrapper = new Unwrapper();
				taOutput.setText(unwrapper.unwrap(taOutput.getText()));
				taOutput.selectAll();
				taOutput.requestFocus();
				
			}
		});
		treeUnits.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
		treeUnits.addTreeSelectionListener(new TreeSelectionListener() {
			public void valueChanged(TreeSelectionEvent e) {
				DefaultMutableTreeNode node = (DefaultMutableTreeNode) treeUnits.getLastSelectedPathComponent();
				if (node == null || node.getParent() == null)
					return;
				String name = (String) node.getUserObject();
				String type = (String) ((DefaultMutableTreeNode)node.getParent()).getUserObject();
				
				DBSchemaUtil dbSchemaUtil = new DBSchemaUtil();
				String source = dbSchemaUtil.getSource(type, name);
				taOutput.setText(source);
			}
		});
		
		SaveAsAction.getInstance().addSavable(this);
	}

	/**
	 * UI actions for connection enabled
	 */
	private void connected() {
		try {
			if (DBConnections.getConnection() != null) {
				HashMap<String, DefaultMutableTreeNode> map = load();
				Iterator<DefaultMutableTreeNode> i = map.values().iterator();
				while (i.hasNext()) {
					DefaultMutableTreeNode DefaultMutableTreeNode = i.next();
					DefaultMutableTreeNode.setAllowsChildren(true);
					((DefaultMutableTreeNode) treeUnits.getModel().getRoot()).add(DefaultMutableTreeNode);
				}
			}
		} catch (SQLException s) {
			s.printStackTrace();
		}
	}

	/**
	 * UI actions for connection disabled
	 */
	private void disConnected() {
		DefaultMutableTreeNode root = (DefaultMutableTreeNode) treeUnits.getModel().getRoot();
		root.removeAllChildren();
		root.setUserObject("USER");
		treeUnits.expandPath(new TreePath(root.getPath()));
		TreeModel treeModel = new DefaultTreeModel(root);
		treeUnits.setModel(treeModel);
		comboBoxSearch.removeAllItems();
		comboBoxSearch.setSelectedItem(null);
		taOutput.setText("");
	}

	/**
	 * Find and expand tree node when found
	 */
	private TreePath find(DefaultMutableTreeNode root, String s) {
		@SuppressWarnings("unchecked")
		Enumeration<DefaultMutableTreeNode> e = root.depthFirstEnumeration();
		while (e.hasMoreElements()) {
			DefaultMutableTreeNode node = e.nextElement();
			if (node.toString().equalsIgnoreCase(s)) {
				return new TreePath(node.getPath());
			}
		}
		return null;
	}

	/**
	 * Load DB objects after connection has been established
	 */
	private HashMap<String, DefaultMutableTreeNode> load() throws SQLException {
		Connection conn = DBConnections.getConnection();
		String url = conn.getMetaData().getURL();
		DBSchemaUtil dbSchemaUtil = new DBSchemaUtil();
		ArrayList<String[]> userObjectsList = dbSchemaUtil.getSchemaObjectNames(conn);

		((DefaultMutableTreeNode) treeUnits.getModel().getRoot()).removeAllChildren();

		HashMap<String, DefaultMutableTreeNode> map = new HashMap<String, DefaultMutableTreeNode>();
		for (String[] dbObject : userObjectsList) {
			if (map.get(dbObject[0]) == null)
				map.put(dbObject[0], new DefaultMutableTreeNode(dbObject[0]));
			map.get(dbObject[0]).add(new DefaultMutableTreeNode(dbObject[1]));
			comboBoxSearch.addItem(dbObject[1]);
		}
		comboBoxSearch.setSelectedItem(null);
		((DefaultMutableTreeNode) treeUnits.getModel().getRoot())
				.setUserObject(conn.getSchema() + url.substring(url.indexOf("@")));
		return map;
	}

	public String getSavableText() {
		return taOutput.getText();
	}

}
