package org.aia.pages.ces;

import static org.testng.Assert.assertTrue;

import java.util.Set;

import org.aia.utility.Utility;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class CheckOutPageCes {
	
WebDriver driver;
	
	Utility util = new Utility(driver, 30);
	
	public CheckOutPageCes(WebDriver Idriver) {
		this.driver = Idriver;
	}
	
	String creditCardNum = "4111111111111111";
	String cardExpMonth = "02";
	String cardExpYr = "2027";
	//ArchitectureElements
		
	@FindBy(xpath="//a[text()='Credit Card']") WebElement creditCardCheckoutCes;
	
	@FindBy(xpath="//span[@data-name='full_name']/following-sibling::div/div/input") WebElement cardHolderNameCheckOutCes;
	
	@FindBy(xpath="//select[@name='Exp month']") WebElement expMonthCheckOutCes;
	
	@FindBy(xpath="//select[@name='Exp year']") WebElement expYearCheckOutCes;
	
	@FindBy(css="#ccPaymentComp>div:nth-child(1)>div:nth-child(7)>div>div:nth-child(2)>div>label>span:nth-child(2)") WebElement CreditCrdcheckboxCheckOutCes;
	
	@FindBy(xpath="//button[text()='Process payment']") WebElement btnProcessPaymnt;
	
	@FindBy(xpath="//button[contains(text(),'Create address')]") WebElement creditCrdCreateAddressCes;
	
	@FindBy(xpath="//iframe[@title='Credit Card Input Frame']") WebElement creditCardNumFrame1Ces;
	
	//@FindBy(xpath="//iframe[@id='spreedlyFrame']") WebElement creditCardNumFrame1Ces;
	
	@FindBy(xpath="//iframe[@title='Card number']") WebElement creditCardNumFrame2Ces;
	
	@FindBy(xpath="//input[@id='card_number']") WebElement cardNumInputCes;
	
	@FindBy(xpath="//a[text()='ECheck']") WebElement eCheckCes;
	
	@FindBy(xpath="//a[contains(text(),'Download invoice')]") WebElement downloadInvoiceCes;
	
	@FindBy(xpath="//div[@data-name='name']") WebElement nameAddressCes;
	
	@FindBy(xpath="//select[@name='Type']") WebElement typeAddressCes;
	
	@FindBy(xpath="//*[@role='textbox']") WebElement enterYourAddressCes;
	
	@FindBy(xpath="//div[@class='selectize-dropdown-content']/div[1]") WebElement selectfirstAddressCes;	
	
	@FindBy(xpath="//div[@data-label='Discount Code']/input") WebElement discountCodeOrderCes;
	
	@FindBy(xpath="//*[text()='Save']") WebElement saveBtnAddressCes;
	
	@FindBy(xpath="//div[@data-name='fullName']/input") WebElement fullNameECheckoutCes;
	
	@FindBy(xpath="//div[@data-name='bankName']/input") WebElement bankNameECheckoutCes;
	
	@FindBy(xpath="//div[@data-name='bankRoutingNumber']/input") WebElement bankRoutingECheckoutCes;
	
	@FindBy(xpath="//div[@data-name='bankAccountNumber']/input") WebElement bankAccntNumECheckoutCes;
	
	@FindBy(xpath="//select[@name='Bank Account Type']") WebElement bankAccntTypeECheckoutCes;
	
	@FindBy(xpath="//select[@name='Bank Account Holder Type']") WebElement bankAccntHolderTypeECheckoutCes;
	
	@FindBy(xpath="#eCheckMethod>div:nth-child(6)>div>div:nth-child(2)>div>label>span:nth-child(2)") WebElement checkboxECheckoutCes;
	
	@FindBy(xpath="//button[text()='Process payment']") WebElement btnProcessPaymntECheckoutCes;
	
	@FindBy(xpath="div[data-name='paymentMethodsDiv']>div:nth-child(2)>div>button") WebElement sendPerformaInvoiceBtnCes; 
	
	@FindBy(xpath="//p[contains(text(), 'Thank you for submitting your application')]") WebElement passportConfirmtxtCes;
	
	@FindBy(xpath = "//button[text()='Confirm Order']") WebElement confirmOrderBtn;
	
	/*
	 * @param : text
	 * @param : aiaMemberNumber
	 * @param : orgType
	 */
	public void SubscriptionType(String text) throws Exception {
		if(text.contentEquals("Architecture Firm")||text.contentEquals("Architecture - Single Discipline")||text.contentEquals("Multi-Disciplinary (Architect Led)")||text.contentEquals("Multi-disciplinary (engineer led)")
				||text.contentEquals("Multi-disciplinary (interior led)")||text.contentEquals("Multi-disciplinary (planning led)")||text.contentEquals("Design & construction services")) 
		{
			verifyConfirmationTxt();
		}
		
		else if(text.contentEquals("Building product manufacturer")||text.contentEquals("Construction")||text.contentEquals("Consulting")||text.contentEquals("Engineering")
				||text.contentEquals("Interior Design")||text.contentEquals("Landscape")||text.contentEquals("Real Estate/Building owner")||text.contentEquals("Law")
				||text.contentEquals("Press")||text.contentEquals("Other")) 
		{
			enterCardDetailsCes();
		}
		
		else if(text.contentEquals("Institutional")||text.contentEquals("Government/public")||text.contentEquals("Non-profit/trade association")||text.contentEquals("Licensing Board"))
		{
			verifyConfirmationTxt();
		}
		
	}
	
	public void enterCardDetailsCes() throws InterruptedException {
		util.waitUntilElement(driver, creditCardCheckoutCes);
		util.waitUntilElement(driver, creditCardNumFrame1Ces);
		Thread.sleep(5000);
		driver.switchTo().frame(creditCardNumFrame1Ces);
		Thread.sleep(10000);
		driver.switchTo().frame(creditCardNumFrame2Ces);
		cardNumInputCes.sendKeys(creditCardNum);
		driver.switchTo().defaultContent();
		util.selectDropDownByText(expMonthCheckOutCes, cardExpMonth);
		Thread.sleep(2000);
		util.waitUntilElement(driver, expYearCheckOutCes);
		util.selectDropDownByText(expYearCheckOutCes, cardExpYr);
		discountCodeOrderCes.sendKeys("");
		btnProcessPaymnt.click();		
	}
	
	
	public void enterECheckDetailsCes(String accountHolderName ,String bankName, String bankRoutingNumber, String bankAccountNumber) throws InterruptedException {
		//util.waitUntilElement(driver, eCheckCes);
		JavascriptExecutor executor = (JavascriptExecutor) driver;
		Thread.sleep(50000);
		executor.executeScript("arguments[0].scrollIntoView(true);", eCheckCes);
		util.waitUntilElement(driver, eCheckCes);
		Thread.sleep(20000);
		executor.executeScript("arguments[0].click();",eCheckCes);
		//eCheckCes.click();
		Thread.sleep(2000);
		util.waitUntilElement(driver, fullNameECheckoutCes);
		fullNameECheckoutCes.sendKeys(accountHolderName);
		bankNameECheckoutCes.sendKeys(bankName);
		bankRoutingECheckoutCes.sendKeys(bankRoutingNumber);
		bankAccntNumECheckoutCes.sendKeys(bankAccountNumber);
		util.selectDropDownByText(bankAccntTypeECheckoutCes, "savings");
		util.selectDropDownByText(bankAccntHolderTypeECheckoutCes, "personal");
		//checkboxECheckoutCes.click();
		//discountCodeOrderCes.sendKeys("");
		btnProcessPaymntECheckoutCes.click();
	}

	public void enterBillingAddressDetailsCes() throws InterruptedException {
		util.waitUntilElement(driver, creditCrdCreateAddressCes);
		creditCrdCreateAddressCes.click();
		util.waitUntilElement(driver, nameAddressCes);
		nameAddressCes.sendKeys("AddressFN");
		util.selectDropDownByText(typeAddressCes, "Home");
		enterYourAddressCes.sendKeys("Jasonville, IN, USA");
		selectfirstAddressCes.click();
		saveBtnAddressCes.click();
	}
	
	public void downloadInvoice() {
		util.waitUntilElement(driver, downloadInvoiceCes);
		downloadInvoiceCes.click();
		sendPerformaInvoiceBtnCes.click();
		Set<String>links = driver.getWindowHandles();
		String currWin = driver.getWindowHandle();
		for(String s1: links)
		if(!s1.contentEquals(currWin))	
		{
			driver.switchTo().window(s1);
			System.out.println("Link is identified");
		}
		
	}

	public void verifyConfirmationTxt() throws Exception {
		Thread.sleep(1000);
		util.waitUntilElement(driver, passportConfirmtxtCes);
		assertTrue(passportConfirmtxtCes.isDisplayed(), "Submission Confirmation text is visible.");
	}
	
	public void confirmOrderWithNoAmt() {
     util.waitUntilElement(driver, confirmOrderBtn);
     confirmOrderBtn.click();
	}
}
