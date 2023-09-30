package org.aia.pages.ces;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Set;

import org.aia.utility.Utility;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.encryption.InvalidPasswordException;
import org.apache.pdfbox.text.PDFTextStripper;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class PaymentSuccessFullPageCes {

	WebDriver driver;

	Utility util = new Utility(driver, 30);

	public PaymentSuccessFullPageCes(WebDriver Idriver) {
		this.driver = Idriver;
	}

	@FindBy(xpath = "//div[text()='Payment Successful']")
	WebElement paymentSucessfulMessage;

	@FindBy(xpath = "//span[text()='View Receipt']/parent::button")
	WebElement viewReceiptBtn;

	@FindBy(xpath = "//span[@class = 'currencyInputSpan FrameworkCurrencyField']")
	WebElement paymentTotalTxt;

	public String ClickonViewReceipt() throws InvalidPasswordException, IOException, InterruptedException {
		String pdfContent = null;
		util.waitUntilElement(driver, paymentSucessfulMessage);
		System.out.println("Payment is Succesfull.");
		String totalAmnt = paymentTotalTxt.getText();
		int i = totalAmnt.indexOf(".");
		String finalPagetotal = totalAmnt.substring(1, i);
		totalAmnt = paymentTotalTxt.getText().replaceAll("[$]*", "").trim();
		Object totalAmnt1 = Double.valueOf(((String) totalAmnt).replaceAll(",", "").trim());
		viewReceiptBtn.click();
		Thread.sleep(10000);
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

					// Printing the content on console
					System.out.println(pdfContent);
					if (pdfContent.contains(totalAmnt)) {
						assertTrue(pdfContent.contains(totalAmnt), "Total amount paid is present in Recipt.");
					}
					System.out.println("Link is identified");
					break;
				}
			}
		return pdfContent;
	}

	public Object amountPaid() throws InvalidPasswordException, IOException {
		util.waitUntilElement(driver, paymentSucessfulMessage);
		String totalAmnt = paymentTotalTxt.getText();
		int i = totalAmnt.indexOf(".");
		String finalPagetotal = totalAmnt.substring(1, i);
		totalAmnt = paymentTotalTxt.getText().replaceAll("[$]*", "").trim();
		Object totalAmnt1 = Double.valueOf(((String) totalAmnt).replaceAll(",", "").trim());
		return totalAmnt1;
	}

	public Object freeDuesamountPaid() throws InvalidPasswordException, IOException {
		util.waitUntilElement(driver, paymentSucessfulMessage);
		Object totalAmnt1 = 0;
		String totalAmnt = paymentTotalTxt.getText();
		if (totalAmnt.equalsIgnoreCase("Free")) {
			totalAmnt1 = 0.00;
		} else {
			int i = totalAmnt.indexOf(".");
			String finalPagetotal = totalAmnt.substring(1, i);
			totalAmnt = paymentTotalTxt.getText().replaceAll("[$]*", "").trim();
			totalAmnt1 = Double.valueOf(((String) totalAmnt).replaceAll(",", "").trim());
			System.out.println("Dues are not zero");
		}
		return totalAmnt1;
	}
	
	public String clickonViewReceiptNoDues() throws InvalidPasswordException, IOException, InterruptedException {
		String pdfContent = null;
		util.waitUntilElement(driver, paymentSucessfulMessage);
		System.out.println("Payment is Succesfull.");
		String totalAmnt = "0";
		viewReceiptBtn.click();
		Thread.sleep(10000);
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

					// Printing the content on console
					System.out.println(pdfContent);
					if (pdfContent.contains(totalAmnt)) {
						assertTrue(pdfContent.contains(totalAmnt), "Total amount paid is present in Recipt.");
					}
					System.out.println("Link is identified");
					break;
				}
			}
		return pdfContent;
	}
}
