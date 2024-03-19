package org.home.listener;
import org.aia.pages.BaseClass;
import org.aia.utility.Utility;
import org.home.common.extent.ExtentService;
import org.home.common.extent.ExtentTestManager;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.AnalysisStrategy;


public class ExtentITestListenerAdapter implements ITestListener {

    @Override
	public synchronized void onStart(ITestContext context) {

        ExtentService.getInstance().setAnalysisStrategy(AnalysisStrategy.TEST);
    }

    @Override
	public synchronized void onFinish(ITestContext context) {

//    	Iterator<ITestResult> failedTestCases =context.getFailedTests().getAllResults().iterator();
//        Set<ITestResult> skippedTests = context.getSkippedTests().getAllResults();
//
//        while (failedTestCases.hasNext()) {
//            System.out.println("failedTestCases");
//            ITestResult failedTestCase = failedTestCases.next();
//            ITestNGMethod method = failedTestCase.getMethod();
//            if (context.getFailedTests().getResults(method).size() > 1) {
//                System.out.println("failed test case remove as dup:" + failedTestCase.getTestClass().toString());
//                failedTestCases.remove();
//                ExtentService.getInstance().removeTest(ExtentTestManager.getTest());
//            } else {
//
//                if (context.getPassedTests().getResults(method).size() > 0) {
//                    System.out.println("failed test case remove as pass retry:" + failedTestCase.getTestClass().toString());
//                    failedTestCases.remove();
//                    ExtentService.getInstance().removeTest(ExtentTestManager.getTest());
//                }
//            }
//        }
//
//        for (ITestResult temp : skippedTests) {
//
//        	skippedTests.remove(temp);
//        	ExtentService.getInstance().removeTest(ExtentTestManager.getTest(temp));
//        }

        ExtentService.getInstance().flush();
    }

    @Override
	public synchronized void onTestStart(ITestResult result) {
        ExtentTestManager.createMethod(result, true);
    }

    @Override
	public synchronized void onTestSuccess(ITestResult result) {
        ExtentTestManager.log(result);
    }

    @Override
	public synchronized void onTestFailure(ITestResult result) {
    	WebDriver driver = BaseClass.getDriverInstance();
//		String screenshotPath2 =Utility.captureScreenshotFromBase64(driver);
		ExtentTestManager.log(result);
//		ExtentTestManager.getTest().addScreenCaptureFromBase64String(screenshotPath2, "Here is the failed test case screenshot");

    }

    @Override
	public synchronized void onTestSkipped(ITestResult result) {
        ExtentTestManager.log(result);
        if(result.getStatus() == ITestResult.SKIP) {
            if (result.wasRetried())
            	ExtentService.getInstance().removeTest(ExtentTestManager.getTest());

        }
    }

    @Override
	public synchronized void onTestFailedButWithinSuccessPercentage(ITestResult result) {

    }


}
