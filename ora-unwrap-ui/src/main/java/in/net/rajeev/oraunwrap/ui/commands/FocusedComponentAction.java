package in.net.rajeev.oraunwrap.ui.commands;

import java.awt.event.ActionEvent;

import javax.swing.text.JTextComponent;
import javax.swing.text.TextAction;

public class FocusedComponentAction extends TextAction {
	private static final long serialVersionUID = 1L;
	
	private static FocusedComponentAction instance = new FocusedComponentAction();
	
	private FocusedComponentAction() {
        super("focused-component");
    }
	
	public static FocusedComponentAction getInstance() {
		return instance;
	}

    
    /** adding this method because of protected final getFocusedComponent */
    JTextComponent getFocusedComponent2() {
        return getFocusedComponent();
    }
    public  void actionPerformed(ActionEvent evt){}
}
