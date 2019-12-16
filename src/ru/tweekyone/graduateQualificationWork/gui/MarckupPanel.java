package ru.tweekyone.graduateQualificationWork.gui;

import java.awt.ComponentOrientation;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;
import ru.tweekyone.graduateQualificationWork.objects.RegionMargin;

/**
 *
 * @author Пирожок
 */
public class MarckupPanel{
    private JPanel panel;
    
    public MarckupPanel(){
        panel = new JPanel();
    }
    
    public JPanel getMarckupPanel(RegionMargin rm){
        GroupLayout layout = new GroupLayout(panel);
        panel.setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);
        
        JLabel wholesale = new JLabel("Оптовая:");
        JLabel retail = new JLabel("Розничная:");
        JLabel upTo50 = new JLabel("до 50 руб:");
        JLabel over50To500 = new JLabel("от 50 до 500 руб:");
        JLabel over500 = new JLabel("свыше 500 руб:");
        JTextField tfWhol_50 = new JTextField();
        JTextField tfWhol_50_500 = new JTextField();
        JTextField tfWhol_500 = new JTextField();
        JTextField tfRet_50 = new JTextField();
        JTextField tfRet_50_500 = new JTextField();
        JTextField tfRet_500 = new JTextField();
              
        if (rm.isHasZone()){
            JComboBox<String> zones = new JComboBox<>(rm.getZoneArray());
            JLabel zoneConsist = new JLabel();
            
            return panel;
        } else {
            
            return panel;
        }
    }
}
