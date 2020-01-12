package ru.tweekyone.graduateQualificationWork.drugsBase;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.List;
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveInputStream;
//Добавление дополнительных библиотек
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.commons.compress.archivers.zip.ZipFile;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbookFactory;
import org.apache.poi.openxml4j.util.ZipFileZipEntrySource;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

/**
 *
 * @author Пирожок
 */
public class DrugBaseDataAccess {
    private String path;
    private HSSFWorkbook workbook;
    
    public DrugBaseDataAccess() {
        path = "data\\lp.zip";
        exstractData();
    }
    
    private void exstractData(){
        File file = new File(path);
        try(ZipFile zf = new ZipFile(file);
            ZipFileZipEntrySource zfzes = new ZipFileZipEntrySource(zf)){
            Enumeration<ZipArchiveEntry> entryes = zf.getEntries();
            workbook = new HSSFWorkbook(zfzes.getInputStream(entryes.nextElement()));
        } catch (IOException e){
            e.printStackTrace();
        }
    }
    
    public LinkedList<Row> getInfo(String drugName){
        HSSFSheet sheet = workbook.getSheet(workbook.getSheetName(0));
        LinkedList<Row> rows = new LinkedList<>();
        for(Row row : sheet){
            for(Cell cell : row){
                if (cell.getCellType() == CellType.STRING){
                    if (cell.getStringCellValue().toLowerCase().trim().contains(drugName.toLowerCase())){
                        if(!rows.contains(cell.getRow())){
                            rows.add(cell.getRow());
                        }
                    }
                }
            }
        }
        return rows;
    }
}
