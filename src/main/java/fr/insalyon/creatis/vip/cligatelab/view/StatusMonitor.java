package fr.insalyon.creatis.vip.cligatelab.view;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static fr.insalyon.creatis.vip.cligatelab.controller.Main.INTERFACESCALE;
import static fr.insalyon.creatis.vip.cligatelab.controller.Main.SCREENHEIGHT;
import static fr.insalyon.creatis.vip.cligatelab.controller.Main.SCREENWIDTH;

/**
 * Created by qifan on 2017/7/12.
 */
public class StatusMonitor {

    JFrame actualFrame;
    JFrame callingFrame;
    Object[] columnNames;
    Object[] filteredColumnNames;
    Object[][] allContent;
    Object[][] filteredContent;
    private JPanel jPanel;
    private JButton backButton;
    private JTable allExecutionTable;
    private JTabbedPane tabbedPane1;
    private JTable gateLabTable;


    public StatusMonitor(Object[] columnNames, Object[] filteredColumnNames, Object[][] allContent, Object[][] filteredContent) {
        this.filteredColumnNames = filteredColumnNames;
        this.filteredContent = filteredContent;
        this.columnNames = columnNames;
        this.allContent = allContent;
        $$$setupUI$$$();
        actualFrame = new JFrame();
        actualFrame.setVisible(true);
        actualFrame.setBounds((int) (SCREENWIDTH * INTERFACESCALE * 0.25), (int) (SCREENHEIGHT * INTERFACESCALE * 0.3), (int) (SCREENWIDTH * INTERFACESCALE * 0.5), (int) (SCREENHEIGHT * INTERFACESCALE * 0.4));
        actualFrame.setContentPane(jPanel);
        actualFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actualFrame.setVisible(false);
                callingFrame.setVisible(true);
            }
        });
    }


    private void createUIComponents() {
        // TODO: place custom component creation code here
        allExecutionTable = new JTable(allContent, columnNames) {
            public Component prepareRenderer(TableCellRenderer renderer, int row, int col) {
                Component comp = super.prepareRenderer(renderer, row, col);
                Object value = getModel().getValueAt(row, col);
                if (value instanceof String) {
                    if (value.equals("finished")) {
                        comp.setBackground(Color.green);
                    } else if (value.equals("killed")) {
                        comp.setBackground(Color.red);
                    } else if (value.equals("running")) {
                        comp.setBackground(Color.cyan);
                    } else {
                        comp.setBackground(Color.LIGHT_GRAY);
                    }

                }
                return comp;
            }
        };

        allExecutionTable.getColumnModel().getColumn(0).setPreferredWidth(120);
        allExecutionTable.getColumnModel().getColumn(1).setPreferredWidth(120);
        allExecutionTable.getColumnModel().getColumn(2).setPreferredWidth(180);
        allExecutionTable.getColumnModel().getColumn(3).setPreferredWidth(80);
        allExecutionTable.setRowHeight((int) (SCREENHEIGHT * INTERFACESCALE * 0.03));

        gateLabTable = new JTable(filteredContent, filteredColumnNames) {
            public Component prepareRenderer(TableCellRenderer renderer, int row, int col) {
                Component comp = super.prepareRenderer(renderer, row, col);
                Object value = getModel().getValueAt(row, col);
                if (value instanceof String) {
                    if (value.equals("finished")) {
                        comp.setBackground(Color.green);
                    } else if (value.equals("killed")) {
                        comp.setBackground(Color.red);
                    } else if (value.equals("running")) {
                        comp.setBackground(Color.cyan);
                    } else {
                        comp.setBackground(Color.LIGHT_GRAY);
                    }
                }
                return comp;
            }
        };
        gateLabTable.getColumn("result").setCellRenderer(new TableButtonRenderer("download"));
        gateLabTable.getColumn("result").setCellEditor(new DownloadButtonEditor(new JCheckBox()));
        gateLabTable.getColumn("relaunch").setCellRenderer(new TableButtonRenderer("relaunch"));
        gateLabTable.getColumn("relaunch").setCellEditor(new RelaunchButtonEditor(new JCheckBox(), this));
        gateLabTable.getColumnModel().getColumn(0).setPreferredWidth(120);
        gateLabTable.getColumnModel().getColumn(1).setPreferredWidth(120);
        gateLabTable.getColumnModel().getColumn(2).setPreferredWidth(180);
        gateLabTable.getColumnModel().getColumn(3).setPreferredWidth(80);
        gateLabTable.setRowHeight((int) (SCREENHEIGHT * INTERFACESCALE * 0.03));
    }

    public JFrame getActualFrame() {
        return actualFrame;
    }

    public JFrame getCallingFrame() {
        return callingFrame;
    }

    public void setActualFrame(JFrame actualFrame) {
        this.actualFrame = actualFrame;
    }

    public void setCallingFrame(JFrame callingFrame) {
        this.callingFrame = callingFrame;
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        createUIComponents();
        jPanel = new JPanel();
        jPanel.setLayout(new GridLayoutManager(2, 1, new Insets(0, 0, 0, 0), -1, -1));
        backButton = new JButton();
        backButton.setText("back");
        jPanel.add(backButton, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JScrollPane scrollPane1 = new JScrollPane();
        jPanel.add(scrollPane1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        tabbedPane1 = new JTabbedPane();
        scrollPane1.setViewportView(tabbedPane1);
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        tabbedPane1.addTab("GateLab", panel1);
        panel1.add(gateLabTable, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_WANT_GROW, null, new Dimension(150, 50), null, 0, false));
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        tabbedPane1.addTab("All types", panel2);
        panel2.add(allExecutionTable, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_WANT_GROW, null, new Dimension(150, 50), null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return jPanel;
    }
}
