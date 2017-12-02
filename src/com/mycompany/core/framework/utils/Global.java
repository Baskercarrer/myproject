package com.mycompany.core.framework.utils;

import java.util.Map;


public class Global 
{	
	public static String BrowserURL="http://accounts.google.com";
	
	public static String Domain="";
	public static String gBrowserType = "firefox";
	public static String gURL = "";
	public static String gApplicationErrMsg = null;
	public static String gErrMsg = null;
	public static Map<String, String> loginObjMap = null;
	public static String sScriptName;
	public static boolean bAssertion = false;
	public static String sScreenShot="";
	public static String gFileSize = null;
	public static String gQuotaUsed = null;
	public static String sDownloadPath ="";
	public static String gStepDescription="";
	public static String gDateFormat="EEE MMM dd yyyy hh:mma";
	
	//Screen names and error messages
	public static String sAccessAdminScreen = "Access Administration";
	public static String gRelTestDataPath = "\\src\\com\\edpulsecheck\\test\\testdata\\";
	
	

	/*public static String sFilePath = Constants.sReportFolderPath + Utility.getSlashType()
					+Application.getUniqueReportName("Report")+".html";*/
	public static String sFilePath = Constants.sReportFolderPath + Utility.getSlashType()
	+Application.getUniqueReportName("Report")+".html";
	
}
