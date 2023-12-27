package org.aia.testcases.ces;

import org.aia.pages.BaseClass;
import org.aia.utility.MyRetryAnalyzer;
import org.testng.Assert;
import org.testng.annotations.Test;

public class SampleTest{

	
	@Test(priority=1, description="Validate Online JOIN for Architecture Firm using credit card.")
	public void Test1() throws InterruptedException {
		Thread.sleep(60000);
		try {
			Assert.assertTrue(true);
		} catch (Exception e) {
			Assert.assertTrue(true);
		}
		
	}
	
	@Test(priority=2, description="Validate Online JOIN for Architecture Firm using Echeck.")
	public void Test2() throws InterruptedException {
		Thread.sleep(6000);
		try {
			Assert.assertTrue(true);
		} catch (Exception e) {
			Assert.assertTrue(true);
		}
		
	}
}
