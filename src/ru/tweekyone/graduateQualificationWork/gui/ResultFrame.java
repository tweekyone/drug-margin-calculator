/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.tweekyone.graduateQualificationWork.gui;

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
import ru.tweekyone.graduateQualificationWork.objects.DrugInfo;

/**
 *
 * @author Пирожок
 */
public class ResultFrame extends AbstractFrame{
    private JTable resultTable;
    private ResultTableController rtc;
    private JRadioButton vat;
    private JRadioButton retailVat;
    private JTextField price;
    private JPanel controllPanel;
    
    
    public ResultFrame(LinkedList<DrugInfo> searchResult, MarkupController mp){
        setTitle("Результаты расчетов");
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
        add(controllPanel);
        add(scrollPane);
        revalidate();
    }
    
    private void onInitControllPanel(){
        controllPanel = new JPanel();
        controllPanel.setLayout(new BoxLayout(controllPanel, BoxLayout.LINE_AXIS));
        vat = new JRadioButton("Оптовая на УСН");
        vat.setSelected(false);
        vat.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                rtc.setMarginType(vat.isSelected(), retailVat.isSelected());
                resultTable.revalidate();
            }
        });
      
        retailVat = new JRadioButton("Розничная на УСН");
        retailVat.setSelected(false);
        retailVat.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                rtc.setMarginType(vat.isSelected(), retailVat.isSelected());
                resultTable.revalidate();
            }
        });
        
        JLabel label = new JLabel("Фактическая отпускная цена:");
        price = new JTextField(5);
        JButton changePrice = new JButton("Применить");
        controllPanel.add(vat);
        controllPanel.add(retailVat);
        controllPanel.add(label);
        controllPanel.add(price);
        controllPanel.add(changePrice);
    }
}
