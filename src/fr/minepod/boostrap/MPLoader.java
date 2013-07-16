package fr.minepod.boostrap;

public class MPLoader {
	public MPLoader(String path) throws Exception {
		Runtime runtime = Runtime.getRuntime();
			runtime.exec("java -jar -Xmx1G " + path);
		}
}
