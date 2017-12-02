package com.mycompany.core.framework.utils;

import org.openqa.selenium.JavascriptExecutor;

public class MyRunnable implements Runnable{
                
	com.mycompany.core.framework.webdriver.SeWebDriver wd;

                public MyRunnable(com.mycompany.core.framework.webdriver.SeWebDriver browser) {
                                this.wd= browser;
                }

         

				@Override
                public void run() {
                                JavascriptExecutor js=(JavascriptExecutor)wd;
                                js.executeAsyncScript("document.getElementById('addAttachmentForm:uploader_input').click();");

                }

}
