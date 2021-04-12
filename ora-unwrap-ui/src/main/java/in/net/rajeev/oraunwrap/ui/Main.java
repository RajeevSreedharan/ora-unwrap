package in.net.rajeev.oraunwrap.ui;

import java.awt.EventQueue;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import javax.swing.UIManager;

import com.formdev.flatlaf.FlatLightLaf;

public class Main {
	static File propFile = new File(System.getProperty("user.home") + File.separator + "oraunwrap.properties");
	static Properties properties = new Properties();

	public static void main(String[] args) {

		initProperties();

		try {
			UIManager.setLookAndFeel(new FlatLightLaf());
		} catch (Exception ex) {
			System.err.println("Failed to initialize LaF");
		}

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FrmUnwrapUI frame = new FrmUnwrapUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

	}

	private static void initProperties() {

		try {
			if (propFile.exists()) {
				FileInputStream propIPStream = new FileInputStream(propFile);
				properties.load(propIPStream);
				propIPStream.close();
			} else {
				FileOutputStream propOPStream = new FileOutputStream(propFile);
				properties.setProperty("last.saved.folder", System.getProperty("user.home"));
				properties.store(propOPStream, "ora-unwrap configuration file");
				propOPStream.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static Properties getProperties() {
		return properties;
	}

	public static void updateProperty(String key, String value) {
		try {
			FileOutputStream propOPStream = new FileOutputStream(propFile);
			properties.setProperty(key, value);
			properties.store(propOPStream, "ora-unwrap configuration file");
			propOPStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
