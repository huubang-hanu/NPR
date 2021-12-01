package com.hanu.chat.database;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Support read/write object to file
 * @author Admin
 *
 */
public class FileIO {
	public void saveDataToFile(Object obj, String fileName){
			try(
					FileOutputStream f = new FileOutputStream(new File(fileName), false);
					ObjectOutputStream o = new ObjectOutputStream(f);
			){
				o.writeObject(obj);
				
			} catch (IOException e) {
				e.printStackTrace();
			}
	
	}

	public Object readDataFromFile(String fileName) {
		if(new File(fileName).length() == 0 ) {
			return null;
		}
		
		try(
				FileInputStream fi = new FileInputStream(new File(fileName));
				ObjectInputStream oi = new ObjectInputStream(fi);
		) {
			 
			return oi.readObject();
			
		} catch (IOException | ClassNotFoundException e) {
			
		}
		
		return null;
	}

}
