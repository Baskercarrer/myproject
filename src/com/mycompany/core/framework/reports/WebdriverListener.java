package com.mycompany.core.framework.reports;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.events.AbstractWebDriverEventListener;

public class WebdriverListener extends AbstractWebDriverEventListener
{
  String exceptionMessage = null;

  @Override
public void beforeNavigateTo(String paramString, WebDriver paramWebDriver)
  {
    try
    {
       TestEnvironmentProperties.driver = paramWebDriver; //com.reports.
      setBrowserProperties(paramWebDriver);
    }
    catch (Exception localException)
    {
    }
  }

  private void setBrowserProperties(WebDriver paramWebDriver)
  {
    String str1 = (String)((JavascriptExecutor)paramWebDriver).executeScript("return navigator.userAgent;", new Object[0]);
    String str2 = "";
    String str3 = "";
    if (str1.contains("MSIE"))
    {
      str2 = str1.substring(str1.indexOf("MSIE") + 5, str1.indexOf("Windows NT") - 2);
      str3 = "Internet Explorer";
      TestEnvironmentProperties.Browser_Name = "Internet Explorer";
    }
    else if (str1.contains("Firefox/"))
    {
      str2 = str1.substring(str1.indexOf("Firefox/") + 8);
      str3 = "Mozilla Firefox";
       TestEnvironmentProperties.Browser_Name = "Mozilla Firefox"; //com.reports.
    }
    else if (str1.contains("Chrome/"))
    {
      str2 = str1.substring(str1.indexOf("Chrome/") + 7, str1.lastIndexOf("Safari/"));
      str3 = "Google Chrome";
       TestEnvironmentProperties.Browser_Name = "Google Chrome";//com.reports.
    }
    else if ((str1.contains("AppleWebKit")) && (str1.contains("Version/")))
    {
      str2 = str1.substring(str1.indexOf("Version/") + 8, str1.lastIndexOf("Safari/"));
      str3 = "Apple Safari";
      TestEnvironmentProperties.Browser_Name = "Apple Safari"; //com.reports.
    }
    else if (str1.startsWith("Opera/"))
    {
      str2 = str1.substring(str1.indexOf("Version/") + 8);
      str3 = "Opera";
       TestEnvironmentProperties.Browser_Name = "Opera";//com.reports.
    }
    else
    {
      str2 = "Unable to Determine";
      str3 = "Unable to Determine";
    }
     TestEnvironmentProperties.Browser_Name = str3; //com.reports.
     TestEnvironmentProperties.Browser_Version = str2; //com.reports.
  }

  @Override
public void onException(Throwable paramThrowable, WebDriver paramWebDriver)
  {
  }
}