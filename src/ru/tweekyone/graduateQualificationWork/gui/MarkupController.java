package ru.tweekyone.graduateQualificationWork.gui;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.GroupLayout;
import static javax.swing.GroupLayout.Alignment.BASELINE;
import static javax.swing.GroupLayout.Alignment.LEADING;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import ru.tweekyone.graduateQualificationWork.objects.RegionMargin;

/**
 *
 * @author Пирожок
 */
public class MarkupController{
    private RegionMargin currentRegionMargin;
    private int currentZoneId;
    private JPanel panel;
    private JLabel wholesale;
    private JLabel retail;
    private JLabel upTo50;
    private JLabel over50To500;
    private JLabel over500;
    private JTextField tfWhol_50;
    private JTextField tfWhol_50_500;
    private JTextField tfWhol_500;
    private JTextField tfRet_50;
    private JTextField tfRet_50_500;
    private JTextField tfRet_500;
    private JComboBox<String> zones;
    private JLabel zoneContent;

    public MarkupController(RegionMargin rm){
        currentRegionMargin = rm;
        panel = new JPanel();
        panel.setBorder(BorderFactory.createTitledBorder("Наценка, %"));
        tfWhol_50 = new JTextField();
        tfWhol_50_500 = new JTextField();
        tfWhol_500 = new JTextField();
        tfRet_50 = new JTextField();
        tfRet_50_500 = new JTextField();
        tfRet_500 = new JTextField();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        wholesale = new JLabel("Оптовая:");
        retail = new JLabel("Розничная:");
        upTo50 = new JLabel("до 50 руб:");
        over50To500 = new JLabel("от 50 до 500 руб:");
        over500 = new JLabel("свыше 500 руб:");
        
        zoneMarginController();
    }
    
    public JPanel getMarckupPanel(){
        return panel;
    }
    
    private JPanel getMarckupSetterLayout(){
        JPanel marckupSetter = new JPanel();
        GroupLayout layout = new GroupLayout(marckupSetter);
        marckupSetter.setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);
        
