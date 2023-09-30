package org.aia.pages.membership;

import static org.testng.Assert.assertTrue;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

import org.aia.utility.Utility;
import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.testng.Reporter;

public class OrderSummaryPage {

	WebDriver driver;
	Utility util = new Utility(driver, 10);
	String renew_membername = "autoMember" + RandomStringUtils.randomAlphabetic(3);
	String memberID;
	ArrayList<String> list = new ArrayList<String>();

	public OrderSummaryPage(WebDriver Idriver) {
		this.driver = Idriver;
	}

	@FindBy(xpath = "//p[text()='Dues:']")
	WebElement duesLabel;

	@FindBy(xpath = "//input[@id='confirmLicense']/parent::span")
	WebElement confirmLicense;

	@FindBy(xpath = "//input[@id='confirmAlliedProfessional']/parent::span")
	WebElement confirmAlliedProfessional;

	@FindBy(xpath = "//input[@id='confirmTerms']")
	WebElement confirmTerms;

	@FindBy(xpath = "//p[text()='Pay in Full']")
	WebElement payInFull;

	@FindBy(xpath = "//p[text()='Pay in installments']")
	WebElement payInInstallments;

	@FindBy(xpath = "//p[text()='Exit']")
	WebElement exitBtn;

	// Testing Env
	@FindBy(xpath = "//p[text()='Pay now']")
	WebElement payNowBtn;

	@FindBy(xpath = "//*[@id='menu-subscriptionPlanId']/div[3]/ul/li[1]/text()")
	WebElement devpayinFull;

	// @FindBy(xpath="//*[@id='menu-subscriptionPlanId']/div[3]/ul/li[2]")
	// WebElement sixInstallments;
	@FindBy(xpath = "//*[@id='menu-subscriptionPlanId']/div[3]/ul/li[text()=6][1]")
	WebElement sixInstallments;

	@FindBy(xpath = "//input[@id='confirmInstallments']/parent::span")
	WebElement confirmInstallments;

	@FindBy(xpath = "//form/table[2]/tbody/tr/td[2]")
	WebElement totalMembershipDues;

	@FindBy(xpath = "//label[text()='Installments']/following-sibling::div/div")
	WebElement Installments;

	@FindBy(xpath = "//p[text()='Pay offline']")
	WebElement payoffline;

	@FindBy(xpath = "//span[text()='ArchiPAC donation']/preceding-sibling::span")
	WebElement pacChkBx;

	@FindBy(xpath = "//*[@id=\"root\"]/div/div/div/div[2]/div[1]/form/table[3]/tbody/tr/td[2]/span")
	WebElement pacValue;

	@FindBy(xpath = "//*[@id=\"memberTypeLabel\"]")
	WebElement supplementalDuesRdoBtn;

	// Renew - chapter assesses supplemental dues for membership

	@FindBy(xpath = "//*[@id=\"memberName\"]")
	WebElement memberName;

	@FindBy(xpath = "//*[@id=\"memberId\"]")
	WebElement memberNumber;

	@FindBy(xpath = "//*[@id=\"nonMemberArchitectCount\"]")
	WebElement nonMemberArchitectCount;

	@FindBy(xpath = "//*[@id=\"technicalStaffCount\"]")
	WebElement technicalStaffCount;

	@FindBy(xpath = "//*[@id=\"otherStaffCount\"]")
	WebElement otherStaffCount;
	
	@FindBy(xpath="//span[text()='ArchiPAC donation']")
    WebElement archipacText;
	
	@FindBy(xpath="//input[contains(@name,'additionalPackages')]//parent::span")
	WebElement archipacCheckBox;

	public void confirmTerms(String text) throws InterruptedException {
		if (text.contentEquals("activeNonUSLicense") || text.contentEquals("supervision")
				|| text.contentEquals("noLicense") || text.contentEquals("graduate") || text.contentEquals("axp")
				|| text.contentEquals("faculty")) {
			util.waitUntilElement(driver, duesLabel);
			Thread.sleep(10000);
			// util.waitUntilElement(driver, confirmTerms);
			confirmTerms.click();
		}

		else if (text.contentEquals("activeUSLicense")) {
			Thread.sleep(20000);
			util.waitUntilElement(driver, duesLabel);
			Thread.sleep(10000);
			util.waitUntilElement(driver, confirmLicense);
			Thread.sleep(10000);
			confirmLicense.click();
			confirmTerms.click();
		}

		else if (text.contentEquals("allied")) {
			util.waitUntilElement(driver, duesLabel);
			Thread.sleep(10000);
			// util.waitUntilElement(driver, confirmTerms);
			confirmTerms.click();
			confirmAlliedProfessional.click();
		}

	}

