package server;

import utilities.Constants;

import java.io.IOException;
import java.lang.reflect.Array;
import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static utilities.Constants.RMI_REGISTRY_PORT;

public class Server {
    static HashMap<String, CenterServer> servers;
    static CenterServer serverMTL, serverLVL, serverDDO;
    static CenterServerI skeletonMTL, skeletonLVL, skeletonDDO;


    public static void createUnicastObject() {
        try {
            skeletonMTL = (CenterServerI) UnicastRemoteObject.exportObject(serverMTL, Constants.RMI_PORT_MTL);
            skeletonLVL = (CenterServerI) UnicastRemoteObject.exportObject(serverLVL, Constants.RMI_PORT_LVL);
            skeletonDDO = (CenterServerI) UnicastRemoteObject.exportObject(serverDDO, Constants.RMI_PORT_DDO);


        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }


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

        serverMTL.printRecords();


        serverLVL.createTRecord("Donna", "Green", "Downtown Street Montreal", "1234567890", "French", "MTL");
        serverLVL.createTRecord("Red", "Forman", "Cote Street Laval", "1234561190", "Java", "LVL");


        String[] myCoursesLVL = {"COMP6411, SOEN6411"};
        serverLVL.createSRecord("Dhawani", "Hansoti", Arrays.asList(myCoursesLVL), "active", "12-01-2021");
        serverLVL.createSRecord("Payal", "Rohit", Arrays.asList(myCoursesLVL), "active", "15-01-2021");
        serverLVL.createSRecord("Sid", "Dave", Arrays.asList(myCoursesLVL), "inactive", "01-01-2021");
        serverLVL.printRecords();

        serverDDO.createTRecord("Sheldon", "Copper", "Hue Street Montreal", "1234567890", "French", "MTL");
        serverDDO.createTRecord("Raj", "Khotrapali", "Cote Street Laval", "1234561190", "Java", "LVL");
        serverDDO.createTRecord("Howard", "Wolowtiz", "Notre Doval", "1234567890", "Algo", "DDO");


        String[] myCoursesDDO = {"SOEN6411, COMP6231"};
        serverDDO.createSRecord("Raj", "Banker", Arrays.asList(myCoursesDDO), "active", "12-01-2021");
        serverDDO.createSRecord("Harshil", "Patel", Arrays.asList(myCoursesDDO), "active", "15-01-2021");
        serverDDO.createSRecord("Parth", "Navsariwala", Arrays.asList(myCoursesDDO), "inactive", "01-01-2021");
        serverDDO.printRecords();

    }


    public static void main(String[] args) throws IOException, AlreadyBoundException {
        initializeServers();
        //populateServers();
        Registry registry = LocateRegistry.createRegistry(RMI_REGISTRY_PORT);
        Registry registryMTL, registryLVL, registryDDO;
//        createUnicastObject();
        registryMTL = LocateRegistry.createRegistry(Constants.RMI_PORT_MTL);
        registryLVL = LocateRegistry.createRegistry(Constants.RMI_PORT_LVL);
        registryDDO = LocateRegistry.createRegistry(Constants.RMI_PORT_DDO);

        registryMTL.bind("MTL", serverMTL);
        registryLVL.bind("LVL", serverLVL);
        registryDDO.bind("DDO", serverDDO);

//        registry.bind("MTL",skeletonMTL);
//        registry.bind("LVL",skeletonLVL);
//        registry.bind("DDO",skeletonDDO);

        populateServers();


    }
}
