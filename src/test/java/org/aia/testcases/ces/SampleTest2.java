package org.aia.testcases.ces;

import org.aia.pages.BaseClass;

import org.aia.utility.MyRetryAnalyzer;
import org.aia.utility.Retry;
import org.home.common.extent.ExtentTestManager;
import org.home.listener.AnnotationTransformer;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.IRetryAnalyzer;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import org.testng.ITestNGListener;

//@Listeners(com.maxsoft.testngtestresultsanalyzer.TestAnalyzeReportListener.class)
public class SampleTest2 extends BaseClass {
	WebDriver driver;
	
	@Test(priority = 1, description = "Validate Online JOIN for Architecture Firm using credit card.")
	public void Test1() throws InterruptedException {
		ExtentTestManager.getTest().info("Test Case started.");
		try {
			Assert.assertTrue(true);
		} catch (Exception e) {
			Assert.assertTrue(true);
		}

	}

	@Test(priority = 2, description = "Validate Online JOIN for Architecture Firm using Echeck.")
	public void Test2() throws InterruptedException {
		ExtentTestManager.getTest().info("Test Case started.");
//		ExtentTestManager.getTest().warning("Please Enter Username & password");
		try {
			Assert.assertTrue(true);
		} catch (Exception e) {
			Assert.assertTrue(true);
		}

	}

	@Test(priority = 3, description = "Validate Online JOIN for Architecture Firm using DD.",groups="master")
	public void Test3() throws InterruptedException {
		ExtentTestManager.getTest().info("Test Case started.");
		try {
			Assert.assertTrue(true);
		} catch (Exception e) {
			Assert.assertTrue(true);
		}
	}

		@Test(priority = 4, description = "Validate Online JOIN for Architecture Firm using DD.",groups="master")
		public void Test4() throws InterruptedException {
			ExtentTestManager.getTest().info("Test Case started.");
			try {
				Assert.assertTrue(true);
			} catch (Exception e) {
				Assert.assertTrue(true);
			}
	}
		
		
}
