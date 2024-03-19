package org.aia.utility;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.ITestContext;
import org.testng.TestListenerAdapter;

import java.io.*;
import java.util.*;

public class TestListener extends TestListenerAdapter {
	private Map<String, ModuleResult> moduleResults = new HashMap<>();

	@Override
	public void onFinish(ITestContext context) {
		String moduleName = context.getSuite().getName();
		int totalTests = context.getAllTestMethods().length;
		int passedTests = context.getPassedTests().size();
		int failedTests = context.getFailedTests().size();

		ModuleResult moduleResult = moduleResults.getOrDefault(moduleName, new ModuleResult());
		moduleResult.updateResults(totalTests, passedTests, failedTests);
		moduleResults.put(moduleName, moduleResult);

		writeToExcel();
	}

	private void writeToExcel() {
		Workbook workbook;
		Sheet sheet;

		try {
			FileInputStream fileIn = new FileInputStream(System.getProperty("user.dir") + "/test_result.xlsx");
			workbook = new XSSFWorkbook(fileIn);
			sheet = workbook.getSheet("Test Results");

			if (sheet == null) {
				sheet = workbook.createSheet("Test Results");
				Row headerRow = sheet.createRow(0);
				headerRow.createCell(0).setCellValue("Date");
				headerRow.createCell(1).setCellValue("Module");
				headerRow.createCell(2).setCellValue("Total Tests");
				headerRow.createCell(3).setCellValue("Passed Tests");
				headerRow.createCell(4).setCellValue("Failed Tests");
				headerRow.createCell(5).setCellValue("Pass %");
			}

			int lastRowNum = sheet.getLastRowNum();

			for (Map.Entry<String, ModuleResult> entry : moduleResults.entrySet()) {
				String moduleName = entry.getKey();
				ModuleResult moduleResult = entry.getValue();

				// Calculate pass percentage
				float passPercentageNumner = ((float) moduleResult.getPassedTests() / moduleResult.getTotalTests())
						* 100;
				String passPercentage = String.format("%.2f", passPercentageNumner);
				Row dataRow = sheet.createRow(lastRowNum + 1);

				dataRow.createCell(0).setCellValue(new Date().toString());
				dataRow.createCell(1).setCellValue(moduleName);
				dataRow.createCell(2).setCellValue(moduleResult.getTotalTests());
				dataRow.createCell(3).setCellValue(moduleResult.getPassedTests());
				dataRow.createCell(4).setCellValue(moduleResult.getFailedTests());
				dataRow.createCell(5).setCellValue(passPercentage+"%");
			}

			FileOutputStream fileOut = new FileOutputStream(System.getProperty("user.dir") + "/test_result.xlsx");
			workbook.write(fileOut);
			fileOut.close();
			workbook.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static class ModuleResult {
		private int totalTests;
		private int passedTests;
		private int failedTests;

		public void updateResults(int total, int passed, int failed) {
			totalTests += total;
			passedTests += passed;
			failedTests += failed;
		}

		public int getTotalTests() {
			return totalTests;
		}

		public int getPassedTests() {
			return passedTests;
		}

		public int getFailedTests() {
			return failedTests;
		}
	}
}
