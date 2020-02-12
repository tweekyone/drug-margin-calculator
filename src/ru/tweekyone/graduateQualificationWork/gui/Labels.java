package ru.tweekyone.graduateQualificationWork.gui;

import java.awt.Color;
import static java.awt.Component.CENTER_ALIGNMENT;
import javax.swing.JLabel;

public class Labels {
    private JLabel warningLabel;
    
    public Labels(){
        warningLabel = new JLabel();
        warningLabel.setBackground(Color.red);
        warningLabel.setVisible(false);
        warningLabel.setOpaque(true);
        warningLabel.setAlignmentX(CENTER_ALIGNMENT);
    }
    
    public JLabel getWarningLabel(){
        return warningLabel;
    }
}
