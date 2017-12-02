package com.mycompany.core.framework.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Random;

import javax.swing.JFileChooser;


@SuppressWarnings("unused")
public class Utility {
	private static Map<String, String> objMap = null;
	private static BufferedReader bufRdr = null;
	private static String FILE_NAME = null;

	/**
	 * Purpose:Reads testData based on test script name
	 * @param String sFileName, String sTestScriptName
	 * @return Map
	 * @author UST-TACOE
	 */
	public static Map<String, String> readTestData(String sFileName, String sTestScriptName){
		int iLineNumber = -1;
        String[] sValues = null;
        String sLine = null;
		String sTestCaseName;
		String headers[] = null;
		
		if (!sFileName.equals(FILE_NAME)) {
			File dFile = new File(sFileName);

			try {
				bufRdr = new BufferedReader(new FileReader(dFile));
				objMap = new HashMap<String, String>();
				
				//reads the file line by line
				while ((sLine = bufRdr.readLine()) != null) {
					
					//reads the headings/columnNames in .csv file
					if (iLineNumber == -1) {
						headers = sLine.split(",");
					}
					//reads the data in .csv file
					else {
						sValues = sLine.split(",");
						sTestCaseName = sValues[0];
						
						//if the testScript name matches with the given testScript name, puts data related to that script in map
						if (sTestCaseName.equalsIgnoreCase(sTestScriptName)) {
							for (int iLoopCounter = 0; iLoopCounter < sValues.length; iLoopCounter++) {
								objMap.put(headers[iLoopCounter], sValues[iLoopCounter]);
							}
						}
					}
					iLineNumber++;
				}
				//close the file
				bufRdr.close();
			}
			catch (IOException e) {
				
			}
		}
		return objMap;
	}
	
	/**
	 * Purpose:Reads configuration details from properties file
	 * @param String sFileName
	 * @return Map
	 * @author UST-TACOE
	 */
	public static Map<String, String> readConfigData(String sFileName) {
		try{
			Map<String,String> objMap = new HashMap<String,String>();
			Properties propFile = new Properties();
			
			File oFile = new File(sFileName);
			propFile.load(new FileReader(oFile));
			String[] keys = new String[propFile.size()];
			propFile.keySet().toArray(keys);
			for(String key:keys)
			{
				objMap.put(key, propFile.getProperty(key));
			}
			return objMap;
		}
		catch(Exception e){
			Global.gErrMsg =e.getMessage();
			return null;
		}
    }
	
	/**
	 * Purpose:Sends Email
	 * @return boolean
	 * @author UST-TACOE
	 */ 
/*	public static boolean sendEmail() {
		try {
			Properties props = new Properties();
			props.put("mail.smtp.host", Global.sHostName);
			
			if(Global.sHostName.contains("gmail")){
				props.put("mail.smtp.port", Constants.iPort); 
				props.put("mail.smtp.starttls.enable","true");
			}
			Session session = Session.getInstance(props, null);

			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress(Global.sFromAddress));
			InternetAddress[] toAddresses = InternetAddress.parse(Global.sStakeHoldersList, false);
			message.setRecipients(Message.RecipientType.TO, toAddresses);
			Calendar calendar = Calendar.getInstance();
			message.setSubject(Constants.sSubject + new Date());
			message.setSentDate(new Date());

			MimeBodyPart objBody = new MimeBodyPart();
			objBody.setText(Constants.sBody);
			MimeBodyPart objFiles = new MimeBodyPart();
			
			//Attaching file to the message
			objFiles.attachFile(Global.sFilePath);
			//Attaches file and adds body to the message
			Multipart multiPart = new MimeMultipart();
			multiPart.addBodyPart(objBody);
			multiPart.addBodyPart(objFiles);

			message.setContent(multiPart);
			Transport transport = session.getTransport(Constants.sSMTP);
			
			//Sends mail to the specified users
			if(Global.sHostName.equals("smtp.UST-TACOE.com"))
				transport.connect();
			else
				transport.connect(Global.sHostName, Global.sUserName, Global.sPassword);
			transport.sendMessage(message, message.getAllRecipients());	
			
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}  */
	public static String getOS(){
		  return System.getProperty("os.name");

	  }
	public static String getSlashType(){
		  String sOSName = getOS();
		  if (sOSName.equalsIgnoreCase("Linux")){
		  	return "/";
		  }
		  return "\\";
	  }
	  
	public static void reportingResults(String sStatus,String sStepName,String sStepDesc){
		testResult.Report(sStepDesc, sStatus, sStepName);
	}

	public static String getFileType(String sFileName){
		JFileChooser chooser = new JFileChooser();
	    File ofile = new File(sFileName);
	    
	    String sfileType = chooser.getTypeDescription(ofile);
	    return sfileType;
	}
	
	public static int randumNum(int min, int max) {
	    // NOTE: Usually this should be a field rather than a method
	    // variable so that it is not re-seeded every call.
	    Random rand = new Random();

	    // nextInt is normally exclusive of the top value,
	    // so add 1 to make it inclusive
	    int randomNum = rand.nextInt((max - min) + 1) + min;

	    return randomNum;
	}
	
	public static String getFileSize(String sFileName) {
		String sSetBytes="";
		double lFilesize=0;
	    File oFile = new File(sFileName);
	    if (!oFile.exists() || !oFile.isFile()) {
	      Global.gErrMsg="File doesn't exist";
	      return "";
	    }
	    lFilesize=oFile.length();
	    //Here we get the actual size
	    if (lFilesize >= 1073741824) {
	    	
		    sSetBytes = Math.round((lFilesize / 1024 / 1024 / 1024)) + " GB";

		    }
		    else if (lFilesize >= 1048576) {
		    sSetBytes= Math.round((lFilesize  / 1024 / 1024)) + " MB";
			}
		else if (lFilesize>= 1024) {
			System.out.println(lFilesize);
			System.out.println(4779/1024);
		    sSetBytes= Math.round((lFilesize  / 1024)) + " KB";
			}
		else if (lFilesize< 1024) {
		    sSetBytes= lFilesize + " Bytes";
		}
		
	    return sSetBytes;
	  }
	
	public static void closeprocess(String strProcessName) throws IOException 
	 {
		
		Runtime.getRuntime().exec("taskkill /F /IM "+ strProcessName);
       }
	
	public static void closeDriverprocess() throws IOException 
	 {
		if(Global.gBrowserType.equalsIgnoreCase("IE"))
		{
		Runtime.getRuntime().exec("taskkill /F /IM IEDriverServer.exe");
		}
      }	
}


