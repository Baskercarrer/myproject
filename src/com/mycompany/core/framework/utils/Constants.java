package com.mycompany.core.framework.utils;

public class Constants {
	public static String sPageLoadTimeout = "30000";
	public static long lUpLoadTimeout = 600000;
	public static long lElementVisibleTimeout = 20000;
	public static long lElementVisibleDefaultTimeout = 30000;
	public static long lWaitForTimeout = 2000;
	public static String sTestDataFolderPath = "TestData";
	public static String sConfigFolderPath = "environment";
	public static String sReportFolderPath = "Reports";
	public static String sProfilePath = "lib";
	public static String sConfigFileName = "config.properties";
	//Email details
	public static String sSubject = "Automated Email:TestCase execution reseults";
	public static String sBody = "Hello Everyone, \n\nPlease find the attached Smoke Automation Test Suite Execution Report \n\n Thanks";
	public static String sSMTP = "smtp";
	public static int iPort = 587;
	public static int iMailRefresh = 50;
	public static final String propertyFile = "sugarCRM.properties";
}