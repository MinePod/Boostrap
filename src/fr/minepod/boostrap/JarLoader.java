package fr.minepod.boostrap;

import java.awt.Desktop;
import java.io.File;

public class JarLoader {
	private Config Config = new Config();
	private String BootstrapVersion = Config.BootstrapVersion;
	public JarLoader(String path) throws Exception {
		String OS = System.getProperty("os.name").toUpperCase();
		if(OS.contains("WIN")) {
			Runtime.getRuntime().exec("java -jar -Xmx1G \"" + path + "\" " + BootstrapVersion);
		} else if(OS.contains("MAC")) {
			Desktop.getDesktop().open(new File(path));
		} else if(OS.contains("NUX")) {
			Runtime.getRuntime().exec("java -jar -Xmx1G \"" + path + "\" " + BootstrapVersion);
		} else {
			Runtime.getRuntime().exec("java -jar -Xmx1G \"" + path + "\" " + BootstrapVersion);
		}
	}
}
