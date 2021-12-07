package com.hanu.chat.customUI;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;


public class TestFile {
	public static void main(String[] args) {

		File file = new File("C:/Users/nhbang/Desktop/NguyenHuuBang_CV_Intern.pdf");
	       
	    readContentIntoByteArray(file);
	}
	
	private static byte[] readContentIntoByteArray(File file)
	   {
	      FileInputStream fileInputStream = null;
	      byte[] bFile = new byte[(int) file.length()];
	      try
	      {
	         //convert file into array of bytes
	         fileInputStream = new FileInputStream(file);
	         fileInputStream.read(bFile);
	         fileInputStream.close();
	         for (int i = 0; i < bFile.length; i++)
	         {
	            System.out.print((char) bFile[i]);
	         }
	      }
	      catch (IOException e)
	      {
	         e.printStackTrace();
	      }
	      return bFile;
	   }
}
