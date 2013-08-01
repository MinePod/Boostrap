package fr.minepod.boostrap;

import java.awt.Desktop;
import java.io.File;

public class JarLoader {
	public JarLoader(String path) throws Exception {
		String OS = System.getProperty("os.name").toUpperCase();
		if(OS.contains("WIN")) {
			Runtime.getRuntime().exec("java -jar -Xmx1G \"" + path + "\"");
		} else if(OS.contains("MAC")) {
			Desktop.getDesktop().open(new File(path));
		} else if(OS.contains("NUX")) {
			Runtime.getRuntime().exec("java -jar -Xmx1G \"" + path + "\"");
		} else {
			Runtime.getRuntime().exec("java -jar -Xmx1G \"" + path + "\"");
		}
	}
}
