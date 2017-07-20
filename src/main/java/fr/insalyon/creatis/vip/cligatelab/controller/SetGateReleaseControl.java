package fr.insalyon.creatis.vip.cligatelab.controller;

import fr.insalyon.creatis.vip.cligatelab.view.LaunchWindow;
import org.json.JSONArray;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.*;

import static fr.insalyon.creatis.vip.cligatelab.controller.Main.*;
import static fr.insalyon.creatis.vip.cligatelab.controller.Main.INTERFACESCALE;
import static fr.insalyon.creatis.vip.cligatelab.controller.Main.SCREENHEIGHT;

/**
 * Created by qifan on 2017/6/29.
 */
public class SetGateReleaseControl {
    private LaunchWindow launchWindow;

    public SetGateReleaseControl(LaunchWindow launchWindow) {
        this.launchWindow = launchWindow;
    }

    public void execute() {
        new Thread(() -> {
            launchWindow.getGateReleaseComboBox().removeAllItems();


            JSONArray jsonArray = null;//.substring(1,sb.toString().length()-1));
            try {
                jsonArray = new JSONArray(sendRequest());
                System.out.println("Gate Release on VIP obtained.");
                setLaunchWindowComboBox(jsonArray);
            } catch (IOException e) {
                e.printStackTrace();
            }




        }).start();
    }

    private String sendRequest() throws IOException {
        String path = BASEPATH + "/path/directory?uri=vip://" + GATERELEASESPATH;
        HttpURLConnection httpConnection = (HttpURLConnection) new URL(path).openConnection();
        httpConnection.setRequestMethod("GET");
        httpConnection.setRequestProperty("apiKey", APIKEY);
        InputStream response = null;
        response = httpConnection.getInputStream();
        BufferedReader br = null;
        StringBuilder sb = new StringBuilder();
        String line;
        br = new BufferedReader(new InputStreamReader(response));
        while ((line = br.readLine()) != null) {
            sb.append(line);
        }
        br.close();
        return sb.toString();

    }
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
}
