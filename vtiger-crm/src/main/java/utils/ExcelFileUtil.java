package utils;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.*;

public class ExcelFileUtil {
	
	private static Map<String, String> dataMap = new HashMap<>();
	private static final String FILE_PATH = "./src/test/resources/vtiger-excel-data.xlsx";
	
	/**
	 * Read data from Excel based on test case type
	 */
	public static void readData(String sheetName, String tastCaseType) 
			throws EncryptedDocumentException, IOException {
		
		dataMap.clear();

		FileInputStream fis = new FileInputStream(FILE_PATH);
		Workbook wb = WorkbookFactory.create(fis);
		Sheet sh = wb.getSheet(sheetName);

		if (sh == null) {
			wb.close();
			throw new RuntimeException("Sheet not found: " + sheetName);
		}

		Row headerRow = sh.getRow(0); // assume first row is header

		for (int i = 1; i <= sh.getLastRowNum(); i++) {
			Row row = sh.getRow(i);
			if (row == null) continue;

			Cell tcCell = row.getCell(2); // test case column
			if (tcCell == null) continue;

			if (tastCaseType.equals(tcCell.toString())) {

				for (int c = 0; c < headerRow.getLastCellNum(); c++) {

					Cell keyCell = headerRow.getCell(c);
					Cell valueCell = row.getCell(c);

					String key = (keyCell != null) ? keyCell.toString() : "";
					String value = (valueCell != null) ? valueCell.toString() : "";

					dataMap.put(key, value);
				}
				break; // stop after match
			}
		}

		wb.close();
	}
	
	/**
	 * Get specific value
	 */
	public static String getData(String key) {
		return dataMap.get(key);
	}
	
	/**
	 * Get all data
	 */
	public static Map<String, String> getAllData() {
		return dataMap;
	}

	/**
	 * Write data to Excel
	 */
	public static void writeData(String SheetName, int row_index, int col_index, String value) 
			throws EncryptedDocumentException, IOException {

		FileInputStream fis = new FileInputStream(FILE_PATH);
		Workbook wb = WorkbookFactory.create(fis);

		Sheet sheet = wb.getSheet(SheetName);
		Row row = sheet.getRow(row_index);

		if (row == null) {
			row = sheet.createRow(row_index);
		}

		Cell cell = row.getCell(col_index);
		if (cell == null) {
			cell = row.createCell(col_index);
		}

		cell.setCellValue(value);

		FileOutputStream fos = new FileOutputStream(FILE_PATH);
		wb.write(fos);

		wb.close();
	}
}