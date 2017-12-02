package com.mycompany.core.framework.webdriver;

import org.openqa.selenium.remote.DesiredCapabilities;

/**
 * 
 * @author myCompany-TACOE
 * Class to initialize browser capabilities.
 * 
 */
public class SeCapabilities {
	
	public DesiredCapabilities caps = new DesiredCapabilities();
	
	public void setBrowserCapabilities(String browser) {
		
				if(browser.equalsIgnoreCase("Internet Explorer"))
					caps = DesiredCapabilities.internetExplorer();
				
				else if(browser.equalsIgnoreCase("Firefox"))
					caps = DesiredCapabilities.firefox();
				
				else if(browser.equalsIgnoreCase("Chrome"))
					caps = DesiredCapabilities.chrome();
				
				else if(browser.equalsIgnoreCase("Android"))
					caps = DesiredCapabilities.android();
				
				//Default condition
				else caps = DesiredCapabilities.firefox();
		
	}
	
	public void setPlatform(String platform) {
		
				//Platforms
				if(platform.equalsIgnoreCase("WINDOWS"))
					caps.setPlatform(org.openqa.selenium.Platform.WINDOWS);
				
				if(platform.equalsIgnoreCase("VISTA"))
					caps.setPlatform(org.openqa.selenium.Platform.VISTA);
				
				if(platform.equalsIgnoreCase("ANDROID"))
					caps.setPlatform(org.openqa.selenium.Platform.ANDROID);
	}
	
	public void setVersion(String version) {
				caps.setVersion(version);
	}

}
