package ru.tweekyone.graduateQualificationWork.gui;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BoxLayout;
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
import ru.tweekyone.graduateQualificationWork.databaseConnection.RegionMarginDataAccess;
import ru.tweekyone.graduateQualificationWork.drugsBase.DrugBaseDataAccess;
import ru.tweekyone.graduateQualificationWork.drugsBase.DrugBaseDownload;
import ru.tweekyone.graduateQualificationWork.objects.DrugInfo;
import ru.tweekyone.graduateQualificationWork.objects.RegionMargin;

/**
 *
 * @author Пирожок
 */
public class MainFrame extends AbstractFrame{
    private DrugBaseDownload dbd;
    private DrugBaseDataAccess dbda;
    private LinkedList<DrugInfo> drugsList;
    private ResultTable rt;
    private JLabel connectionLabel;
    private JLabel floatWarningLabel;
    
   //Адаптер для событий при открытии\закрытии окна
    private class EventHandler extends WindowAdapter{
        
        @Override
        public void windowOpened(WindowEvent e){
            Thread drugBaseDownloadThread = new Thread(){
                @Override
                public void run(){
                    dbd = new DrugBaseDownload(connectionLabel);
                    dbd.checkDrugBase();
                }
            };
            drugBaseDownloadThread.setName("DrugBaseDownloadThread");
            drugBaseDownloadThread.start();
            try {
                drugBaseDownloadThread.join();
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }
    
    public MainFrame(){
        setSize(600, 300);
        setResizable(false);
        //Получение размера окна пользователя
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension dim = toolkit.getScreenSize();
        //Подгонка расположения окна
        setLocation(dim.width/2 - 300, dim.height/2 - 150);
        setTitle("Калькулятор надбавки ЖНВЛП");
        connectionLabel = new Labels().getWarningLabel();
        floatWarningLabel = new Labels().getWarningLabel();
        this.addWindowListener(new EventHandler());
        onInitComponents();
    }
    
    //инициализация компонентов окна
    private void onInitComponents(){
        
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        
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
        
        
        //Выдает первый RegionMargin (Москва)
        RegionMargin rm = RegionMarginDataAccess.getRegionMargin(1);
        MarkupController marckupPanel = new MarkupController(rm, floatWarningLabel);
        JPanel marckupSetter = marckupPanel.getMarckupPanel();
        
        JComboBox<String> regions = getRegions();
        regions.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                int regionId = regions.getSelectedIndex() + 1;
                //Нельзя передать ссылку на созданный класс actionRm в rm напрямую
                RegionMargin actionRm = RegionMarginDataAccess.getRegionMargin(regionId);
                marckupPanel.setZoneMarginController(actionRm);
            }
        });
        
        JButton confirm = new JButton("Рассчитать");
        confirm.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Thread drugSearchThread = new Thread(){
                    @Override
                    public void run(){
                        dbda = new DrugBaseDataAccess(dbd);
                        drugsList = dbda.getDrugsList(textField.getText(), mnn.isSelected());
                    }
                };
                drugSearchThread.setName("DrugSearchThread");
                drugSearchThread.start();
                try {
                    drugSearchThread.join();
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
                rt = new ResultTable(drugsList, marckupPanel);
            }
        });
        
        JButton saveUserMargin = new JButton("Сохранить \nнаценку");
        saveUserMargin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Thread marginSavingThread = new Thread(){
                    @Override
                    public void run(){
                        RegionMarginDataAccess.setRegionMargin(marckupPanel);
                    }
                };
                marginSavingThread.setName("MarginSavingThread");
                marginSavingThread.start();
                try {
                    marginSavingThread.join();
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
        });
        
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
                            .addComponent(confirm)
                            .addComponent(saveUserMargin)));
                    
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
                    .addGroup(searchLayout.createParallelGroup(BASELINE)
                            .addComponent(marckupSetter, GroupLayout.PREFERRED_SIZE, 
                            GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                            .addComponent(saveUserMargin)));
        add(searchPanel);
        add(connectionLabel);
        add(floatWarningLabel);
        //Обновление компонентов
        revalidate();
    }
      
    //Добавление списка регионов
    private JComboBox<String> getRegions(){
        LinkedList<String> list = RegionMarginDataAccess.getRegionsList();
        String[] regionsArray = new String[list.size()];
        for (int i = 0; i < regionsArray.length; i++) {
            regionsArray[i] = list.get(i);
        }
        JComboBox<String> regions = new JComboBox<>(regionsArray);
        regions.setToolTipText("Регион");
        return regions;
    }
}
