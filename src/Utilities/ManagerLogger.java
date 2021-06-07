package Utilities;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class ManagerLogger {


    String dirPath = Configurations.LOG_DIR;
    String dir = dirPath + "\\Logs\\ManagerLogs";
    String managerID;
    String filePath;

    public ManagerLogger(String managerID) {
        this.managerID = managerID;
        File fileDir = new File(dir);
        if (!fileDir.exists()) {
            fileDir.mkdirs();
        }
        this.filePath = dir + "\\" + managerID + ".log";


    }

    public void addLog(String message) {

        FileWriter fileWriter;
        String timeStamp = new SimpleDateFormat("dd MMM yyyy HH.mm.ss").format(new Date());

        try {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(timeStamp);
            stringBuilder.append("       ");
            stringBuilder.append(message);
            stringBuilder.append("\n");


            fileWriter = new FileWriter(filePath, true);
            fileWriter.write(stringBuilder.toString());
            fileWriter.close();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }
}
