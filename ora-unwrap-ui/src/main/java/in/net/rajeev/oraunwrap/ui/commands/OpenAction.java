package in.net.rajeev.oraunwrap.ui.commands;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.io.File;

import javax.swing.AbstractAction;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

public class OpenAction  extends AbstractAction {
	Component parent;
	
	public OpenAction(Component parent) {
		super();
		this.parent = parent;
	}

	private static final long serialVersionUID = 1L;

	public void actionPerformed(ActionEvent e) {

		FileNameExtensionFilter fileNameExtensionFilter = new FileNameExtensionFilter("PL/SQL Binary (*.plb)", "plb");
		JFileChooser jf = new JFileChooser();
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
