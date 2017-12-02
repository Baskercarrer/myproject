package com.mycompany.core.framework.reports;

import com.google.common.io.Files;

/*import com.reports.TestEnvironmentProperties;
import com.reports.WebDriverExceptionDetails;
import com.reports.files.HtmlWriter;
import com.reports.files.JSWriter;
import com.reports.files.ReportFiles;
import com.reports.files.ReportsUtilities; */
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import javax.swing.JOptionPane;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.ITestContext;
import org.testng.ITestNGMethod;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.TestListenerAdapter;
import org.testng.annotations.Optional;
import org.testng.xml.XmlTest;

public class TestExecutionListener extends TestListenerAdapter {
	List<ITestResult> passedTests = new ArrayList();
	List<ITestResult> failedTests = new ArrayList();
	List<ITestResult> skippedTests = new ArrayList();
	public static List<ITestResult> configFailures = new ArrayList();
	ReportFiles reportFiles = null;
	ReportsUtilities reportsHelper = null;
	HtmlWriter htmlWriter = null;
	JSWriter jsWriter = null;
	List<String> allTCs = null;
	public static boolean configFail = false;
	public static int lineNumber = -1;
	public static List<String> screenShotNames = new ArrayList();
	public static String reportsDirectory =  System.getProperty("user.dir") + System.getProperty("file.separator") + "Reports";
	public static int executionCount = 0;
	static int serialNum = 1;
	PrintWriter out = null;
	Properties properties = null;
	
	 
	public  void setUserReportDirectory( @Optional()  final String userDir)
	{
		if(userDir.isEmpty())
		{
			reportsDirectory = System.getProperty("user.dir") + System.getProperty("file.separator") + "Reports";
		}
		else
		{
			reportsDirectory = userDir ; //System.getProperty("user.dir") + System.getProperty("file.separator") + "Reports";
		}
		
	}

	@Override
	public void onConfigurationFailure(ITestResult paramITestResult) {
		configFail = true;
		ITestNGMethod localITestNGMethod = TestInvokeListener.invokedMethod
				.getTestMethod();
		if (localITestNGMethod.isBeforeSuiteConfiguration())
			paramITestResult.setAttribute("configAttrib", "Before Suite");
		else if (localITestNGMethod.isBeforeTestConfiguration())
			paramITestResult.setAttribute("configAttrib", "Before Test");
		else if (localITestNGMethod.isBeforeClassConfiguration())
			paramITestResult.setAttribute("configAttrib", "Before Class");
		else if (localITestNGMethod.isBeforeGroupsConfiguration())
			paramITestResult.setAttribute("configAttrib", "Before Groups");
		else if (localITestNGMethod.isBeforeMethodConfiguration())
			paramITestResult.setAttribute("configAttrib", "Before Method");
		else if (localITestNGMethod.isAfterMethodConfiguration())
			paramITestResult.setAttribute("configAttrib", "After Method");
		else if (localITestNGMethod.isAfterGroupsConfiguration())
			paramITestResult.setAttribute("configAttrib", "After Groups");
		else if (localITestNGMethod.isAfterClassConfiguration())
			paramITestResult.setAttribute("configAttrib", "After Class");
		else if (localITestNGMethod.isAfterTestConfiguration())
			paramITestResult.setAttribute("configAttrib", "After Test");
		else if (localITestNGMethod.isAfterSuiteConfiguration())
			paramITestResult.setAttribute("configAttrib", "After Suite");
		configFailures.add(paramITestResult);
	}

