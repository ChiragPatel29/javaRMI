package Server;

import Utilities.Configurations;
import Utilities.ServerLogger;

import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Arrays;
import java.util.HashMap;


public class Server {
    static HashMap<String, CenterServer> servers;
    static CenterServer serverMTL, serverLVL, serverDDO;
    static ServerLogger globalServer;

    public static void initializeServers() {
        globalServer = new ServerLogger("GlobalServer");
        servers = new HashMap<>();
        try {
            serverMTL = new CenterServer("MTL");
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            serverLVL = new CenterServer("LVL");
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            serverDDO = new CenterServer("DDO");
        } catch (IOException e) {
            e.printStackTrace();
        }
        servers.put("MTL", serverMTL);
        servers.put("LVL", serverLVL);
        servers.put("DDO", serverLVL);
        globalServer.addLog("MTL Server Started");
        globalServer.addLog("LVL Server Started");
        globalServer.addLog("DDO Server Started");

    }


    public static void populateServers() throws RemoteException {
        serverMTL.createTRecord("Alice", "Brown", "Hue Street Montreal", "1234567890", "French", "MTL");
        serverMTL.createTRecord("Bob", "Red", "Cote Street Laval", "1234561190", "Java", "LVL");
        serverMTL.createTRecord("George", "Forman", "Notre Doval", "1234567809", "Algo", "DDO");
        serverMTL.createTRecord("Steve", "Hyde", "Dam Montreal", "1234567880", "English", "MTL");


        String[] myCourses = {"COMP335, SOEN6411"};
        serverMTL.createSRecord("Chirag", "Patel", Arrays.asList(myCourses), "active", "12-01-2021");
        serverMTL.createSRecord("Harsh", "Patel", Arrays.asList(myCourses), "active", "15-01-2021");
        serverMTL.createSRecord("Parth", "Shah", Arrays.asList(myCourses), "inactive", "01-01-2021");
        serverMTL.createSRecord("Sanyam", "Narula", Arrays.asList(myCourses), "inactive", "20-10-2020");


        serverLVL.createTRecord("Donna", "Green", "Downtown Street Montreal", "1234567890", "French", "MTL");
        serverLVL.createTRecord("Red", "Forman", "Cote Street Laval", "1234561190", "Java", "LVL");


        String[] myCoursesLVL = {"COMP6411, SOEN6411"};
        serverLVL.createSRecord("Dhawani", "Hansoti", Arrays.asList(myCoursesLVL), "active", "12-01-2021");
        serverLVL.createSRecord("Payal", "Rohit", Arrays.asList(myCoursesLVL), "active", "15-01-2021");
        serverLVL.createSRecord("Sid", "Dave", Arrays.asList(myCoursesLVL), "inactive", "01-01-2021");


        serverDDO.createTRecord("Sheldon", "Copper", "Hue Street Montreal", "1234567890", "French", "MTL");
        serverDDO.createTRecord("Raj", "Khotrapali", "Cote Street Laval", "1234561190", "Java", "LVL");
        serverDDO.createTRecord("Howard", "Wolowtiz", "Notre Doval", "1234567890", "Algo", "DDO");


        String[] myCoursesDDO = {"SOEN6411, COMP6231"};
        serverDDO.createSRecord("Raj", "Banker", Arrays.asList(myCoursesDDO), "active", "12-01-2021");
        serverDDO.createSRecord("Harshil", "Patel", Arrays.asList(myCoursesDDO), "active", "15-01-2021");
        serverDDO.createSRecord("Parth", "Navsariwala", Arrays.asList(myCoursesDDO), "inactive", "01-01-2021");

        System.out.println("Servers populated with MTL: " + serverMTL.getCurrentServerCount() + " LVL: " + serverLVL.getCurrentServerCount() + " DDO: " + serverLVL.getCurrentServerCount() + " Records respectively");
        globalServer.addLog("Servers populated with MTL: " + serverMTL.getCurrentServerCount() + " LVL: " + serverLVL.getCurrentServerCount() + " DDO: " + serverLVL.getCurrentServerCount() + " Records respectively");
    }


    public static void main(String[] args) {
        initializeServers();

        Registry registryMTL, registryLVL, registryDDO;

        try {
            registryMTL = LocateRegistry.createRegistry(Configurations.RMI_PORT_MTL);
            globalServer.addLog("MTL Registry created at: " + Configurations.RMI_PORT_MTL);

            registryMTL.bind("MTL", serverMTL);
            globalServer.addLog("serverMTL binded with MTL for lookup");
            System.out.println("MTL Server Started on: " + Configurations.RMI_PORT_MTL);

        } catch (Exception e) {
            System.out.println("Port No: " + Configurations.RMI_PORT_MTL + " in use Can't Start Server:MTL");
        }
        try {
            registryLVL = LocateRegistry.createRegistry(Configurations.RMI_PORT_LVL);
            globalServer.addLog("LVL Registry created at: " + Configurations.RMI_PORT_LVL);

            registryLVL.bind("LVL", serverLVL);
            globalServer.addLog("serverLVL binded with LVL for lookup");
            System.out.println("LVL Server Started on: " + Configurations.RMI_PORT_LVL);
        } catch (Exception e) {
            System.out.println("Port No: " + Configurations.RMI_PORT_LVL + " in use Can't Start Server:LVL");

        }

        try {
            registryDDO = LocateRegistry.createRegistry(Configurations.RMI_PORT_DDO);
            globalServer.addLog("DDO Registry created at: " + Configurations.RMI_PORT_DDO);

            registryDDO.bind("DDO", serverDDO);
            globalServer.addLog("serverDDO binded with DDO for lookup");
            System.out.println("DDO Server Started on: " + Configurations.RMI_PORT_DDO);
            populateServers();
        } catch (Exception e) {
            System.out.println("Port No: " + Configurations.RMI_PORT_DDO + " in use Can't Start Server:DDO");
        }
    }
}
