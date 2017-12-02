package com.mycompany.core.framework.webcomponents;

import com.mycompany.core.framework.webdriver.SeWebDriver;
import com.mycompany.core.framework.webdriver.SeWebElement;

/**
 * Webcheckbox methods
 * 
 * @author UST-TACOE
 * 
 */
public class WebCheckBox implements IWebComponents{

	SeWebDriver Sedriver = null;
	SeWebElement element=null;
	String findby;
	String locator;
	
	public WebCheckBox(SeWebDriver driver,String by, String using)
	{
		this.Sedriver = driver;
		this.findby = by;
		this.locator = using;
		Sedriver.waitForElementVisible(by,using);	
		element = Sedriver.findElement(by, using);
	}
	@Override
	public String getFindBy()
	{
		return findby;
	}
	@Override
	public String getLocator()
	{
		return locator;
	}
	
	 public void check()
	 {
		element.click();
		 
	 }
	 
	 public void uncheck()
	 {
		Sedriver.uncheck(element);
		 
	 }
	 @Override
	 public Boolean isExist()
	 {
		 return element.isAvailableForAction();
		 
	 }
	 @Override
	 public String getProperty(String prp)
	 {
		 return element.getAttribute(prp);
	 }

	@Override
	public void click() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void CaptureScreenshot(String filepath) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Boolean isEnabled() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public  SeWebElement getSeWebElement()
	{
		return element;
	}
	 
}
