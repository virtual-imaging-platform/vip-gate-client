package fr.insalyon.creatis.vip.cligatelab.view;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.io.IOException;

/**
 * Created by qifan on 2017/7/11.
 */
public class TableButtonRenderer implements TableCellRenderer {

    String label;

    public TableButtonRenderer(String label) {
        this.label = label;
    }

    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {

        JButton button =new JButton();
        switch (label) {
            case "download" :
                Image imgDownlaod = null;
                try {

                    imgDownlaod = ImageIO.read(getClass().getResource("/download.png"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                button.setIcon(new ImageIcon(imgDownlaod));
                break;
            case "relaunch" :
                Image imgRelaunch = null;
                try {
                    imgRelaunch = ImageIO.read(getClass().getResource("/reload.png"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                button.setIcon(new ImageIcon(imgRelaunch));
                break;
        }
        return button;
    }
}