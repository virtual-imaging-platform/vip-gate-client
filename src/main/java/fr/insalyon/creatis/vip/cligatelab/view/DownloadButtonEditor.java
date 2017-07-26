package fr.insalyon.creatis.vip.cligatelab.view;

import fr.insalyon.creatis.vip.cligatelab.controller.ResultControl;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static fr.insalyon.creatis.vip.cligatelab.controller.Main.INTERFACESCALE;
import static fr.insalyon.creatis.vip.cligatelab.controller.Main.SCREENHEIGHT;
import static fr.insalyon.creatis.vip.cligatelab.controller.Main.SCREENWIDTH;

/**
 * Created by qifan on 2017/7/12.
 */
class DownloadButtonEditor extends DefaultCellEditor {
    protected JButton button;
    private String label;
    private String executionIdentifier;


    public DownloadButtonEditor(JCheckBox checkBox) {
        super(checkBox);
        button = new JButton();
        button.setOpaque(true);
        /**button.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
         JOptionPane.showMessageDialog(button, executionIdentifier + ": Ouch!");
         }
         });**/
        button.addActionListener(e -> {
            JFileChooser jFileChooser = new JFileChooser() {
                protected JDialog createDialog(Component parent) throws HeadlessException {

                    final JDialog dialog = super.createDialog(parent);
                    Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
                    int defaultWidth = (int) (SCREENWIDTH * INTERFACESCALE * 0.4);
                    int defaultHeight = (int) (SCREENHEIGHT * INTERFACESCALE * 0.4);
                    dialog.setMinimumSize(new Dimension(defaultWidth, defaultHeight));
                    dialog.addComponentListener(new ComponentAdapter() {

                        public void componentResized(ComponentEvent e) {
                            int currentWidth = dialog.getSize().width;
                            dialog.setSize(currentWidth, defaultHeight);
                        }

                    });

                    return dialog;
                }
            };
            jFileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            jFileChooser.setAcceptAllFileFilterUsed(false);
            jFileChooser.showDialog(new JLabel(), "choose download directory");
            if (jFileChooser.getSelectedFile() != null) {
                String downloadDir = jFileChooser.getSelectedFile().getAbsolutePath();
                ResultControl resultControl = new ResultControl(executionIdentifier, downloadDir);
                resultControl.execute();
            }

        });
    }

    public Component getTableCellEditorComponent(JTable table, Object value,
                                                 boolean isSelected, int row, int column) {
        Image imgDownlaod = null;
        try {

            imgDownlaod = ImageIO.read(getClass().getResource("/download.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        button.setIcon(new ImageIcon(imgDownlaod));
        if (isSelected) {
            button.setForeground(table.getSelectionForeground());
            button.setBackground(table.getSelectionBackground());
        } else {
            button.setForeground(table.getForeground());
            button.setBackground(table.getBackground());
        }

        executionIdentifier =(String)value;

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

