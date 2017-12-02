package com.mycompany.core.framework.webcomponents;
 
import com.mycompany.core.framework.webdriver.SeWebDriver;
import com.mycompany.core.framework.webdriver.SeWebElement;

/**
 
 * 
 * @author UST-TACOE
 * 
 */
public class WebButton implements IWebComponents{

	private SeWebDriver Sedriver = null;
	private SeWebElement element=null;
	private String findby;
	private String locator;
	
	public WebButton(SeWebDriver driver,String by, String using)
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
	@Override
	 public void click()
	 {
		element.click();
	 }
	 @Override
	 public Boolean isExist()
	 {
		 return element.isAvailableForAction();
		 
		  
		 
	 }
	 
	 
	 public void doubleClick()
	 {
		 Sedriver.doubleClick(element);
	 }
	  
	 @Override
	 public String getProperty(String prp)
	 {
		 return element.getAttribute(prp);
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
