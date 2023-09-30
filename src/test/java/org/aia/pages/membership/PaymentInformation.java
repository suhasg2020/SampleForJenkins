package org.aia.pages.membership;

import static org.testng.Assert.*;
import org.aia.utility.ConfigDataProvider;
import org.aia.utility.Utility;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

public class PaymentInformation {

	WebDriver driver;
	Actions action;
	Utility util = new Utility(driver, 10);
	ConfigDataProvider data = new ConfigDataProvider();
	public PaymentInformation(WebDriver Idriver) 
	{
		this.driver = Idriver;
		action = new Actions(driver);
	}
	
	String creditCardNum = "4111111111111111";
	String cardExpMonth = "02";
	String cardExpYr = "2027";
	
	@FindBy(xpath="//*[@id='checkout-form-wrapper']/div[1]/h5/text()") WebElement paymentInfo;
	
	@FindBy(xpath="//a[text()='Credit card']") WebElement creditCard;
	
	@FindBy(xpath="//a[text()='ECheck']") WebElement eCheck;
	
	@FindBy(xpath="//div[@data-name='full_name']/input") WebElement fullName;
	
	@FindBy(xpath="//iframe[@title='Credit Card Input Frame']") WebElement cardNumFrame1;
	
	@FindBy(xpath="//iframe[@title='Card number']") WebElement cardNumFrame2;
	
	@FindBy(xpath="//input[@name='card_number']") WebElement cardNum;
	
	@FindBy(xpath="//select[@name='Exp month']") WebElement expMonth;
	
	@FindBy(xpath="//select[@name='Exp year']") WebElement expYr;
	
	@FindBy(xpath="//label[@data-name='savePaymentMethod']") WebElement chckBox;
	
	@FindBy(xpath="//button[@data-name='processBtn']") WebElement procssPaymntBtn;
	
	@FindBy(xpath="//button[text()='Process payment']") WebElement processPaymnt;
	
	@FindBy(xpath="//*[@class=\"shopping-cart-summary-component\"]//li[1]//span/table/tbody/tr/td[4]/span/span/span") WebElement aiaNational;

	@FindBy(xpath="//a[@id='completePayment']") WebElement completeOrder;
	
	@FindBy(xpath="//li[@title=\"Credit card\"]/a") WebElement creditCardLink;
	
	@FindBy(xpath="//span[@id='order_total']") WebElement afterZeroSalesOrderAmtText;
	
	@FindBy(xpath="//a[@id='completePayment']") WebElement complatePaymentBtn;
	@FindBy(xpath="//a[text()='ECheck']") WebElement echeckTab;
	
	@FindBy(xpath="//div[@data-label='Account Holder Name']/input") WebElement accountHolderName;

	@FindBy(xpath="//div[@data-label='Bank Name']/input") WebElement bankName;

	@FindBy(xpath="//div[@data-name='bankRoutingNumber']/input") WebElement bankRoutingNumber;
	
	@FindBy(xpath="//div[@data-name='bankAccountNumber']/input") WebElement accountNumber;
	
	@FindBy(xpath="//select[contains(@name,'Account Type')]") WebElement accountType;
	
	@FindBy(xpath="//select[contains(@name,'Account Holder Type')]") WebElement holderType;
	
	@FindBy(xpath="//button[text()='Process payment']") WebElement processPaymentBtn;
	public void clickOnProcesspaymnt() {
		
		util.waitUntilElement(driver, processPaymnt);
		processPaymnt.click();
		
	}
	
	public void clickOnCreditCard() {
		
		util.waitUntilElement(driver, creditCard);
		creditCard.click();
		
	}
	public String paymentDetails(String text ) throws InterruptedException {
		String aiaNatnl = null;
		if(text.contentEquals("noLicense")||text.contentEquals("graduate")
				||text.contentEquals("axp")) 
		{
			//util.waitUntilElement(driver, completeOrder);
			aiaNatnl =	enterCrditCardDetails();
			Thread.sleep(30000);
			//completeOrder.click();
			Thread.sleep(30000);
		}
		else if(text.contentEquals("activeUSLicense")||text.contentEquals("activeNonUSLicense")||text.contentEquals("supervision")||text.contentEquals("faculty")
				||text.contentEquals("allied")) {
		 aiaNatnl =	enterCrditCardDetails();

			Thread.sleep(10000);
		}
		
		return aiaNatnl;
	}
	
	public String enterCrditCardDetails() throws InterruptedException 
	{
		Thread.sleep(20000);
		util.waitUntilElement(driver, creditCard);
		util.waitUntilElement(driver, cardNumFrame1);
		
		String aiaNatnl = aiaNational.getText();
		util.waitUntilElement(driver, cardNumFrame1);
		driver.switchTo().frame(cardNumFrame1);
		Thread.sleep(2000);
		util.waitUntilElement(driver, cardNumFrame2);
		driver.switchTo().frame(cardNumFrame2);
		Thread.sleep(2000);
		util.enterText(driver, cardNum, creditCardNum);
		driver.switchTo().defaultContent();
		Select s1 = new Select(expMonth);
		s1.selectByValue(cardExpMonth);
		
		Select s2 = new Select(expYr);
		s2.selectByValue(cardExpYr);
		
		chckBox.click();
		procssPaymntBtn.click();
		return aiaNatnl;
	}
	
	/**
	 * Owner: Suhas
	 * this method is created for $0 sales order 
	 */
	public void makeZeroOrderPayment() {
	    util.waitUntilElement(driver, afterZeroSalesOrderAmtText);
	    //Validate final price is become zero in UI.
        assertEquals(afterZeroSalesOrderAmtText.getText(),data.testDataProvider().getProperty("replacatedAmt"));
        util.waitUntilElement(driver, complatePaymentBtn);
        complatePaymentBtn.click();
	}
	
      /** @param accountHolder
	 * @param accountTypeOpt
	 * @param holderType
	 */
	public void paymentViaEcheck(String accountHolder, String accountTypeOpt, String accountHolderType) {
		//driver.navigate().refresh();
		util.waitUntilElement(driver, echeckTab);
		echeckTab.click();
		util.waitUntilElement(driver, accountHolderName);
		util.enterText(driver, accountHolderName, accountHolder);
		util.enterText(driver, bankName, data.testDataProvider().getProperty("bankName"));
		util.enterText(driver,bankRoutingNumber,data.testDataProvider().getProperty("bankRoutingNo"));
		util.enterText(driver, accountNumber, data.testDataProvider().getProperty("bankAccountNo"));
		action.moveToElement(accountType).build().perform();
		util.selectDrp(accountType).selectByValue(accountTypeOpt);
		util.selectDrp(holderType).selectByValue(accountHolderType);
		processPaymentBtn.click();
	}
	
}