	@Override
	public void onTestFailure(ITestResult paramITestResult)
  {
    for (StackTraceElement localObject3 : paramITestResult.getThrowable().getStackTrace())
      if (paramITestResult.getTestClass().getName().equals(localObject3.getClassName()))
      {
        if (lineNumber + 1 == localObject3.getLineNumber())
        {
          paramITestResult.setAttribute("exceptionLineAttrib", "true");
          break;
        }
        paramITestResult.setAttribute("exceptionLineAttrib", "false");
        break;
      }
    
    Object var = paramITestResult.getThrowable().toString();
    Object localObject2 = var;
    if (((String)var).contains(":"))
      localObject2 = ((String)var).substring(0, ((String)var).indexOf(":")).trim();
    else
      var = "";
    try
    {
      localObject2 = getExceptionClassName((String)localObject2, (String)var);
      if (((String)localObject2).equals("Assertion Error"))
      {
        if (((String)var).contains(">"))
        {
          localObject2 = (String)localObject2 + ((String)var).substring(((String)var).indexOf(":"), ((String)var).lastIndexOf(">") + 1).replace(">", "\"");
          localObject2 = ((String)localObject2).replace("<", "\"");
        }
        if (paramITestResult.getThrowable().getMessage().trim().length() > 0)
          localObject2 = paramITestResult.getThrowable().getMessage().trim();
      }
      else if (((String)var).contains("{"))
      {
        localObject2 = (String)localObject2 + ((String)var).substring(((String)var).indexOf("{"), ((String)var).lastIndexOf("}"));
        localObject2 = ((String)localObject2).replace("{\"method\":", " With ").replace(",\"selector\":", " = ");
      }
      else if ((((String)localObject2).equals("Unable to connect Browser")) && (((String)var).contains(".")))
      {
        localObject2 = (String)localObject2 + ": Browser is Closed";
      }
      else if (((String)localObject2).equals("WebDriver Exception"))
      {
        localObject2 = paramITestResult.getThrowable().getMessage();
      }
    }
    catch (ClassNotFoundException localClassNotFoundException)
    {
    }
    catch (Exception localException1)
    {
    }
    localObject2 = ((String)localObject2).replace(">", "\"");
    localObject2 = ((String)localObject2).replace("<", "\"");
    String str = takeScreenShot(reportsDirectory + System.getProperty("file.separator") + "executions" + System.getProperty("file.separator") + "Execution_" + executionCount + System.getProperty("file.separator") + "teststeps" + System.getProperty("file.separator") + "screenshots" + System.getProperty("file.separator") + "[" + paramITestResult.getTestClass().getName() + "]" + paramITestResult.getName() + "_Iteration" + Integer.parseInt(paramITestResult.getAttribute("invokeCountAtttrib").toString()) + "_ExceptionSnap");
    paramITestResult.setAttribute("exceptionScreenShot", str);
    paramITestResult.setAttribute("exceptionAttrib", localObject2);
    try
    {
      setTestCaseEnvironmentProperties(paramITestResult);
    }
    catch (Exception localException2)
    {
    }
    this.failedTests.add(paramITestResult);
    setFailedTests(this.failedTests);
  }

	@Override
	public void onTestSkipped(ITestResult paramITestResult) {
		try {
			if (paramITestResult.getThrowable().getMessage().length() > 0)
				paramITestResult.setAttribute("exceptionAttrib",
						paramITestResult.getThrowable().getMessage());
			setTestCaseEnvironmentProperties(paramITestResult);
		} catch (Exception localException) {
		}
		this.skippedTests.add(paramITestResult);
		setSkippedTests(this.skippedTests);
	}

	@Override
	public void onTestSuccess(ITestResult paramITestResult) {
		try {
			setTestCaseEnvironmentProperties(paramITestResult);
		} catch (Exception localException) {
		}
		this.passedTests.add(paramITestResult);
		setPassedTests(this.passedTests);
	}

	@Override
	public void onStart(ITestContext paramITestContext) {
		this.reportFiles = new ReportFiles();
		this.htmlWriter = new HtmlWriter();
		this.reportsHelper = new ReportsUtilities();
		this.jsWriter = new JSWriter();
		// adding a statement
		
		if (!this.reportFiles.hasDirectory(reportsDirectory))
			this.reportFiles.createRequiredDirectories(reportsDirectory);
		this.reportFiles.init(reportsDirectory);
		this.properties = new Properties();
		this.reportsHelper.loadPropertyFile(this.properties, reportsDirectory
				+ System.getProperty("file.separator") + "report.properties");
		executionCount = Integer.parseInt(this.properties.getProperty(
				"executionCount").trim()) + 1;
		this.properties.setProperty("executionCount", "" + executionCount);
		this.reportsHelper.closePropertyFile(this.properties, reportsDirectory
				+ System.getProperty("file.separator") + "report.properties");
		if (!this.reportFiles.hasDirectory(reportsDirectory
				+ System.getProperty("file.separator") + "executions"
				+ System.getProperty("file.separator") + "Execution_"
				+ executionCount + System.getProperty("file.separator")
				+ "teststeps" + System.getProperty("file.separator")
				+ "screenshots"))
			this.reportFiles.createRequiredDirectories(reportsDirectory
					+ System.getProperty("file.separator") + "executions"
					+ System.getProperty("file.separator") + "Execution_"
					+ executionCount + System.getProperty("file.separator")
					+ "teststeps" + System.getProperty("file.separator")
					+ "screenshots");
	}

