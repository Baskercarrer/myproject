package com.mycompany.core.framework.webcomponents;
 
import com.mycompany.core.framework.webdriver.SeWebDriver;
import com.mycompany.core.framework.webdriver.SeWebElement;

/**
 *  
 * 
 *@author UST-TACOE
 * 
 */
public class WebList implements IWebComponents {

	SeWebDriver Sedriver = null;
	SeWebElement element=null;
	String findby;
	String locator;
	
	public WebList(SeWebDriver driver,String by, String using)
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
	 
	 public void selectByIndex(int index)
	 {
		Sedriver.selectByIndex(element, index);
	 }
	 
	 public void selectByValue(String valueToSelect)
	 {
		Sedriver.selectByValue(element, valueToSelect); 
	 }
	 
	 public void selectByVisibleText(String visibleText)
	 {
		Sedriver.selectByVisibleText(element, visibleText); 
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
