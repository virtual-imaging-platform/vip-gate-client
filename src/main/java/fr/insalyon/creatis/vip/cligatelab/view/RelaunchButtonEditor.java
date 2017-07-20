package fr.insalyon.creatis.vip.cligatelab.view;

import fr.insalyon.creatis.vip.cligatelab.controller.RelaunchControl;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by qifan on 2017/7/12.
 */
public class RelaunchButtonEditor  extends DefaultCellEditor {
    protected JButton button;

    private String label;
    private String executionIdentifier;
    private StatusMonitor statusMonitor;


    public RelaunchButtonEditor(JCheckBox checkBox,StatusMonitor statusMonitor) {
        super(checkBox);
        this.statusMonitor=statusMonitor;
        button = new JButton();
        button.setOpaque(true);
        button.addActionListener(e -> {
            RelaunchControl relaunchControl=new RelaunchControl(executionIdentifier,statusMonitor);
            relaunchControl.execute();
        });
    }

    public Component getTableCellEditorComponent(JTable table, Object value,
                                                 boolean isSelected, int row, int column) {
        if (isSelected) {
            button.setForeground(table.getSelectionForeground());
            button.setBackground(table.getSelectionBackground());
        } else {
            button.setForeground(table.getForeground());
            button.setBackground(table.getBackground());
        }
        Image imgRelaunch = null;
        try {
            imgRelaunch = ImageIO.read(getClass().getResource("/reload.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        button.setIcon(new ImageIcon(imgRelaunch));



        executionIdentifier = ((String)value);




        return button;
    }

    public Object getCellEditorValue() {

        return label;
    }

    public boolean stopCellEditing() {

        return super.stopCellEditing();
    }

    protected void fireEditingStopped() {
        super.fireEditingStopped();
    }



}