	@Override
	public void onFinish(ITestContext paramITestContext) {
		Object localObject2;
		Iterator iter;
		if (paramITestContext.getCurrentXmlTest().getSuite().getTests().size() > 1) {
			iter = paramITestContext.getCurrentXmlTest().getSuite().getTests()
					.iterator();
			while (iter.hasNext()) {
				localObject2 = iter.next();
				System.out.println(((XmlTest) localObject2).getName());
			}
		}
		this.allTCs = new ArrayList();
		Object localObject1 = getPassedTests().iterator();
		while (((Iterator) localObject1).hasNext()) {
			localObject2 = ((Iterator) localObject1).next();
			this.allTCs.add("["
					+ ((ITestResult) localObject2).getTestClass().getName()
					+ "]" + ((ITestResult) localObject2).getName());
		}
		localObject1 = getFailedTests().iterator();
		while (((Iterator) localObject1).hasNext()) {
			localObject2 = ((Iterator) localObject1).next();
			this.allTCs.add("["
					+ ((ITestResult) localObject2).getTestClass().getName()
					+ "]" + ((ITestResult) localObject2).getName());
		}
		localObject1 = getSkippedTests().iterator();
		while (((Iterator) localObject1).hasNext()) {
			localObject2 = ((Iterator) localObject1).next();
			this.allTCs.add("["
					+ ((ITestResult) localObject2).getTestClass().getName()
					+ "]" + ((ITestResult) localObject2).getName());
		}
		this.reportsHelper.loadPropertyFile(this.properties, reportsDirectory
				+ System.getProperty("file.separator") + "report.properties");
		localObject1 = this.reportsHelper.elapsedTime(paramITestContext
				.getStartDate().getTime(), paramITestContext.getEndDate()
				.getTime());
		this.properties.setProperty("executionTime",
				this.properties.getProperty("executionTime") + ","
						+ (String) localObject1);
		this.reportsHelper.closePropertyFile(this.properties, reportsDirectory
				+ System.getProperty("file.separator") + "report.properties");
		testCaseReportPage(this.reportsHelper.elapsedTime(paramITestContext
				.getStartDate().getTime(), paramITestContext.getEndDate()
				.getTime()));
		completeReportsPage();
		testSteps();
	}

	public static String getExceptionClassName(String paramString1,
			String paramString2) throws ClassNotFoundException {
		String str = "";
		try {
			str = WebDriverExceptionDetails.valueOf(
					paramString1.trim().replace(".", "_")).getExceptionInfo();
		} catch (Exception localException) {
			str = paramString1;
		}
		return str;
	}

	private void setTestCaseEnvironmentProperties(ITestResult paramITestResult) {
		paramITestResult.setAttribute("Browser Name",
				TestEnvironmentProperties.Browser_Name);
		paramITestResult.setAttribute("Browser Version",
				TestEnvironmentProperties.Browser_Version);
		paramITestResult.setAttribute("Operating System",
				TestEnvironmentProperties.OS_Name);
		paramITestResult.setAttribute("OS Architecture",
				TestEnvironmentProperties.OS_Architecture);
		paramITestResult.setAttribute("Java Version",
				TestEnvironmentProperties.JAVA_Version);
		paramITestResult.setAttribute("WebDriver Version",
				TestEnvironmentProperties.WebDriver_Version);
	}

	public String takeScreenShot(String paramString) {
		String str = "";
		File localFile = new File(paramString + ".jpg");
		try {
			if ((TestEnvironmentProperties.driver instanceof TakesScreenshot)) {
				byte[] arrayOfByte = ((TakesScreenshot) TestEnvironmentProperties.driver)
						.getScreenshotAs(OutputType.BYTES);
				try {
					Files.write(arrayOfByte, localFile);
					str = localFile.getName();
				} catch (IOException localIOException) {
				}
			}
		} catch (Exception localException) {
		}
		return str;
	}

