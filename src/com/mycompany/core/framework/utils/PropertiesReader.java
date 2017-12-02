package com.mycompany.core.framework.utils;

import org.testng.internal.PropertiesFile;


public class PropertiesReader {
	public  String filename;
	
	
	public PropertiesReader(String filename){
	this.filename=filename;
	}
	
	
	
	public String readProp(String key){
		try{
		PropertiesFile pf= new PropertiesFile(filename);
			
		return pf.getProperties().getProperty(key);
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}

	
}
