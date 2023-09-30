package org.aia.pages.ces;

import org.aia.utility.Utility;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 *  Email confirmation link page.
 * @author Pallavi A
 */


public class CloseBtnPageCes {

	WebDriver driver;
	
	Utility util = new Utility(driver, 30);
	public CloseBtnPageCes(WebDriver Idriver)
	{
		this.driver=Idriver;
	}
	
	@FindBy(xpath="//span[text()= 'CHECK YOUR EMAIL']") WebElement pageTitleEmailConfirmation;
	
	@FindBy(xpath="//span[text()= 'Close']") WebElement closebtn;
	
	@FindBy(xpath="//span[text()= ' Send the link again']") WebElement resendLink;
	
	
	public void clickCloseAfterVerification() throws InterruptedException
	{
		util.waitUntilElement(driver, pageTitleEmailConfirmation);
		closebtn.click();
		Thread.sleep(1000);
		//driver.get("https://account-stage.aia.org/signin?redirectUrl=https:%2F%2Fw21upgrade-us-tdm-tso-15eb63ff4c6-1626e-167f0569011.cs203.force.com%2FProviders%2Fs%2Fprovider-application");
	}
	
	public void clickSendLinkAgain() throws InterruptedException
	{
		util.waitUntilElement(driver, resendLink);
		resendLink.click();
	}
}
