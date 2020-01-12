package ru.tweekyone.graduateQualificationWork;

import java.util.LinkedList;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import ru.tweekyone.graduateQualificationWork.drugsBase.DrugBaseDataAccess;
import ru.tweekyone.graduateQualificationWork.gui.MainFrame;

public class DrugMarginCalculator {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        //MainFrame window = new MainFrame();
        DrugBaseDataAccess db = new DrugBaseDataAccess();
        LinkedList<Row> rows = db.getInfo("Парацетамол");
        Row row = rows.get(0);
        Cell cell = row.getCell(0);
        System.out.println(cell.getStringCellValue() + " " + row.getCell(2).getStringCellValue() + " " + row.getCell(3).getStringCellValue());
    }
    
}
