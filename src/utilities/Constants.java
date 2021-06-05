package utilities;

public class Constants {

    public static final String LOG_DIR = System.getProperty("user.dir");

    public static final int RMI_REGISTRY_PORT = 2965;

    public static final String[] LOCATION_LIST = new String[]{"MTL", "LVL", "DDO"};

    public static final String MTL_SERVER_IP_ADDRESS = "localhost";
    public static final String LVL_SERVER_IP_ADDRESS = "localhost";
    public static final String DDO_SERVER_IP_ADDRESS = "localhost";

    public static final int UDP_PORT_MTL = 4000;
    public static final int UDP_PORT_LVL = 4001;
    public static final int UDP_PORT_DDO = 4002;

    public static final int RMI_PORT_MTL = 3000;
    public static final int RMI_PORT_LVL = 3001;
    public static final int RMI_PORT_DDO = 3002;

    public static int getRegistryPort(String serverName) {
        if (serverName.equals("MTL"))
            return RMI_PORT_MTL;
        else if (serverName.equals("LVL"))
            return RMI_PORT_LVL;
        else
            return RMI_PORT_DDO;
    }

    public static int getUDPPort(String serverName) {

        if (serverName.equals("MTL"))
            return UDP_PORT_MTL;
        else if (serverName.equals("LVL"))
            return UDP_PORT_LVL;
        else
            return UDP_PORT_DDO;
    }


    public static String getIP(String serverName) {

        if (serverName.equals("MTL"))
            return MTL_SERVER_IP_ADDRESS;
        else if (serverName.equals("LVL"))
            return LVL_SERVER_IP_ADDRESS;
        else
            return DDO_SERVER_IP_ADDRESS;
    }


}
