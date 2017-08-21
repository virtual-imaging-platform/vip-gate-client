package fr.insalyon.creatis.vip.cligatelab.view;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;
import fr.insalyon.creatis.vip.cligatelab.controller.*;

import javax.swing.*;
import java.awt.*;

import static fr.insalyon.creatis.vip.cligatelab.controller.Main.INTERFACESCALE;
import static fr.insalyon.creatis.vip.cligatelab.controller.Main.SCREENHEIGHT;
import static fr.insalyon.creatis.vip.cligatelab.controller.Main.SCREENWIDTH;

/**
 * Created by qifan on 2017/7/3.
 */
public class StartMenu {
    private JFrame actualFrame;
    private JFrame callingFrame;
    private JButton LaunchNewButton;
    private JButton StatusButton;
    private JPanel JPanel1;
    private JTextArea InfoTextArea;
    private JButton apiKeyButton;
    private JLabel spinnerGif;


    public StartMenu() {
        spinnerGif.setVisible(false);
        LaunchNewButton.setPreferredSize(new Dimension((int) (SCREENWIDTH * INTERFACESCALE * 0.2), (int) (SCREENHEIGHT * INTERFACESCALE * 0.1)));
        StatusButton.setPreferredSize(new Dimension((int) (SCREENWIDTH * INTERFACESCALE * 0.2), (int) (SCREENHEIGHT * INTERFACESCALE * 0.1)));
        apiKeyButton.setPreferredSize(new Dimension((int) (SCREENWIDTH * INTERFACESCALE * 0.05), (int) (SCREENHEIGHT * INTERFACESCALE * 0.03)));
        LaunchNewButton.addActionListener(e -> {
            ParserGUI parserGUI = new ParserGUI();
            parserGUI.setCallingFrame(actualFrame);
            actualFrame.setVisible(false);

        });
        StatusButton.addActionListener(new StatusButtonControl(this));

        apiKeyButton.addActionListener(e -> {
            JFrame jFrame2 = new JFrame();
            jFrame2.setSize(400, 200);
            jFrame2.setVisible(true);
            jFrame2.setContentPane(new ApiKeyMenu().getJPanel1());

        });
        actualFrame = new JFrame("LaunchWindow");
        actualFrame.setContentPane(JPanel1);
        actualFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        actualFrame.setBounds((int) (SCREENWIDTH * 0.3), (int) (SCREENHEIGHT * 0.2), (int) (SCREENWIDTH * INTERFACESCALE * 0.4), (int) (SCREENHEIGHT * INTERFACESCALE * 0.6));
        actualFrame.setVisible(true);


        //PrintStream printStream = new PrintStream(new CustomOutputStream(InfoTextArea));
        //System.setOut(printStream);
        //System.setErr(printStream);
    }

    public JFrame getActualFrame() {

        return actualFrame;
    }

    public void setCallingFrame(JFrame callingFrame) {
        this.callingFrame = callingFrame;
    }

    public JLabel getSpinnerGif() {
        return spinnerGif;
    }

    public JTextArea getInfoTextArea() {
        return InfoTextArea;
    }

    public JPanel getJPanel1() {
        return JPanel1;
    }


    public void setActualFrame(JFrame actualFrame) {
        this.actualFrame = actualFrame;
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
        JPanel1 = new JPanel();
        JPanel1.setLayout(new GridLayoutManager(5, 2, new Insets(0, 0, 0, 0), -1, -1));
        LaunchNewButton = new JButton();
        LaunchNewButton.setIcon(new ImageIcon(getClass().getResource("/launchgate.png")));
        LaunchNewButton.setText("Launch New Execution");
        JPanel1.add(LaunchNewButton, new GridConstraints(1, 0, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        StatusButton = new JButton();
        StatusButton.setIcon(new ImageIcon(getClass().getResource("/app-monitor.png")));
        StatusButton.setText("Execution Monitor");
        JPanel1.add(StatusButton, new GridConstraints(2, 0, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        apiKeyButton = new JButton();
        apiKeyButton.setText("manage apikey");
        JPanel1.add(apiKeyButton, new GridConstraints(4, 0, 1, 2, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_VERTICAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        spinnerGif = new JLabel();
        spinnerGif.setIcon(new ImageIcon(getClass().getResource("/Spinner.gif")));
        spinnerGif.setText("Loading");
        JPanel1.add(spinnerGif, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer1 = new Spacer();
        JPanel1.add(spacer1, new GridConstraints(0, 0, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(-1, 100), null, 0, false));
        final Spacer spacer2 = new Spacer();
        JPanel1.add(spacer2, new GridConstraints(3, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return JPanel1;
    }
}