	private void completeReportsPage() {
		try {
			this.out = new PrintWriter(reportsDirectory
					+ System.getProperty("file.separator") + "executions"
					+ System.getProperty("file.separator")
					+ "completeReports.html");
			this.htmlWriter.generateCompleteReportsPage_1(this.out);
			this.reportsHelper.loadPropertyFile(this.properties,
					reportsDirectory + System.getProperty("file.separator")
							+ "report.properties");
			int i = Integer.parseInt(this.properties.getProperty(
					"maxBarChartResults").trim());
			String str1 = this.properties.getProperty("passedSet").trim() + ","
					+ getPassedTests().size();
			String str2 = this.properties.getProperty("failedSet").trim() + ","
					+ getFailedTests().size();
			String str3 = this.properties.getProperty("skippedSet").trim()
					+ "," + getSkippedTests().size();
			String str4 = str1;
			String str5 = str2;
			String str6 = str3;
			int j = 1;
			if ((executionCount > i) && (i <= 8)) {
				str4 = str1.substring((executionCount - i) * 2);
				str5 = str2.substring((executionCount - i) * 2);
				str6 = str3.substring((executionCount - i) * 2);
				j = executionCount - i + 1;
			}
			this.jsWriter.generateCompleteReportJS(
					reportsDirectory + System.getProperty("file.separator")
							+ "executions"
							+ System.getProperty("file.separator")
							+ "completeReports.js",
					str4.substring(str4.indexOf(",") + 1),
					str5.substring(str5.indexOf(",") + 1),
					str6.substring(str6.indexOf(",") + 1), j, executionCount);
			this.properties.setProperty("passedSet", str1);
			this.properties.setProperty("failedSet", str2);
			this.properties.setProperty("skippedSet", str3);
			this.reportsHelper.closePropertyFile(this.properties,
					reportsDirectory + System.getProperty("file.separator")
							+ "report.properties");
			String[] arrayOfString1 = str1.split(",");
			String[] arrayOfString2 = str2.split(",");
			String[] arrayOfString3 = str3.split(",");
			String[] arrayOfString4 = this.properties.getProperty(
					"executionTime").split(",");
			for (int k = 1; k <= executionCount; k++) {
				this.out.print("                                <tr>\n                                    <td>"
						+ k
						+ "</td>\n"
						+ "                                    <td><a href=\"Execution_"
						+ k
						+ "/testcaseReports.html\">Execution "
						+ k
						+ "</a></td>\n");
				this.out.print("<td>" + arrayOfString4[k] + "</td>");
				File localFile = new File(reportsDirectory
						+ System.getProperty("file.separator") + "executions"
						+ System.getProperty("file.separator") + "Execution_"
						+ k);
				Date localDate = new Date(localFile.lastModified());
				String str7 = "dd MMM yyyy '-' h:mm a";
				SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat(
						str7);
				int m = Integer.parseInt(arrayOfString1[k])
						+ Integer.parseInt(arrayOfString2[k])
						+ Integer.parseInt(arrayOfString3[k]);
				this.out.print("<td>" + m + "</td>");
				if (m > 0) {
					this.out.print("<td>" + arrayOfString1[k] + "    -    ["
							+ Integer.parseInt(arrayOfString1[k]) * 100 / m
							+ "%]</td>");
					this.out.print("<td>" + arrayOfString2[k] + "    -    ["
							+ Integer.parseInt(arrayOfString2[k]) * 100 / m
							+ "%]</td>");
					this.out.print("<td>" + arrayOfString3[k] + "    -    ["
							+ Integer.parseInt(arrayOfString3[k]) * 100 / m
							+ "%]</td>");
				}
				this.out.println("                                    <td>"
						+ localSimpleDateFormat.format(localDate) + "</td>\n"
						+ "                                </tr>\n");
			}
			this.htmlWriter.generateCompleteReportsPage_2(this.out);
		} catch (FileNotFoundException localFileNotFoundException) {
		}
	}

	private void testSteps() {
		tcStepData(getPassedTests());
		tcStepData(getFailedTests());
		tcStepData(getSkippedTests());
	}

