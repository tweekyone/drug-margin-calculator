package ru.tweekyone.graduateQualificationWork.gui;

import java.util.LinkedList;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import ru.tweekyone.graduateQualificationWork.calculation.MarginCalculation;
import ru.tweekyone.graduateQualificationWork.objects.DrugInfo;

/**
 *
 * @author Пирожок
 */
public class ResultTableController{
    private String[][] dataArray;
    private String[] columnNames;
    private MarkupController mp;
    private JTable resultTable;
    private DefaultTableModel dtm;
    
    public ResultTableController(LinkedList<DrugInfo> searchResult, MarkupController mp){
        this.mp = mp;
        dataArray = new String[searchResult.size()][];
        columnNames = new String[]{"МНН", "Торговое наименование", "Лекарственная форма, дозировка, упаковка",
                        "Владелец РУ", "Количество в упаковке", "Регистрационный номер",
                        "Дата регистрации", "Отпускная цена производителя", "Предельная оптовая наценка",
                        "Предельная розничная наценка"};
        
        createDataArray(searchResult);
        onInitComponents();
    }
    
    private void createDataArray(LinkedList<DrugInfo> searchResult){
        for (int i = 0; i < dataArray.length; i++) {
            DrugInfo di = searchResult.get(i);
            String mnn = di.getMNN();
            String tn = di.getTN();
            String spec = di.getSPEC();
            String own = di.getOWNER();
            String amount = String.valueOf(di.getAMOUNT());
            String regNo = di.getREGNO();
            String date = di.getDATE();
            String ownerPrice = String.valueOf(di.getOwnerPrice());
            String wholesalePrice = MarginCalculation.getWholesaleMargin(true, di.getOwnerPrice(), mp);
            String retailPrice = MarginCalculation.getRetailMargin(true, true, di.getOwnerPrice(), wholesalePrice, mp);
            dataArray[i] = new String[]{mnn, tn, spec, own, amount, regNo, date, ownerPrice, wholesalePrice, retailPrice};
        }
    };
    
    //добавить кнопки УСН/НДС, коррекция фактической цены производителя
    private void onInitComponents(){
        dtm = new DefaultTableModel(dataArray, columnNames);
        resultTable = new JTable(dtm);
        resultTable.setAutoCreateRowSorter(true);
        resultTable.setAutoResizeMode(JTable.AUTO_RESIZE_NEXT_COLUMN);
    }
    
    public void setMarginType(boolean isVAT, boolean isWholesaleVAT){
        for (int i = 0; i < dataArray.length; i++) {
            String wholesaleNewPrice = MarginCalculation.getWholesaleMargin(isVAT, Float.valueOf((String) resultTable.getValueAt(i, 7)), mp);
            String retailNewPrice = MarginCalculation.getRetailMargin(isVAT, isWholesaleVAT, Float.valueOf((String) resultTable.getValueAt(i, 7)), wholesaleNewPrice, mp);
            resultTable.setValueAt(wholesaleNewPrice, i, 8);
            resultTable.setValueAt(retailNewPrice, i, 9);
        }
    }
    
    public void setSelectedDrug(boolean isVAT, boolean isWholesaleVAT, int row){
        String wholesaleNewPrice = MarginCalculation.getWholesaleMargin(isVAT, Float.valueOf((String) resultTable.getValueAt(row, 7)), mp);
        String retailNewPrice = MarginCalculation.getRetailMargin(isVAT, isWholesaleVAT, Float.valueOf((String) resultTable.getValueAt(row, 7)), wholesaleNewPrice, mp);
        resultTable.setValueAt(wholesaleNewPrice, row, 8);
        resultTable.setValueAt(retailNewPrice, row, 9);
    }
    
    public JTable getResultTable(){
        return resultTable;
    }
}
