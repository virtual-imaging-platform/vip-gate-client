package fr.insalyon.creatis.vip.cligatelab.controller;

import fr.insalyon.creatis.vip.cligatelab.util.MacroParser;
import fr.insalyon.creatis.vip.cligatelab.util.ParserException;
import fr.insalyon.creatis.vip.cligatelab.util.Util;
import fr.insalyon.creatis.vip.cligatelab.view.ParserGUI;
import org.json.JSONObject;

import javax.net.ssl.HttpsURLConnection;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Base64;
import java.util.Date;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import static fr.insalyon.creatis.vip.cligatelab.controller.Main.*;

/**
 * Created by qifan on 2017/6/29.
 * listener for the button load .mac chosenFile in the paser window
 */
public class LoadMacButtonControl implements ActionListener {
    ParserGUI parserGUI;
    String sep = java.io.File.separator;
    File folder;
    String zipFileName;   //abc.zip
    String targetDirName; // Document/gateinputs/100
    String zipFilePath; //Document/gateinputs/100/abc.zip
    String fileDirVip;
    File chosenFile;
    SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd_HHmmss");

    public LoadMacButtonControl(ParserGUI parserGUI) {
        this.parserGUI = parserGUI;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        parserGUI.getUploadInfoLabel().setVisible(false);
        parserGUI.getLoadInfoLabel().setVisible(false);
        parserGUI.getUploadInfoLabel().setText("");
        parserGUI.getLoadInfoLabel().setText("");
        parserGUI.getLaunchExecutionButton().setEnabled(false);
        parserGUI.setFilePathVip(null);
        parserGUI.getInfoArea().setText(null);
        parserGUI.getFc().showDialog(new JLabel(), "Choose Mac File");
        chosenFile = parserGUI.getFc().getSelectedFile();
        //if nothing is chosen, do nothing
        if (chosenFile != null) {
            //else use the macropaser to parser the mac chosenFile
            setZipAndUploadAction(chosenFile);
            try {
                if (parseMac()) {
                    zipFile();
                    executeUpload();
                }
            } catch (IOException e1) {
                e1.printStackTrace();
                parserGUI.getInfoArea().setText(parserGUI.getInfoArea().getText() + System.lineSeparator() + "upload failed, please retry");
                parserGUI.getUploadInfoLabel().setText(parserGUI.getUploadInfoLabel().getText() + "\r\nupload file failed, check log for more info");
                parserGUI.getUploadInfoLabel().setVisible(true);
                parserGUI.getUploadInfoLabel().setForeground(Color.RED);
            }
        }

    }

    private void executeUpload() {

        new Thread(() -> {
            parserGUI.getSpinnerLabel().setVisible(true);

            fileDirVip = DIRINPUTPATH + "/" + df.format(new Date());

            Util.callVipCliCommand(
                    "upload",
                    Arrays.asList(
                            zipFilePath, //zipFilePath= ~/Document/xxx/123/abc.zip
                            fileDirVip //filedirvip = /GateInputs/20991203_123124
                    ),
                    (Process ps) -> {
                        String stdErr = Util.getStringFromInputStream(ps.getErrorStream());
                        parserGUI.getInfoArea().setText(parserGUI.getInfoArea().getText() + System.lineSeparator() + stdErr);
                        String stdOut = Util.getStringFromInputStream(ps.getInputStream());
                        parserGUI.getInfoArea().setText(parserGUI.getInfoArea().getText() + System.lineSeparator() + stdOut);
                        if (stdErr.equals("")) {
                            parserGUI.getLaunchExecutionButton().setEnabled(true);
                            parserGUI.setFilePathVip("/vip/Home" + fileDirVip + "/" + zipFileName); // /vip/home/GateInputs/20171211_121412/abc.zip
                            parserGUI.getUploadInfoLabel().setText(parserGUI.getUploadInfoLabel().getText() + "\r\nupload file complete");
                            parserGUI.getUploadInfoLabel().setVisible(true);
                            parserGUI.getUploadInfoLabel().setForeground(Color.GREEN);

                        } else {
                            parserGUI.getUploadInfoLabel().setText(parserGUI.getUploadInfoLabel().getText() + "\r\nupload file failed, check log for more info");
                            parserGUI.getUploadInfoLabel().setVisible(true);
                            parserGUI.getUploadInfoLabel().setForeground(Color.RED);

                        }
                        parserGUI.getSpinnerLabel().setVisible(false);
                        //the file is deleted after being uploaded
                        Files.delete(Paths.get(zipFilePath));
                        return;
                    }

            );


        }).start();
    }


    private boolean parseMac() throws IOException {
        try {
            MacroParser macroParser = new MacroParser(chosenFile);
            macroParser.parseMacroFiles();
            //give back to paserGUI the infos obtained from the mac chosenFile
            parserGUI.setNumberOfParticles(macroParser.getNbParticles());
            parserGUI.getLoadInfoLabel().setText(parserGUI.getLoadInfoLabel().getText() + "Load mac file complete");
            parserGUI.getLoadInfoLabel().setVisible(true);
            parserGUI.getLoadInfoLabel().setForeground(Color.GREEN);
            return true;
        } catch (ParserException pe) {
            parserGUI.getLoadInfoLabel().setText(parserGUI.getLoadInfoLabel().getText() + "Load mac file failed, check log for more info");
            parserGUI.getLoadInfoLabel().setVisible(true);
            parserGUI.getLoadInfoLabel().setForeground(Color.RED);
            parserGUI.getInfoArea().setText(parserGUI.getInfoArea().getText() + System.lineSeparator() + pe.getMessage());
            return false;
        }

    }

    /**
     * determine path to put zipped chosenFile
     *
     * @param macFile
     */
    private void setZipAndUploadAction(File macFile) {
        folder = macFile.getParentFile().getParentFile();
        zipFileName = folder.getAbsolutePath().substring(folder.getAbsolutePath().lastIndexOf(sep) + 1) + ".zip";
        this.zipFilePath = folder.getParent() + sep + zipFileName;
        this.targetDirName = folder.getAbsolutePath();
        parserGUI.getInfoArea().setText(parserGUI.getInfoArea().getText() + System.lineSeparator() + "folder is " + folder);
        parserGUI.getInfoArea().setText(parserGUI.getInfoArea().getText() + System.lineSeparator() + "zip file name is " + zipFileName);
        parserGUI.getInfoArea().setText(parserGUI.getInfoArea().getText() + System.lineSeparator() + "zip file path is " + zipFilePath);
        parserGUI.getInfoArea().setText(parserGUI.getInfoArea().getText() + System.lineSeparator() + "target dir path is" + targetDirName);
    }

    /**
     * zip the chosenFile
     *
     * @throws IOException
     */
    private void zipFile() throws IOException {
        File file = new File(zipFilePath);
        file.delete();
        file.createNewFile();
        try (ZipOutputStream zs = new ZipOutputStream(Files.newOutputStream(Paths.get(zipFilePath)))) {
            Path pp = Paths.get(targetDirName);
            Files.walk(pp)
                    .filter(path -> !Files.isDirectory(path))
                    .forEach(path -> {
                        ZipEntry zipEntry = new ZipEntry(pp.relativize(path).toString());
                        try {
                            zs.putNextEntry(zipEntry);
                            zs.write(Files.readAllBytes(path));
                            zs.closeEntry();
                        } catch (Exception e) {
                            parserGUI.getInfoArea().setText(parserGUI.getInfoArea().getText() + e);
                        }
                    });
            parserGUI.getInfoArea().setText(parserGUI.getInfoArea().getText() + System.lineSeparator() + "zip file created: " + zipFilePath);
        }
    }


}
