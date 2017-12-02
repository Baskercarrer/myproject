package com.mycompany.core.framework.utils;

//import java.io.File; 

import autoitx4java.AutoItX; 

import com.jacob.com.LibraryLoader;

public class SeleniumFileUploadHandler {
	
	public  void uploadFile(String path, String browser){
//		File file = new File("lib", "jacob-1.18-M2-x64.dll"); //path to the jacob dll 
//		System.setProperty(LibraryLoader.JACOB_DLL_PATH, file.getAbsolutePath();
        System.setProperty(LibraryLoader.JACOB_DLL_PATH, "C:\\Madhubabu\\ToolRelated\\Selenium\\AutoItX4Java\\jacob-1.18-M2\\jacob-1.18-M2-x64.dll");
        LibraryLoader.loadJacobLibrary();
        AutoItX x = new AutoItX(); 
	    if(browser.equalsIgnoreCase("chrome")){
	    	x.winActivate("Open");
	        if(x.winWaitActive("Open", "", 10)){
	            if(x.winExists("Open")){
	                x.sleep(500);
	                x.send(path);
	                x.send("{Enter}",false);
	              
	            }
	        }

	    }

	    if(browser.equalsIgnoreCase("firefox")){
//	    	x.winActivate("File Upload");
//	        if(x.winWaitActive("File Upload", "", 10)){
//	            if(x.winExists("File Upload")){
//	                x.sleep(500);
//	                x.send(path);	                
//	                x.send("{Enter}",false);
//
//	            }
//	        }
	    	
	    	
	    	x.winActivate("Opening bulkUploadErrors.csv");
	        
	            if(x.winExists("Opening bulkUploadErrors.csv")){
	                x.sleep(500);
	                //x.send(path);	
	                x.send("{DOWN}",false);

	                x.send("{Enter}",false);

	           
	        }
	    }

	    if(browser.equalsIgnoreCase("InternetExplorer")){
	    	
	    	x.winActivate("Choose File to Upload");
	    	if(x.winWaitActive("Choose File to Upload", "", 10)){
	            if(x.winExists("Choose File to Upload")){
	            	 x.send(path);
	            	 x.send("{Enter}",false);	        
	            }
	        }
	    }



	}


//	   public void test(){
//	       //Click on the Select button of the file upload
//	       
//
//uploadFile("Path", "chrome");
//	   }

}
