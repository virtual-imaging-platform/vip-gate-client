package fr.insalyon.creatis.vip.cligatelab.view;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;
import fr.insalyon.creatis.vip.cligatelab.controller.*;
import fr.insalyon.creatis.vip.cligatelab.util.CustomOutputStream;
import org.json.JSONArray;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

import static fr.insalyon.creatis.vip.cligatelab.controller.Main.*;

/**
 * Created by qifan on 2017/6/28.
 */
public class LaunchWindow {
    private JFrame actualFrame;
    private JFrame callingFrame;
    private JPanel panel1;
    private JButton launchButton;
    private JTextField GateInputFiled;
    private JTextField NumberOfParticlesField;
    private JTextArea infoTextArea;
    private JComboBox parallelizationTypeComboBox;
    private JComboBox cpuEstimationComboBox;
    private JComboBox gateReleaseComboBox;
    private String defaultGateRelease = DEFAULTGATERELEASE;
    private JLabel spinnerLabel;
    private JTextField executionNameField;
    private JLabel executionInfoLabel;
    private JButton backButton;
    private JFileChooser fc;

    public LaunchWindow() {

        //PrintStream printStream = new PrintStream(new CustomOutputStream(infoTextArea));
        //System.setOut(printStream);
        //System.setErr(printStream);
        infoTextArea.setText(infoTextArea.getText() + "Getting gate release from vip...");
        launchButton.addActionListener(new LaunchButtonControl(this));


        infoTextArea.setLineWrap(true);
        infoTextArea.setWrapStyleWord(true);
        parallelizationTypeComboBox.addItem("static");
        parallelizationTypeComboBox.addItem(("dynamic"));

        cpuEstimationComboBox.addItem("a few minutes");
        cpuEstimationComboBox.addItem("a few hours");
        cpuEstimationComboBox.addItem("a few days");
        cpuEstimationComboBox.addItem("more than a few days");


        spinnerLabel.setVisible(false);
        // new JLabel(new ImageIcon("default.gif"));


        executionNameField.setPreferredSize(new Dimension((int) (SCREENWIDTH * INTERFACESCALE * 0.3), (int) (SCREENHEIGHT * INTERFACESCALE * 0.03)));
        GateInputFiled.setPreferredSize(new Dimension((int) (SCREENWIDTH * INTERFACESCALE * 0.3), (int) (SCREENHEIGHT * INTERFACESCALE * 0.03)));
        NumberOfParticlesField.setPreferredSize(new Dimension((int) (SCREENWIDTH * INTERFACESCALE * 0.3), (int) (SCREENHEIGHT * INTERFACESCALE * 0.03)));
        gateReleaseComboBox.setPreferredSize(new Dimension((int) (SCREENWIDTH * INTERFACESCALE * 0.3), (int) (SCREENHEIGHT * INTERFACESCALE * 0.03)));
        cpuEstimationComboBox.setPreferredSize(new Dimension((int) (SCREENWIDTH * INTERFACESCALE * 0.3), (int) (SCREENHEIGHT * INTERFACESCALE * 0.03)));
        parallelizationTypeComboBox.setPreferredSize(new Dimension((int) (SCREENWIDTH * INTERFACESCALE * 0.3), (int) (SCREENHEIGHT * INTERFACESCALE * 0.03)));

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actualFrame.setVisible(false);
                callingFrame.setVisible(true);
            }
        });
        actualFrame = new JFrame("LaunchWindow");
        actualFrame.setContentPane(panel1);
        actualFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        actualFrame.setBounds((int) (SCREENWIDTH * 0.3), (int) (SCREENHEIGHT * 0.2), (int) (SCREENWIDTH * INTERFACESCALE * 0.4), (int) (SCREENHEIGHT * INTERFACESCALE * 0.6));
        actualFrame.setVisible(true);
        /**
         new Thread(() -> {
         setGateReleaseComboBox();
         }).start();
         **/
        SetGateReleaseControl setGateReleaseControl = new SetGateReleaseControl(this);
        setGateReleaseControl.execute();

    }

    public JButton getLaunchButton() {
        return launchButton;
    }


    public JLabel getExecutionInfoLabel() {
        return executionInfoLabel;
    }

    public void setDefaultGateRelease(String defaultGateRelease) {
        this.defaultGateRelease = defaultGateRelease;
        DefaultComboBoxModel dcbm = (DefaultComboBoxModel) gateReleaseComboBox.getModel();


        if (dcbm.getIndexOf(defaultGateRelease) != -1) {

            gateReleaseComboBox.setSelectedItem(defaultGateRelease);
        }
    }


    public void setCallingFrame(JFrame callingFrame) {
        this.callingFrame = callingFrame;
    }

    public void setActualFrame(JFrame actualFrame) {
        this.actualFrame = actualFrame;
    }

    public JFrame getActualFrame() {

        return actualFrame;
    }

    public void setFc(JFileChooser fc) {
        this.fc = fc;
    }

    public JComboBox getParallelizationTypeComboBox() {
        return parallelizationTypeComboBox;
    }

    public JComboBox getCpuEstimationComboBox() {
        return cpuEstimationComboBox;
    }

    public JComboBox getGateReleaseComboBox() {
        return gateReleaseComboBox;
    }

    public void setGateInputFiled(String gateInputFiled) {
        GateInputFiled.setText(gateInputFiled);
    }

    public void setNumberOfParticlesField(String numberOfParticlesField) {
        NumberOfParticlesField.setText(numberOfParticlesField);
    }


    public JLabel getSpinnerLabel() {
        return spinnerLabel;
    }

    public JTextField getExecutionNameField() {
        return executionNameField;
    }


    public String getDefaultGateRelease() {
        return defaultGateRelease;
    }

    public JTextField getGateInputFiled() {
        return GateInputFiled;
    }


    public JTextField getNumberOfParticlesField() {
        return NumberOfParticlesField;
    }

    public JTextArea getInfoTextArea() {
        return infoTextArea;
    }


    public JPanel getPanel1() {

        return panel1;
    }

    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        panel1 = new JPanel();
        panel1.setLayout(new GridLayoutManager(15, 1, new Insets(0, 0, 0, 0), -1, -1));
        panel1.putClientProperty("html.disable", Boolean.FALSE);
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        panel1.add(panel2, new GridConstraints(14, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JPanel panel3 = new JPanel();
        panel3.setLayout(new GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), -1, -1));
        panel1.add(panel3, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JLabel label1 = new JLabel();
        label1.setText("CPUestimation");
        panel3.add(label1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        cpuEstimationComboBox = new JComboBox();
        panel3.add(cpuEstimationComboBox, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel4 = new JPanel();
        panel4.setLayout(new GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), -1, -1));
        panel1.add(panel4, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JLabel label2 = new JLabel();
        label2.setText("GateInput");
        panel4.add(label2, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        GateInputFiled = new JTextField();
        GateInputFiled.setEditable(false);
        panel4.add(GateInputFiled, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel5 = new JPanel();
        panel5.setLayout(new GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), -1, -1));
        panel1.add(panel5, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JLabel label3 = new JLabel();
        label3.setText("GateRelease");
        panel5.add(label3, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        gateReleaseComboBox = new JComboBox();
        panel5.add(gateReleaseComboBox, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel6 = new JPanel();
        panel6.setLayout(new GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), -1, -1));
        panel1.add(panel6, new GridConstraints(4, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JLabel label4 = new JLabel();
        label4.setText("NumberOfParticles");
        panel6.add(label4, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        NumberOfParticlesField = new JTextField();
        NumberOfParticlesField.setEditable(false);
        panel6.add(NumberOfParticlesField, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel7 = new JPanel();
        panel7.setLayout(new GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), -1, -1));
        panel1.add(panel7, new GridConstraints(5, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JLabel label5 = new JLabel();
        label5.setText("ParallelizationType");
        panel7.add(label5, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        parallelizationTypeComboBox = new JComboBox();
        panel7.add(parallelizationTypeComboBox, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JScrollPane scrollPane1 = new JScrollPane();
        panel1.add(scrollPane1, new GridConstraints(13, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        infoTextArea = new JTextArea();
        infoTextArea.setLineWrap(false);
        infoTextArea.setText("");
        infoTextArea.setWrapStyleWord(false);
        scrollPane1.setViewportView(infoTextArea);
        final JLabel label6 = new JLabel();
        label6.setText("LOG");
        panel1.add(label6, new GridConstraints(12, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        spinnerLabel = new JLabel();
        spinnerLabel.setIcon(new ImageIcon(getClass().getResource("/Spinner.gif")));
        spinnerLabel.setText("Loading");
        panel1.add(spinnerLabel, new GridConstraints(7, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel8 = new JPanel();
        panel8.setLayout(new GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), -1, -1));
        panel1.add(panel8, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JLabel label7 = new JLabel();
        label7.setText("Execution Name");
        panel8.add(label7, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        executionNameField = new JTextField();
        panel8.add(executionNameField, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        executionInfoLabel = new JLabel();
        executionInfoLabel.setText("");
        panel1.add(executionInfoLabel, new GridConstraints(6, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        launchButton = new JButton();
        launchButton.setText("Launch");
        panel1.add(launchButton, new GridConstraints(8, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, new Dimension(50, 50), new Dimension(50, 50), null, 0, false));
        backButton = new JButton();
        backButton.setText("back");
        panel1.add(backButton, new GridConstraints(9, 0, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer1 = new Spacer();
        panel1.add(spacer1, new GridConstraints(10, 0, 2, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return panel1;
    }
}
