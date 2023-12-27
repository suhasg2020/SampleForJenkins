/**
 * @author Pallavi
 */

/***************************************************/

package org.aia.utility;

import org.aia.utility.Constants;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.aia.java_mail_API.*;
import org.aia.java_mail_API.*;

import static org.aia.java_mail_API.EmailConfig.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.file.Path;
import java.util.Base64;
import java.util.concurrent.TimeUnit;

import javax.mail.MessagingException;

public class EmailSendUtils {
	private int serialNumber = 0;

	public static void sendEmail(int count_totalTCs, int count_passedTCs, int count_failedTCs, int count_skippedTCs,ITestContext testContext) throws IOException {

		if (DataProviderFactory.getConfig().getValue("sendmail").equalsIgnoreCase("YES")) {
			System.out.println("****************************************");
			System.out.println("Send Email - START");
			System.out.println("****************************************");

			System.out.println("File name: " + Constants.EXTENT_REPORT_NAME);

			String messageBody = createHtmlReport(testContext);
			System.out.println(messageBody);

			/*
			 * String attachmentFile_ExtentReport = Constants.REPORTS_Folder +
			 * "report.html";
			 */

			String attachmentFile_ExtentReport = Constants.GENERATE_Template_REPORT_PATH;

			try {
				EmailAttachmentsSender.sendEmailWithAttachments(SERVER, PORT, FROM, PASSWORD, TO, SUBJECT, messageBody,
						attachmentFile_ExtentReport);

				System.out.println("****************************************");
				System.out.println("Email sent successfully.");
				System.out.println("Send Email - END");
				System.out.println("****************************************");
			} catch (MessagingException e) {
				e.printStackTrace();
			}

		}

	}

	private static String getTestCasesCountInFormat(int count_totalTCs, int count_passedTCs, int count_failedTCs,
			int count_skippedTCs) {
		System.out.println("count_totalTCs: " + count_totalTCs);
		System.out.println("count_passedTCs: " + count_passedTCs);
		System.out.println("count_failedTCs: " + count_failedTCs);
		System.out.println("count_skippedTCs: " + count_skippedTCs);

		return "<html>\r\n" + "\r\n" + " \r\n" + "\r\n" + "<head>\"Hello, " + "" + "We just got a new build to test."
				+ "We have run a quick test to verify the Application is smooth and up. Please check the reports. <b>Here is a quick summary :</b> \"</head>"
				+ "        <body> \r\n<table class=\"container\" align=\"center\" style=\"padding-top:20px\">\r\n<tr align=\"center\"><td colspan=\"4\"><h2>"
				+ Constants.getProjectName() + "</h2></td></tr>\r\n<tr><td>\r\n\r\n"
				+ "       <table style=\"background:#67c2ef;width:120px\" >\r\n"
				+ "                     <tr><td style=\"font-size: 36px\" class=\"value\" align=\"center\">"
				+ count_totalTCs + "</td></tr>\r\n"
				+ "                     <tr><td align=\"center\">Total</td></tr>\r\n" + "       \r\n"
				+ "                </table>\r\n" + "                </td>\r\n" + "                <td>\r\n"
				+ "               \r\n" + "                 <table style=\"background:#79c447;width:120px\">\r\n"
				+ "                     <tr><td style=\"font-size: 36px\" class=\"value\" align=\"center\">"
				+ count_passedTCs + "</td></tr>\r\n"
				+ "                     <tr><td align=\"center\">Passed</td></tr>\r\n" + "       \r\n"
				+ "                </table>\r\n" + "                </td>\r\n" + "                <td>\r\n"
				+ "                <table style=\"background:#ff5454;width:120px\">\r\n"
				+ "                     <tr><td style=\"font-size: 36px\" class=\"value\" align=\"center\">"
				+ count_failedTCs + "</td></tr>\r\n"
				+ "                     <tr><td align=\"center\">Failed</td></tr>\r\n" + "       \r\n"
				+ "                </table>\r\n" + "                \r\n" + "                </td>\r\n"
				+ "                <td>\r\n" + "                <table style=\"background:#fabb3d;width:120px\">\r\n"
				+ "                     <tr><td style=\"font-size: 36px\" class=\"value\" align=\"center\">"
				+ count_skippedTCs + "</td></tr>\r\n"
				+ "                     <tr><td align=\"center\">Skipped</td></tr>\r\n" + "       \r\n"
				+ "                </table>\r\n" + "                \r\n" + "                </td>\r\n"
				+ "                </tr>\r\n" + "               \r\n" + "                \r\n"
				+ "            </table>\r\n" + "       \r\n" + "    </body>\r\n" + "</html>";
	}

	public static String createHtmlReport(ITestContext testContext) throws IOException {
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

	private static String generateAndEncodePieChart(ITestContext testContext) {
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

	private static double calculatePercentage(int count, int total) {
		return ((double) count / total) * 100;
	}

	private static void addTestCaseDetails(PrintWriter writer, ITestResult result, int serialNumber) {
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
	}}
