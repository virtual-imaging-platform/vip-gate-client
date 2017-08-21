package fr.insalyon.creatis.vip.cligatelab.controller;

import fr.insalyon.creatis.vip.cligatelab.util.Util;
import fr.insalyon.creatis.vip.cligatelab.view.LaunchWindow;
import fr.insalyon.creatis.vip.cligatelab.view.RelaunchMonitor;
import fr.insalyon.creatis.vip.cligatelab.view.StatusMonitor;

import java.io.IOException;
import java.util.Arrays;

import static fr.insalyon.creatis.vip.cligatelab.controller.Main.*;

/**
 * Controller for the relaunch action
 * Created by qifan on 2017/7/12.
 */
public class RelaunchControl {
    String executionIdentifier;
    StatusMonitor statusMonitor;

    public RelaunchControl(String executionIdentifier, StatusMonitor statusMonitor) {
        this.statusMonitor = statusMonitor;
        this.executionIdentifier = executionIdentifier;
    }

    /**
     * call vip-cli to get the inputs of an execution of the given pipeline identifier
     */


    public void execute() {


        //create a relaunch monitor window (to indicate its gathering information)
        RelaunchMonitor relaunchMonitor = new RelaunchMonitor();
        String action = "getgateinput";
        //create a thread to gather information
        Runnable r = () -> {
            Util.callVipCliCommand(
                    action,
                    Arrays.asList(
                            executionIdentifier
                    ),
                    (Process ps) -> {
                        String stdOut = Util.getStringFromInputStream(ps.getInputStream());
                        String stdErr = Util.getStringFromInputStream(ps.getErrorStream());
                        relaunchMonitor.getSpinner().setVisible(false);
                        // if failed gathering information (the existence of input files in vip-cli),
                        // the msg will be printed by stdErr
                        if (!(stdErr.equals(""))) {
                            relaunchMonitor.getInfoLabel().setText(stdErr);
                        } else {
                            relaunchMonitor.getActualFrame().setVisible(false);
                            String[] inputs = (stdOut.split("&&&"));
                            LaunchWindow launchWindow = new LaunchWindow();
                            launchWindow.setDefaultGateRelease(inputs[2].substring(inputs[2].lastIndexOf("/") + 1));
                            launchWindow.getCpuEstimationComboBox().setSelectedIndex(Integer.parseInt(inputs[0]) - 1);
                            launchWindow.getGateInputFiled().setText(inputs[1]);
                            launchWindow.getNumberOfParticlesField().setText(inputs[3]);
                            switch (inputs[4]) {
                                case "stat":
                                    launchWindow.getParallelizationTypeComboBox().setSelectedItem("static");
                                    break;
                                case "dyn":
                                    launchWindow.getParallelizationTypeComboBox().setSelectedItem("dynamic");
                                    break;
                            }
                            launchWindow.setCallingFrame(statusMonitor.getActualFrame());
                            statusMonitor.getActualFrame().setVisible(false);
                        }
                    }
            );


            return;

        };

        new Thread(r).start();


    }
}

