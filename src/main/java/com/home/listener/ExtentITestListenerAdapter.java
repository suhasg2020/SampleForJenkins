package com.home.listener;


import java.util.Iterator;
import java.util.Set;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestNGMethod;
import org.testng.ITestResult;

import com.aventstack.extentreports.AnalysisStrategy;
import com.home.common.extent.ExtentService;
import com.home.common.extent.ExtentTestManager;


public class ExtentITestListenerAdapter implements ITestListener {

    public synchronized void onStart(ITestContext context) {
    	
        ExtentService.getInstance().setAnalysisStrategy(AnalysisStrategy.TEST);
    }

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

    public synchronized void onTestStart(ITestResult result) {
        ExtentTestManager.createMethod(result, true);
    }

    public synchronized void onTestSuccess(ITestResult result) {
        ExtentTestManager.log(result);
    }

    public synchronized void onTestFailure(ITestResult result) {
        ExtentTestManager.log(result);
    }

    public synchronized void onTestSkipped(ITestResult result) {
        ExtentTestManager.log(result);
        if(result.getStatus() == ITestResult.SKIP) {
            if (result.wasRetried()) 
            	ExtentService.getInstance().removeTest(ExtentTestManager.getTest());
            
        }
    }

    public synchronized void onTestFailedButWithinSuccessPercentage(ITestResult result) {
    	
    }
    

}