	// Staging Env
	public void clickonPayInFull() throws InterruptedException {
		payInFull.click();
	}

	// Testing Env
	public void clickonPayNow() {
		util.waitUntilElement(driver, payNowBtn);
		payNowBtn.click();
	}

	public void clickonPayOffline() {
		payoffline.click();

	}

	// Staging Env
	public void clickonPayInInstallments() {
		util.waitUntilElement(driver, duesLabel);
		confirmLicense.click();
		confirmTerms.click();
		payInInstallments.click();
	}

	// Testing Env
	public String payInInstallmentsClick(String text) throws InterruptedException {
		LocalDate localDate = java.time.LocalDate.now();
		String totalMembership = null;
		if (localDate.getMonthValue() >= 8 || localDate.getMonthValue() <= 04) {
			if (text.contentEquals("activeUSLicense") || text.contentEquals("allied")) {
				util.waitUntilElement(driver, Installments);
				totalMembership = totalMembershipDues.getText();
				Installments.click();
				util.waitUntilElement(driver, sixInstallments);
				sixInstallments.click();
				util.waitUntilElement(driver, confirmInstallments);
				confirmInstallments.click();
				payNowBtn.click();
			}

			if (text.contentEquals("graduate") || text.contentEquals("axp") || text.contentEquals("noLicense")
					|| text.contentEquals("supervision") || text.contentEquals("faculty")) {

				totalMembership = totalMembershipDues.getText();
				System.out.println("Continue");
			}

			if (text.contentEquals("activeNonUSLicense")) {
				totalMembership = totalMembershipDues.getText();
				Installments.click();
				sixInstallments.click();
				util.waitUntilElement(driver, confirmInstallments);
				confirmInstallments.click();
				payNowBtn.click();
			}
		} else {
			payNowBtn.click();
			Thread.sleep(5000);

		}
		return totalMembership;
	}

	public int GetPacDonationAmount() {
		String pac = pacValue.getText();
		int i = pac.indexOf(".");
		String p = pac.substring(2, i);
		pacChkBx.click();
		String total = totalMembershipDues.getText();
		i = total.indexOf(".");
		String t = total.substring(2, i);

		int amnt = Integer.parseInt(t) + Integer.parseInt(p);
		return amnt;
	}

	public void selectRadioBtnByValue(String string) {
		Utility.waitForWebElement(driver, supplementalDuesRdoBtn, 10);
		String radioBtnValue = string;
		String value = "";
		switch (radioBtnValue) {
		case "architecturalFirmOwner":
			value = "I own or manage an architectural firm.";
			break;
		case "solePractitioner":
			value = "I am a sole practitioner.";
			break;
		case "architectureFirmManager":
			value = "I own or manage an architecture firm and designate another AIA member to pay the supplemental dues my firm owes.";
			break;
		case "notSolePractitioner":
			value = "I am not a sole practitioner, and I do not own or manage an architecture firm.";
			break;
		}
		driver.findElement(By.xpath("//span[text()= '" + value + "'] //parent::label")).click();
	}

	public ArrayList<String> SupplementalDuesData() throws Exception {

		renew_membername = "renewmember" + RandomStringUtils.randomAlphabetic(4);
		list.add(0, renew_membername);
		System.out.println(renew_membername);
		memberID = "012345" + String.format("%05d", new Random().nextInt(10000));
		list.add(1, memberID);

		return list;
	}

	public void enterSupplementalDuesDetails(String radbtnString, String nonMemberCount, String StaffCount,
			String otherCount) throws InterruptedException {
		selectRadioBtnByValue(radbtnString);
		if (radbtnString.contentEquals("notSolePractitioner")) {
			Reporter.log("No Extra dues for selection of : Not a sole practitioner");
		}

		else if (radbtnString.contentEquals("architecturalFirmOwner")
				|| radbtnString.contentEquals("solePractitioner")) {
			util.enterText(driver, nonMemberArchitectCount, nonMemberCount);
			util.enterText(driver, technicalStaffCount, StaffCount);
			// util.enterText(driver, otherStaffCount, otherCount);
		} else if (radbtnString.contentEquals("architectureFirmManager")) {
			util.enterText(driver, memberName, renew_membername);
			// util.enterText(driver, memberNumber, memberID);
		}
	}


	/**
	 * 
	 */
	public void checkAdditionalProduct() {
		util.waitUntilElement(driver, archipacText);
		assertTrue(archipacText.isDisplayed());
		util.waitUntilElement(driver, archipacCheckBox);
		archipacCheckBox.click();
	}
}
