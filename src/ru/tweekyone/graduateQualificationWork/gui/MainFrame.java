package ru.tweekyone.graduateQualificationWork.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.LinkedList;
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
    //private ResultFrame rf;
    private JLabel connectionLabel;
    private JLabel resultWarning;
    
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
        setSize(650, 300);
        setResizable(true);
        //Получение размера окна пользователя
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension dim = toolkit.getScreenSize();
        //Подгонка расположения окна
        setLocation(dim.width/2 - 325, dim.height/2 - 150);
        setTitle("Калькулятор надбавки ЖНВЛП");
        connectionLabel = new Labels().getWarningLabel();
        resultWarning= new Labels().getWarningLabel();
        this.addWindowListener(new EventHandler());
        onInitComponents();
    }
    
    //инициализация компонентов окна
    private void onInitComponents(){
        
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        
        JLabel drugName = new JLabel("Наименование препарата:");
        JLabel regNumber = new JLabel("Регистрационный номер:");
        JLabel regionName = new JLabel("Регион:");
        JTextField textField = new JTextField(14);
        JTextField regTextField = new JTextField(14);
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
        MarkupController marckupPanel = new MarkupController(rm);
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
                textField.setBackground(Color.white);
                regTextField.setBackground(Color.white);
                if(!textField.getText().trim().equals("")){
                    Thread drugSearchThread = new Thread(){
                        @Override
                        public void run(){
                            confirm.setEnabled(false);
                            if(dbda == null){
                                dbda = new DrugBaseDataAccess(dbd);
                            } 
                            drugsList = dbda.getDrugsList(textField.getText(), mnn.isSelected(), regTextField.getText());
                            new ResultFrame(drugsList, marckupPanel, (String) regions.getSelectedItem(), textField.getText());
                            confirm.setEnabled(true);
                        }
                    };
                    drugSearchThread.setName("DrugSearchThread");
                    drugSearchThread.start();
                } else if(!regTextField.getText().trim().equals("")){
                    Thread drugSearchThread = new Thread(){
                        @Override
                        public void run(){
                            confirm.setEnabled(false);
                            if(dbda == null){
                                dbda = new DrugBaseDataAccess(dbd);
                            } 
                            drugsList = dbda.getDrugsList(textField.getText(), mnn.isSelected(), regTextField.getText());
                            new ResultFrame(drugsList, marckupPanel, (String) regions.getSelectedItem(), textField.getText());
                            confirm.setEnabled(true);
                        }
                    };
                    drugSearchThread.setName("DrugSearchThread");
                    drugSearchThread.start();
                } else {
                    textField.setBackground(Color.red);
                    regTextField.setBackground(Color.red);
                }
            }
        });
        
        JButton saveUserMargin = new JButton("Сохранить наценку");
        saveUserMargin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Thread marginSavingThread = new Thread(){
                    @Override
                    public void run(){
                        RegionMarginDataAccess.setRegionMargin(marckupPanel, resultWarning);
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
                            .addComponent(regNumber)
                            .addComponent(regionName))
                    .addGroup(searchLayout.createParallelGroup(LEADING)
                            .addComponent(textField)
                            .addComponent(regTextField)
                            .addComponent(regions)
                            .addComponent(marckupSetter))
                    .addGroup(searchLayout.createParallelGroup(LEADING)
                            .addGroup(searchLayout.createSequentialGroup()
                                    .addComponent(mnn)
                                    .addComponent(tn))
                            .addComponent(confirm, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(saveUserMargin, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
                    
        searchLayout.setVerticalGroup(searchLayout.createSequentialGroup()
                    .addGroup(searchLayout.createParallelGroup(BASELINE)
                            .addComponent(drugName)
                            .addComponent(textField)
                            .addGroup(searchLayout.createParallelGroup(BASELINE)
                                    .addComponent(mnn)
                                    .addComponent(tn)))
                    .addGroup(searchLayout.createParallelGroup(BASELINE)
                            .addComponent(regNumber)
                            .addComponent(regTextField))
                    .addGroup(searchLayout.createParallelGroup(BASELINE)
                            .addComponent(regionName)
                            .addComponent(regions)
                            .addComponent(confirm))
                    .addGroup(searchLayout.createParallelGroup(BASELINE)
                            .addComponent(marckupSetter)
                            .addComponent(saveUserMargin)));
        add(searchPanel);
        add(connectionLabel);
        add(resultWarning);
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
