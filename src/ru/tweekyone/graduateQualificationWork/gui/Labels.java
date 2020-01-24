package ru.tweekyone.graduateQualificationWork.gui;

import java.awt.Color;
import static java.awt.Component.CENTER_ALIGNMENT;
import javax.swing.JLabel;

/**
 *
 * @author Пирожок
 */
public class Labels {
    private JLabel connectionLabel;
    
    public Labels(){
        connectionLabel = new JLabel();
        connectionLabel.setBackground(Color.red);
        connectionLabel.setVisible(false);
        connectionLabel.setOpaque(true);
        connectionLabel.setAlignmentX(CENTER_ALIGNMENT);
    }
    
    public JLabel getConnectionLabel(){
        return connectionLabel;
    }
}
