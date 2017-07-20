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
    String zipFileName;
    String targetDirName;
    String zipFilePath;
    String fileDirOnVip;
    File chosenFile;

    public LoadMacButtonControl(ParserGUI parserGUI) {
        this.parserGUI = parserGUI;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        parserGUI.getUploadInfoLabel().setVisible(false);
        parserGUI.getLoadInfoLabel().setVisible(false);
        parserGUI.getLaunchExecutionButton().setEnabled(false);
        parserGUI.setFileDirOnVip(null);
        parserGUI.getInfoArea().setText(null);
        parserGUI.getFc().showDialog(new JLabel(), "Choose Mac File");
        chosenFile = parserGUI.getFc().getSelectedFile();
        //if nothing is chosen, do nothing
        if (chosenFile != null) {
            //else use the macropaser to parser the mac chosenFile
            setZipAndUploadAction(chosenFile);
            try {
                parseMac();
                zipFile();
                executeUpload();
            } catch (IOException e1) {
                e1.printStackTrace();
                System.err.println("upload failed, please retry");
                parserGUI.getUploadInfoLabel().setText(parserGUI.getUploadInfoLabel().getText() + "\r\nupload file failed, check log for more info");
                parserGUI.getUploadInfoLabel().setVisible(true);
                parserGUI.getUploadInfoLabel().setForeground(Color.RED);
            } catch (ParserException pe) {
                parserGUI.getLoadInfoLabel().setText(parserGUI.getLoadInfoLabel().getText() + "Load mac file failed, check log for more info");
                parserGUI.getLoadInfoLabel().setVisible(true);
                parserGUI.getLoadInfoLabel().setForeground(Color.RED);
                System.err.println(pe.getMessage());
            }
        }

    }

    private void executeUpload() {

        new Thread(() -> {
            parserGUI.getSpinnerLabel().setVisible(true);


            try {
                if (uploadFile()) {
                    parserGUI.getLaunchExecutionButton().setEnabled(true);
                    parserGUI.setFileDirOnVip(fileDirOnVip);
                    parserGUI.getUploadInfoLabel().setText(parserGUI.getUploadInfoLabel().getText() + "\r\nupload file complete");
                    parserGUI.getUploadInfoLabel().setVisible(true);
                    parserGUI.getUploadInfoLabel().setForeground(Color.GREEN);
                } else {
                    System.err.println("upload failed, please retry");
                    parserGUI.getUploadInfoLabel().setText(parserGUI.getUploadInfoLabel().getText() + "\r\nupload file failed, check log for more info");
                    parserGUI.getUploadInfoLabel().setVisible(true);
                    parserGUI.getUploadInfoLabel().setForeground(Color.RED);
                }
            } catch (IOException e1) {
                e1.printStackTrace();
            }

            parserGUI.getSpinnerLabel().setVisible(false);
        }).start();
    }


    private void parseMac() throws ParserException, IOException {
        MacroParser macroParser = new MacroParser(chosenFile);
        macroParser.parseMacroFiles();
        //give back to paserGUI the infos obtained from the mac chosenFile
        parserGUI.setNumberOfParticles(macroParser.getNbParticles());
        parserGUI.getLoadInfoLabel().setText(parserGUI.getLoadInfoLabel().getText() + "Load mac file complete");
        parserGUI.getLoadInfoLabel().setVisible(true);
        parserGUI.getLoadInfoLabel().setForeground(Color.GREEN);

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
        System.out.println("folder is " + folder);
        System.out.println("zip file name is " + zipFileName);
        System.out.println("zip file path is " + zipFilePath);
        System.out.println("target dir path is" + targetDirName);
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
                            System.err.println(e);
                        }
                    });
            System.out.println("zip file created: " + zipFilePath);
        }
    }

    /**
     * upload chosenFile, can be replaced by the new version of api in the future
     *
     * @return
     * @throws IOException
     */
    private boolean uploadFile() throws IOException {
        URL checkDirInputsExist = new URL(BASEPATH + "/path/exists?uri=vip://vip.creatis.insa-lyon.fr" + DIRINPUTSONVIP);
        HttpsURLConnection checkDirBaseConn = (HttpsURLConnection) checkDirInputsExist.openConnection();
        checkDirBaseConn.setRequestMethod("GET");
        checkDirBaseConn.setRequestProperty("apiKey", APIKEY);
        String msg = Util.getStringFromInputStream(checkDirBaseConn.getInputStream());
        System.out.println(msg);
        System.out.println(checkDirBaseConn.getResponseCode());
        System.out.println(checkDirBaseConn.getResponseMessage());
        checkDirBaseConn.disconnect();

        if (msg.equals("false")) {
            String dirBaseToCreate = "/vip/Home/GateInputs";
            URL urlDirBase = new URL(BASEPATH + "/path/directory?uri=vip://vip.creatis.insa-lyon.fr" + dirBaseToCreate);
            HttpsURLConnection CreateDirBaseConn = (HttpsURLConnection) urlDirBase.openConnection();
            CreateDirBaseConn.setRequestMethod("POST");
            CreateDirBaseConn.setRequestProperty("apiKey", APIKEY);
            System.out.println(CreateDirBaseConn.getResponseCode());
            System.out.println(CreateDirBaseConn.getResponseMessage());
            CreateDirBaseConn.disconnect();
        }

        // create repository on vip to put data
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd_HHmmss");
        String dirToCreate = "/vip/Home/GateInputs/" + df.format(new Date());
        URL urlCreateDir = new URL(BASEPATH + "/path/directory?uri=vip://vip.creatis.insa-lyon.fr" + dirToCreate);
        HttpsURLConnection createDirSpecificConn = (HttpsURLConnection) urlCreateDir.openConnection();
        createDirSpecificConn.setRequestMethod("POST");
        createDirSpecificConn.setRequestProperty("apiKey", APIKEY);
        int responseCode1 = createDirSpecificConn.getResponseCode();
        System.out.println(createDirSpecificConn.getResponseCode());
        System.out.println(createDirSpecificConn.getResponseMessage());
        createDirSpecificConn.disconnect();

        //upload data
        URL url = new URL(BASEPATH + "/path/content");
        HttpsURLConnection UploadConn = (HttpsURLConnection) url.openConnection();
        UploadConn.setDoOutput(true);
        UploadConn.setRequestMethod("PUT");
        UploadConn.setRequestProperty("apiKey", APIKEY);
        UploadConn.setRequestProperty("Content-Type", "application/json");
        OutputStream os = UploadConn.getOutputStream();
        String encodedFile = encodeFileToBase64Binary(new File(zipFilePath));
        fileDirOnVip = dirToCreate + "/" + zipFileName;
        String jsonString = new JSONObject()
                .put("uri", "vip://vip.creatis.insa-lyon.fr" + fileDirOnVip)
                .put("pathContent", encodedFile).toString();
        os.write(jsonString.getBytes());
        os.flush();
        os.close();
        int responseCode2 = UploadConn.getResponseCode();
        System.out.println(UploadConn.getResponseCode());
        System.out.println(UploadConn.getResponseMessage());
        UploadConn.disconnect();

        return (responseCode1 == 200 && responseCode2 == 200);
    }

    private String encodeFileToBase64Binary(File file) throws IOException {

        byte[] bytes = loadFile(file);
        String encoded = Base64.getEncoder().encodeToString(bytes);
        return encoded;
    }

    private static byte[] loadFile(File file) throws IOException {
        InputStream is = new FileInputStream(file);
        long length = file.length();
        if (length > Integer.MAX_VALUE) {
            // File is too large
        }
        byte[] bytes = new byte[(int) length];
        int offset = 0;
        int numRead = 0;
        while (offset < bytes.length
                && (numRead = is.read(bytes, offset, bytes.length - offset)) >= 0) {
            offset += numRead;
        }
        if (offset < bytes.length) {
            throw new IOException("Could not completely read chosenFile " + file.getName());
        }
        is.close();
        return bytes;

    }

    private void createFileChooser() {

    }
}
