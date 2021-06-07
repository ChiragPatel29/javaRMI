package Server;

import Utilities.Configurations;

import java.io.IOException;
import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import java.util.Arrays;
import java.util.HashMap;


public class Server {
    static HashMap<String, CenterServer> servers;
    static CenterServer serverMTL, serverLVL, serverDDO;


    public static void initializeServers() throws IOException {
        servers = new HashMap<>();
        serverMTL = new CenterServer("MTL");
        serverLVL = new CenterServer("LVL");
        serverDDO = new CenterServer("DDO");
        servers.put("MTL", serverMTL);
        servers.put("LVL", serverLVL);
        servers.put("DDO", serverLVL);

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

        System.out.println("Servers populated with MTL: 8 LVL: 5 DDO: 6 Records respectively");
    }


    public static void main(String[] args) throws IOException, AlreadyBoundException {
        initializeServers();

        Registry registryMTL, registryLVL, registryDDO;

        registryMTL = LocateRegistry.createRegistry(Configurations.RMI_PORT_MTL);
        registryLVL = LocateRegistry.createRegistry(Configurations.RMI_PORT_LVL);
        registryDDO = LocateRegistry.createRegistry(Configurations.RMI_PORT_DDO);

        registryMTL.bind("MTL", serverMTL);
        registryLVL.bind("LVL", serverLVL);
        registryDDO.bind("DDO", serverDDO);

        populateServers();


    }
}
