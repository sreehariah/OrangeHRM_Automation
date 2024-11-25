package orangeHrmHelpers;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.FileInputStream;
import java.io.IOException;

public class OrangeHrmDataProvider {
	
	/*
	 * This is the Data Provider class of OrangeHRM Automation Project.
	 */
	
	public static Object[][] getExcelData(String filePath, String sheetName) throws IOException {
		
        FileInputStream fis = new FileInputStream(filePath);
        Workbook workbook = new XSSFWorkbook(fis);
        Sheet sheet = workbook.getSheet(sheetName);
        int rowCount = sheet.getPhysicalNumberOfRows();
        int colCount = sheet.getRow(0).getLastCellNum();

        Object[][] data = new Object[rowCount - 1][colCount];

        for (int i = 1; i < rowCount; i++) {
            Row row = sheet.getRow(i);
            String s ="";
            for (int j = 0; j < colCount; j++) {
                Cell cell = row.getCell(j);
                if(cell.getCellType()==CellType.NUMERIC) {
                	double doubleValue = cell.getNumericCellValue();
                	int n;
                    n = (int) doubleValue; // Convert to integer
                    data[i - 1][j] = n;
                } else {
                	data[i - 1][j] = cell.toString();
                }
                data[i - 1][j] = cell.toString();
                            
//                System.out.println("["+(i-1)+"]"+"["+j+"]"+data[i-1][j]);
            }
        }

        workbook.close();
        return data;
        
    }
}
