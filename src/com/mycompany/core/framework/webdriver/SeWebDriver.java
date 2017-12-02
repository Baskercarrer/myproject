package com.mycompany.core.framework.webdriver;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openqa.selenium.Alert;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriver.Timeouts;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.mycompany.core.framework.utils.Global;
import com.mycompany.core.framework.utils.Utility;


public class SeWebDriver {

	public WebDriver driver;
	protected SeDriverFactory browser;
	protected String webUrl;
	private long defTimeOut = 600;
	

	public SeWebDriver(WebDriver driver) {
		this.driver = driver;
		this.webUrl = driver.getCurrentUrl();
	}

	public void setDefaultTime(long dtime) {
		defTimeOut = dtime;
	}

	
	
	public SeWebDriver() {
		this("firefox");
	}

	public SeWebDriver(String browserType) {
		browser = new SeDriverFactory();
		driver = browser.getDriver(browserType);

	}

	public SeWebDriver(String webUrl, String browserType) {

		this(browserType);
		this.launch(webUrl);
	}

	public String getURL() {
		return this.webUrl;
	}

	public SeWebElement findElement(String findByMechanism, String locator) {
		SeWebElement retElement =null;
		try{
		retElement= new SeWebElement(getWebDriver().findElement(FindBy.seByMechanism(findByMechanism, locator))); 	
		((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView(true)", retElement.getWebElement());
		String colour[] = {"red","yellow","blue","grey","green"};
		for(String a:colour){
		((JavascriptExecutor)getWebDriver()).executeScript("arguments[0].style.border='1.5px solid "+a+"'", retElement.getWebElement());
		}
		return retElement;
		}
		catch(Exception err)
		{
			System.out.println(err.getMessage());
			Utility.reportingResults("Fail", Global.gStepDescription,"unable to click the element due to " + err.getMessage() );
			
		}
		return null;
		
	}

	public SeWebElement findElement(Properties prp) {
		SeWebElement retElement = null;

		try {
			retElement = new SeWebElement(getWebDriver().findElement(FindBy.seByMechanism(prp.getProperty("findBy"),prp.getProperty("prpValue"))));
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}

		return retElement;
	}

	public List<SeWebElement> findElements(String findByMechanism,String locator) {
		
		List<WebElement> webElements = null;
		webElements = getWebDriver().findElements(FindBy.seByMechanism(findByMechanism,
				locator));

		return toSeWebElements(webElements);
	}

	public List<SeWebElement> toSeWebElements(List<WebElement> webElements) {
		List<SeWebElement> seWebElements = new ArrayList<>();
		for (WebElement item : webElements) {
			seWebElements.add(new SeWebElement(item));
		}
		return seWebElements;
	}

	// Wrapping-ups WebDriver methods

	public WebDriver.Options manage() {
		return driver.manage();
	}

	public void launch(String webURL) {
		this.webUrl = webURL;
		driver.get(webURL);
		driver.manage().window().maximize();
	}

	public WebDriver getWebDriver() {
		return driver;
	}

	public void close() {
		driver.close();
	}

	public void quit() {
		driver.quit();
	}

	public String getTitle() {
		return driver.getTitle();
	}

	public String getPageSource() {
		return driver.getPageSource();
	}
	// wrapping-up WebDriver.Navigation

	public void back() {
		getWebDriver().navigate().back();

	}

	public void forward() {
		getWebDriver().navigate().forward();
	}

	public void refresh() {
		getWebDriver().navigate().refresh();

	}

	public void to(String url) {
		getWebDriver().navigate().to(url);
	}

	public void to(URL url) {
		getWebDriver().navigate().to(url);
	}

	// wrapping-up WebDriver.Options

	public void addCookie(Cookie cookie) {
		getWebDriver().manage().addCookie(cookie);

	}

	public void deleteAllCookies() {
		getWebDriver().manage().deleteAllCookies();

	}

	public void deleteCookie(Cookie cookie) {
		getWebDriver().manage().deleteCookie(cookie);

	}

	public void deleteCookieNamed(String name) {
		getWebDriver().manage().deleteCookieNamed(name);

	}

	public Cookie getCookieNamed(String name) {
		return getWebDriver().manage().getCookieNamed(name);
	}

	public Set<Cookie> getCookies() {
		return getWebDriver().manage().getCookies();
	}

	public SeWebDriver window(String nameOrHandle) {
		return toSeWebDriver((getWebDriver().switchTo().window(nameOrHandle)));
	}

	// wrapping WebDriver.TargetLocator
	public SeWebElement activeElement() {
		return toSeWebElement(getWebDriver().switchTo().activeElement());
	}

	public Alert alert() {
		return getWebDriver().switchTo().alert();
	}

	public void dismissAlert() {
		alert().dismiss();
	}

	public void acceptAlert() {
		alert().accept();
	}

	public String getTextAlert() {
		return alert().getText();
	}

	public void sendKeysAlert(String keysToSend) {
		alert().sendKeys(keysToSend);
	}

	public SeWebDriver defaultContent() {
		return toSeWebDriver((getWebDriver().switchTo().defaultContent()));
	}

	public SeWebDriver frame(int index) {
		return toSeWebDriver(getWebDriver().switchTo().frame(index));

	}

	
	public SeWebDriver handleMultipleWindows(String windowTitle) {
        Set<String> windows = getWebDriver().getWindowHandles();

        for (String window : windows) {
        	getWebDriver().switchTo().window(window);
            if (getWebDriver().getTitle().contains(windowTitle)) {            	
                return toSeWebDriver(getWebDriver());               
            }
        }
		return null;
	}
	
	public SeWebDriver switchToCurrentWindow() {
        Set<String> windows = getWebDriver().getWindowHandles();

        for (String window : windows) {
        	         	
                return toSeWebDriver(getWebDriver().switchTo().window(window));               
            }
        
		return null;
	}
	
	public SeWebDriver switchToWindowbyURL(String regex) {
        Set<String> windows = getWebDriver().getWindowHandles();

        for (String window : windows) {
        	getWebDriver().switchTo().window(window);
            System.out.println("#switchToWindow() : url=" + getWebDriver().getCurrentUrl());

            Pattern p = Pattern.compile(regex);
            Object m = p.matcher(driver.getCurrentUrl());

            if (((Matcher) m).find())
            {
            	 return toSeWebDriver(getWebDriver().switchTo().window(window));
            }
        }

        return null;
    }
	
	public SeWebDriver frame(String nameOrId) {
		return toSeWebDriver(getWebDriver().switchTo().frame(nameOrId));

	}

	public SeWebDriver frame(SeWebElement frameElement) {
		return toSeWebDriver(getWebDriver().switchTo().frame(
				getWebElement(frameElement)));
	}

	// wrapping WebDriver.Timeouts

	public Timeouts implicitlyWait(long time, TimeUnit unit) {
		return getWebDriver().manage().timeouts().implicitlyWait(time, unit);
	}

	public Timeouts implicitlyWait(long time) {
		return getWebDriver().manage().timeouts()
				.implicitlyWait(time, TimeUnit.MILLISECONDS);
	}

	public Timeouts setScriptTimeout(long time, TimeUnit unit) {
		return getWebDriver().manage().timeouts().setScriptTimeout(time, unit);
	}

	public Timeouts setScriptTimeout(long time) {
		return getWebDriver().manage().timeouts()
				.setScriptTimeout(time, TimeUnit.MILLISECONDS);
	}

	// wrapping WebDriver.Window
	public void maximize() {
		getWebDriver().manage().window().maximize();
	}

	public Actions getAction() {
		return new Actions(getWebDriver());
	}

	// Additional methods
	public void dragAndDropByCoordinates(SeWebElement source, int x, int y) {
		getAction().dragAndDropBy(getWebElement(source), x, y).build()
				.perform();

	}

	public void dragAndDrop(SeWebElement source, SeWebElement destination) {
		getAction()
				.dragAndDrop(getWebElement(source), getWebElement(destination))
				.build().perform();

	}

	public void keyUp(Keys key) {
		getAction().keyUp(key).perform();

	}

	public void keyUp(SeWebElement control, Keys key) {
		getAction().keyUp(getWebElement(control), key).perform();
	}

	public void keyDown(Keys key) {
		getAction().sendKeys(key).perform();
	}

	public void keyDown(SeWebElement control, Keys key) {
		getAction().sendKeys(getWebElement(control), key).perform();
	}

	public void doubleClick(SeWebElement control) {
		getAction().doubleClick(getWebElement(control)).perform();
	}

	public void mouseDown(SeWebElement control) {
		getAction().clickAndHold(getWebElement(control)).perform();

	}

	public void mouseUp(SeWebElement control) {
		getAction().release(getWebElement(control)).perform();
	}

	public void moveByOffset(int xOffset, int yOffset) {
		getAction().moveByOffset(xOffset, yOffset).perform();
	}

	public void mouseMove(SeWebElement element) {
		getAction().moveToElement(getWebElement(element)).build().perform();
	}

	public void contextClick(SeWebElement element) {
		getAction().contextClick(getWebElement(element)).perform();
	}

	public boolean isChecked(SeWebElement element) {
		if (element.isSelected()) {
			return true;
		}
		return false;
	}

	public void uncheck(SeWebElement element) {
		if (element.isSelected()) {
			element.click();
		}
	}

	public void submit(SeWebElement element) {
		element.submit();
	}

	public void click(SeWebElement element) {
		element.click();
	}

	public boolean isEditable(SeWebElement control) {
		String tagName = control.getTagName().toLowerCase();
		boolean acceptableTagName = "input".equals(tagName)
				|| "select".equals(tagName);
		String readonly = "";
		if ("input".equals(tagName)) {
			readonly = control.getAttribute("readonly");
			if (readonly == null)
				readonly = "";
		}

		return control.isEnabled() && acceptableTagName && "".equals(readonly);
	}

	// Wait methods

	public void waitForPageToLoad() throws Exception {
		ExpectedCondition<Boolean> expectation = new ExpectedCondition<Boolean>() {
			@Override
			public Boolean apply(WebDriver driver) {
				return ((JavascriptExecutor) driver).executeScript(
						"return document.readyState").equals("complete");
			}
		};
		Wait<WebDriver> wait = new WebDriverWait(driver, defTimeOut);
		try {
			wait.until(expectation);
		} catch (Exception error) {
			throw new Exception("Failed while loading page", error);
		}
	}

	public void waitForElementPresent(String findBy, String locator) {
		WebDriverWait wait = new WebDriverWait(driver, defTimeOut);
		wait.until(ExpectedConditions.presenceOfElementLocated(FindBy
				.seByMechanism(findBy, locator)));
	}

	public void waitForElementVisible(String findBy, String locator) {
		WebDriverWait wait = new WebDriverWait(driver, defTimeOut);
		wait.until(ExpectedConditions.visibilityOfElementLocated(FindBy
				.seByMechanism(findBy, locator)));
	}

	public boolean verifyElementPresent(String findBy, String locator) {
		try {
			this.findElement(findBy, locator);
			return true;
		} catch (org.openqa.selenium.NoSuchElementException ex) {

			return false;
		}
	}

	public void selectByValue(SeWebElement element, String valueToSelect) {
		new Select(getWebElement(element)).selectByValue(valueToSelect);
	}

	public void selectByIndex(SeWebElement element, int index) {
		new Select(getWebElement(element)).selectByIndex(index);
	}

	public void selectByVisibleText(SeWebElement element, String visibleText) {
		new Select(getWebElement(element)).selectByVisibleText(visibleText);
	}

	public void deselectByValue(SeWebElement element, String valueToDeselect) {
		new Select(getWebElement(element)).deselectByValue(valueToDeselect);
	}

	public void deselectByIndex(SeWebElement element, int index) {
		new Select(getWebElement(element)).deselectByIndex(index);
	}

	public void deselectByVisibleText(SeWebElement element, String visibleText) {
		new Select(getWebElement(element)).deselectByVisibleText(visibleText);
	}

	public void deselectAll(SeWebElement element) {
		new Select(getWebElement(element)).deselectAll();
	}

	public SeWebElement toSeWebElement(WebElement webElement) {
		return new SeWebElement(webElement);
	}

	public WebElement getWebElement(SeWebElement webElement) {
		return webElement.getWebElement();
	}

	public SeWebDriver toSeWebDriver(WebDriver driver) {
		return new SeWebDriver(driver);
	}

	public String getBrowserName(){
		return ((RemoteWebDriver)getWebDriver()).getCapabilities().getBrowserName();
	}
	
	public String getBrowserVersion(){
		return ((RemoteWebDriver)getWebDriver()).getCapabilities().getVersion();
	}
	
	public String getOsName(){
		return ((RemoteWebDriver)getWebDriver()).getCapabilities().getPlatform().name();
	}
	
	
	
	
	
	
}