        layout.setHorizontalGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(LEADING)
                                        .addComponent(upTo50)
                                        .addComponent(over50To500)
                                        .addComponent(over500))
                                .addGroup(layout.createParallelGroup(LEADING)
                                        .addComponent(wholesale)
                                        .addComponent(tfWhol_50)
                                        .addComponent(tfWhol_50_500)
                                        .addComponent(tfWhol_500))
                                .addGroup(layout.createParallelGroup(LEADING)
                                        .addComponent(retail)
                                        .addComponent(tfRet_50)
                                        .addComponent(tfRet_50_500)
                                        .addComponent(tfRet_500)));
        
        layout.setVerticalGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(BASELINE)
                                        .addComponent(wholesale)
                                        .addComponent(retail))
                                .addGroup(layout.createParallelGroup(BASELINE)
                                        .addComponent(upTo50)
                                        .addComponent(tfWhol_50)
                                        .addComponent(tfRet_50))
                                .addGroup(layout.createParallelGroup(BASELINE)
                                        .addComponent(over50To500)
                                        .addComponent(tfWhol_50_500)
                                        .addComponent(tfRet_50_500))
                                .addGroup(layout.createParallelGroup(BASELINE)
                                        .addComponent(over500)
                                        .addComponent(tfWhol_500)
                                        .addComponent(tfRet_500)));
        return marckupSetter;
    }
    
    //Создание первичного поля натройки наценки
    private void zoneMarginController(){
        if (currentRegionMargin.hasZone()){
            zones = new JComboBox<>(currentRegionMargin.getZoneArray());
            String content = currentRegionMargin.getZoneMargin().get(0).getContent();
            currentZoneId = currentRegionMargin.getZoneMargin().get(0).getId();
            zoneContent = new JLabel(content);
            zoneContent.setVerticalAlignment(JLabel.TOP);
            zoneContent.setHorizontalAlignment(JLabel.CENTER);
            
            zones.addItemListener(new ItemListener() {
                @Override
                public void itemStateChanged(ItemEvent e) {
                    int zoneId = zones.getSelectedIndex();
                    String newContent = currentRegionMargin.getZoneMargin().get(zoneId).getContent();
                    zoneContent.setText(newContent);
                    setTextFieldsByIndex(currentRegionMargin, zoneId);
                }
            });
            panel.add(zones);
            panel.add(zoneContent);
            panel.add(getMarckupSetterLayout());
            setTextFieldsByIndex(currentRegionMargin, 0);
        } else {
            panel.add(getMarckupSetterLayout());
            setTextFieldsByIndex(currentRegionMargin, 0);
        }
        
        panel.revalidate();
    }
    
    //Коррекция поля настройки наценки
    public void setZoneMarginController(RegionMargin newRm){
        currentZoneId = newRm.getZoneMargin().get(0).getId();
        if(newRm.hasZone()){
            //удаление старых элементов
            panel.removeAll();
            if (zones != null && zoneContent != null){
                zones = null;
                zoneContent = null;
            }
            zones = new JComboBox<>(newRm.getZoneArray());
            String content = newRm.getZoneMargin().get(0).getContent();
            zoneContent = new JLabel(content);
            zoneContent.setVerticalAlignment(JLabel.TOP);
            zoneContent.setHorizontalAlignment(JLabel.CENTER);

            zones.addItemListener(new ItemListener() {
                @Override
                public void itemStateChanged(ItemEvent e) {
                    int zoneId = zones.getSelectedIndex();
                    String newContent = newRm.getZoneMargin().get(zoneId).getContent();
                    currentZoneId = newRm.getZoneMargin().get(zoneId).getId();
                    zoneContent.setText(newContent);
                    setTextFieldsByIndex(newRm, zoneId);
                }
            });
            panel.add(zones);
            panel.add(zoneContent);
            panel.add(getMarckupSetterLayout());
            setTextFieldsByIndex(newRm, 0);
        } else {
            panel.removeAll();
            if (zones != null && zoneContent != null){
                zones = null;
                zoneContent = null;
            }
            panel.add(getMarckupSetterLayout());
            setTextFieldsByIndex(newRm, 0);
        }
        panel.revalidate();
    }
    
    private void setTextFieldsByIndex(RegionMargin rm, int index){
            tfWhol_50.setText(((Double) rm.getZoneMargin().get(index).getWrUpTo50()).toString());
            tfWhol_50_500.setText(((Double) rm.getZoneMargin().get(index).getWrOver50to500()).toString());
            tfWhol_500.setText(((Double) rm.getZoneMargin().get(index).getWrOver500()).toString());
            tfRet_50.setText(((Double) rm.getZoneMargin().get(index).getRrUpTo50()).toString());
            tfRet_50_500.setText(((Double) rm.getZoneMargin().get(index).getRrOver50to500()).toString());
            tfRet_500.setText(((Double) rm.getZoneMargin().get(index).getRrOver500()).toString());
    }

    public float getUpTo50Value() {
        return Float.parseFloat(upTo50.getText());
    }

    public float getTfWhol_50Value() {
        return Float.parseFloat(tfWhol_50.getText());
    }

    public float getTfWhol_50_500Value() {
        return Float.parseFloat(tfWhol_50_500.getText());
    }

    public float getTfWhol_500Value() {
        return Float.parseFloat(tfWhol_500.getText());
    }

    public float getTfRet_50Value() {
        return Float.parseFloat(tfRet_50.getText());
    }

    public float getTfRet_50_500Value() {
        return Float.parseFloat(tfRet_50_500.getText());
    }

    public float getTfRet_500Value() {
        return Float.parseFloat(tfRet_500.getText());
    }
    
    public RegionMargin getRegionMargin(){
        return currentRegionMargin;
    }
}
