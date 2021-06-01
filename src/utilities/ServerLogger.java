package utilities;

import server.Server;

import java.io.File;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class ServerLogger {

    public static Logger logger;
    String dirPath = Constants.LOG_DIR;
    String dir = dirPath + "\\Logs\\ServerLogs";

    public ServerLogger() {
        File fileDir = new File(dir);
        if (!fileDir.exists()) {
            fileDir.mkdirs();
        }
    }

    public ServerLogger(String serverName) throws IOException {

        logger = Logger.getLogger(serverName);
        String filePath = dir + "\\" + serverName + ".log";

        FileHandler handler = new FileHandler(filePath, true);
        SimpleFormatter simpleFormatter = new SimpleFormatter();
        handler.setFormatter(simpleFormatter);

        logger.setUseParentHandlers(false);
        logger.addHandler(handler);
        logger.setLevel(Level.INFO);

    }

    public static void main(String[] args) {
        ServerLogger serverLogger = null;
        try {
            serverLogger = new ServerLogger("MTL");
        } catch (IOException e) {
            e.printStackTrace();
        }
        serverLogger.logger.log(Level.INFO, "First Log from Server");

    }

}

