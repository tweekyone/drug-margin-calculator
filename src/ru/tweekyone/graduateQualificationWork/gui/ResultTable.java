package ru.tweekyone.graduateQualificationWork.gui;

import java.util.LinkedList;
import java.util.Vector;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import ru.tweekyone.graduateQualificationWork.calculation.MarginCalculation;
import ru.tweekyone.graduateQualificationWork.objects.DrugInfo;

/**
 *
 * @author Пирожок
 */
public class ResultTable extends AbstractFrame{
    private String[][] dataArray;
    private String[] columnNames;
    private MarkupPanel mp;
    private JTable resultTable;
    
    public ResultTable(LinkedList<DrugInfo> searchResult, MarkupPanel mp){
        this.mp = mp;
        dataArray = new String[searchResult.size()][];
        columnNames = new String[]{"МНН", "Торговое наименование", "Лекарственная форма, дозировка, упаковка",
                        "Владелец РУ", "Количество в упаковке", "Регистрационный номер",
                        "Дата регистрации", "Отпускная цена производителя", "Предельная оптовая наценка",
                        "Предельная розничная наценка"};
        setTitle("Результаты расчетов");
        setSize(600, 600);
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
            String wholesalePrice = MarginCalculation.getWholesaleMargin(true, ownerPrice, mp);
            String retailPrice = MarginCalculation.getRetailMargin(true, true, ownerPrice, wholesalePrice, mp);
            dataArray[i] = new String[]{mnn, tn, spec, own, amount, regNo, date, ownerPrice, wholesalePrice, retailPrice};
        }
    };
    
    //добавить кнопки УСН/НДС, коррекция фактической цены производителя
    private void onInitComponents(){
        resultTable = new JTable(dataArray, columnNames);
        resultTable.setAutoCreateRowSorter(true);
        JScrollPane scrollPane = new JScrollPane(resultTable);
        add(scrollPane);
        revalidate();
    }
}
