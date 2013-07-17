package fr.minepod.boostrap;

public class JarLoader {
	public JarLoader(String path) throws Exception {
		Runtime runtime = Runtime.getRuntime();
			runtime.exec("java -jar -Xmx1G " + path);
		}
}
