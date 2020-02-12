package ru.tweekyone.graduateQualificationWork.gui;

import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.UIManager;

//Абстрактный фрейм для унификации других окон
public abstract class AbstractFrame extends JFrame{
    
    
    /**
     * Настройка внешнего вида интерфейса в зависимости от окружающей среды
     * static отрабатывает единожды
     */
    static{
        try{
            String className = UIManager.getSystemLookAndFeelClassName();
            UIManager.setLookAndFeel(className);
        } catch (Exception ignore){}
    }
    
    public AbstractFrame(){
        //DISPOSE_ON_CLOSE для правильного закрытия ресурсов
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        //Добавление иконки в шапку
        Image icon = new ImageIcon("images\\doctor.png").getImage();
        setIconImage(icon);
    }   
}
