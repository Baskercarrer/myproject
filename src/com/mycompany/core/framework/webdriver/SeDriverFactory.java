package com.mycompany.core.framework.webdriver;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import org.openqa.selenium.Capabilities;
//import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;


/**
 * 
 * @author UST-TACOE
 *
 */
public class SeDriverFactory {

	public WebDriver getDriver() {
		return this.getDriver("firefox");
	}

	public WebDriver getNewRemoteDriver(String hubUrl, Capabilities capabilities) {
		RemoteWebDriver driver;
		try {
			driver = new RemoteWebDriver(new URL(hubUrl), capabilities);
		} catch (MalformedURLException ex) {
			throw new RuntimeException("Hub URL does not seem to be working: " + hubUrl, ex);
		}
		return driver;
	}

	//intentionally used if-else-if statements over switch
	//@SuppressWarnings("static-access")
	public WebDriver getDriver(String browserType) {
		WebDriver driver = null;
		File file = null;
		if (browserType.equalsIgnoreCase("Internet Explorer") || browserType.equalsIgnoreCase("iexplore") ||
				browserType.equalsIgnoreCase("IE") || browserType.equalsIgnoreCase("IExplorer")) {
			file = new File("Resources/BrowserDrivers/IEDriverServer.exe");
			System.setProperty("webdriver.ie.driver",file.getAbsolutePath());				 
			DesiredCapabilities Capability = DesiredCapabilities.internetExplorer();
			Capability.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS,true);
			
			driver = new InternetExplorerDriver();
			
		} else if (browserType.equalsIgnoreCase("firefox")) {
			file = new File("Resources/BrowserDrivers/geckodriver.exe");
			System.setProperty("webdriver.gecko.driver",file.getAbsolutePath());
			
			driver = new FirefoxDriver();
		} else if (browserType.equalsIgnoreCase("chrome")) {
			file = new File("Resources/BrowserDrivers/chromedriver.exe");
			System.setProperty("webdriver.chrome.driver",file.getAbsolutePath());
			driver = new ChromeDriver();
		} else if (browserType.equalsIgnoreCase("safari")) {
			driver = new SafariDriver();
		} 
		return driver;
	}


}
