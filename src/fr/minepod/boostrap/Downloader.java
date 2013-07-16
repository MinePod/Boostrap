package fr.minepod.boostrap;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;

public class Downloader {
	 private FileOutputStream fos;
	 private InputStream rbc;
	 private URL website;
	 private byte[] buffer;
	 private double fileLength = 0.0D;
	 private double totalBytesRead = 0.0D;
	 private int bytesRead;
	 private int percent;
	 private DisplayDownload DisplayDownload = new DisplayDownload();
	 private ReadFile ReadFile = new ReadFile();
	 private GetMd5 GetMd5 = new GetMd5();
   	 private URLConnection urlConnection;
	 private String AppDataPath;
	 private String latestVersionLauncher;
	 private String MinePod;
	 private File MinePodLaunch;
	 private String latestVersionLibraries;
	 private String MinePodLibrariesZip;
	 private String latestVersionVersions;
	 private String MinePodVersionsZip;
	private String Minecraft;
	private String MinePodAppData;
	 
	 private void Downloader(URL website, FileOutputStream fos) {
		    System.out.println("Starting...");
		    try {
		      System.out.println("Getting url...");
		      urlConnection = website.openConnection();
		      fileLength = urlConnection.getContentLength();

		      System.out.println("Openning stream...");
		      this.rbc = website.openStream();

		      System.out.println("Reading stream...");
		      this.buffer = new byte[153600];
		      this.totalBytesRead = 0.0D;
		      this.bytesRead = 0;

		      this.DisplayDownload.pack();
		      this.DisplayDownload.setVisible(true);

		      while ((this.bytesRead = this.rbc.read(this.buffer)) > 0) {
		        fos.write(this.buffer, 0, this.bytesRead);
		        this.buffer = new byte[153600];
		        this.totalBytesRead += this.bytesRead;
		        this.percent = ((int)Math.round(this.totalBytesRead / fileLength * 100.0D));
		        System.out.println("Bytes readed: " + (int)this.totalBytesRead + "/" + (int)fileLength + " " + this.percent + "%");
		        this.DisplayDownload.Update(this.percent * 20);
		      }

		    } catch (MalformedURLException e) {
		      e.printStackTrace();
		    } catch (IOException e) {
		      e.printStackTrace();
		    }

		    System.out.println("Downloading complete!");
		  }
	 
	 public void Clean(String PathToClean) {
			new File(PathToClean + "\\Launcher.txt").delete();
			new File(PathToClean + "\\Launcher.md5").delete();
			System.out.println("Directory cleaned up!");
	 }
	 
	 public void Launch() {
		 try {
			String OS = System.getProperty("os.name").toUpperCase();
			if(OS.contains("WIN")) {
				AppDataPath = System.getenv("APPDATA");
				MinePod = "\\.MinePod";
				Minecraft = "\\.minecraft";
			} else if(OS.contains("MAC")) {
				AppDataPath = System.getProperty("user.home") + "/Library/Application " + "Support";
				MinePod = "\\MinePod";
				Minecraft = "\\minecraft";
			} else if(OS.contains("NUX")) {
			    AppDataPath = System.getProperty("user.home");
				MinePod = "\\.MinePod";
				Minecraft = "\\.minecraft";
			} else {
				AppDataPath =  System.getProperty("user.dir");
				MinePod = "\\.MinePod";
				Minecraft = "\\.minecraft";
			}
			
			System.out.println(AppDataPath);
			
			MinePodAppData = AppDataPath + MinePod;
			MinePodLaunch = new File(MinePodAppData + "\\Launcher.jar");
			MinePodLibrariesZip = MinePodAppData + "\\Libraries.zip";
			MinePodVersionsZip = MinePodAppData + "\\Versions.zip";
			
			if(!new File(MinePodAppData).exists()) {
				new File(MinePodAppData).mkdir();
			}
			
			if(!new File(MinePodAppData + "\\Libraries").exists()) {
				new File(MinePodAppData + "\\Libraries").mkdir();
			}
			
			if(!new File(MinePodAppData + "\\Versions").exists()) {
				new File(MinePodAppData + "\\Versions").mkdir();
			}
			
			Clean(MinePodAppData);
			
			
			Downloader(new URL("http://assets.minepod.fr/launcher/versions/launcher.txt"), new FileOutputStream(MinePodAppData + "\\Launcher.txt"));
			latestVersionLauncher = ReadFile.ReadFile(MinePodAppData + "\\Launcher.txt", StandardCharsets.UTF_8);
			
			Downloader(new URL("http://assets.minepod.fr/launcher/md5.php?file=" + latestVersionLauncher), new FileOutputStream(MinePodAppData + "\\Launcher.md5"));
			
			if(!GetMd5.VerifyMd5(new File(AppDataPath + MinePod + "\\Launcher.md5"), MinePodLaunch)) {
				MinePodLaunch.delete();
				Downloader(new URL(latestVersionLauncher), new FileOutputStream(MinePodLaunch));
			}
			
			
			new MPLoader(MinePodLaunch.getAbsolutePath());
			
			System.exit(0);
			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	 }
}
