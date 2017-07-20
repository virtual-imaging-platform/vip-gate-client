package fr.insalyon.creatis.vip.cligatelab.view;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;
import fr.insalyon.creatis.vip.cligatelab.controller.LoadMacButtonControl;
import fr.insalyon.creatis.vip.cligatelab.util.CustomOutputStream;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.PrintStream;

import static fr.insalyon.creatis.vip.cligatelab.controller.Main.*;

/**
 * Created by qifan on 2017/7/3.
 */
public class ParserGUI {
    private JFrame actualFrame;
    private JFrame callingFrame;
    private JPanel JPanel;
    private JTextArea infoArea;
    private JButton loadMacFileButton;
    private JButton launchExecutionButton;
    private JButton returnButton;
    private JLabel spinnerLabel;
    private JLabel loadInfoLabel;
    private JLabel uploadInfoLabel;
    private JProgressBar progressBar1;
    public JProgressBar getProgressBar1() {
        return progressBar1;
    }
    private JFileChooser fc;
    private String NumberOfParticles;
    private String fileDirOnVip;


    public ParserGUI() {
        loadMacFileButton.setPreferredSize(new Dimension((int) (SCREENWIDTH * INTERFACESCALE * 0.2), (int) (SCREENHEIGHT * INTERFACESCALE * 0.1)));
        launchExecutionButton.setPreferredSize(new Dimension((int) (SCREENWIDTH * INTERFACESCALE * 0.2), (int) (SCREENHEIGHT * INTERFACESCALE * 0.1)));
        fc=new JFileChooser() {
            protected JDialog createDialog(Component parent) throws HeadlessException {
                final JDialog dialog = super.createDialog(parent);
                int defaultWidth = (int)(SCREENWIDTH*INTERFACESCALE*0.4);
                int defaultHeight =  (int)(SCREENHEIGHT*INTERFACESCALE*0.4);
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

        FileNameExtensionFilter filter = new FileNameExtensionFilter(
                "mac files", "mac");
        fc.setFileFilter(filter);

        loadMacFileButton.addActionListener(new LoadMacButtonControl(this));
        launchExecutionButton.addActionListener(e -> {
            LaunchWindow launchWindow = new LaunchWindow();
            launchWindow.setGateInputFiled(fileDirOnVip);
            launchWindow.setNumberOfParticlesField(NumberOfParticles);
            launchWindow.setGateInputFiled(fileDirOnVip);
            launchWindow.setNumberOfParticlesField(NumberOfParticles);
            actualFrame.setVisible(false);
            launchWindow.setCallingFrame(actualFrame);

        });
        returnButton.addActionListener(e -> {
            actualFrame.setVisible(false);
            callingFrame.setVisible(true);

        });

        PrintStream printStream = new PrintStream(new CustomOutputStream(infoArea));
        System.setOut(printStream); // TODO redirect on normal streams when the window is closed
        System.setErr(printStream);
        spinnerLabel.setVisible(false);
        loadInfoLabel.setVisible(false);

        actualFrame = new JFrame("LaunchWindow");
        actualFrame.setContentPane(JPanel);
        actualFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        actualFrame.setBounds((int) (SCREENWIDTH * 0.3), (int) (SCREENHEIGHT * 0.2), (int) (SCREENWIDTH * INTERFACESCALE * 0.4), (int) (SCREENHEIGHT * INTERFACESCALE * 0.6));
        actualFrame.setVisible(true);
    }

    public void setFileDirOnVip(String fileDirOnVip) {
        this.fileDirOnVip = fileDirOnVip;
    }

    public void setNumberOfParticles(String numberOfParticles) {
        NumberOfParticles = numberOfParticles;
    }

    public void setFc(JFileChooser fc) {
        this.fc = fc;
    }

    public JFileChooser getFc() {
        return fc;
    }

    public JButton getLaunchExecutionButton() {
        return launchExecutionButton;
    }

    public JPanel getJPanel() {

        return JPanel;
    }

    public JTextArea getInfoArea() {
        return infoArea;
    }

    public JLabel getLoadInfoLabel() {
        return loadInfoLabel;
    }

    public void setCallingFrame(JFrame callingFrame) {
        this.callingFrame = callingFrame;
    }

    public JFrame getCallingFrame() {

        return callingFrame;
    }

    public JLabel getUploadInfoLabel() {
        return uploadInfoLabel;
    }

    public void setActualFrame(JFrame actualFrame) {
        this.actualFrame = actualFrame;
    }

    public JLabel getSpinnerLabel() {
        return spinnerLabel;
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
        JPanel = new JPanel();
        JPanel.setLayout(new GridLayoutManager(9, 1, new Insets(0, 0, 0, 0), -1, -1));
        final JScrollPane scrollPane1 = new JScrollPane();
        JPanel.add(scrollPane1, new GridConstraints(8, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        infoArea = new JTextArea();
        scrollPane1.setViewportView(infoArea);
        loadMacFileButton = new JButton();
        loadMacFileButton.setText("Load .mac file");
        JPanel.add(loadMacFileButton, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label1 = new JLabel();
        label1.setText("LOG");
        JPanel.add(label1, new GridConstraints(7, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        launchExecutionButton = new JButton();
        launchExecutionButton.setEnabled(false);
        launchExecutionButton.setText("launch execution");
        JPanel.add(launchExecutionButton, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer1 = new Spacer();
        JPanel.add(spacer1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(-1, 100), null, 0, false));
        returnButton = new JButton();
        returnButton.setText("back");
        JPanel.add(returnButton, new GridConstraints(6, 0, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        loadInfoLabel = new JLabel();
        loadInfoLabel.setText("");
        JPanel.add(loadInfoLabel, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        uploadInfoLabel = new JLabel();
        uploadInfoLabel.setText("");
        JPanel.add(uploadInfoLabel, new GridConstraints(4, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final javax.swing.JPanel panel1 = new JPanel();
        panel1.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        JPanel.add(panel1, new GridConstraints(5, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        spinnerLabel = new JLabel();
        spinnerLabel.setIcon(new ImageIcon(getClass().getResource("/Spinner.gif")));
        spinnerLabel.setText("Loading");
        panel1.add(spinnerLabel, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return JPanel;
    }
}