	private void tcStepData(List<ITestResult> paramList) {
		try {
			Iterator localIterator1 = paramList.iterator();
			while (localIterator1.hasNext()) {
				ITestResult localITestResult = (ITestResult) localIterator1
						.next();
				int i = 1;
				String str1 = "[" + localITestResult.getTestClass().getName()
						+ "]" + localITestResult.getName();
				int j = this.allTCs.indexOf(str1);
				int k = this.allTCs.lastIndexOf(str1);
				int m = 0;
				try {
					m = Integer.parseInt(localITestResult.getAttribute(
							"invokeCountAtttrib").toString());
				} catch (Exception localException1) {
					m = 1;
				}
				boolean bool = false;
				if (j != k) {
					str1 = str1 + "_Iteration" + m;
					bool = true;
				}
				String str2 = null;
				if (localITestResult.getStatus() == 3) {
					try {
						str2 = localITestResult.getAttribute("exceptionAttrib")
								.toString();
						this.out = new PrintWriter(reportsDirectory
								+ System.getProperty("file.separator")
								+ "executions"
								+ System.getProperty("file.separator")
								+ "Execution_" + executionCount
								+ System.getProperty("file.separator")
								+ "teststeps"
								+ System.getProperty("file.separator") + str1
								+ ".html");
						this.htmlWriter.generateTestStepsPage_1(this.out, str1);
						this.out.println("<tr>\n<td>" + i++ + "</td>\n"
								+ "<td style=\"text-align:left\">" + ""
								+ "</td>\n");
						this.out.println("<td></td>\n");
						this.out.println("<td style=\"text-align:left\">"
								+ str2 + "</td>\n" + "<td>");
						this.out.println("</td>\n</tr>\n");
						this.htmlWriter.generateTestStepsPage_2(this.out,
								this.reportsHelper.elapsedTime(
										localITestResult.getStartMillis(),
										localITestResult.getEndMillis()),
								localITestResult, bool);
					} catch (Exception localException2) {
					}
				} else {
					this.out = new PrintWriter(reportsDirectory
							+ System.getProperty("file.separator")
							+ "executions"
							+ System.getProperty("file.separator")
							+ "Execution_" + executionCount
							+ System.getProperty("file.separator")
							+ "teststeps"
							+ System.getProperty("file.separator") + str1
							+ ".html");
					this.htmlWriter.generateTestStepsPage_1(this.out, str1);
					String str3 = null;
					int n = Reporter.getOutput(localITestResult).size();
					int i1 = 0;
					Iterator localIterator2 = Reporter.getOutput(
							localITestResult).iterator();
					while (localIterator2.hasNext()) {
						String str4 = (String) localIterator2.next();
						str2 = "";
						this.out.println("<tr>\n<td>" + i++ + "</td>\n"
								+ "<td style=\"text-align:left\">" + str4
								+ "</td>\n");
						if ((i1 + 1 == n)
								&& (localITestResult.getStatus() == 2)
								&& (Boolean.parseBoolean(localITestResult
										.getAttribute("exceptionLineAttrib")
										.toString()))) {
							str2 = localITestResult.getAttribute(
									"exceptionAttrib").toString();
							str3 = localITestResult.getAttribute(
									"exceptionScreenShot").toString();
							this.out.println("<td><img alt=\"FAIL\" src=\"../../../other_files/images/no.png\"/></td>\n");
							this.out.println("<td style=\"text-align:left\">"
									+ str2 + "</td>\n"
									+ "<td><a href=\"screenshots/" + str3
									+ "\"><img src=\"screenshots/" + str3
									+ "\"/></a></td>\n" + "</tr>\n");
							break;
						}
						this.out.println("<td><img alt=\"PASS\" src=\"../../../other_files/images/yes.png\"/></td>\n");
						i1++;
						this.out.println("<td style=\"text-align:left\">"
								+ str2 + "</td>\n" + "<td>");
						Iterator localIterator3 = screenShotNames.iterator();
						while (localIterator3.hasNext()) {
							String str5 = (String) localIterator3.next();
							int i2;
							if (bool) {
								i2 = 1;
								while (i2 <= Reporter.getOutput(
										localITestResult).size())
									if ((str5.equals(str1 + "_Step_" + i2++
											+ ".jpg"))
											&& (i - 1 == i2 - 1)) {
										this.out.print("<a href=\"screenshots/"
												+ str5
												+ "\"><img src=\"screenshots/"
												+ str5 + "\"/></a>");
										//screenShotNames.remove(str5);
										break;
									}
							} else {
								i2 = 1;
								while (i2 <= Reporter.getOutput(
										localITestResult).size())
									if ((str5.equals(str1 + "_Iteration1_Step_"
											+ i2++ + ".jpg"))
											&& (i - 1 == i2 - 1)) {
										this.out.print("<a href=\"screenshots/"
												+ str5
												+ "\"><img src=\"screenshots/"
												+ str5 + "\"/></a>");
										//screenShotNames.remove(str5);
										break;
									}
							}
						}
						
						label1127: this.out.println("</td>\n</tr>\n");
					}
					if ((localITestResult.getStatus() == 2)
							&& (!Boolean.parseBoolean(localITestResult
									.getAttribute("exceptionLineAttrib")
									.toString()))) {
						str2 = localITestResult.getAttribute("exceptionAttrib")
								.toString();
						str3 = localITestResult.getAttribute(
								"exceptionScreenShot").toString();
						this.out.println("<tr>\n<td>" + i++ + "</td>\n"
								+ "<td style=\"text-align:left\">" + ""
								+ "</td>\n");
						this.out.println("<td><img alt=\"FAIL\" src=\"../../../other_files/images/no.png\"/></td>\n");
						this.out.println("<td style=\"text-align:left\">"
								+ str2 + "</td>\n"
								+ "<td><a href=\"screenshots/" + str3
								+ "\"><img src=\"screenshots/" + str3
								+ "\"/></a></td>\n" + "</tr>\n");
					}
					this.htmlWriter.generateTestStepsPage_2(this.out,
							this.reportsHelper.elapsedTime(
									localITestResult.getStartMillis(),
									localITestResult.getEndMillis()),
							localITestResult, bool);
				}
			}
		} catch (FileNotFoundException localFileNotFoundException) {
		}
	}

