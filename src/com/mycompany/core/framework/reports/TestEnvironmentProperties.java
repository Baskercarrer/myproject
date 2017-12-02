package com.mycompany.core.framework.reports;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.internal.BuildInfo;

public class TestEnvironmentProperties
{
  private static BuildInfo driverInfo = new BuildInfo();
  public static WebDriver driver;
  public static final String OS_Name = System.getProperty("os.name");
  public static final String OS_Architecture = System.getProperty("os.arch");
  public static final String OS_Version = System.getProperty("os.version");
  public static final String JAVA_Version = System.getProperty("java.version");
  public static final String WebDriver_Version = driverInfo.getReleaseLabel();
  public static final String WebDriver_Revision = driverInfo.getBuildRevision();
  public static String Browser_Name;
  public static String Browser_Version;
}