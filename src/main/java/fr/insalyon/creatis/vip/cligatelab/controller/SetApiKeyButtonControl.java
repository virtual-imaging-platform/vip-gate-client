package fr.insalyon.creatis.vip.cligatelab.controller;

import fr.insalyon.creatis.vip.cligatelab.util.Util;
import fr.insalyon.creatis.vip.cligatelab.view.ApiKeyMenu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static fr.insalyon.creatis.vip.cligatelab.controller.Main.APIKEY;
import static fr.insalyon.creatis.vip.cligatelab.controller.Main.EXECUTABLEFILE;

/**
 * Created by qifan on 2017/7/5.
 */
public class SetApiKeyButtonControl implements ActionListener {
    private ApiKeyMenu apiKeyMenu;


    public SetApiKeyButtonControl(ApiKeyMenu apiKeyMenu) {
        this.apiKeyMenu = apiKeyMenu;

    }

    public void actionPerformed(ActionEvent e) {
        Pattern p = Pattern.compile("\\s*|\t|\r|\n");
        Matcher m = p.matcher(apiKeyMenu.getApiKeyArea().getText());
        String apikey = m.replaceAll("");
        Util.callVipCliCommand(
                "setapikey",
                Arrays.asList(
                        apikey
                ),
                (Process ps) -> {
                   return;
                }
        );


    }
}
