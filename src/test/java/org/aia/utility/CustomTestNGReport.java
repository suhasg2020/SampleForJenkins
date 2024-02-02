package org.aia.utility;

import org.testng.*;
import org.testng.xml.XmlSuite;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class CustomTestNGReport implements IReporter {

    public void generateReport(List<XmlSuite> xmlSuites, List<ISuite> suites, String outputDirectory) {
        StringBuilder htmlContent = new StringBuilder();
        XmlSuite xmlSuite = xmlSuites.get(0);
        String executedModule=xmlSuite.getName();// Retrieve the XML suit
        htmlContent.append("<!DOCTYPE html>")
        .append("<html>")
        .append("<head>")
        .append("<title>AIA Automation Execution</title>")
        .append("<script src='https://cdn.jsdelivr.net/npm/chart.js'></script>")
        .append("<style>")
        .append("/* Your CSS Styles */")
        .append(".pass { background-color: #7FFF7F; }") // Green background for pass
        .append(".fail { background-color: #FF7F7F; }") // Red background for fail
        .append(".skip { background-color: #FFFF7F; }") // Yellow background for skipped
        .append("</style>")
        .append("</head>")
        .append("<body>")
        .append("<h1>AIA Automation Execution of "+executedModule+"</h1>")
        .append("<div style='width: 50%; margin: auto;'>")
        .append("<canvas id='testPieChart'></canvas>")
        .append("</div>")
        .append("<table border='1' style='width: 80%; margin: auto; margin-top: 20px;'>")
        .append("<tr><th>Sr.No.</th><th>Test Case Description</th><th>Status</th><th>Execution Time (m)</th></tr>");

        int serialNumber = 1;
        int passCount = 0;
        int failCount = 0;
        int skipCount = 0;

		for (ISuite suite : suites) {
			Map<String, ISuiteResult> suiteResults = suite.getResults();
			for (ISuiteResult suiteResult : suiteResults.values()) {
				ITestContext testContext = suiteResult.getTestContext();
				passCount += testContext.getPassedTests().size();
				failCount += testContext.getFailedTests().size();
				skipCount += testContext.getSkippedTests().size();

				for (ITestNGMethod method : testContext.getAllTestMethods()) {
	                IResultMap passedResult = testContext.getPassedTests();
	                IResultMap failedResult = testContext.getFailedTests();
	                IResultMap skippedResult = testContext.getSkippedTests();

	                if (getTestResultForMethod(method, passedResult) != null) {
	                    ITestResult result = getTestResultForMethod(method, passedResult);
	                    long executionTimeMinutes = (result.getEndMillis() - result.getStartMillis()) / (1000 * 60);
	                    htmlContent.append("<tr class='pass'>")
	                            .append("<td>").append(serialNumber++).append("</td>")
	                            .append("<td>").append(method.getDescription() != null ? method.getDescription() : method.getMethodName()).append("</td>")
	                            .append("<td>Pass</td>")
	                            .append("<td>").append(executionTimeMinutes).append("</td>")
	                            .append("</tr>");
	                } else if (getTestResultForMethod(method, failedResult) != null) {
	                    ITestResult result = getTestResultForMethod(method, failedResult);
	                    long executionTimeMinutes = (result.getEndMillis() - result.getStartMillis()) / (1000 * 60);
	                    htmlContent.append("<tr class='fail'>")
	                            .append("<td>").append(serialNumber++).append("</td>")
	                            .append("<td>").append(method.getDescription() != null ? method.getDescription() : method.getMethodName()).append("</td>")
	                            .append("<td>Fail</td>")
	                            .append("<td>").append(executionTimeMinutes).append("</td>")
	                            .append("</tr>");
	                } else if (getTestResultForMethod(method, skippedResult) != null) {
	                    ITestResult result = getTestResultForMethod(method, skippedResult);
	                    long executionTimeMinutes = (result.getEndMillis() - result.getStartMillis()) / (1000 * 60);
	                    htmlContent.append("<tr class='skip'>")
	                            .append("<td>").append(serialNumber++).append("</td>")
	                            .append("<td>").append(method.getDescription() != null ? method.getDescription() : method.getMethodName()).append("</td>")
	                            .append("<td>Skip</td>")
	                            .append("<td>").append(executionTimeMinutes).append("</td>")
	                            .append("</tr>");
	                }
	            }
	        }
	    }

	    int totalTests = passCount + failCount + skipCount;
	    float passPercentage = ((float) passCount / totalTests) * 100;
	    float failPercentage = ((float) failCount / totalTests) * 100;
	    float skipPercentage = ((float) skipCount / totalTests) * 100;

	    htmlContent.append("</table>")
	            .append("<script>")
	            .append("var ctx = document.getElementById('testPieChart').getContext('2d');")
	            .append("var testPieChart = new Chart(ctx, {")
	            .append("type: 'pie',")
	            .append("data: {")
	            .append("labels: ['Pass', 'Fail', 'Skip'],")
	            .append("datasets: [{")
	            .append("data: [").append(passPercentage).append(", ").append(failPercentage).append(", ").append(skipPercentage).append("],")
	            .append("backgroundColor: ['#7FFF7F', '#FF7F7F', '#FFFF7F']") // Green for pass, Red for fail, Yellow for skip
	            .append("}]")
	            .append("},")
	            .append("options: {")
	            .append("title: { display: true, text: 'Pass/Fail/Skip Percentage' },")
	            .append("responsive: true,")
	            .append("maintainAspectRatio: false,")
	            .append("}")
	            .append("});")
	            .append("</script>")
	            .append("</body>")
	            .append("</html>");
        saveReportToFile(htmlContent.toString(), outputDirectory + "/testReport.html");
}
    private ITestResult getTestResultForMethod(ITestNGMethod method, IResultMap testResult) {
        for (ITestResult result : testResult.getAllResults()) {
            if (method.equals(result.getMethod())) {
                return result;
            }
        }
        return null;
    }

    private void saveReportToFile(String content, String filePath) {
        try {
            FileWriter fileWriter = new FileWriter(filePath);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(content);
            bufferedWriter.close();
            fileWriter.close();
            System.out.println("HTML report saved successfully: " + filePath);
        } catch (IOException e) {
            System.err.println("Error occurred while saving the HTML report: " + e.getMessage());
        }
    }
}
