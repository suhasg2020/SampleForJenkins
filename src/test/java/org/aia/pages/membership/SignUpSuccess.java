package org.aia.pages.membership;

import java.util.ArrayList;

import org.aia.utility.Utility;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class SignUpSuccess {

	WebDriver driver;
	Utility util = new Utility(driver, 30);
	
	public SignUpSuccess(WebDriver Idriver)
	{
		this.driver=Idriver;
	}
	
	@FindBy(xpath="//span[text()='SUCCESS']") WebElement successMessage;
	
	
	public void clickConfirmationLink(String link) throws InterruptedException
	{
		Thread.sleep(3000);		
		((JavascriptExecutor)driver).executeScript("window.open()");
		ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());
		driver.switchTo().window(tabs.get(1));
		driver.get(link);
		util.waitUntilElement(driver, successMessage);		
		driver.switchTo().window(tabs.get(0));
	}
}
