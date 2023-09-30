package org.aia.pages.fonteva.membership;

import static org.testng.Assert.assertTrue;

import java.util.List;

import org.aia.utility.ConfigDataProvider;
import org.aia.utility.Utility;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;

/**
 * @author IM-RT-LP-1483(Suhas)
 *
 */
public class ReNewUser {
	WebDriver driver;
	Utility util = new Utility(driver, 30);
	ConfigDataProvider data = new ConfigDataProvider();
	static Logger log = Logger.getLogger(ContactCreateUser.class);
	Actions action;
	JavascriptExecutor executor;

	/**
	 * @param Idriver
	 */
	public ReNewUser(WebDriver Idriver) {
		this.driver = Idriver;
		action = new Actions(driver);
		executor = (JavascriptExecutor) driver;
	}

	String contact = "//span[text()='%s']//ancestor::a";
   
  	@FindBy(xpath="//a[contains(text(),'Show All')]")
  	WebElement showAll;
  	
	@FindBy(xpath = "//a/slot/span[contains(text(),'Memberships')]")
	WebElement selectMembership;

	@FindBy(xpath = "(//table[@aria-label='Memberships']//tr)[2]//th//a")
	//@FindBy(xpath = "(//table[@aria-label='Memberships']//tr)[2]/th/span/a")
	WebElement subscriptionId;

	@FindBy(xpath = "(//span[contains(text(),'Terms')])[2]/ancestor::a")
	WebElement termsLink;

	@FindBy(xpath = "(//table[@aria-label='Terms']//tr)[2]/th//a")
   //@FindBy(xpath = "(//table[@aria-label='Terms']//tr)[2]/th/span/a")
	WebElement termId;

	@FindBy(xpath = "//button[@title='Edit Term End Date']")
	WebElement termEditBtn;

	@FindBy(xpath = "//input[@name='OrderApi__Term_End_Date__c']")
	WebElement termDate;

	@FindBy(xpath = "//button[text()='Save']")
	WebElement saveBtn;

	//String contactTerm = "//span[text()='%s']//ancestor::a";
	String contactTerm ="(//span[text()='%s']//ancestor::a)[2]";
                        
	@FindBy(xpath = "(//button[text()='Renew'])[2]")
	WebElement renewBtn;

	@FindBy(xpath = "//button[contains(@aria-label,'Subscription ')]")
	WebElement subPlanDrp;
	
	@FindBy(xpath="//span[contains(@title,'Dues Installment Plan ')]")
	WebElement selectPayInInsatllmentElement;

	@FindBy(xpath = "//span[text()='Dues - Renew Payment in Full']")
	WebElement selectDeusPlan;

	@FindBy(xpath = "//button[@name='executeRenew']")
	WebElement updateSalesOrderBtn;

	@FindBy(xpath = "//button[text()='Ready For Payment']")
	WebElement readyForPayment;

	@FindBy(xpath = "//button[text()='Apply Payment']")
	WebElement applyPaymentTab;

	@FindBy(xpath = "(//iframe[@title='accessibility title'])[3]")
	WebElement drpIframe;
	
	@FindBy(xpath = "//span[text()='Apply Payment']/parent::button")
	WebElement applyLastPayment;
	
	@FindBy(xpath="//button[contains(text(),'Update Sales Order')]")
	WebElement updateSalesOrder;

	/**
	 * @param fullName
	 * @throws InterruptedException
	 */
	public void changeTermDate(String fullName) throws InterruptedException {
		Thread.sleep(7000);
		WebElement selectContact = driver.findElement(By.xpath(String.format(contact, fullName)));
		executor.executeScript("arguments[0].click();", selectContact);
		util.waitUntilElement(driver, showAll);
		showAll.click();
		action.sendKeys(Keys.ARROW_DOWN).build().perform();
		action.sendKeys(Keys.ARROW_DOWN).build().perform();
		action.sendKeys(Keys.ARROW_DOWN).build().perform();
		util.waitUntilElement(driver, selectMembership);
		selectMembership.click();
		Thread.sleep(10000);
		driver.navigate().refresh();
		util.waitUntilElement(driver, subscriptionId);
		executor.executeScript("arguments[0].click();", subscriptionId);
		//subscriptionId.click();
		util.waitUntilElement(driver, termsLink);
		termsLink.click();
		util.waitUntilElement(driver, termId);
		executor.executeScript("arguments[0].click();", termId);
		//termId.click();
		util.waitUntilElement(driver, termEditBtn);
		action.scrollToElement(termEditBtn);
		termEditBtn.click();
		util.enterText(driver, termDate, data.testDataProvider().getProperty("tremendDate"));
		Thread.sleep(5000);
		saveBtn.click();
	}

	/**
	 * @param fullName
	 * @throws InterruptedException
	 */
	public void renewMembership(String fullName) throws InterruptedException {
		Thread.sleep(10000);
		executor.executeScript("window.scrollBy(0,-500)", "");
		WebElement contactInTermLink = driver.findElement(By.xpath(String.format(contactTerm, fullName)));
		executor.executeScript("arguments[0].click();", contactInTermLink);
		util.waitUntilElement(driver, renewBtn);
		renewBtn.click();
		util.waitUntilElement(driver, subPlanDrp);
		subPlanDrp.click();
		selectDeusPlan.click();
		util.waitUntilElement(driver, updateSalesOrderBtn);
		updateSalesOrderBtn.click();
	}

	/**
	 * @throws InterruptedException
	 */
	public void applyForPayment(String paymentMethod) throws InterruptedException {
		util.waitUntilElement(driver, readyForPayment);
		readyForPayment.click();
		util.waitUntilElement(driver, applyPaymentTab);
		applyPaymentTab.click();
		Thread.sleep(10000);
		// check wait
		driver.switchTo().frame(drpIframe);
		Thread.sleep(7000);
		// check wait
		List<WebElement> options = driver.findElements(By.xpath("//select[@aria-label='Payment Type']/option"));
		for (WebElement drpOption : options) {
			System.out.println(drpOption.getText());
			if (drpOption.getText().equalsIgnoreCase(paymentMethod)) {
				drpOption.click();
			}
		}
		util.waitUntilElement(driver, applyLastPayment);
		applyLastPayment.click();
	}
	
	/**
	 * @param fullName 
	 * @throws InterruptedException 
	 * 
	 */
	public void renewUserForSOLine(String fullName) throws InterruptedException {
		Thread.sleep(10000);
		executor.executeScript("window.scrollBy(0,-500)", "");
		WebElement contactInTermLink = driver.findElement(By.xpath(String.format(contactTerm, fullName)));
		executor.executeScript("arguments[0].click();", contactInTermLink);
		util.waitUntilElement(driver, renewBtn);
		renewBtn.click();
	}
	
	public void createSaleorderinInstallments() {
		util.waitUntilElement(driver, subPlanDrp);
		subPlanDrp.click();
		// executor.executeScript("arguments[0].click();", selectDeusOpt);
		selectPayInInsatllmentElement.click();
		updateSalesOrder.click();
		assertTrue(driver.getTitle().contains(data.testDataProvider().getProperty("salesorderPage")));
	}

}
