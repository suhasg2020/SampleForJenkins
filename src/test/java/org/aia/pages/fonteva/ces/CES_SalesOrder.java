package org.aia.pages.fonteva.ces;

import static org.testng.Assert.*;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Set;

import org.aia.utility.ConfigDataProvider;
import org.aia.utility.Utility;
import org.apache.commons.lang3.Validate;
import org.apache.log4j.Logger;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.encryption.InvalidPasswordException;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.xmlbeans.soap.SOAPArrayType;
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
public class CES_SalesOrder {
	WebDriver driver;
	Utility util = new Utility(driver, 30);
	ConfigDataProvider data = new ConfigDataProvider();
	Actions action;
	JavascriptExecutor executor;
	String pdfContent = null;

	public CES_SalesOrder(WebDriver Idriver) {
		this.driver = Idriver;
		action = new Actions(driver);
		executor = (JavascriptExecutor) driver;
	}

	@FindBy(xpath = "(//ul//span[contains(text(),'Sales Order')]/ancestor::a)[1]")
	WebElement salesOrderLink;

	@FindBy(xpath = "//table[@aria-label='Sales Orders']")
	WebElement salesOrderTable;

	@FindBy(xpath = "(//table[@aria-label='Sales Orders']//tr)[2]/th//a")
	WebElement orderId;

	@FindBy(xpath = "//span[contains(text(),'Sales Order Lines')]//ancestor::a")
	WebElement salesOrderLine;

	@FindBy(xpath = "//table[@aria-label='Sales Order Lines']")
	WebElement salesOrderLineTable;

	// @FindBy(xpath="(//table[@aria-label='Sales Order
	// Lines']//tr[1])[2]//th/span/a")
	@FindBy(xpath = "(//table[@aria-label='Sales Order Lines']//tr[1])[2]//th//a")
	WebElement salesOrderFirstLine;

	@FindBy(xpath = "(//table[@aria-label='Sales Order Lines']//tr[2])//th//a")
	WebElement salesOrderSecondLine;

	@FindBy(xpath = "(//button[text()='Set Discount'])[1]")
	WebElement setDiscountBtn;

	@FindBy(xpath = "(//button[text()='Set Discount'])[2]")
	WebElement setDiscountBtnSecond;

	@FindBy(xpath = "//h2[text()='Set Discount']")
	WebElement discountPopUp;

	@FindBy(xpath = "//input[@name='Discount_Ammount_Input']")
	WebElement discountInput;

	@FindBy(xpath = "//button[text()='Next']")
	WebElement nextBtn;

	@FindBy(xpath = "//p[@title='Total']/parent::div/p[2]/slot/lightning-formatted-text")
	WebElement afterDiscountAmt;

	@FindBy(xpath = "//table[@aria-label='Sales Order Lines']//tbody//tr[1]//td[3]//a")
	WebElement firstSalesorderLineText;

	@FindBy(xpath = "//table[@aria-label='Sales Order Lines']//tbody//tr[2]//td[3]//a")
	WebElement secondSalesorderLineText;

	@FindBy(xpath = "//table[@aria-label='Sales Order Lines']//tbody//tr[1]//td[6]//lst-formatted-text")
	WebElement salesOrderListPriceText;

	@FindBy(xpath = "//table[@aria-label='Sales Order Lines']//tbody//tr[1]//td[5]//lst-formatted-text")
	WebElement salesPriceText;

	@FindBy(xpath = "//table[@aria-label='Sales Orders']//tbody//tr[2]//th//a")
	WebElement renewSalesOrder;

	@FindBy(xpath = "//table[@aria-label='Sales Orders']//tbody//tr//th//a")
	WebElement JoinSalesOrder;

	@FindBy(xpath = "(//a[contains(text(),'Show All')])")
	WebElement showallBtn;

	@FindBy(xpath = "(//span[contains(text(),'Receipts')]//ancestor::a)[2]")
	WebElement receiptListBtn;

	@FindBy(xpath = "//table[@aria-label='Receipts']//tbody//tr//th//a")
	WebElement receiptNumber;

