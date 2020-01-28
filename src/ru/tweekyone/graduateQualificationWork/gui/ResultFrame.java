package ru.tweekyone.graduateQualificationWork.gui;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import ru.tweekyone.graduateQualificationWork.objects.DrugInfo;

/**
 *
 * @author Пирожок
 */
public class ResultFrame extends AbstractFrame{
    private JTable resultTable;
    private ResultTableController rtc;
    private JRadioButton withoutVat;
    private JRadioButton withoutRetailVat;
    private JTextField price;
    private JPanel controllPanel;
    private JLabel floatWarningLabel;
    
    
    public ResultFrame(LinkedList<DrugInfo> searchResult, MarkupController mp, String region, String drugName){
        if(mp.getRegionMargin().hasZone()){
            setTitle("Результаты расчетов " + region + " " + mp.getZones().getSelectedItem() + " " + drugName);
        } else {
            setTitle("Результаты расчетов " + region + " " + drugName);
        }
        rtc = new ResultTableController(searchResult, mp);
        resultTable = rtc.getResultTable();
        //автоматически устанавливает размер окна
        onInitComponents();
        pack();
    }
    
    private void onInitComponents(){
        onInitControllPanel();
        JScrollPane scrollPane = new JScrollPane(resultTable);
        setLayout(new BoxLayout(getContentPane(), BoxLayout.PAGE_AXIS));
        floatWarningLabel = new Labels().getWarningLabel();
        add(controllPanel);
        add(scrollPane);
        add(floatWarningLabel);
        revalidate();
    }
    
    private void onInitControllPanel(){
        controllPanel = new JPanel();
        controllPanel.setLayout(new BoxLayout(controllPanel, BoxLayout.LINE_AXIS));
        
        withoutVat = new JRadioButton("Оптовая на УСН");
        withoutVat.setSelected(false);
        withoutVat.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                rtc.setMarginType(withoutVat.isSelected(), withoutRetailVat.isSelected());
                resultTable.revalidate();
            }
        });
      
        withoutRetailVat = new JRadioButton("Розничная на УСН");
        withoutRetailVat.setSelected(false);
        withoutRetailVat.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                rtc.setMarginType(withoutVat.isSelected(), withoutRetailVat.isSelected());
                resultTable.revalidate();
            }
        });
        
        JLabel label = new JLabel("Фактическая отпускная цена:");
        price = new JTextField(5);
        JButton changePrice = new JButton("Применить");
        changePrice.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = resultTable.getSelectedRow();
                if(selectedRow != -1 && price.getText() != null){
                    try{
                        Float.parseFloat(price.getText());
                        floatWarningLabel.setVisible(false);
                        resultTable.setValueAt(price.getText(), selectedRow, 7);
                        rtc.setSelectedDrug(withoutVat.isSelected(), withoutRetailVat.isSelected(), resultTable.getSelectedRow());
                        resultTable.revalidate();
                    } catch (NumberFormatException n){
                        floatWarningLabel.setText("Введите число!");
                        floatWarningLabel.setVisible(true);
                        n.printStackTrace();
                    }
                }
            }
        });
        
        resultTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                int selectedRow = resultTable.getSelectedRow();
                if(selectedRow != -1){
                    price.setText((String)resultTable.getValueAt(selectedRow, 7));
                }
            }
        });
        
        controllPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, changePrice.getPreferredSize().height));
        
        controllPanel.add(withoutVat);
        controllPanel.add(withoutRetailVat);
        controllPanel.add(label);
        controllPanel.add(price);
        controllPanel.add(changePrice);
    }
}
