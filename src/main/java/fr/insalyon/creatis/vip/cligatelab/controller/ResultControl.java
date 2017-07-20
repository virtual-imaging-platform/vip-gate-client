package fr.insalyon.creatis.vip.cligatelab.controller;

import fr.insalyon.creatis.vip.cligatelab.util.Util;
import fr.insalyon.creatis.vip.cligatelab.view.DownloadMonitor;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

import static fr.insalyon.creatis.vip.cligatelab.controller.Main.*;

/**
 * Created by qifan on 2017/6/30.
 * controller for the download result action
 */
public class ResultControl implements ActionListener{

    String executionIdentifier;
    String downloadDir;
    String sep=java.io.File.separator;

    public ResultControl(String executionIdentifier, String downloadDir) {
        this.executionIdentifier = executionIdentifier;
        this.downloadDir = downloadDir;

    }



    public void execute() {



        DownloadMonitor downloadMonitor = new DownloadMonitor();

        File file = new File(downloadDir + sep + new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date()));
        if (!file.exists()) {
            file.mkdir();
        }

        Runnable r = () -> {

            Util.callVipCliCommand(
                    "result",
                    Arrays.asList(
                            executionIdentifier,
                            downloadDir + sep + new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date()),
                            "-gate"
                    ),
                    (Process ps) -> {
                        String stdErr= Util.getStringFromInputStream(ps.getErrorStream());
                        String stdOut= Util.getStringFromInputStream(ps.getInputStream());
                        downloadMonitor.getSpinner().setVisible(false);
                        if (stdErr.equals("")) {

                            downloadMonitor.getInfoLabel().setText("download complete");
                        } else {
                            downloadMonitor.getInfoLabel().setText("download error, check log");
                        }
                    }
            );


            return;


        };

        new Thread(r).start();


    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
