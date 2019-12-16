package ru.tweekyone.graduateQualificationWork.gui;

import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.LinkedList;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.GroupLayout;
import static javax.swing.GroupLayout.Alignment.BASELINE;
import static javax.swing.GroupLayout.Alignment.LEADING;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.Border;
import ru.tweekyone.graduateQualificationWork.databaseConnection.RegionMarginDataAccess;
import static ru.tweekyone.graduateQualificationWork.databaseConnection.RegionMarginDataAccess.getRegionsList;
import ru.tweekyone.graduateQualificationWork.objects.RegionMargin;

/**
 *
 * @author Пирожок
 */
public class MainFrame extends AbstractFrame{
   //Адаптер для событий при открытии\закрытии окна
    private class EventHandler extends WindowAdapter{
        
        @Override
        public void windowOpened(WindowEvent e){
            //...
        }
    }
    
    public MainFrame(){
        setSize(600, 300);
        //Получение размера окна пользователя
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension dim = toolkit.getScreenSize();
        //Подгонка расположения окна
        setLocation(dim.width/2 - 300, dim.height/2 - 150);
        setTitle("Калькулятор надбавки ЖНВЛП");
        onInitComponents();
        addWindowListener(new EventHandler());
    }
    
    //инициализация компонентов окна
    private void onInitComponents(){
        
        JLabel drugName = new JLabel("Наименование препарата:");
        JLabel regionName = new JLabel("Регион:");
        JTextField textField = new JTextField(14);
        ButtonGroup buttonGroup = new ButtonGroup();
        
        JRadioButton mnn = new JRadioButton("МНН");
        mnn.setSelected(true);
        mnn.setToolTipText("Международное непатинтованное наименование");
        JRadioButton tn = new JRadioButton("ТН");
        tn.setToolTipText("Торговое наименование");
        buttonGroup.add(mnn);
        buttonGroup.add(tn);
        
        RegionMargin rm = new RegionMargin();
        
        JComboBox<String> regions = getRegions();
        regions.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int regionId = regions.getSelectedIndex() + 1;
                //Нельзя передать ссылку на созданный класс actionRM в rm напрямую
                RegionMargin actionRm = RegionMarginDataAccess.getRegionMargin(regionId);
                if(actionRm != null){
                    rm.setId(actionRm.getId());
                    rm.setRegion(actionRm.getRegion());
                    rm.setHasZone(actionRm.isHasZone());
                    rm.setZoneMargin(actionRm.getZoneMargin());
                } else{
                    //Передать сообщение об ошибке в панель
                }
            }
        });
        
        JButton confirm = new JButton("Рассчитать");
        
        JPanel marckupSetter = new MarckupPanel().getMarckupPanel(rm);
        
        JPanel searchPanel = new JPanel();
        GroupLayout searchLayout = new GroupLayout(searchPanel);
        searchPanel.setLayout(searchLayout);
        searchLayout.setAutoCreateGaps(true);
        searchLayout.setAutoCreateContainerGaps(true);
        
        
        searchLayout.setHorizontalGroup(searchLayout.createSequentialGroup()
                    .addGroup(searchLayout.createParallelGroup(LEADING)
                            .addComponent(drugName)
                            .addComponent(regionName))
                    .addGroup(searchLayout.createParallelGroup(LEADING)
                            .addComponent(textField)
                            .addComponent(regions)
                            .addComponent(marckupSetter))
                    .addGroup(searchLayout.createParallelGroup(LEADING)
                            .addGroup(searchLayout.createSequentialGroup()
                                    .addComponent(mnn)
                                    .addComponent(tn))
                            .addComponent(confirm)));
                    
        searchLayout.setVerticalGroup(searchLayout.createSequentialGroup()
                    .addGroup(searchLayout.createParallelGroup(BASELINE)
                            .addComponent(drugName)
                            .addComponent(textField)
                            .addGroup(searchLayout.createParallelGroup(BASELINE)
                                    .addComponent(mnn)
                                    .addComponent(tn)))
                    .addGroup(searchLayout.createParallelGroup(BASELINE)
                            .addComponent(regionName)
                            .addComponent(regions)
                            .addComponent(confirm))
                    .addComponent(marckupSetter, GroupLayout.PREFERRED_SIZE, 
                            GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE));
        
        add(searchPanel);
        //Обновление компонентов
        revalidate();
    }
      
    //Добавление списка регионов
    private JComboBox<String> getRegions(){
        //вывод сообщения, если list пустой
        LinkedList<String> list = getRegionsList();
        String[] regionsArray = new String[list.size()];
        for (int i = 0; i < regionsArray.length; i++) {
            regionsArray[i] = list.get(i);
        }
        JComboBox<String> regions = new JComboBox<>(regionsArray);
        regions.setToolTipText("Регион");
        return regions;
    }

}
