package fr.minepod.boostrap;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class ClassFile {
	public static String ReadFile(String path) throws IOException {
	    BufferedReader br = new BufferedReader(new FileReader(path));
	    try {
	        StringBuilder sb = new StringBuilder();
	        String line = br.readLine();

	        while (line != null) {
	            sb.append(line);
	            sb.append("\n");
	            line = br.readLine();
	        }
	        return sb.toString();
	    } finally {
	        br.close();
	    }
	}
	
	public static void WriteFile(String path, String stringToWrite) throws IOException {
		File file = new File(path);
 
		if(!file.exists()) {
			file.createNewFile();
		}
 
		FileWriter fw = new FileWriter(file.getAbsoluteFile());
		BufferedWriter bw = new BufferedWriter(fw);
		bw.write(stringToWrite);
		bw.close();
	}
	
	public static void Delete(File file) {		
		if(file.exists()) {
			if(file.isDirectory() && file.list().length != 0) {
				String files[] = file.list();
				for (String temp : files) {
	        		File fileDelete = new File(file, temp);
	        		Delete(fileDelete);
	        	}
			}
			file.delete();
		}
	}
}
