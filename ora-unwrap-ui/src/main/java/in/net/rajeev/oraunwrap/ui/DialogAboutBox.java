package in.net.rajeev.oraunwrap.ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JEditorPane;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.border.EmptyBorder;

import in.net.rajeev.oraunwrap.ui.helpers.UnwrapperIconSet;

public class DialogAboutBox extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private static DialogAboutBox instance = new DialogAboutBox();

	public static DialogAboutBox getInstance() {
		if(instance == null)
			instance = new DialogAboutBox();
		return instance;
	}

	/**
	 * Create the dialog.
	 */
	private DialogAboutBox() {
		setTitle("About Unwrapper");
		setModal(true);
		setIconImage(UnwrapperIconSet.getMainIcon());
		setBounds(100, 100, 589, 300);
		setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setLayout(new FlowLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		{
			JEditorPane editorPane = new JEditorPane();
			editorPane.setEditable(false);
			editorPane.setContentType("text/html");
			contentPanel.add(editorPane);
			editorPane.setText("<html>Unrwapper for Oracle database wrapped units.<p>"
					+ "Copyright © 2021 Rajeev Sreedharan");
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			JButton okButton = new JButton("OK");
			buttonPane.add(okButton);
			getRootPane().setDefaultButton(okButton);
			okButton.addActionListener(new ActionListener() {
				
				public void actionPerformed(ActionEvent e) {
					DialogAboutBox.this.setVisible(false);
				}
			});
		}

		addEscapeListener(this);
		
		pack();

	}
	
	public void addEscapeListener(final JDialog dialog) {
	    ActionListener escListener = new ActionListener() {
	        public void actionPerformed(ActionEvent e) {
	            dialog.setVisible(false);
	        }
	    };

	    dialog.getRootPane().registerKeyboardAction(escListener,
	            KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0),
	            JComponent.WHEN_IN_FOCUSED_WINDOW);

	}

}
