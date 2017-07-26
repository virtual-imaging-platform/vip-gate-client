package fr.insalyon.creatis.vip.cligatelab.controller;

import fr.insalyon.creatis.vip.cligatelab.util.Util;
import fr.insalyon.creatis.vip.cligatelab.view.LaunchWindow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static fr.insalyon.creatis.vip.cligatelab.controller.Main.GATERELEASEFOLDER;
import static fr.insalyon.creatis.vip.cligatelab.controller.Main.PIPELINE;

/**
 * Created by qifan on 2017/6/29.
 * Listener for the button launch in the gatelabgui(main) windows
 */
public class LaunchButtonControl implements ActionListener {

    private LaunchWindow launchWindow;
    Map<String, String> params;

    public LaunchButtonControl(LaunchWindow launchWindow) {
        this.launchWindow = launchWindow;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        launchWindow.getExecutionInfoLabel().setVisible(false);

        //detect if any field is empty
        if (launchWindow.getExecutionNameField().getText().equals("") || launchWindow.getGateInputFiled().getText().equals("") || launchWindow.getNumberOfParticlesField().getText().equals("")) {
            JFrame popup = new JFrame();
            JOptionPane.showMessageDialog(popup,
                    "please fill the missing field");
            launchWindow.getInfoTextArea().setText(launchWindow.getInfoTextArea().getText()+System.lineSeparator()+"please fill the missing field");
        } else {
            setParams();
            execute();
        }

    }

    /**
     * pass the params in the hash map to vip-cli and launch an execution
     */
    private void setParams() {
        //put all the params chosen into a hashmap
        params = new HashMap<>();
        params.put("name", launchWindow.getExecutionNameField().getText());
        switch (launchWindow.getCpuEstimationComboBox().getSelectedIndex()) {
            case 0:
                params.put("CPUestimation", "1");
                break;
            case 1:
                params.put("CPUestimation", "2");
                break;
            case 2:
                params.put("CPUestimation", "3");
                break;
            case 3:
                params.put("CPUestimation", "4");
                break;

        }
        params.put("GateInput", launchWindow.getGateInputFiled().getText());
        String tmp = GATERELEASEFOLDER + launchWindow.getGateReleaseComboBox().getSelectedItem();
        params.put("GateRelease", tmp);
        params.put("NumberOfParticles", launchWindow.getNumberOfParticlesField().getText());
        switch ((String) launchWindow.getParallelizationTypeComboBox().getSelectedItem()) {
            case "dynamic":
                params.put("ParallelizationType", "dyn");
                break;
            case "static":
                params.put("ParallelizationType", "stat");
                break;
        }



    }

    //create a thread for the launching action, in order not to block the gui
    private void execute() {

        new Thread(() -> {
            launchWindow.getLaunchButton().setEnabled(false);
            launchWindow.getSpinnerLabel().setVisible(true);

            String action = "execute";
            Util.callVipCliCommand(
                    action,
                    Arrays.asList(
                            PIPELINE,
                            "--CPUestimation",
                            params.get("CPUestimation"),
                            "--GateInput",
                            params.get("GateInput"),
                            "--GateRelease",
                            params.get("GateRelease"),
                            "--NumberOfParticles",
                            params.get("NumberOfParticles"),
                            "--ParallelizationType",
                            params.get("ParallelizationType"),
                            "-nodir",
                            "--name",
                            params.get("name")

                    ),
                    (Process ps) -> {
                        String stdErr = Util.getStringFromInputStream(ps.getErrorStream());
                        launchWindow.getInfoTextArea().setText(launchWindow.getInfoTextArea().getText()+System.lineSeparator()+stdErr);
                        String stdOut = Util.getStringFromInputStream(ps.getInputStream());
                        launchWindow.getInfoTextArea().setText(launchWindow.getInfoTextArea().getText()+System.lineSeparator()+stdOut);
                        //if stderr is empty, the launching is succeeded
                        if (new String(stdErr).equals("")) {
                            launchWindow.getExecutionInfoLabel().setText("launch complete");
                            launchWindow.getExecutionInfoLabel().setForeground(Color.GREEN);
                            launchWindow.getExecutionInfoLabel().setVisible(true);
                        } else {
                            launchWindow.getExecutionInfoLabel().setText("launch failed");
                            launchWindow.getExecutionInfoLabel().setForeground(Color.RED);
                            launchWindow.getExecutionInfoLabel().setVisible(true);
                        }
                    }
            );

            launchWindow.getSpinnerLabel().setVisible(false);
            launchWindow.getLaunchButton().setEnabled(true);


        }).start();

    }

}
