package org.aia.utility;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.Reporter;

public class ExcelDataProvider {

	
	XSSFWorkbook wb;
	
	public ExcelDataProvider()
	{
		try 
		{
			wb=new XSSFWorkbook(new FileInputStream(new File(System.getProperty("user.dir")+"\\TestData\\MyData.xlsx")));
			
			Reporter.log("LOG : INFO -Data File loaded", true);

			
		}  catch (IOException e) {
			
			Reporter.log("LOG : FAIL-Failed to load excel files", true);
			
		}
	}
	
	public int getNumberOfRows(String sheetName)
	{
		return wb.getSheet(sheetName).getPhysicalNumberOfRows();
	}
	
	public int getNumberOfColumn(String sheetName)
	{
		return wb.getSheet(sheetName).getRow(0).getPhysicalNumberOfCells();
	}
	
	public String getCellData(String sheetName,int row,int col)
	{
	
		Cell cell=wb.getSheet(sheetName).getRow(row).getCell(col);
		
		if(cell.getCellTypeEnum()==CellType.STRING)
		{
			return wb.getSheet(sheetName).getRow(row).getCell(col).getStringCellValue();
		}
		else if(cell.getCellTypeEnum()==CellType.NUMERIC)
		{
			double data=wb.getSheet(sheetName).getRow(row).getCell(col).getNumericCellValue();
			
			return String.valueOf(data);
		}
		else if(cell.getCellTypeEnum()==CellType.BLANK)
		{
			return  "";
		}
		
		return "No Data found in "+sheetName+row+col +" Please verify your test data";
		
	}
	
	
	
	
	
	
}
