package fr.insalyon.creatis.vip.cligatelab.controller;

import fr.insalyon.creatis.vip.cligatelab.util.Util;
import fr.insalyon.creatis.vip.cligatelab.view.StartMenu;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by qifan on 2017/6/27.
 */
public class Main {
    public final static String EXECUTABLEFILE = "vip-cli.jar";  //"/Users/qifan/GitHub/vip_client/target/vip-cli-1.0-jar-with-dependencies.jar";
    public final static String PIPELINE = "GateLab/0.4.5";
    public final static String BASEPATH = "https://vip.creatis.insa-lyon.fr/rest";
    public final static String GATERELEASESPATH = "vip.creatis.insa-lyon.fr/vip/GateLab%20(group)/releases/";
    public final static String TRUSTSTOREFILE = "./truststore";
    public final static String DIRINPUTSONVIP = "/vip/Home/GateInputs";
    public final static GraphicsDevice GRAPHICDEVICE = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
    public final static double SCREENWIDTH = GRAPHICDEVICE.getDisplayMode().getWidth();
    public final static double SCREENHEIGHT = GRAPHICDEVICE.getDisplayMode().getHeight();
    public final static int FONTSIZE = (int) SCREENHEIGHT / 70;
    public final static double INTERFACESCALE = 1;
    public final static String DEFAULTGATERELEASE = "";

    public static String APIKEY;

    public static void main(String args[]) {
        new Main().run();
    }

    public void run() {

        setSslConnection();

        getApiKey();

        setLookAndFeel();
        //create the windows Start Menu
        new StartMenu();
        detectAndSetApikey();


    }

    public void getApiKey() {
        //get api key from vip-cli.jar
        String action = "getapikey";
        // TODO refactor all vip cli calls
        Util.callVipCliCommand(
                action,
                null,
                (Process process) -> {

                    APIKEY = Util.getStringFromInputStream(process.getInputStream());
                    Pattern p = Pattern.compile("\\s*|\t|\r|\n");
                    Matcher m = p.matcher(APIKEY);
                    APIKEY = m.replaceAll("");
                }
        );

    }

    private void setLookAndFeel() {
        //set look and fell (for font size)
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
            ex.printStackTrace();
        }
        setDefaultSize();
    }

    private static void setDefaultSize() {

        Set<Object> keySet = UIManager.getLookAndFeelDefaults().keySet();
        Object[] keys = keySet.toArray(new Object[keySet.size()]);

        for (Object key : keys) {

            if (key != null && key.toString().toLowerCase().contains("font")) {
                Font font = UIManager.getDefaults().getFont(key);
                if (font != null) {
                    font = font.deriveFont((float) FONTSIZE);
                    UIManager.put(key, font);
                }

            }

        }

    }

    private void setSslConnection() {
        //set trust chosenFile
        System.setProperty("javax.net.ssl.keyStore", TRUSTSTOREFILE);
        System.setProperty("javax.net.ssl.trustStore", TRUSTSTOREFILE);
        System.setProperty("javax.net.ssl.keyStorePassword", "creatis");
    }

    private void detectAndSetApikey() {
        //detect if apikey is empty, ask user to set apikey
        if (APIKEY.equals("")) {
            JFrame pop = new JFrame();
            String apikey = JOptionPane.showInputDialog(pop, "please enter the api key");
            String action = "setapikey";

            Pattern p = Pattern.compile("\\s*|\t|\r|\n");
            Matcher m = p.matcher(apikey);
            apikey = m.replaceAll("");
            Util.callVipCliCommand(
                    action,
                    Arrays.asList(
                            apikey
                    ),
                    (Process process) -> {
                        return;
                    }
            );
            APIKEY = apikey;
        }
    }

}
