package in.net.rajeev.oraunwrap.ui;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class MessagesUI {
	private static final String BUNDLE_NAME = "in.net.rajeev.oraunwrap.ui.messagesui";

	private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle(BUNDLE_NAME);

	private MessagesUI() {
	}

	public static String getString(String key) {
		try {
			return RESOURCE_BUNDLE.getString(key);
		} catch (MissingResourceException e) {
			return '!' + key + '!';
		}
	}
}
