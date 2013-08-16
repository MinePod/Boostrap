package fr.minepod.boostrap;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import javax.swing.JOptionPane;

public class Downloader {
	 private InputStream rbc;
	 private byte[] buffer;
	 private double fileLength = 0.0D;
	 private double totalBytesRead = 0.0D;
	 private int bytesRead;
	 private int percent;
	 private DisplayDownload DisplayDownload = new DisplayDownload();
	 private GetMd5 GetMd5 = new GetMd5();
	 private Config Config = new Config();
	 private URLConnection urlConnection;
	 private String AppDataPath;
	 private String LauncherLocation;
	 private String Slash;
	 private File LauncherJar;
	 
	 private String LauncherLatestVersionUrl = Config.LauncherLatestVersionUrl;
	 private String BootstrapVersionUrl = Config.BootstrapVersionUrl;
	 private String BootstrapNewVersionUrl = Config.BootstrapNewVersionUrl;
	 private String GetMd5FileUrl = Config.GetMd5FileUrl;
	 private String LauncherName = Config.LauncherName;
	 private String BootstrapVersion = Config.BootstrapVersion;
	 
	 
	 private void DownloadFiles(URL website, FileOutputStream fos) {
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
		        this.DisplayDownload.Update(this.percent);
		      }

		    } catch (MalformedURLException e) {
		      e.printStackTrace();
		      javax.swing.JOptionPane.showMessageDialog(null, e.toString(), "Erreur", javax.swing.JOptionPane.ERROR_MESSAGE);
		      System.exit(0);
		    } catch (IOException e) {
		      e.printStackTrace();
		      javax.swing.JOptionPane.showMessageDialog(null, e.toString(), "Erreur", javax.swing.JOptionPane.ERROR_MESSAGE);
		      System.exit(0);
		    }

		    System.out.println("Downloading complete!");
		  }
	 
	 public void Clean(String PathToClean) {
			new File(PathToClean + "Launcher.md5").delete();
			new File(PathToClean + "Boostrap.txt").delete();
			System.out.println("Directory cleaned up!");
	 }
	 
	 public void Launch() {
		 try {
			String OS = System.getProperty("os.name").toUpperCase();
			if(OS.contains("WIN")) {
				AppDataPath = System.getenv("APPDATA");
				LauncherName = "\\." + LauncherName;
				Slash = "\\";
			} else if(OS.contains("MAC")) {
				AppDataPath = System.getProperty("user.home") + "/Library/Application " + "Support";
				LauncherName = "/" + LauncherName;
				Slash = "/";
			} else if(OS.contains("NUX")) {
			    AppDataPath = System.getProperty("user.home");
			    LauncherName = "/." + LauncherName;
				Slash = "/";
			} else {
				AppDataPath =  System.getProperty("user.dir");
				LauncherName = "/." + LauncherName;
				Slash = "/";
			}
			
			System.out.println(AppDataPath);
			
			LauncherLocation = AppDataPath + LauncherName;
			LauncherJar = new File(LauncherLocation + Slash + "Launcher.jar");
			
			if(!new File(LauncherLocation).exists()) {
				new File(LauncherLocation).mkdir();
			}
			
			Clean(LauncherLocation + Slash);
			
			DownloadFiles(new URL(BootstrapVersionUrl), new FileOutputStream(LauncherLocation + Slash + "bootstrap.txt"));
			
			if(!fr.minepod.boostrap.ClassFile.ReadFile(LauncherLocation + Slash + "bootstrap.txt").startsWith(BootstrapVersion)) {
				JOptionPane.showMessageDialog(null, "Une nouvelle version est disponible: " + BootstrapNewVersionUrl, "Nouvelle version disponible", JOptionPane.WARNING_MESSAGE);
				System.exit(0);
			}
			
			DownloadFiles(new URL(GetMd5FileUrl + LauncherLatestVersionUrl), new FileOutputStream(LauncherLocation + Slash + "Launcher.md5"));
			
			if(!GetMd5.VerifyMd5(new File(LauncherLocation + Slash + "Launcher.md5"), LauncherJar)) {
				LauncherJar.delete();
				DownloadFiles(new URL(LauncherLatestVersionUrl), new FileOutputStream(LauncherJar));
			}
			
			
			new JarLoader(LauncherJar.getAbsolutePath());
			
			System.exit(0);
			
		} catch (IOException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e.toString(), "Erreur", JOptionPane.ERROR_MESSAGE);
			System.exit(0);
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e.toString(), "Erreur", JOptionPane.ERROR_MESSAGE);
			System.exit(0);
		}
	 }
}
