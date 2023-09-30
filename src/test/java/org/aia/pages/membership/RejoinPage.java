package org.aia.pages.membership;

import org.aia.utility.Utility;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class RejoinPage {

		WebDriver driver;
		Utility util = new Utility(driver, 30);

		public RejoinPage(WebDriver Idriver) {
			this.driver = Idriver;
		}

		@FindBy(xpath = "//input[@name=\"email \"] | //input[@name=\"email\"]")
		WebElement enteremail;

		@FindBy(xpath = "//p[text()='Continue']")
		WebElement continuebtn;

		@FindBy(xpath = "//p[text()='Restart your membership']/ancestor::button")
		WebElement reJoinBtn;

		public void reJoinMembership(String emaildata) throws InterruptedException {
			Thread.sleep(4000);
			driver.navigate().refresh();
			util.waitUntilElement(driver, enteremail);
			enteremail.sendKeys(emaildata);
			util.waitUntilElement(driver, continuebtn);
			continuebtn.click();
			util.waitUntilElement(driver, reJoinBtn);
			reJoinBtn.click();
		}

}
