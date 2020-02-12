package ru.tweekyone.graduateQualificationWork.drugsBase;

import java.io.File;
import java.io.IOException;
import java.util.Enumeration;
import java.util.LinkedList;
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
//Добавление дополнительных библиотек
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.commons.compress.archivers.zip.ZipFile;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.openxml4j.util.ZipFileZipEntrySource;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import ru.tweekyone.graduateQualificationWork.objects.DrugInfo;

public class DrugBaseDataAccess {
    private String path;
    private HSSFSheet sheet;
    
    public DrugBaseDataAccess(String drugTablePath) {
        path = drugTablePath;
        exstractData();
    }
    
    private void exstractData(){
        File file = new File(path);
        try(ZipFile zf = new ZipFile(file);
            ZipFileZipEntrySource zfzes = new ZipFileZipEntrySource(zf)){
            Enumeration<ZipArchiveEntry> entryes = zf.getEntries();
            HSSFWorkbook workbook = new HSSFWorkbook(zfzes.getInputStream(entryes.nextElement()));
            sheet = workbook.getSheet(workbook.getSheetName(0));
        } catch (IOException e){
            e.printStackTrace();
        } 
    }
    
    public synchronized LinkedList<DrugInfo> getDrugsList(String drugName, boolean mnn, String regNumber){
        LinkedList<DrugInfo> drugsList = new LinkedList<>();
        if(regNumber.isEmpty()){
            if(mnn){
                for(Row row : sheet){
                    for(Cell cell : row){
                        if (cell.getColumnIndex() == 0){
                            if (cell.getStringCellValue().toLowerCase().trim()
                                    .contains(drugName.toLowerCase().trim())){
                                DrugInfo di = rowToDrug(cell.getRow());
                                if(!drugsList.contains(di)){
                                    drugsList.add(di);
                                }
                            }
                        }
                    }
                }
            } else {
                for(Row row : sheet){
                    for(Cell cell : row){
                        if (cell.getColumnIndex() == 1){
                            if (cell.getStringCellValue().toLowerCase().trim()
                                    .contains(drugName.toLowerCase().trim())){
                                DrugInfo di = rowToDrug(cell.getRow());
                                if(!drugsList.contains(di)){
                                    drugsList.add(di);
                                }
                            }
                        }
                    }
                }
            }
        } else {
            for(Row row : sheet){
                for(Cell cell : row){
                    if (cell.getColumnIndex() == 8){
                        if (cell.getStringCellValue().toLowerCase().trim()
                                .contains(regNumber.toLowerCase().trim())){
                            DrugInfo di = rowToDrug(cell.getRow());
                            if(!drugsList.contains(di)){
                                drugsList.add(di);
                            }
                        }
                    }
                }
            }
        }
        return drugsList;
    }
    
    public DrugInfo rowToDrug(Row row){
        String mnn = row.getCell(0).getStringCellValue();
        String tn = row.getCell(1).getStringCellValue();
        String spec = row.getCell(2).getStringCellValue();
        String owner = row.getCell(3).getStringCellValue();
        int amount = (int)(row.getCell(5).getNumericCellValue());
        float ownerPrice = (float)(row.getCell(6).getNumericCellValue());
        String regNo = row.getCell(8).getStringCellValue();
        String date = row.getCell(9).getStringCellValue();
        return new DrugInfo(mnn, tn, spec, owner, amount, ownerPrice, regNo, date);
    }
}
