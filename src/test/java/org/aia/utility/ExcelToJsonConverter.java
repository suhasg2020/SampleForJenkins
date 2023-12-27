package org.aia.utility;
import com.google.gson.Gson;

import com.google.gson.GsonBuilder;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
 
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
 
public class ExcelToJsonConverter {
 
    public static void main(String[] args) {
        String excelFilePath = "C:\\Users\\sghodake\\Desktop\\Sample\\SampleForJenkins\\Membership Export-2023-12-01-00-55-22.xlsx";
 
        try (InputStream inputStream = new FileInputStream(excelFilePath)) {
            Workbook workbook = new XSSFWorkbook(inputStream);
            Sheet sheet = workbook.getSheetAt(0); // Assuming data is in the first sheet
 
            List<Object> data = new ArrayList<>();
            Iterator<Row> iterator = sheet.iterator();
 
            while (iterator.hasNext()) {
                Row currentRow = iterator.next();
                Iterator<Cell> cellIterator = currentRow.iterator();
                List<Object> rowData = new ArrayList<>();
 
                while (cellIterator.hasNext()) {
                    Cell currentCell = cellIterator.next();
 
                    switch (currentCell.getCellType()) {
                        case STRING:
                            rowData.add(currentCell.getStringCellValue());
                            break;
                        case NUMERIC:
                            rowData.add(currentCell.getNumericCellValue());
                            break;
                        case BOOLEAN:
                            rowData.add(currentCell.getBooleanCellValue());
                            break;
                        default:
                            rowData.add(null);
                    }
                }
 
                data.add(rowData);
            }
 
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String json = gson.toJson(data);
            System.out.println(json);
 
            workbook.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}