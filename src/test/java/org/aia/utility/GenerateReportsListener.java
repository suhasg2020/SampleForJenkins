package org.aia.utility;

import java.io.File;


import java.io.IOException;
import java.util.Arrays;

import org.aia.pages.BaseClass;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.io.FileHandler;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;

public class GenerateReportsListener implements ITestListener{
	
	ExtentHtmlReporter htmlreport;
	public static ExtentReports extent;
	public static ExtentTest logger;
	public GenerateReports report = GenerateReports.getInstance();
	static int count_passedTCs;
	static int count_skippedTCs;
	static int count_failedTCs;
	static int count_totalTCs;

	public void onTestStart(ITestResult result) {
		count_totalTCs = count_totalTCs + 1;
		System.out.println("Inside GenerateReportsListener onTestStart() method creating test report");
		report.startTestReport(result.getTestClass().getName() + "@TestCase :" + result.getMethod().getMethodName());
		System.out.println("onTestStart completed");
	}

	public void onTestSuccess(ITestResult result) {
		count_passedTCs = count_passedTCs + 1;
		String logText = "<b>" + "TEST CASE:- " + result.getMethod().getMethodName().toUpperCase() + " PASSED" + "</b>";
		System.out.println(" Inside onTestSuccess Listener method");
		report.logTestpassed(logText);
	}

	/*
	 * public void onTestFailure2(ITestResult result) throws Exception {
	 * count_failedTCs = count_failedTCs + 1; WebDriver driver =
	 * BaseClass.getDriverInstance(); String screenshotPath =
	 * captureScreenshot(driver);
	 * //report.logTestFailed(result.getMethod().getMethodName()); try {
	 * report.attachScreeshot(screenshotPath); } catch (IOException e) {
	 * e.printStackTrace(); } }
	 */
	
	public void onTestFailure(ITestResult result){
		count_failedTCs = count_failedTCs + 1;
		String exceptionMessage = Arrays.toString(result.getThrowable().getStackTrace());
		WebDriver driver = BaseClass.getDriverInstance();
		String screenshotPath2 =Utility.captureScreenshotFromBase64(driver);
		report.logTestFailed("<span class='label failure'>" + result.getMethod().getMethodName() + "</span>", "<pre>Results = " + result.getThrowable().getCause() + "\n\n GET MESSAGE : " + result.getThrowable().getMessage() + "</pre>");
		try {
			report.attachScreeshot(screenshotPath2);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void onTestSkipped(ITestResult result) {
		count_skippedTCs = count_skippedTCs + 1;
		report.logTestSkipped(result.getMethod().getMethodName());
	}

	public void onStart(ITestContext context) {
		System.out.println("Inside GenerateReportsListener onStart() method creating report");
		report.createReport();
	}

	public void onFinish(ITestContext context) {
		report.endReport();
		try {
			EmailSendUtils.sendEmail(count_totalTCs, count_passedTCs, count_failedTCs, count_skippedTCs,context);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/*
	 * private String captureScreenshot(WebDriver driver) throws IOException { File
	 * scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE); File
	 * destinationFilePath = new File("src/../ScreenShots" +
	 * System.currentTimeMillis() + ".png"); String absolutepathlocation =
	 * destinationFilePath.getAbsolutePath(); try { FileHandler.copy(scrFile,
	 * destinationFilePath); } catch (IOException e) { e.printStackTrace(); } return
	 * absolutepathlocation; }
	 */
}
