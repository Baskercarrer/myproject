package com.mycompany.appspecific.libray;

import com.company.PageObjects.GmailObjects;
import com.mycompany.core.framework.webdriver.SeWebDriver;






public class GmailCheck {

	protected SeWebDriver driver;
	public GmailCheck(SeWebDriver driver){
		this.driver=driver;
	}


	public void cehckscroll(){
		driver.findElement("xpath","//a[contains(text(),'XML DOM')]");
	}

	public boolean gmailLogin(String username,String password) throws InterruptedException{
		boolean isEmptyOFuserNameAndPassword = username.isEmpty() && password.isEmpty();
		
		if(!isEmptyOFuserNameAndPassword){
			System.out.println(driver);
			driver.findElement("xpath", GmailObjects.username).sendKeys(username);
			driver.findElement("xpath", GmailObjects.password).sendKeys(password);
			driver.findElement("xpath", GmailObjects.logInButton).click();
			
			return true;
		}else{
			return false;
		}
	}
}
