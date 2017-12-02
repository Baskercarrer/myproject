package com.mycompany.test.scenarios;

import static io.restassured.RestAssured.*;
import static io.restassured.assertion.BodyMatcher.*;

import io.restassured.matcher.ResponseAwareMatcher;

import java.io.IOException;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.custom.htmlreport.HTMLReporter;
import com.mycompany.core.framework.webdriver.SeWebDriver;


public class GmailLoginScenarios {
	public SeWebDriver driver;
	public HTMLReporter hr;	

	@BeforeClass
	public void before() throws IOException{
		
		driver = new SeWebDriver("http://www.google.com","chrome");
		Initialize();
	}

	public void Initialize() throws IOException{
		hr = new HTMLReporter(driver.getBrowserName(),driver.getBrowserVersion(),driver.getOsName(),"Selenium");
		hr.initalizeHTMLReport("C:\\Users\\Basker\\Desktop\\");
	}
	
	

	//@Test
	public void TC001() throws InterruptedException, IOException {
		hr.addTestCase("TC001", "Verify the google has launched");
		hr.addTestStep("Lauching the google Page", "URL Should launch", "URL Launched", "Pass");
		driver.to("https://fb.com");
	}
	
	@Test
	public void TC002(){
	
	}
	
	
	
	@AfterClass
	public void closeBrowser() throws IOException{
		driver.close();	
		hr.closeHTMLReport();
		
	}

}

