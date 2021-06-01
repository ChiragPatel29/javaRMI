package utilities;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class ManagerLogger {

    public static Logger logger;
    String dirPath = Constants.LOG_DIR;
    String dir = dirPath + "\\Logs\\ManagerLogs";

    public ManagerLogger() {
        File fileDir = new File(dir);
        if (!fileDir.exists()) {
            fileDir.mkdirs();
        }
    }

    public ManagerLogger(String managerID) throws IOException {

        logger = Logger.getLogger(managerID);
        String filePath = dir + "\\" + managerID + ".log";

        FileHandler handler = new FileHandler(filePath, true);
        SimpleFormatter simpleFormatter = new SimpleFormatter();
        handler.setFormatter(simpleFormatter);

        logger.setUseParentHandlers(false);
        logger.addHandler(handler);
        logger.setLevel(Level.INFO);

    }




}
