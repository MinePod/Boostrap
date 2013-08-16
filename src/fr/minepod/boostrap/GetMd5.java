package fr.minepod.boostrap;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import org.bouncycastle.util.encoders.Hex;

public class GetMd5
{
  private BufferedReader lect;
  private FileInputStream fis;

  public boolean VerifyMd5(File Md5F, File f)
  {
    if ((Md5F.exists()) && (f.exists()) && (f.length() > 0L)) {
      String tmp = "";
      try
      {
        this.lect = new BufferedReader(new FileReader(Md5F));
        if (this.lect.ready())
          tmp = tmp + this.lect.readLine();
      }
      catch (NullPointerException e) {
        e.printStackTrace();
      } catch (IOException e) {
        e.printStackTrace();
      }

      System.out.println("Expected MD5: " + tmp);
      try
      {
        MessageDigest md = MessageDigest.getInstance("MD5");
        this.fis = new FileInputStream(f);
        byte[] dataBytes = new byte[1024];
        int nread = 0;

        while ((nread = this.fis.read(dataBytes)) != -1) {
        	md.update(dataBytes, 0, nread);
        }

        byte[] mdbytes = md.digest();

        String result = new String(Hex.encode(mdbytes));

        System.out.println("Current MD5: " + result);
        
        if (tmp.equals(result)) {
        	return true;
        }
        return false;
      }
      catch (NoSuchAlgorithmException e)
      {
        e.printStackTrace();
        return false;
      }
      catch (IOException e) {
        e.printStackTrace();
        return false;
      }
    }
    System.out.println("The file not exists!");
    return false;
  }
}