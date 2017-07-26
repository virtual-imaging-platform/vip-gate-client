package fr.insalyon.creatis.vip.cligatelab.controller;

import fr.insalyon.creatis.vip.cligatelab.util.Util;
import fr.insalyon.creatis.vip.cligatelab.view.LaunchWindow;
import org.json.JSONArray;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.*;
import java.util.Arrays;

import static fr.insalyon.creatis.vip.cligatelab.controller.Main.*;

/**
 * Created by qifan on 2017/6/29.
 */
public class SetGateReleaseControl {
    private LaunchWindow launchWindow;

    public SetGateReleaseControl(LaunchWindow launchWindow) {
        this.launchWindow = launchWindow;
    }

    public void execute() {
        launchWindow.getGateReleaseComboBox().removeAllItems();
        new Thread(() -> {
            Util.callVipCliCommand(
                    "getgaterelease",
                    null,
                    (Process ps) -> {
                        String stdErr = Util.getStringFromInputStream(ps.getErrorStream());
                        String stdOut = Util.getStringFromInputStream(ps.getInputStream());
                        launchWindow.getInfoTextArea().setText(launchWindow.getInfoTextArea().getText()+stdErr);
                      //  JSONArray jsonArray = new JSONArray(stdOut);
                        if (stdErr.equals("") && !stdOut.equals("")) {
                            launchWindow.getInfoTextArea().setText(launchWindow.getInfoTextArea().getText()+System.lineSeparator()+"Gate Release on VIP gathered.");
                            setLaunchWindowComboBox(stdOut);
                        } else {
                            launchWindow.getInfoTextArea().setText(launchWindow.getInfoTextArea().getText()+System.lineSeparator()+"error gathering gate release, please retry");

                        }

                    }
            );


        }).start();
    }

    private void setLaunchWindowComboBox (String stdOut) {
        String [] gateReleases=stdOut.split(System.lineSeparator());
        for (int i=0;i<gateReleases.length;i++) {
            launchWindow.getGateReleaseComboBox().addItem((gateReleases[i].substring(gateReleases[i].lastIndexOf("/")+1)));
        }
        DefaultComboBoxModel dcbm = (DefaultComboBoxModel) launchWindow.getGateReleaseComboBox().getModel();
        if (launchWindow.getDefaultGateRelease().equals("")) {

        } else if (dcbm.getIndexOf(launchWindow.getDefaultGateRelease()) != -1) {

            launchWindow.getGateReleaseComboBox().setSelectedItem(launchWindow.getDefaultGateRelease());
        }
    }

    /** version adapted to manuel httpconnection, no more used

    private void setLaunchWindowComboBox(JSONArray jsonArray) {
        for (int i = 0; i < jsonArray.length(); i++) {
            String releaseuri = jsonArray.getJSONObject(i).getString("platformURI");
            launchWindow.getGateReleaseComboBox().addItem(releaseuri.substring(releaseuri.lastIndexOf('/') + 1));
        }
        DefaultComboBoxModel dcbm = (DefaultComboBoxModel) launchWindow.getGateReleaseComboBox().getModel();
        if (launchWindow.getDefaultGateRelease().equals("")) {

        } else if (dcbm.getIndexOf(launchWindow.getDefaultGateRelease()) != -1) {

            launchWindow.getGateReleaseComboBox().setSelectedItem(launchWindow.getDefaultGateRelease());
        }
    }
     **/
}
