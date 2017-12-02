package com.mycompany.core.framework.reports;

import com.google.common.io.Files;
//import TestExecutionListener; 
import java.io.File;
import java.io.IOException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.ITestResult;
import org.testng.Reporter;
public class CustomReporter
{
	
	//static int value = 0; 
  public static void log(String paramString)
  {
    TestExecutionListener.lineNumber = java.lang.Thread.currentThread().getStackTrace()[2].getLineNumber();
    Reporter.log(paramString);
  }

  public static void log(String paramString, boolean paramBoolean)
  {
    TestExecutionListener.lineNumber = java.lang.Thread.currentThread().getStackTrace()[2].getLineNumber();
    Reporter.log(paramString, paramBoolean);
  }

  public static void logWithScreenShot(String paramString)
  {
    TestExecutionListener.lineNumber = java.lang.Thread.currentThread().getStackTrace()[2].getLineNumber();
    Reporter.log(paramString);
    String str1 = "[" + Reporter.getCurrentTestResult().getTestClass().getName() + "]" + Reporter.getCurrentTestResult().getName();
    String str2 = takeScreenShot(TestExecutionListener.reportsDirectory + System.getProperty("file.separator") + "executions" + System.getProperty("file.separator") + "Execution_" + TestExecutionListener.executionCount + System.getProperty("file.separator") + "teststeps" + System.getProperty("file.separator") + "screenshots" + System.getProperty("file.separator") + str1, Reporter.getCurrentTestResult());
    TestExecutionListener.screenShotNames.add(str2);
  }

  private static String takeScreenShot(String paramString, ITestResult paramITestResult)
  {
    File localFile = null;
    String str = "";
    int i = Integer.parseInt(paramITestResult.getAttribute("invokeCountAtttrib").toString());
    localFile = new File(paramString + "_Iteration" +i + "_Step_" + Reporter.getOutput(Reporter.getCurrentTestResult()).size() + ".jpg");
   // value++;
    try
    {
      if ((TestEnvironmentProperties.driver instanceof TakesScreenshot))
      {
        byte[] arrayOfByte = ((TakesScreenshot)TestEnvironmentProperties.driver).getScreenshotAs(OutputType.BYTES);
        try
        {
          Files.write(arrayOfByte, localFile);
          str = localFile.getName();
        }
        catch (IOException localIOException)
        {
        }
      }
    }
    catch (Exception localException)
    {
    }
    return str;
  }
}