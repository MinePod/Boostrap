package fr.minepod.boostrap;

import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;

public class MPLoader {
	public void addURL(URL url) throws Exception {
		  URLClassLoader classLoader = (URLClassLoader) ClassLoader.getSystemClassLoader();
		  Class clazz = URLClassLoader.class;

		  // Use reflection
		  Method method = clazz.getDeclaredMethod("addURL", new Class[] { URL.class });
		  method.setAccessible(true);
		  method.invoke(classLoader, new Object[] { url });
		}
}
