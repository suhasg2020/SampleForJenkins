package org.aia.utility;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Base64;
import java.util.concurrent.TimeUnit;

public class emailTemplate implements ITestListener {

	public String onFinishHtml(ITestContext testContext) {
		StringWriter reportContent = new StringWriter();
		PrintWriter writer = new PrintWriter(reportContent);

		writer.println("<html><head><title>TestNG Custom Report</title></head><body>" + "<h2>Test Results:</h2>"
				+ "<table border='1'><tr><th>Serial No.</th><th>Test Case Description</th><th>Status</th><th>Execution Time (minutes)</th></tr>");

		int serialNumber = 0;
		for (ITestResult result : testContext.getPassedTests().getAllResults()) {
			addTestCaseDetails(writer, result, ++serialNumber);
		}

		for (ITestResult result : testContext.getFailedTests().getAllResults()) {
			addTestCaseDetails(writer, result, ++serialNumber);
		}

		for (ITestResult result : testContext.getSkippedTests().getAllResults()) {
			addTestCaseDetails(writer, result, ++serialNumber);
		}

		// Generate pie chart and encode as Base64
		String base64Chart = generateAndEncodePieChart(testContext);

		// Include pie chart and percentages in the HTML report
		writer.println("<div>");
		writer.println("<h2>Test Results Pie Chart:</h2>");
		writer.println("<img src=\"data:image/png;base64," + base64Chart + "\" alt=\"Test Results Pie Chart\">");
		writer.println("</div>");
		writer.println("<h2>Test Results Percentage:</h2>");
		writer.println("<p>Passed: "
				+ calculatePercentage(testContext.getPassedTests().size(), testContext.getAllTestMethods().length)
				+ "%</p>");
		writer.println("<p>Failed: "
				+ calculatePercentage(testContext.getFailedTests().size(), testContext.getAllTestMethods().length)
				+ "%</p>");
		writer.println("<p>Skipped: "
				+ calculatePercentage(testContext.getSkippedTests().size(), testContext.getAllTestMethods().length)
				+ "%</p>");

		writer.println("</table></body></html>");

		writer.close();

        return reportContent.toString();
		
	}

	private String generateAndEncodePieChart(ITestContext testContext) {
		DefaultPieDataset dataset = new DefaultPieDataset();

		int passedTests = testContext.getPassedTests().size();
		int failedTests = testContext.getFailedTests().size();
		int skippedTests = testContext.getSkippedTests().size();

		dataset.setValue("Passed", passedTests);
		dataset.setValue("Failed", failedTests);
		dataset.setValue("Skipped", skippedTests);

		JFreeChart chart = ChartFactory.createPieChart("Test Results", dataset, true, true, false);

		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		try {
			ChartUtilities.writeChartAsPNG(outputStream, chart, 600, 400);
		} catch (IOException e) {
			e.printStackTrace();
		}

		byte[] byteArray = outputStream.toByteArray();
		return Base64.getEncoder().encodeToString(byteArray);
	}

	private double calculatePercentage(int count, int total) {
		return ((double) count / total) * 100;
	}

	private void addTestCaseDetails(PrintWriter writer, ITestResult result, int serialNumber) {
		String testName = result.getMethod().getDescription();
		long executionTimeMillis = result.getEndMillis() - result.getStartMillis();
		long executionTimeMinutes = TimeUnit.MILLISECONDS.toMinutes(executionTimeMillis);

		String status = "";
		if (result.getStatus() == ITestResult.SUCCESS) {
			status = "Passed";
		} else if (result.getStatus() == ITestResult.FAILURE) {
			status = "Failed";
		} else if (result.getStatus() == ITestResult.SKIP) {
			status = "Skipped";
		}

		writer.println("<tr>");
		writer.println("<td>");
		writer.println(serialNumber);
		writer.println("</td>");
		writer.println("<td>");
		writer.println(testName);
		writer.println("</td>");
		writer.println("<td>");
		writer.println(status);
		writer.println("</td>");
		writer.println("<td>");
		writer.println(executionTimeMinutes);
		writer.println("</td>");
		writer.println("</tr>");
	}
}