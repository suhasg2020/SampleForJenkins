package org.aia.utility;

import java.io.File;
import java.io.IOException;

import org.apache.pdfbox.contentstream.operator.state.SetRenderingIntent;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.io.FileHandler;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

//Singleton class
public class GenerateReports {
	
	public static ExtentReports extentreport;
	//public static ExtentReports extent;
	public static ExtentSparkReporter sparkReport;
	public static ExtentTest logger;
	private static GenerateReports gr;
	public static File file;
	
	private GenerateReports() {
		
	}
	
	//Returns GenerateReports object when called
	public static GenerateReports getInstance() {
		if(gr==null) {
			gr=new GenerateReports();
		}
		return gr;
	}
	
	public void createReport() {

		extentreport = new ExtentReports();
		file = new File(Constants.GENERATE_REPORT_PATH);
		sparkReport = new ExtentSparkReporter(file);
		
		extentreport.attachReporter(sparkReport);
		extentreport.setSystemInfo("Host Name", "AIA");
		extentreport.setSystemInfo("Environment", "Testing-Sandbox");
		extentreport.setSystemInfo("User Name", "Suhas");

		sparkReport.config().setDocumentTitle("Test Execution Report");
		sparkReport.config().setReportName("Fonteva Ops Automation Test results");
		sparkReport.config().setTheme(Theme.STANDARD);
		sparkReport.config().setTimeStampFormat("MMM d, yyyy hh:mm:ss a");
		sparkReport.config().setTheme(Theme.DARK);
//		sparkReport.config().thumbnailForBase64(true);
		
	}
	
	public void startTestReport(String testName) {
		logger = extentreport.createTest(testName);
	}
	
	public void logTestInfo(String message) {
		logger.log(Status.INFO, message);
	}
	
	public void logTestpassed(String testcaseName) {
		logger.log(Status.PASS, MarkupHelper.createLabel(testcaseName + "is passTest", ExtentColor.GREEN));
	}

	public void logTestFailed(String testcaseName, String string) {
		logger.log(Status.FAIL, MarkupHelper.createLabel(testcaseName + "is not passTest", ExtentColor.RED));
		logger.log(Status.FAIL, MarkupHelper.createLabel(string + "With above execution errors.", ExtentColor.RED));
	}
	
	public void logTestFailedWithException(Exception e) {
		logger.log(Status.FAIL,e);
	}
	
	public void logTestSkipped(String testcaseName) {
		logger.log(Status.SKIP,
				MarkupHelper.createLabel(testcaseName + " skipped the Test", ExtentColor.YELLOW));
	}

	public void endReport() {
		extentreport.flush();
	}
	
	public void attachScreeshot(String path) throws IOException {
		
		logger.addScreenCaptureFromBase64String(path);
	}

}