	@FindBy(xpath = "//button[text()='Download PDF']")
	WebElement downloadPdfBtn;

	@FindBy(xpath = "//embed")
	WebElement downloadPdf;

	/**
	 * @throws InterruptedException
	 * 
	 */
	public void setDiscount() throws InterruptedException {
		util.waitUntilElement(driver, salesOrderLink);
		executor.executeScript("arguments[0].click();", salesOrderLink);
		// Validate sales order line is there
		util.waitUntilElement(driver, salesOrderTable);
		assertTrue(salesOrderTable.isDisplayed());
		util.waitUntilElement(driver, orderId);
		executor.executeScript("arguments[0].click();", orderId);
		// orderId.click();
		util.waitUntilElement(driver, salesOrderLine);
		salesOrderLine.click();
		util.waitUntilElement(driver, salesOrderLineTable);
		assertTrue(salesOrderLineTable.isDisplayed());

		util.waitUntilElement(driver, salesOrderFirstLine);
		executor.executeScript("arguments[0].click();", salesOrderFirstLine);
		// salesOrderFirstLine.click();
		util.waitUntilElement(driver, setDiscountBtn);
		setDiscountBtn.click();
		util.waitUntilElement(driver, discountPopUp);
		discountPopUp.click();
		assertTrue(discountPopUp.isDisplayed());
		util.enterText(driver, discountInput, data.testDataProvider().getProperty("discountAmt"));
		util.waitUntilElement(driver, nextBtn);
		nextBtn.click();
		util.waitUntilElement(driver, afterDiscountAmt);
		// Validate discount is set as $0 for first line
		driver.navigate().refresh();
		util.waitUntilElement(driver, afterDiscountAmt);
		assertEquals(afterDiscountAmt.getText(), data.testDataProvider().getProperty("replacatedAmt"));
		driver.navigate().back();
		driver.navigate().back();
		util.waitUntilElement(driver, salesOrderFirstLine);
		executor.executeScript("arguments[0].click();", salesOrderFirstLine);
		// salesOrderSecondLine.click();
		// util.waitUntilElement(driver, salesOrderFirstLine);
		setDiscountBtnSecond.click();
		util.waitUntilElement(driver, discountPopUp);
		assertTrue(discountPopUp.isDisplayed());
		util.enterText(driver, discountInput, data.testDataProvider().getProperty("discountAmt"));
		util.waitUntilElement(driver, nextBtn);
		nextBtn.click();
		driver.navigate().refresh();
		util.waitUntilElement(driver, afterDiscountAmt);
		// Validate discount is set as $0 for second Line
		assertEquals(afterDiscountAmt.getText(), data.testDataProvider().getProperty("replacatedAmt"));
		ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());
		driver.switchTo().window(tabs.get(0));
		Thread.sleep(10000);
		driver.navigate().refresh();
	}

	/**
	 * 
	 */
	public void switchToTab() {
		((JavascriptExecutor) driver).executeScript("window.open()");
		ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());
		driver.switchTo().window(tabs.get(1));
	}

	/**
	 * 
	 */
	public void checkDuplicateSOLItem() {
		action.sendKeys(Keys.ARROW_DOWN).build().perform();
		util.waitUntilElement(driver, salesOrderLink);
		salesOrderLink.click();
		util.waitUntilElement(driver, salesOrderTable);
		assertTrue(salesOrderTable.isDisplayed());
		util.waitUntilElement(driver, orderId);
		executor.executeScript("arguments[0].click();", orderId);
		// orderId.click();
	}

	/**
	 * @return
	 */
	public Double checkSaleorderLine() {
		util.waitUntilElement(driver, salesOrderLine);
		salesOrderLine.click();
		util.waitUntilElement(driver, salesOrderLineTable);
		assertTrue(salesOrderLineTable.isDisplayed());
		String salesOrderListPrice = salesOrderListPriceText.getText().replaceAll("[$]*", "");
		// System.out.println("So Price:" + salesOrderListPrice);
		Double listPrice = Double.parseDouble(salesOrderListPrice);
		// System.out.println("So Price:" + listPrice);
		Double salesPrice = listPrice / 12;// Here 12 are months
		LocalDate localDate = java.time.LocalDate.now();
		// System.out.println("Current month:" + localDate.getMonth().getValue());
		// In this equation we are taking left months up to expire membership
		Double installMentSalePrice = salesPrice * ((12 - localDate.getMonth().getValue() + 1));
		Double finalSalePrice = installMentSalePrice / 6; // Here 6 is how much installment we gone use
		return installMentSalePrice;
		// System.out.println("Last sale price" + finalSalePrice);
	}

	/**
	 * Here I am validate the both sales order line using the assertions.
	 */
	public void validateSalesOrderLine() {
		util.waitUntilElement(driver, firstSalesorderLineText);
		String firstSOLineText = firstSalesorderLineText.getAttribute("title");
		util.waitUntilElement(driver, secondSalesorderLineText);
		String secondSOLineText = secondSalesorderLineText.getAttribute("title");
		assertNotEquals(firstSOLineText, secondSOLineText);
	}

	/**
	 * 
	 */
	public void selectSalesOrder() {
		action.sendKeys(Keys.ARROW_DOWN).build().perform();
		util.waitUntilElement(driver, salesOrderLink);
		salesOrderLink.click();
		util.waitUntilElement(driver, salesOrderTable);
		assertTrue(salesOrderTable.isDisplayed());
	}

	/**
	 * @throws IOException
	 * @throws InvalidPasswordException
	 * @throws InterruptedException
	 * 
	 */
	public void renewReceipt() throws InvalidPasswordException, IOException, InterruptedException {
		util.waitUntilElement(driver, renewSalesOrder);
		// renewSalesOrder.click();
		executor.executeScript("arguments[0].click();", renewSalesOrder);
		util.waitUntilElement(driver, showallBtn);
		showallBtn.click();
		action.sendKeys(Keys.ARROW_DOWN).build().perform();
		action.sendKeys(Keys.ARROW_DOWN).build().perform();
		util.waitUntilElement(driver, receiptListBtn);
		receiptListBtn.click();
		util.waitUntilElement(driver, receiptNumber);
		executor.executeScript("arguments[0].click();", receiptNumber);
		// receiptNumber.click();
		util.waitUntilElement(driver, downloadPdfBtn);
		downloadPdfBtn.click();
		Set<String> links = driver.getWindowHandles();
		String currWin = driver.getWindowHandle();
		Thread.sleep(1000);
		for (String s1 : links)
			if (!s1.contentEquals(currWin)) {
				driver.switchTo().window(s1);
				String currentUrl = driver.getCurrentUrl();
				if (currentUrl.contains("signupSuccess")) {
					continue;
				} else if (currentUrl.contains("generateMultiplePDF")) {
					URL url = new URL(currentUrl);

					// Open stream method is used to open the pdf file
					InputStream is = url.openStream();

					// using the Buffered input class(creating the object file parse)
					BufferedInputStream fileParse = new BufferedInputStream(is);

					// PD document is coming from PDF box
					PDDocument document = null;

					// Initialize the document from load method(load buffered input class)
					document = PDDocument.load(fileParse);

					// creating object he he & returning the content
					PDFTextStripper strip = new PDFTextStripper();

					strip.setStartPage(1);
					pdfContent = strip.getText(document);
					if (pdfContent.contains(data.testDataProvider().getProperty("pdfContentRenew"))) {
						assertTrue(pdfContent.contains(data.testDataProvider().getProperty("pdfContent")),
								"Pdf is downloaded.");
					}
					break;

				}
			}
	}

	/**
	 * @throws InvalidPasswordException
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public void reJoinReceipt() throws InvalidPasswordException, IOException, InterruptedException {
		util.waitUntilElement(driver, JoinSalesOrder);
		// renewSalesOrder.click();
		executor.executeScript("arguments[0].click();", JoinSalesOrder);
		util.waitUntilElement(driver, showallBtn);
		showallBtn.click();
		action.sendKeys(Keys.ARROW_DOWN).build().perform();
		action.sendKeys(Keys.ARROW_DOWN).build().perform();
		util.waitUntilElement(driver, receiptListBtn);
		receiptListBtn.click();
		util.waitUntilElement(driver, receiptNumber);
		executor.executeScript("arguments[0].click();", receiptNumber);
		// receiptNumber.click();
		util.waitUntilElement(driver, downloadPdfBtn);
		downloadPdfBtn.click();
		Set<String> links = driver.getWindowHandles();
		String currWin = driver.getWindowHandle();
		Thread.sleep(1000);
		for (String s1 : links)
			if (!s1.contentEquals(currWin)) {
				driver.switchTo().window(s1);
				String currentUrl = driver.getCurrentUrl();
				if (currentUrl.contains("signupSuccess")) {
					continue;
				} else if (currentUrl.contains("generateMultiplePDF")) {
					URL url = new URL(currentUrl);

					// Open stream method is used to open the pdf file
					InputStream is = url.openStream();

					// using the Buffered input class(creating the object file parse)
					BufferedInputStream fileParse = new BufferedInputStream(is);

					// PD document is coming from PDF box
					PDDocument document = null;

					// Initialize the document from load method(load buffered input class)
					document = PDDocument.load(fileParse);

					// creating object he he & returning the content
					PDFTextStripper strip = new PDFTextStripper();

					strip.setStartPage(1);
					pdfContent = strip.getText(document);

					// Here we validate pdf is downloaded;
					if (pdfContent.contains(data.testDataProvider().getProperty("pdfContentRejoin"))) {
						assertTrue(pdfContent.contains(data.testDataProvider().getProperty("pdfContent")),
								"Pdf is downloaded.");
					}
					break;
				}
			}

	}
	
	/**
	 * @throws InvalidPasswordException
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public void joinReceipt() throws InvalidPasswordException, IOException, InterruptedException {
		util.waitUntilElement(driver, JoinSalesOrder);
		// renewSalesOrder.click();
		executor.executeScript("arguments[0].click();", JoinSalesOrder);
		util.waitUntilElement(driver, showallBtn);
		showallBtn.click();
		action.sendKeys(Keys.ARROW_DOWN).build().perform();
		action.sendKeys(Keys.ARROW_DOWN).build().perform();
		util.waitUntilElement(driver, receiptListBtn);
		receiptListBtn.click();
		util.waitUntilElement(driver, receiptNumber);
		executor.executeScript("arguments[0].click();", receiptNumber);
		// receiptNumber.click();
		util.waitUntilElement(driver, downloadPdfBtn);
		downloadPdfBtn.click();
		Set<String> links = driver.getWindowHandles();
		String currWin = driver.getWindowHandle();
		Thread.sleep(1000);
		for (String s1 : links)
			if (!s1.contentEquals(currWin)) {
				driver.switchTo().window(s1);
				String currentUrl = driver.getCurrentUrl();
				if (currentUrl.contains("signupSuccess")) {
					continue;
				} else if (currentUrl.contains("generateMultiplePDF")) {
					URL url = new URL(currentUrl);

					// Open stream method is used to open the pdf file
					InputStream is = url.openStream();

					// using the Buffered input class(creating the object file parse)
					BufferedInputStream fileParse = new BufferedInputStream(is);

					// PD document is coming from PDF box
					PDDocument document = null;

					// Initialize the document from load method(load buffered input class)
					document = PDDocument.load(fileParse);

					// creating object he he & returning the content
					PDFTextStripper strip = new PDFTextStripper();

					strip.setStartPage(1);
					pdfContent = strip.getText(document);

					// Here we validate pdf is downloaded;
					if (pdfContent.contains(data.testDataProvider().getProperty("pdfContent"))) {
						assertTrue(pdfContent.contains(data.testDataProvider().getProperty("pdfContent")),
								"Pdf is downloaded.");
					}
					break;
				}
			}

	}
	

}