	private void testCaseReportPage(String paramString) {
		try {
			this.out = new PrintWriter(reportsDirectory
					+ System.getProperty("file.separator") + "executions"
					+ System.getProperty("file.separator") + "Execution_"
					+ executionCount + System.getProperty("file.separator")
					+ "testcaseReports.html");
			this.htmlWriter.generateTestCaseReportsPage_1(this.out);
			tcReportData(this.out, getPassedTests());
			tcReportData(this.out, getFailedTests());
			tcReportData(this.out, getSkippedTests());
			this.htmlWriter
					.generateTestCaseReportsPage_2(this.out, paramString);
			int[] arrayOfInt = { getPassedTests().size(),
					getFailedTests().size(), getSkippedTests().size() };
			this.jsWriter.generateTestCaseReportJS(
					reportsDirectory + System.getProperty("file.separator")
							+ "executions"
							+ System.getProperty("file.separator")
							+ "Execution_" + executionCount
							+ System.getProperty("file.separator")
							+ "testCaseReport.js", arrayOfInt);
		} catch (Exception localException) {
			JOptionPane.showMessageDialog(null, localException);
		}
	}

	private void tcReportData(PrintWriter paramPrintWriter,
			List<ITestResult> paramList) {
		Iterator localIterator = paramList.iterator();
		while (localIterator.hasNext()) {
			ITestResult localITestResult = (ITestResult) localIterator.next();
			String str = "[" + localITestResult.getTestClass().getName() + "]"
					+ localITestResult.getName();
			int i = this.allTCs.indexOf(str);
			int j = this.allTCs.lastIndexOf(str);
			int k = 0;
			try {
				k = Integer.parseInt(localITestResult.getAttribute(
						"invokeCountAtttrib").toString());
			} catch (Exception localException1) {
				k = 1;
			}
			if (i != j)
				str = str + "_Iteration" + k;
			if (localITestResult.getStatus() != 3)
				paramPrintWriter.write("<tr><td>" + serialNum++ + "</td>"
						+ "<td><a href=\"teststeps/" + str + ".html\">" + str
						+ "</a></td>");
			else
				try {
					if (localITestResult.getAttribute("exceptionAttrib")
							.toString().length() > 0)
						paramPrintWriter.write("<tr><td>" + serialNum++
								+ "</td>" + "<td><a href=\"teststeps/" + str
								+ ".html\">" + str + "</a></td>");
				} catch (Exception localException2) {
					paramPrintWriter.write("<tr><td>" + serialNum++ + "</td>"
							+ "<td>" + str + "</td>");
				}
			if (localITestResult.getStatus() == 1)
				paramPrintWriter
						.write("<td><img alt=\"PASS\" src=\"../../other_files/images/yes.png\"/></td>");
			else if (localITestResult.getStatus() == 2)
				paramPrintWriter
						.write("<td><img alt=\"FAIL\" src=\"../../other_files/images/no.png\"/></td>");
			else if (localITestResult.getStatus() == 3)
				paramPrintWriter
						.write("<td><img alt=\"SKIP\" src=\"../../other_files/images/skip.png\"/></td>");
			paramPrintWriter.write("<td>"
					+ this.reportsHelper.elapsedTime(
							localITestResult.getStartMillis(),
							localITestResult.getEndMillis()) + "</td>"
					+ "</tr>");
		}
	}
}