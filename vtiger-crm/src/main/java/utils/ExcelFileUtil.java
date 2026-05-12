package utils;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.poi.ss.usermodel.*;

public class ExcelFileUtil {

  private static final String FILE_PATH = "./src/test/resources/vtiger-excel-data.xlsx";

  // ========== METHOD 1: READ DATA FROM EXCEL ==========
  public static Map<String, String> readData(String sheetName, String testCaseName)
      throws IOException {

    Map<String, String> dataMap = new HashMap<>();
    
    // Open Excel file
    FileInputStream fis = new FileInputStream(FILE_PATH);
    Workbook wb = WorkbookFactory.create(fis);
    Sheet sheet = wb.getSheet(sheetName);
    
    // Get header row (row 0)
    Row headerRow = sheet.getRow(0);
    
    // Search for test case row
    for (int rowNum = 1; rowNum <= sheet.getLastRowNum(); rowNum++) {
      Row dataRow = sheet.getRow(rowNum);
      
      // Check if TC_DESC column (column 2) matches
      if (dataRow != null && dataRow.getCell(2) != null) {
        if (testCaseName.equals(dataRow.getCell(2).toString())) {
          
          // Found match - copy all data from this row
          for (int col = 0; col < headerRow.getLastCellNum(); col++) {
            String columnName = headerRow.getCell(col).toString();
            String cellValue = dataRow.getCell(col) != null ? dataRow.getCell(col).toString() : "";
            dataMap.put(columnName, cellValue);  // Example: "ORG_NAME" -> "Marvel"
          }
          break; // Stop searching
        }
      }
    }
    
    // Close files
    wb.close();
    fis.close();
    
    return dataMap;
  }

  // ========== METHOD 2: WRITE DATA TO EXCEL ==========
  private static void writeData(String sheetName, int rowNum, int colNum, String value)
      throws IOException {

    // Open Excel file
    FileInputStream fis = new FileInputStream(FILE_PATH);
    Workbook wb = WorkbookFactory.create(fis);
    Sheet sheet = wb.getSheet(sheetName);
    
    // Get or create row
    Row row = sheet.getRow(rowNum);
    if (row == null) {
      row = sheet.createRow(rowNum);
    }
    
    // Get or create cell
    Cell cell = row.getCell(colNum);
    if (cell == null) {
      cell = row.createCell(colNum);
    }
    
    // Set value
    cell.setCellValue(value);
    
    // Save file
    FileOutputStream fos = new FileOutputStream(FILE_PATH);
    wb.write(fos);
    
    // Close files
    wb.close();
    fis.close();
    fos.close();
  }

  // ========== METHOD 3: UPDATE TEST STATUS ==========
  public static void updateTestStatus(String sheetName, Map<String, String> testData, String status)
      throws IOException {

    // Get row number from SNO or SLNO column
    String rowNumber = testData.get("SNO");  // Try SNO first
    if (rowNumber == null) {
      rowNumber = testData.get("SLNO");     // Try SLNO if SNO not found
    }
    
    if (rowNumber == null) {
      System.out.println("WARNING: No SNO/SLNO found - Cannot update Excel");
      return;
    }
    
    // Convert "1.0" to 1
    int rowIndex = (int) Double.parseDouble(rowNumber);
    
    // Write status to column 5 (Column F)
    writeData(sheetName, rowIndex, 5, status);
    
    System.out.println("✓ Updated status to '" + status + "' for row " + rowIndex);
  }
}