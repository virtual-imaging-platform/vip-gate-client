package fr.insalyon.creatis.vip.cligatelab.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import static fr.insalyon.creatis.vip.cligatelab.controller.Main.EXECUTABLEFILE;

/**
 * Created by qifan on 2017/7/20.
 */
public class Util {
    public static String getStringFromInputStream(InputStream is) throws IOException {
/**
 byte b[] = new byte[is.available()];
 is.read(b, 0, b.length);
 is.close();
 return new String(b);
 /**
 Pattern p = Pattern.compile("\\s*|\t|\r|\n");
 Matcher m = p.matcher(new String(b));

 return m.replaceAll("");
 **/
        BufferedReader br = null;
        StringBuilder sb = new StringBuilder();
        String line;
        br = new BufferedReader(new InputStreamReader(is));
        while ((line = br.readLine()) != null) {
            sb.append(line);
        }
        return new String(sb);
    }

    public interface CommandCallBack {
        void run(Process ps) throws Exception;
    }


    public static void callVipCliCommand(String action, List<String> commandParams, CommandCallBack callback) {
        try {
            String[] execCommand;
            if (commandParams==null) {
                execCommand=new String[4];
            } else {
                execCommand = new String[commandParams.size() + 4];
                String[]tmp=(String[])commandParams.toArray();
                System.arraycopy( tmp,0,execCommand,4,tmp.length);
            }
            execCommand[0] = "java";
            execCommand[1] = "-jar";
            execCommand[2] = EXECUTABLEFILE;
            execCommand[3] = action;

            Process ps = Runtime.getRuntime().exec(execCommand);
            ps.waitFor();
            callback.run(ps);
        } catch (Exception e) {
            throw new RuntimeException("Error calling vip command", e);
        }

    }
}
